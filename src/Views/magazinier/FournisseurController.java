package Views.magazinier;

import Views.Gestionnaire.AjoutEmployesController;
import alimentation.Fournisseur;
import alimentation.Gestionnaire;
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

public class FournisseurController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField search;

    @FXML
    private JFXComboBox<String> using;

    @FXML
    private JFXButton butSearch;

    @FXML
    private JFXButton annuler;

    @FXML
    private Label ajout;

    @FXML
    private TableView<Fournisseur> table;

    
    @FXML
    private TableColumn<Fournisseur, Integer> id;
    
    @FXML
    private TableColumn<Fournisseur, String> nom;
    
    @FXML
    private TableColumn<Fournisseur, String> adresse;
    
    private ObservableList<Fournisseur> data ;
    

    @FXML
    void ajout(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Fournisseur_Ajout.fxml"));
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
                table.setItems(data.filtered(item -> item.getNom().contains(key)));
                break;
            case "Adresse":
                table.setItems(data.filtered(item -> (item.getAdresse().contains(key))));
                break;
            default:
                table.setItems(data.filtered(item -> (item.getAdresse().contains(key)) || item.getNom().contains(key)));
                break;
        }
    }
    
    void setColumns(){
        id.setCellValueFactory(new PropertyValueFactory<>("codeFour"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));      
    }
    
     private void setData(){
        table.getItems().clear();
        data =  FXCollections.observableList(Fournisseur.getFournisseurs());
        table.setItems(data);
        ObservableList<String> sdata = FXCollections.observableArrayList();
        sdata.addAll(
                "Tous",
                "Nom",
                "Adresse"
        );
        this.using.getItems().clear();
        using.setItems(sdata);
        using.setValue("Tous");
    }

    @FXML
    void initialize() {
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert using != null : "fx:id=\"using\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert butSearch != null : "fx:id=\"butSearch\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert annuler != null : "fx:id=\"annuler\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert ajout != null : "fx:id=\"ajout\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'Fournisseur.fxml'.";
        setColumns();
        setData();
    }
}
