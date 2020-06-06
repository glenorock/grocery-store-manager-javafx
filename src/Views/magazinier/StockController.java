package Views.magazinier;

import Views.Constants;
import alimentation.Categorie;
import alimentation.Fournisseur;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import alimentation.Produit;
import java.io.FileInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


public class StockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton search_but;

    @FXML
    private TableView<Produit> table;

    @FXML
    private TableColumn<Produit, Void> image;

    @FXML
    private TableColumn<Produit, Integer> code;

    @FXML
    private TableColumn<Produit, String> nom;

    @FXML
    private TableColumn<Produit, Categorie> cat;

    @FXML
    private TableColumn<Produit, Integer> qte;

    @FXML
    private TableColumn<Produit, Double> cost;
    
    @FXML
    private TableColumn<Produit, Double> sale;
    
    @FXML
    private TableColumn<Produit, Double> date;
    
    @FXML
    private TableColumn<Produit, Fournisseur> four;
    
    @FXML
    private TableColumn<Produit, String> desc;
    
    
    private ObservableList<Produit> data ;
    
    @FXML
    void search(ActionEvent event) {
        String key = searchBox.getText();
        setData();
        if(key == null || "".equals(key))return;
        table.setItems(data.filtered(item ->
                item.getCodePro().toString().toLowerCase().contains(key.toLowerCase()) ||
                item.getNomPro().toLowerCase().contains(key.toLowerCase()) ||
                item.getIdCategorie().toString().toLowerCase().contains(key.toLowerCase()) ||
                item.getDateInsertion().toString().toLowerCase().contains(key.toLowerCase()) ||
                item.getCodeFour().toString().toLowerCase().contains(key.toLowerCase()) 
            )
        );
        
    }
    
    void setColumns(){
        code.setCellValueFactory(new PropertyValueFactory("codePro"));
        nom.setCellValueFactory(new PropertyValueFactory("nomPro"));
        cat.setCellValueFactory(new PropertyValueFactory("idCategorie"));
        qte.setCellValueFactory(new PropertyValueFactory("qte"));
        cost.setCellValueFactory(new PropertyValueFactory("prixAchat"));
        sale.setCellValueFactory(new PropertyValueFactory("prixVente"));
        date.setCellValueFactory(new PropertyValueFactory("dateInsertion"));
        four.setCellValueFactory(new PropertyValueFactory("codeFour"));
        desc.setCellValueFactory(new PropertyValueFactory("description"));
        
        Callback<TableColumn<Produit,Void>,TableCell<Produit,Void>> imageCell = new Callback() {
            @Override
            public TableCell<Produit,Void> call(Object param) {
                final TableCell<Produit,Void> cell = new TableCell<Produit,Void>(){
                    private ImageView img = new ImageView();
                    {
                        img.setFitHeight(50);
                        img.setFitWidth(50);
                        img.setSmooth(true);
                        img.setPreserveRatio(true);
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Produit product = getTableView().getItems().get(getIndex());
                            String imagePath = Constants.Products_Image_Path;
                            try{
                                FileInputStream inputstream = new FileInputStream(imagePath + product.getCodePro().toString() + ".jpg"); 
                                Image pro_image = new Image(inputstream); 
                                img.setImage(pro_image);
                                setGraphic(img);
                            }catch(Exception e){
                                
                            }
                        }
                    }
                };
                return cell;
            }
        };
        image.setCellFactory(imageCell);
    }
    
    void setData(){
        data = FXCollections.observableArrayList(Produit.getProduits());
        data.sort(Produit.sortByIdDesc);        
        table.setItems(data);
    }

    @FXML
    void initialize() {
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'Stock.fxml'.";
        assert search_but != null : "fx:id=\"search_but\" was not injected: check your FXML file 'Stock.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Stock.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Stock.fxml'.";
        assert code != null : "fx:id=\"code\" was not injected: check your FXML file 'Stock.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Stock.fxml'.";
        assert cat != null : "fx:id=\"cat\" was not injected: check your FXML file 'Stock.fxml'.";
        assert qte != null : "fx:id=\"qte\" was not injected: check your FXML file 'Stock.fxml'.";
        assert cost != null : "fx:id=\"cost\" was not injected: check your FXML file 'Stock.fxml'.";
        setColumns();
        setData();
    }
}
