package Views.magazinier;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class CategorieController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchBox;

    @FXML
    private ImageView search_but;

    @FXML
    private JFXButton add;

    @FXML
    private TilePane displayArea;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void search(MouseEvent event) {
        
    }

    @FXML
    void initialize() {
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert search_but != null : "fx:id=\"search_but\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Categorie.fxml'.";
        assert displayArea != null : "fx:id=\"displayArea\" was not injected: check your FXML file 'Categorie.fxml'.";

    }
}
