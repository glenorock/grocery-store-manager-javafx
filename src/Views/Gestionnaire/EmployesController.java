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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import alimentation.Gestionnaire;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author jhy
 */
public class EmployesController implements Initializable {
    
    @FXML
    private TableView<Gestionnaire> table;

    @FXML
    private TableColumn<Gestionnaire,String> nameCol;

    @FXML
    private TableColumn<Gestionnaire, String> userCol;

    @FXML
    private TableColumn<Gestionnaire, String> pwdCol;

    @FXML
    private TableColumn<Gestionnaire,String> typeCol;

    @FXML
    private TableColumn<Gestionnaire, String> activeCol;

    @FXML
    private TableColumn<Gestionnaire,Void> actionCol;
    
    @FXML
    private JFXTextField search;

    @FXML
    private JFXComboBox<String> using;

    @FXML
    private JFXButton butSearch;

    @FXML
    private JFXButton annuler;
    
    @FXML
    private GridPane root;
    
    @FXML
    private Label ajout;
    
    private ObservableList<Gestionnaire> data = FXCollections.observableList(Gestionnaire.getGestionnaires());
        
    @FXML
    void annuler(ActionEvent event) {
        setData();
    }
    
    @FXML
    void ajout(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AjoutEmployes.fxml"));
        Parent parent = fxmlLoader.load();
        AjoutEmployesController controller = fxmlLoader.<AjoutEmployesController>getController();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
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
                data.stream()
                        .filter(item -> (item.getNomGest().contains(key)))
                        .findAny()
                        .ifPresent(item -> {
                            table.getSelectionModel().select(item);
                            table.scrollTo(item);    
                        });
                table.setItems(data.filtered(item -> item.getNomGest().contains(key)));
                break;
            case "Nom Utilisateur":
                table.setItems(data.filtered(item -> item.getLogin().contains(key)));
                break;
            default:
                table.setItems(data.filtered(item -> (item.getLogin().contains(key)) || item.getNomGest().contains(key)));
                break;
        }
    }
    
    private void setColumns(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomGest"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        pwdCol.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        
        Callback<TableColumn<Gestionnaire,String>,TableCell<Gestionnaire,String>> cellType = new Callback(){
            @Override
            public Object call(Object param) {
                return new TableCell<Gestionnaire,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            Gestionnaire gest = getTableView().getItems().get(getIndex());
                            setText(gest.getType());
                        }
                    }
                };
            }
            
        };
        
        Callback<TableColumn<Gestionnaire,String>,TableCell<Gestionnaire,String>> cellActif = new Callback(){
            @Override
            public Object call(Object param) {
                return new TableCell<Gestionnaire,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            Gestionnaire gest = getTableView().getItems().get(getIndex());
                            if(gest.isActif()) setText("Actif");
                            else setText("Inactif");
                        }
                    }
                };
            }
            
        };
        
        Callback<TableColumn<Gestionnaire,Void>,TableCell<Gestionnaire,Void>> cellAction = new Callback() {
            @Override
            public TableCell<Gestionnaire,Void> call(Object param) {
                final TableCell<Gestionnaire, Void> cell = new TableCell<Gestionnaire, Void>() {

                    private Button btn = new Button();
                    
                    
                    {
                        btn.getStyleClass().add("activation-button");
                        btn.setOnAction((ActionEvent event) -> {
                            Gestionnaire gest = getTableView().getItems().get(getIndex());
                            gest.toggleActivate();
                            setData();
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Gestionnaire gest = getTableView().getItems().get(getIndex());
                            if(gest.isActif()) btn.setText("Desactiver");
                            else btn.setText("Activer");
                            setGraphic(btn);
                        }
                    }
                    
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellAction);
        typeCol.setCellFactory(cellType);
        activeCol.setCellFactory(cellActif);
        table.prefHeightProperty().bind(root.heightProperty().subtract(100));
        table.prefWidthProperty().bind(root.widthProperty().multiply(0.9));
    }
    
    private void setData(){
        table.getItems().clear();
        table.setItems(data);
        ObservableList<String> sdata = FXCollections.observableArrayList();
        sdata.addAll(
                "Tous",
                "Nom",
                "Nom Utilisateur"
        );
        this.using.getItems().clear();
        using.setItems(sdata);
        using.setValue("Tous");
        search.setText(null );
    }
    /**
     * Initializes the controller class.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumns();
        setData();
    }
    
    
}
