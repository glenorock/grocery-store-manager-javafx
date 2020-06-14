package Views.caissiere;

import alimentation.Client;
import alimentation.Facture;
import alimentation.Gestionnaire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HistoriqueVentesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> filter;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton search_but;

    @FXML
    private TableView<Facture> table;

    @FXML
    private TableColumn<Facture, Date> date;

    @FXML
    private TableColumn<Facture, Integer> numero;

    @FXML
    private TableColumn<Facture, Double> montant;

    @FXML
    private TableColumn<Facture, Double> remise;

    @FXML
    private TableColumn<Facture, String> mode;

    @FXML
    private TableColumn<Facture, Client> client;

    @FXML
    private TableColumn<Facture, Gestionnaire> caissiere;

    
    
    @FXML
    private JFXButton details;
    
    @FXML
    private JFXButton refresh;
    
    
    ObservableList<Facture> data ;
    
    
    @FXML
    void details(ActionEvent event) throws IOException {
        Facture fact = table.getSelectionModel().getSelectedItem();
        if(fact == null) return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Facture_Lignes.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        Facture_LignesController controller = fxmlLoader.<Facture_LignesController>getController();
        controller.facture = fact;
        controller.init();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    @FXML
    void refresh(ActionEvent event) {
        setData();
    }
    
    @FXML
    void filter(ActionEvent event) {
        String key = filter.getValue();
        if (key ==  null) return;
        Date today = new java.util.Date();
        switch(key){
            case "Jour":
                table.setItems(data.filtered(item -> item.getDateFac().getYear() == today.getYear() &&
                        item.getDateFac().getMonth() == today.getMonth() &&
                        item.getDateFac().getDay() == today.getDay()
                ));
                break;
            case "Mois":
                table.setItems(data.filtered(item -> item.getDateFac().getYear() == today.getYear() &&
                        item.getDateFac().getMonth() == today.getMonth()
                ));
                break;
            case "Année":
                table.setItems(data.filtered(item -> item.getDateFac().getYear() == today.getYear() ));
                break;
            default:
                break;
        }
    }

    @FXML
    void search(ActionEvent event) {
        String key = searchBox.getText();
        String filt = filter.getValue();
        setData();
        filter.setValue(filt);
        filter(event);
        if(key == null || "".equals(key)) return;
        table.setItems(
            table.getItems().filtered(
                item -> item.getIdFac().toString().toLowerCase().contains(key.toLowerCase()) ||
                        item.getDateFac().toString().toLowerCase().contains(key.toLowerCase()) ||
                        item.getIdCaissiere().toString().toLowerCase().contains(key.toLowerCase()) ||
                        item.getClientidClient().toString().toLowerCase().contains(key.toLowerCase()) 
            )
        );      
    }
    
    void setData(){
        ObservableList<String> dataf =  FXCollections.observableArrayList();
        dataf.addAll(
                
                "Jour",
                "Mois",
                "Année"
        );
        filter.getItems().clear();
        filter.setItems(dataf);
        data = FXCollections.observableArrayList(Facture.getFactures());
        data.sort(Facture.sortByMostRecent); 
        table.setItems(data); 
        
    }
    
    void init(){
        date.setCellValueFactory(new PropertyValueFactory<>("dateFac"));
        numero.setCellValueFactory(new PropertyValueFactory<>("idFac"));
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        remise.setCellValueFactory(new PropertyValueFactory<>("remise"));
        client.setCellValueFactory(new PropertyValueFactory<>("clientidClient"));
        caissiere.setCellValueFactory(new PropertyValueFactory<>("idCaissiere"));
        Callback<TableColumn<Facture, String>,TableCell<Facture, String>> type = new Callback() {
            @Override
            public Object call(Object param) {
                return new TableCell<Facture, String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            Facture fact = getTableView().getItems().get(getIndex());
                            if(fact.getTypeFac()){
                                setText("Cash");
                            }else{
                                setText("Credit");
                            }
                        }
                    }
                };
            }
        };
        mode.setCellFactory(type);
        filter.setValue(null);
        searchBox.setText(null);
    }
    
    
    
    @FXML
    void initialize() {
        assert filter != null : "fx:id=\"filter\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert search_but != null : "fx:id=\"search_but\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert montant != null : "fx:id=\"montant\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert remise != null : "fx:id=\"remise\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert mode != null : "fx:id=\"mode\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert client != null : "fx:id=\"client\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert caissiere != null : "fx:id=\"caissiere\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        assert details != null : "fx:id=\"details\" was not injected: check your FXML file 'HistoriqueVentes.fxml'.";
        init();
        setData();
    }
}
