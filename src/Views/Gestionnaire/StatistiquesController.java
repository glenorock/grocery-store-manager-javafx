package Views.Gestionnaire;

import alimentation.Categorie;
import alimentation.Facture;
import alimentation.Gestionstock;
import alimentation.Produit;
import alimentation.Transaction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class StatistiquesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label sales_today;

    @FXML
    private Label sales_month;

    @FXML
    private Label sales_year;

    @FXML
    private JFXButton refresh;

    @FXML
    private AreaChart<String,Number> evolution_chart;

    @FXML
    private JFXDatePicker evolution_start;

    @FXML
    private JFXDatePicker evolution_stop;
    
    @FXML
    private StackPane bar;
    
    private BarChart<String,Number> sales_chart;

    @FXML
    private JFXComboBox<String> sales_period;

    @FXML
    private PieChart participation_pie;

    @FXML
    private JFXListView<String> top_selling_list;

    @FXML
    private JFXListView<String> top_profit_list;
    
    ObservableList<Produit> products;
    
    ObservableList<Categorie> categories;
    
    @FXML
    void refresh(ActionEvent event) {
        init();
    }

    @FXML
    void setEvolution(ActionEvent event) {
        setEvolution();
    }
    
    void setEvolution(){
        Date start = convertToDateViaInstant(this.evolution_start.getValue());
        Date stop = convertToDateViaInstant(this.evolution_stop.getValue());
        long tmp = stop.getTime() + (1000*60*60*24);
        stop.setTime(tmp);
        ObservableList<Transaction> trans = FXCollections.observableArrayList(Gestionstock.getTransactions());
        trans.addAll(Facture.getFactures());
        
        trans.sort(Transaction.sortByDate); 
        
        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> sales = new XYChart.Series<>();
        sales.setName("Net Profit"); 
        chartData.addAll(sales);
        
        double amount = getAmount(trans,start);
        for(Transaction tran:trans.filtered(item -> item.getDate().after(start) && item.getDate().before(stop)  )){ 
            amount += tran.trans();
            sales.getData().add(new XYChart.Data<>(tran.getDate().toString(),amount) );
        }
        
        this.evolution_chart.setData(chartData);
        this.evolution_chart.getXAxis().setTickLabelsVisible(false);
        this.evolution_chart.getXAxis().setOpacity(0);
    }
    
    private double getAmount(ObservableList<Transaction> trans,Date date ){
        double amount = 0;
        for(Transaction tran:trans.filtered(item -> item.getDate().before(date))){
            amount += tran.trans();
        }
        return amount;
    }
    
    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }
    
    void setData(){
        evolution_start.setValue(LocalDate.ofYearDay(LocalDate.now().getYear(), 1));
        evolution_stop.setValue(LocalDate.now());
        ObservableList<String> choice = FXCollections.observableArrayList(
                "Week",
                "Month",
                "Year",
                "Last Year"
        );
        sales_period.setItems(choice);
        sales_period.setValue("Week");
        
    }

    @FXML
    void setSales(ActionEvent event) {
        setSales();
    }
    
    private Double totalRecieved(ObservableList<Facture> list){
        double total = 0;
        for(Facture item: list){
            total += item.received();
        }
        return total;
    }
    
    void setHeader(){
        ObservableList<Facture> factures = FXCollections.observableArrayList(Facture.getFactures());
        ObservableList<Facture> temp;
        
        //This year
        temp = factures.filtered(item ->
                getYear(item.getDateFac()) == getYear(new Date())
        );
        sales_year.setText((this.totalRecieved(temp)).toString() + " FCFA"); 
        
        //This Month
        temp = factures.filtered(item ->
                getYear(item.getDateFac()) == getYear(new Date()) &&
                this.getMonthOfTheYear(item.getDate()) == this.getMonthOfTheYear(new Date()) 
        );
        sales_month.setText((this.totalRecieved(temp)).toString() + " FCFA"); 
        
        //Today
        temp = factures.filtered(item ->
                getYear(item.getDateFac()) == getYear(new Date())&&
                this.getDayOfTheYear(item.getDate()) == this.getDayOfTheYear(new Date()) 
        );
        sales_today.setText((this.totalRecieved(temp)).toString() + " FCFA"); 
        
        
        
    }
    
    void setTop(){
        products = FXCollections.observableArrayList(Produit.getProduits());
        
        //setting top sales
        products.sort(Produit.sortBySales); 
        ObservableList<String> data1 = FXCollections.observableArrayList();
        for(int i = 0;i<3;i++){
            data1.add("N°" + (i+1) + ":     " + products.get(i).toString() );
        }
        top_selling_list.setItems(data1);
        
        
        
        //setting top profit
        ObservableList<String> data2 = FXCollections.observableArrayList();
        products.sort(Produit.sortByProfitMade); 
        for(int i = 0;i<3;i++){
            data2.add("N°" + (i+1) + ":     " + products.get(i).toString() );
        }
        top_profit_list.setItems(data2);
        
    }
    
    void setParticipation(){
        categories = FXCollections.observableArrayList(Categorie.getCategories());
        ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
        for(Categorie cat: categories){
            pieChartData.add(new PieChart.Data(cat.getNomCat(),Double.valueOf(cat.contribution()).intValue()));
        }
        participation_pie.setData(pieChartData);        
    }
    
    void init(){
        setData();
        setParticipation();
        setEvolution();
        setTop();
        setHeader();
        setSales();
    }

    @FXML
    void initialize() {
        assert sales_today != null : "fx:id=\"sales_today\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert sales_month != null : "fx:id=\"sales_month\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert sales_year != null : "fx:id=\"sales_year\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert refresh != null : "fx:id=\"refresh\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert evolution_chart != null : "fx:id=\"evolution_chart\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert evolution_start != null : "fx:id=\"evolution_start\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert evolution_stop != null : "fx:id=\"evolution_stop\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert sales_chart != null : "fx:id=\"sales_chart\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert sales_period != null : "fx:id=\"sales_period\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert participation_pie != null : "fx:id=\"participation_pie\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert top_selling_list != null : "fx:id=\"top_selling_list\" was not injected: check your FXML file 'Statistiques.fxml'.";
        assert top_profit_list != null : "fx:id=\"top_profit_list\" was not injected: check your FXML file 'Statistiques.fxml'.";
        init();
    }
    
    void setSales(){
        ObservableList<Facture> factures = FXCollections.observableArrayList(Facture.getFactures());
        ObservableList<Gestionstock> stocks = FXCollections.observableArrayList(Gestionstock.getTransactions());
         
        ObservableList<Facture> tempGain = null;
        ObservableList<Gestionstock> tempSpend = null;
        
        String key = sales_period.getValue();
        if(key == null || "".equals(key)) return;
        switch(key){
            case "Week":
                tempGain = factures.filtered(item ->
                    item.getDateFac().getYear() == (new Date()).getYear()&&
                    item.getDateFac().getMonth() == (new Date()).getMonth() &&
                    this.getWeekofTheYear(item.getDate()) == this.getWeekofTheYear(new Date())
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()&&
                    item.getDateStock().getMonth() == (new Date()).getMonth() &&
                    this.getWeekofTheYear(item.getDate()) == this.getWeekofTheYear(new Date())
                );
                setWeek(tempGain,tempSpend);
                break;
            case "Month":
                tempGain = factures.filtered(item ->
                    item.getDateFac().getYear() == (new Date()).getYear()&&
                    item.getDateFac().getMonth() == (new Date()).getMonth()
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()&&
                    item.getDateStock().getMonth() == (new Date()).getMonth()        
                );
                setMonth(tempGain,tempSpend);
                break;
            case "Year":
                tempGain = factures.filtered(item ->
                    item.getDateFac().getYear() == (new Date()).getYear()
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()      
                );
                setYear(tempGain,tempSpend);
                break;
            case "Last Year":
                setLastYear(factures.filtered(
                        item ->item.getDateFac().getYear() == (new Date()).getYear()),
                        factures.filtered(item ->item.getDateFac().getYear() == (new Date()).getYear()-1
                ));
                break;
        }
        
    }
    
    void setWeek(ObservableList<Facture> factures,ObservableList<Gestionstock> stocks ){
        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> sales = new XYChart.Series<>();
        sales.setName("Sales"); 
        XYChart.Series<String, Number> expenses = new XYChart.Series<>();
        expenses.setName("Expenses"); 
        chartData.addAll(sales,expenses);
        sales.getData().addAll(
            new XYChart.Data<>("Sunday", getSoldOnDay(factures, 0)),
            new XYChart.Data<>("Monday",getSoldOnDay(factures, 1)),
            new XYChart.Data<>("Tueday",getSoldOnDay(factures, 2)),
            new XYChart.Data<>("Wednesday",getSoldOnDay(factures, 3)),
            new XYChart.Data<>("Thursday",getSoldOnDay(factures, 4)),
            new XYChart.Data<>("Friday",getSoldOnDay(factures, 5)),
            new XYChart.Data<>("Satuday",getSoldOnDay(factures, 6))
        );
        expenses.getData().addAll(
            new XYChart.Data<>("Sunday", getSpendOnDay(stocks, 0)),
            new XYChart.Data<>("Monday", getSpendOnDay(stocks, 1)),
            new XYChart.Data<>("Tueday", getSpendOnDay(stocks, 2)),
            new XYChart.Data<>("Wednesday", getSpendOnDay(stocks, 3)),
            new XYChart.Data<>("Thursday", getSpendOnDay(stocks, 4)),
            new XYChart.Data<>("Friday", getSpendOnDay(stocks, 5)),
            new XYChart.Data<>("Satuday", getSpendOnDay(stocks, 6))
        );
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Days of the week");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Sold");
        sales_chart = new BarChart<>(xAxis, yAxis);
        this.bar.getChildren().clear();
        this.bar.getChildren().add(sales_chart);
        sales_chart.setData(chartData); 
        sales_chart.setTitle("This Week");
        
    }
    
    void setMonth(ObservableList<Facture> factures,ObservableList<Gestionstock> stocks ){
        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> sales = new XYChart.Series<>();
        sales.setName("Sales"); 
        XYChart.Series<String, Number> expenses = new XYChart.Series<>();
        expenses.setName("Expenses"); 
        chartData.addAll(sales,expenses);
        for(int i=1;i<=getDayOfTheMonth(new Date());i++){
            sales.getData().add(new XYChart.Data<>("" + i, getSaleOnDayOfMonth(factures, i)) );
        }
        for(int i=1;i<=getDayOfTheMonth(new Date());i++){
            expenses.getData().add(new XYChart.Data<>("" + i, getSpendOnDayOfMonth(stocks, i)) );
        }
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Days of the week");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Sold");
        sales_chart = new BarChart<>(xAxis, yAxis);
        this.bar.getChildren().clear();
        this.bar.getChildren().add(sales_chart);
        sales_chart.setData(chartData); 
        sales_chart.setTitle("This Month");
        
    }
    
    void setYear(ObservableList<Facture> factures,ObservableList<Gestionstock> stocks ){
        sales_chart.setTitle("This Year");
        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> sales = new XYChart.Series<>();
        sales.setName("Sales"); 
        XYChart.Series<String, Number> expenses = new XYChart.Series<>();
        expenses.setName("Expenses"); 
        chartData.addAll(sales,expenses);
        
        sales.getData().addAll(
            new XYChart.Data<>("January", getSoldOnMonth(factures, 0)),
            new XYChart.Data<>("February", getSoldOnMonth(factures, 1)),
            new XYChart.Data<>("March", getSoldOnMonth(factures, 2)),
            new XYChart.Data<>("April", getSoldOnMonth(factures, 3)),
            new XYChart.Data<>("May", getSoldOnMonth(factures, 4)),
            new XYChart.Data<>("June", getSoldOnMonth(factures, 5)),
            new XYChart.Data<>("July", getSoldOnMonth(factures, 6)),
            new XYChart.Data<>("August", getSoldOnMonth(factures,7)),
            new XYChart.Data<>("September", getSoldOnMonth(factures, 8)),
            new XYChart.Data<>("October",getSoldOnMonth(factures, 9)),
            new XYChart.Data<>("November", getSoldOnMonth(factures, 10)),
            new XYChart.Data<>("December", getSoldOnMonth(factures, 11))           
        );
        expenses.getData().addAll(
            new XYChart.Data<>("January", getSpendOnMonth(stocks,0)),
            new XYChart.Data<>("February", getSpendOnMonth(stocks,1)),
            new XYChart.Data<>("March",getSpendOnMonth(stocks,2)),
            new XYChart.Data<>("April",getSpendOnMonth(stocks,3)),
            new XYChart.Data<>("May", getSpendOnMonth(stocks,4)),
            new XYChart.Data<>("June", getSpendOnMonth(stocks,5)),
            new XYChart.Data<>("July", getSpendOnMonth(stocks,6)),
            new XYChart.Data<>("August", getSpendOnMonth(stocks,7)),
            new XYChart.Data<>("September", getSpendOnMonth(stocks,8)),
            new XYChart.Data<>("October", getSpendOnMonth(stocks,9)),
            new XYChart.Data<>("November", getSpendOnMonth(stocks,10)),
            new XYChart.Data<>("December", getSpendOnMonth(stocks, 11)) 
        );
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Months of the year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Sold");
        sales_chart = new BarChart<>(xAxis, yAxis);
        this.bar.getChildren().clear();
        this.bar.getChildren().add(sales_chart);
        sales_chart.setData(chartData); 
        sales_chart.setTitle("This Year"); 
        
    }
    
    void setLastYear(ObservableList<Facture> thisYear,ObservableList<Facture> lastYear ){
        ObservableList<XYChart.Series<String,Number>> chartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> thisYearData = new XYChart.Series<>();
        thisYearData.setName("Sales This Year"); 
        XYChart.Series<String, Number> lastYearData = new XYChart.Series<>();
        lastYearData.setName("Sales Last Year"); 
        chartData.addAll(thisYearData,lastYearData);
        
        thisYearData.getData().addAll(
            new XYChart.Data<>("January", getSoldOnMonth(thisYear, 0)),
            new XYChart.Data<>("February", getSoldOnMonth(thisYear, 1)),
            new XYChart.Data<>("March", getSoldOnMonth(thisYear, 2)),
            new XYChart.Data<>("April", getSoldOnMonth(thisYear, 3)),
            new XYChart.Data<>("May", getSoldOnMonth(thisYear, 4)),
            new XYChart.Data<>("June", getSoldOnMonth(thisYear, 5)),
            new XYChart.Data<>("July", getSoldOnMonth(thisYear, 6)),
            new XYChart.Data<>("August", getSoldOnMonth(thisYear,7)),
            new XYChart.Data<>("September", getSoldOnMonth(thisYear, 8)),
            new XYChart.Data<>("October",getSoldOnMonth(thisYear, 9)),
            new XYChart.Data<>("November", getSoldOnMonth(thisYear, 10)),
            new XYChart.Data<>("December", getSoldOnMonth(thisYear, 11))           
        );
        
        lastYearData.getData().addAll(
            new XYChart.Data<>("January", getSoldOnMonth(lastYear, 0)),
            new XYChart.Data<>("February", getSoldOnMonth(lastYear, 1)),
            new XYChart.Data<>("March", getSoldOnMonth(lastYear, 2)),
            new XYChart.Data<>("April", getSoldOnMonth(lastYear, 3)),
            new XYChart.Data<>("May", getSoldOnMonth(lastYear, 4)),
            new XYChart.Data<>("June", getSoldOnMonth(lastYear, 5)),
            new XYChart.Data<>("July", getSoldOnMonth(lastYear, 6)),
            new XYChart.Data<>("August", getSoldOnMonth(lastYear,7)),
            new XYChart.Data<>("September", getSoldOnMonth(lastYear, 8)),
            new XYChart.Data<>("October",getSoldOnMonth(lastYear, 9)),
            new XYChart.Data<>("November", getSoldOnMonth(lastYear, 10)),
            new XYChart.Data<>("December", getSoldOnMonth(lastYear, 11))           
        );
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Months of the year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Sold");
        sales_chart = new BarChart<>(xAxis, yAxis);
        this.bar.getChildren().clear();
        this.bar.getChildren().add(sales_chart);
        sales_chart.setData(chartData); 
        sales_chart.setTitle("This Year VS Last Year"); 
    }
    
    
    private double getSpendOnDay(ObservableList<Gestionstock> list,int day){
        ObservableList<Gestionstock> temp = list.filtered(item -> item.getDateStock().getDay() == day);
        double amount = 0;
        for(Gestionstock stock: temp){
            amount += (stock.getQte() * stock.getCodePro().getPrixAchat());
        }
        return amount;
    }
    
    private double getSpendOnMonth(ObservableList<Gestionstock> list,int month){
        ObservableList<Gestionstock> temp = list.filtered(item -> item.getDateStock().getMonth() == month);
        double amount = 0;
        for(Gestionstock stock: temp){
            amount += (stock.getQte() * stock.getCodePro().getPrixAchat());
        }
        return amount;
    }
    
    private double getSoldOnDay(ObservableList<Facture> list,int day){
        ObservableList<Facture> temp = list.filtered(item -> item.getDateFac().getDay() == day);
        double amount = 0;
        for(Facture facture: temp){
            amount += facture.getTotal();
        }
        return amount;
    }
    
    private double getSoldOnMonth(ObservableList<Facture> list,int month ){
        ObservableList<Facture> temp = list.filtered(item -> item.getDateFac().getMonth() == month);
        double amount = 0;
        for(Facture facture: temp){
            amount += facture.getTotal();
        }
        return amount;
    }
    
    private int getDayOfTheMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH); 
    }
    
    private int getDayOfTheYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR); 
    }
    
    private int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR); 
    }
    
    
    
    private int getMonthOfTheYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH); 
    }
    
    private int getWeekofTheYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    private double getSpendOnDayOfMonth(ObservableList<Gestionstock> list,int day){
        ObservableList<Gestionstock> temp = list.filtered(item -> getDayOfTheMonth(item.getDateStock()) == day);
        double amount = 0;
        for(Gestionstock stock: temp){
            amount += (stock.getQte() * stock.getCodePro().getPrixAchat());
        }
        return amount;
    }
    
    private double getSaleOnDayOfMonth(ObservableList<Facture> list,int day){
        ObservableList<Facture> temp = list.filtered(item -> getDayOfTheMonth(item.getDateFac()) == day);
        double amount = 0;
        for(Facture facture: temp){
            amount += facture.getTotal();
        }
        return amount;
    }
    
    
    
}
