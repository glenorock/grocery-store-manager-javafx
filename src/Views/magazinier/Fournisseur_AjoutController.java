package Views.magazinier;

import alimentation.EntityClasses;
import alimentation.Fournisseur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Fournisseur_AjoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton anuller;

    @FXML
    void anuller(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void valider(ActionEvent event) {
        try{
            if(nom.getText() == null || "".equals(nom.getText())) throw new Exception("Nom ne peut etre null");
            EntityClasses.add(new Fournisseur(
                nom.getText(),
                adresse.getText()
            ));
            anuller(event);
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.toString());
                alert.setHeaderText(null);
                alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Founisseur_Ajout.fxml'.";
        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'Founisseur_Ajout.fxml'.";
        assert valider != null : "fx:id=\"valider\" was not injected: check your FXML file 'Founisseur_Ajout.fxml'.";
        assert anuller != null : "fx:id=\"anuller\" was not injected: check your FXML file 'Founisseur_Ajout.fxml'.";

    }
}
