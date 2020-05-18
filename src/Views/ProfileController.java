/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import alimentation.Gestionnaire;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import alimentation.EntityClasses;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author jhy
 */
public class ProfileController implements Initializable {
    
    @FXML
    private JFXButton logiinMod;

    @FXML
    private JFXButton pwdMod;

    @FXML
    private Label id;

    @FXML
    private Label nom;

    @FXML
    private Label type;

    @FXML
    private Label login;

    @FXML
    private Label pwd;

    @FXML
    void Modpwd(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        dialog.getDialogPane().getStylesheets().add(this.getClass().getResource("../styles/DialogStyleSheet.css").toExternalForm());
        dialog.initStyle(StageStyle.UNDECORATED);
        StackPane content = new StackPane();
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50,50,50,50));
        content.setPrefWidth(500);
        VBox vbox = new VBox();
        vbox.setSpacing(25);
        Label label1 = new Label("Entrez le nouveau mot de passe");
        Label label2 = new Label("Ressaisir le mot de passe");
        PasswordField password1 = new PasswordField();
        PasswordField password2 = new PasswordField();
        vbox.getChildren().addAll(label1,password1,label2,password2);
        content.getChildren().add(vbox);
        ButtonType validate = new ButtonType("Valider",ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Anuller",ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(validate,cancel);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(new Callback<ButtonType,String>(){
            @Override
            public String call(ButtonType b) {
                if(b.getButtonData() == ButtonData.OK_DONE){
                    if(!"".equals(password1.getText()) && password2.getText().equals(password1.getText())){
                        return password1.getText();
                    }
                }
                return null;
            }
        });
        
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            LogInController.user.changePassword(result.get());
            set();
        }
        
    }

    @FXML
    void modLogin(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        dialog.getDialogPane().getStylesheets().add(this.getClass().getResource("../styles/DialogStyleSheet.css").toExternalForm());
        dialog.initStyle(StageStyle.UNDECORATED);
        StackPane content = new StackPane();
        content.setPrefSize(500,100);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(75,50,50,50));
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        Label label = new Label("Entrez le nouveau nom d'utilisatuer");
        label.setTextAlignment(TextAlignment.CENTER);
        TextField text =  new TextField();
        text.getStyleClass().add("text");
        text.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().add(label);
        vbox.getChildren().add(text);
        content.getChildren().add(vbox);
        
        ButtonType validate = new ButtonType("Valider",ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Anuller",ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(validate,cancel);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(new Callback<ButtonType,String>(){
            @Override
            public String call(ButtonType b) {
                if(b.getButtonData() == ButtonData.OK_DONE){
                    if(!"".equals(text.getText())){
                        return text.getText();
                    }
                }
                return null;
            }
        });
        
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            LogInController.user.changeUserName(result.get());
            set();
        }
        
    }
    
    void set(){
        Gestionnaire gest = (Gestionnaire) EntityClasses.get(Gestionnaire.class,LogInController.user.getId());
        id.setText(gest.getId().toString());
        nom.setText(gest.getNomGest());
        type.setText(gest.getType());
        login.setText(gest.getLogin());
        pwd.setText(gest.getPwd());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        set();
    }    
    
}
