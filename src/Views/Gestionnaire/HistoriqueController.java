package Views.Gestionnaire;

import alimentation.Gestionnaire;
import alimentation.Gestionstock;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class HistoriqueController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private JFXComboBox<String> filter;

    @FXML
    private TableView<Gestionstock> table;

    @FXML
    private TableColumn<Gestionstock, Date> date;

    @FXML
    private TableColumn<Gestionstock, Integer> code;
    
    @FXML
    private TableColumn<Gestionstock, Integer> qte;
    
    @FXML
    private TableColumn<Gestionstock, String> nom;

    @FXML
    private TableColumn<Gestionstock, String> operation;

    @FXML
    private TableColumn<Gestionstock, Gestionnaire> gest;

    @FXML
    private JFXButton refresh;
    
    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton search_but;


    ObservableList<Gestionstock> data ;
    @FXML
    void filter(ActionEvent event) {
        String key = filter.getValue();
        if (key ==  null) return;
        Date today = new Date();
        switch(key){
            case "Jour":
                table.setItems(data.filtered(item -> item.getDateStock().getYear() == today.getYear() &&
                        item.getDateStock().getMonth() == today.getMonth() &&
                        item.getDateStock().getDay() == today.getDay()
                ));
                break;
            case "Mois":
                table.setItems(data.filtered(item -> item.getDateStock().getYear() == today.getYear() &&
                        item.getDateStock().getMonth() == today.getMonth()
                ));
                break;
            case "Année":
                table.setItems(data.filtered(item -> item.getDateStock().getYear() == today.getYear() ));
                break;
            default:
                break;
        }
        
    }
    
    @FXML
    void search(ActionEvent event) {
        String key = searchBox.getText();
        set();
        filter(event);
        ObservableList<Gestionstock> dat = table.getItems();
        if(key == null || "".equalsIgnoreCase(key)) return;
        table.setItems(dat.filtered(item -> item.getCodePro().getCodePro().toString().toLowerCase().contains(key.toLowerCase()) ||
                        item.getCodePro().getNomPro().toLowerCase().contains(key.toLowerCase()) ||
                        item.getIdGest().getNomGest().toLowerCase().contains(key.toLowerCase()) 
                    )); 
    }

    @FXML
    void refresh(ActionEvent event) {
        init();
        set();
    }

    
    
    void set(){
        ObservableList<String> dataf = FXCollections.observableArrayList();
        dataf.addAll(
                
                "Jour",
                "Mois",
                "Année"
        );
        filter.getItems().clear();
        filter.setItems(dataf);
        data = FXCollections.observableArrayList(Gestionstock.getTransactions());
        data.sort(Gestionstock.sortByMostRecent);
        table.setItems(data);
        
        
    }
    
    void init(){
        date.setCellValueFactory(new PropertyValueFactory<>("dateStock"));
        nom.setCellValueFactory(new PropertyValueFactory<>("codePro"));
        gest.setCellValueFactory(new PropertyValueFactory<>("idGest")); 
        qte.setCellValueFactory(new PropertyValueFactory<>("qte")); 
        
        Callback<TableColumn<Gestionstock,Integer>,TableCell<Gestionstock,Integer>> codeCell = new Callback() {
            @Override
            public Object call(Object param) {
                return new TableCell<Gestionstock,Integer>(){
                    @Override
                    public void updateItem(Integer item,boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            Gestionstock gest = getTableView().getItems().get(getIndex());
                            setText(gest.getCodePro().getId().toString());
                        }
                    }
                    
                };
            }
        };
        code.setCellFactory(codeCell);
        Callback<TableColumn<Gestionstock,String>,TableCell<Gestionstock,String>> opCell = new Callback() {
            @Override
            public Object call(Object param) {
                return new TableCell<Gestionstock,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            Gestionstock gest = getTableView().getItems().get(getIndex());
                            if(gest.getOperation()){
                                setText("Ajout");
                            }else{
                                setText("Retrait");
                            }
                        }
                    }
                    
                };
            }
        };
        operation.setCellFactory(opCell);
        filter.setValue(null);
        searchBox.setText(null); 
    }

    @FXML
    void initialize() {
        assert filter != null : "fx:id=\"filter\" was not injected: check your FXML file 'Historique.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Historique.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'Historique.fxml'.";
        assert code != null : "fx:id=\"code\" was not injected: check your FXML file 'Historique.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Historique.fxml'.";
        assert operation != null : "fx:id=\"operation\" was not injected: check your FXML file 'Historique.fxml'.";
        assert gest != null : "fx:id=\"gest\" was not injected: check your FXML file 'Historique.fxml'.";
        assert refresh != null : "fx:id=\"refresh\" was not injected: check your FXML file 'Historique.fxml'.";
        set();
        init();
    }
}
