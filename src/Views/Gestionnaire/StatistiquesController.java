package Views.Gestionnaire;

import alimentation.Categorie;
import alimentation.Facture;
import alimentation.Gestionstock;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

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
    private AreaChart<?, ?> evolution_chart;

    @FXML
    private JFXDatePicker evolution_start;

    @FXML
    private JFXDatePicker evolution_stop;

    @FXML
    private BarChart<?, ?> sales_chart;

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
    
    void setSales(){
        ObservableList<XYChart.Series<String,Number>> chartData;
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
                    item.getDateFac().getMonth() == (new Date()).getMonth()
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()&&
                    item.getDateStock().getMonth() == (new Date()).getMonth()        
                );
                sales_chart.setTitle("This Week"); 
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
                sales_chart.setTitle("This Month");
                break;
            case "Year":
                tempGain = factures.filtered(item ->
                    item.getDateFac().getYear() == (new Date()).getYear()
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()      
                );
                sales_chart.setTitle("This Year");
                break;
            case "Last Year":
                tempGain = factures.filtered(item ->
                    item.getDateFac().getYear() == (new Date()).getYear()-1
                );
                tempSpend = stocks.filtered(item->
                    item.getDateStock().getYear() == (new Date()).getYear()-1       
                );
                sales_chart.setTitle("Last Year");
                break;
        }
        //DataCreation
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
                item.getDateFac().getYear() == (new Date()).getYear()
        );
        sales_year.setText((this.totalRecieved(temp)).toString() + " FCFA"); 
        
        //This Month
        temp = factures.filtered(item ->
                item.getDateFac().getYear() == (new Date()).getYear() &&
                item.getDateFac().getMonth() == (new Date()).getMonth()
        );
        sales_month.setText((this.totalRecieved(temp)).toString() + " FCFA"); 
        
        //Today
        temp = factures.filtered(item ->
                item.getDateFac().getYear() == (new Date()).getYear()&&
                item.getDateFac().getMonth() == (new Date()).getMonth()&&
                item.getDateFac().getDay() == (new Date()).getDay()
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
    
    void setEvolution(){
        
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
}
