package Views.caissiere;

import alimentation.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField search;
    
    @FXML
    private JFXButton butSearch;
    
    @FXML
    private JFXComboBox<String> using;

    @FXML
    private JFXButton annuler;

    @FXML
    private Label ajout;

    @FXML
    private TableView<Client> table;


    @FXML
    private TableColumn<Client, String> nom;

    @FXML
    private TableColumn<Client, String> tel;

    @FXML
    private TableColumn<Client, String> adresse;
    
    private ObservableList<Client> data ;
    

    @FXML
    void ajout(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Clients_Ajout.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        setData();
    }

    @FXML
    void annuler(ActionEvent event) {
        table.setItems(data);
    }

    @FXML
    void search(ActionEvent event) {
        table.setItems(data);
        String cond = using.getValue();
        String key = search.getText();
        table.getSelectionModel().select(table.getItems().size());
        if(key == null) return;
        switch(cond){
            case "Nom":
                table.setItems(data.filtered(item -> item.getNom().toLowerCase().contains(key.toLowerCase())));
                break;
            case "Adresse":
                table.setItems(data.filtered(item -> (item.getAdresse().toLowerCase().contains(key.toLowerCase()))));
                break;
            case "Tel":
                table.setItems(data.filtered(item -> (item.getTel().toLowerCase().contains(key.toLowerCase()))));
                break;
            default:
                table.setItems(data.filtered(item -> (item.getAdresse().toLowerCase().contains(key.toLowerCase())) || 
                        item.getNom().toLowerCase().contains(key.toLowerCase()) || 
                        item.getTel().toLowerCase().contains(key.toLowerCase())));
                break;
        }
    }
    
    void setColumns(){
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));      
    }
    
     private void setData(){
        table.getItems().clear();
        data =  FXCollections.observableList(Client.getClients());
        table.setItems(data);
        ObservableList<String> sdata = FXCollections.observableArrayList();
        sdata.addAll(
                "Tous",
                "Nom",
                "Tel",
                "Adresse"
        );
        this.using.getItems().clear();
        using.setItems(sdata);
        using.setValue("Tous");
        search.setText(null);
    }
    
    @FXML
    void initialize() {
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'Clients.fxml'.";
        assert using != null : "fx:id=\"using\" was not injected: check your FXML file 'Clients.fxml'.";
        assert annuler != null : "fx:id=\"annuler\" was not injected: check your FXML file 'Clients.fxml'.";
        assert ajout != null : "fx:id=\"ajout\" was not injected: check your FXML file 'Clients.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Clients.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Clients.fxml'.";
        assert tel != null : "fx:id=\"tel\" was not injected: check your FXML file 'Clients.fxml'.";
        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'Clients.fxml'.";
        setColumns();
        setData();
    }
}
