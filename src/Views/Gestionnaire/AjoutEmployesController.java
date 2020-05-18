/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Gestionnaire;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import alimentation.Gestionnaire;
import alimentation.EntityClasses;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jhy
 */
public class AjoutEmployesController implements Initializable {
    
    @FXML
    private JFXTextField nomGest;

    @FXML
    private JFXComboBox<String> typeGest;

    @FXML
    private JFXTextField loginGest;

    @FXML
    private JFXTextField pwdGest;

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
            EntityClasses.add(new Gestionnaire(
                nomGest.getText(),
                loginGest.getText(),
                pwdGest.getText(),
                typeGest.getValue()
            ));
            anuller(event);
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.toString());
                alert.setHeaderText(null);
                alert.showAndWait();
        }
    }
    
    private void set(){
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(
                "Gestionnaire",
                "Caissiere",
                "Magazinier"
        );
        typeGest.setItems(data);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        set();
    }    
    
}
