package Views.magazinier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class StockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane root;

    @FXML
    private TableView<?> table;

    @FXML
    private JFXTextField search;

    @FXML
    private JFXComboBox<?> using;

    @FXML
    private JFXButton butSearch;

    @FXML
    private JFXButton annuler;

    @FXML
    private Label ajout;

    @FXML
    void ajout(MouseEvent event) {

    }

    @FXML
    void annuler(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'Stock.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Stock.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'Stock.fxml'.";
        assert using != null : "fx:id=\"using\" was not injected: check your FXML file 'Stock.fxml'.";
        assert butSearch != null : "fx:id=\"butSearch\" was not injected: check your FXML file 'Stock.fxml'.";
        assert annuler != null : "fx:id=\"annuler\" was not injected: check your FXML file 'Stock.fxml'.";
        assert ajout != null : "fx:id=\"ajout\" was not injected: check your FXML file 'Stock.fxml'.";

    }
}
