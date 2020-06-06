package Views.Gestionnaire;

import alimentation.Categorie;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
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
    private JFXListView<Produit> top_selling_list;

    @FXML
    private JFXListView<Produit> top_profit_list;
    
    ObservableList<Produit> products;
    
    ObservableList<Categorie> categories;
    
    @FXML
    void refresh(ActionEvent event) {
        init();
    }

    @FXML
    void setEvolution(ActionEvent event) {

    }

    @FXML
    void setSales(ActionEvent event) {
        setSales();
    }
    
    void setSales(){
        
    }
    
    void setHeader(){
        
    }
    
    void setTop(){
        
    }
    
    void setEvolution(){
        
    }
    
    void setParticipation(){
        
    }
    
    void setData(){
        
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
