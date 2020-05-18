package Views.magazinier;

import Views.LogInController;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MainSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label logOut;

    @FXML
    private JFXButton stock;

    @FXML
    private JFXButton fournisseur;

    @FXML
    private JFXButton categorie;

    @FXML
    private JFXButton produit;
    
    @FXML
    private JFXButton historiques;

    
    @FXML
    private Label task;
    
    @FXML
    private StackPane mainPane;

    @FXML
    void ShowProfile(MouseEvent event) throws IOException {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Profile");
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.DECORATED);
        dialog.getDialogPane().getStylesheets().add(this.getClass().getResource("../../styles/DialogStyleSheet.css").toExternalForm());
        StackPane pane = FXMLLoader.load(getClass().getResource("../Profile.fxml"));
        dialog.getDialogPane().setContent(pane);
        ButtonType cancel = new ButtonType("OK",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        dialog.showAndWait();
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        dialog.getDialogPane().getStylesheets().add(this.getClass().getResource("../../styles/DialogStyleSheet.css").toExternalForm());
        dialog.initStyle(StageStyle.UNDECORATED);
        StackPane content = new StackPane();
        content.setAlignment(Pos.CENTER);
        content.setPrefWidth(500);
        Label label = new Label("Deconnexion");
        label.setFont(Font.font(30));
        content.getChildren().add(label);
        ButtonType validate = new ButtonType("Oui",ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Non",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(validate,cancel);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(new Callback<ButtonType,Boolean>(){
            @Override
            public Boolean call(ButtonType b) {
                if(b.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                        return true;
                }
                return false;
            }
        });
        
        Optional<Boolean> result = dialog.showAndWait();
        if(result.isPresent()){
            if(result.get()){
                Parent parent = FXMLLoader.load(getClass().getResource("../LogIn.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setResizable(true);
                window.setScene(scene);
                window.show();
            }
        }
    }

    @FXML
    void show_categorie(ActionEvent event) {
        task.setText("Categorie");
        goTo("Categorie.fxml",event);
    }

    @FXML
    void show_fournisseur(ActionEvent event) {
        goTo("Fournisseur.fxml",event);
        task.setText("Fournisseur");
    }
    
    @FXML
    void show_produit(ActionEvent event) {
        task.setText("Produits");
        goTo("Produit.fxml",event);
    }

    @FXML
    void show_stocks(ActionEvent event) {
        task.setText("Stocks");
        goTo("Stock.fxml",event);
    }
    
    @FXML
    void show_historiques(ActionEvent event) {
        task.setText("Historiques");
        goTo("..\\Gestionnaire\\Historique.fxml",event);
    }
    
    private void goTo(String source,ActionEvent event){
        try{
            Pane grid = FXMLLoader.load(getClass().getResource(source));
            mainPane.getChildren().clear();
            mainPane.getChildren().add(grid);
            grid.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.9));
            grid.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.9));
            
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert userNameLabel != null : "fx:id=\"userNameLabel\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert logOut != null : "fx:id=\"logOut\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert stock != null : "fx:id=\"stock\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert fournisseur != null : "fx:id=\"fournisseur\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert categorie != null : "fx:id=\"categorie\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert produit != null : "fx:id=\"produit\" was not injected: check your FXML file 'MainScene.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MainScene.fxml'.";
        this.userNameLabel.setText(LogInController.user.getNomGest());
        show_stocks(new ActionEvent());
        show_produit(new ActionEvent());
    }
}
