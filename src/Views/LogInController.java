/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import alimentation.Gestionnaire;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LogInController {
    public static Gestionnaire user = null;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton logInButton;

    @FXML
    void logIn(ActionEvent event) throws IOException {
            try{
                user = Gestionnaire.getGestionnaireByUserName(userNameField.getText());
            }catch(Exception e){
                System.out.println("userName not found");
            }
            if (user == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("User Name not found");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else if(!user.isActif()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inactive Account");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else if (!user.checkPassword(passwordField.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect Password");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else{
                System.out.println("Logged In");
                if("Gestionnaire".equals(user.getTypeGest())){
                    load("gestionnaire/MainScene.fxml",event);
                }else if("Caissiere".equals(user.getTypeGest())){
                    try{
                        load("caissiere/MainScene.fxml",event);
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }else if("Magazinier".equals(user.getTypeGest())){
                    try{
                        load("magazinier/MainScene.fxml",event);
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
    }
    
    private void load(String url, Event event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setResizable(true);
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    void initialize() {
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert logInButton != null : "fx:id=\"logInButton\" was not injected: check your FXML file 'LogIn.fxml'.";
        LogInController.user = null;
    }
}
