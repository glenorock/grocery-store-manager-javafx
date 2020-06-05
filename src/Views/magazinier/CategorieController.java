package Views.magazinier;

import alimentation.Categorie;
import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CategorieController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchBox;

    @FXML
    private Button search_but;

    @FXML
    private JFXButton add;

    @FXML
    private FlowPane displayArea;
    
    
    ObservableList<Categorie> data;
    
    @FXML
    void add(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Categorie_Ajout.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        Categorie_AjoutController controller = fxmlLoader.<Categorie_AjoutController>getController();
        controller.add.setOnAction(new EventHandler() {
            @Override
            public void handle(Event evt) {
                controller.add();
                try {
                    set();
                } catch (IOException ex) {
                    Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.close();
            }
        });
        controller.image.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    controller.change_pic();
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    void search(ActionEvent event) throws IOException {
        set();
        String key = searchBox.getText();
        if(key == null || "".equals(key)) return;
        show(data.filtered(
                item -> item.toString().toLowerCase().contains(key.toLowerCase())
        ));
    }
    
    void show(ObservableList<Categorie> dat) throws IOException{
        displayArea.getChildren().clear();
        for(Categorie item:dat){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorie_SingleView.fxml"));
            VBox grid = loader.load();
            Categorie_SingleViewController controller = loader.getController();
            controller.cat = item;
            controller.set();
            displayArea.getChildren().add(grid);
        }
    }
    
    void set() throws IOException{
        data = FXCollections.observableArrayList(Categorie.getCategories());
        show(data);
    }

    @FXML
    void initialize(){
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert search_but != null : "fx:id=\"search_but\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert displayArea != null : "fx:id=\"displayArea\" was not injected: check your FXML file 'Categorie.fxml'.";
        try {
            set();
        } catch (IOException ex) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
