package Views.magazinier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Categorie_SingleViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label nom;

    @FXML
    private ImageView image;
        
    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Categorie_SingleView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Categorie_SingleView.fxml'.";

    }
}
        