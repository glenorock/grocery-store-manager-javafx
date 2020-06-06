package Views.caissiere;

import Views.Constants;
import alimentation.Facture;
import alimentation.Lignefacture;
import alimentation.Produit;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class Facture_LignesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label date;

    @FXML
    private Label numero;

    @FXML
    private Label client;

    @FXML
    private TableView<Lignefacture> table;

    @FXML
    private TableColumn<Lignefacture, Void> image;

    @FXML
    private TableColumn<Lignefacture, Produit> product;

    @FXML
    private TableColumn<Lignefacture, Double> unit;

    @FXML
    private TableColumn<Lignefacture, Integer> qte;

    @FXML
    private TableColumn<Lignefacture, Double> total;

    @FXML
    private Label montant;
    
    public Facture facture;
    
    private ObservableList<Lignefacture> data;
    
    void init(){
        setData();
        date.setText(facture.getDateFac().toString());
        numero.setText("Facture numero: " +  facture.getId().toString());
        client.setText("Reçu par: " + facture.getClientidClient().toString());
        if("default".equals(facture.getClientidClient().toString())) client.setVisible(false);
        montant.setText(""+facture.getTotal()); 
        
        
        Callback<TableColumn<Lignefacture,Void>,TableCell<Lignefacture,Void>> imageCell = new Callback() {
            @Override
            public TableCell<Lignefacture,Void> call(Object param) {
                final TableCell<Lignefacture,Void> cell = new TableCell<Lignefacture,Void>(){
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
                            Produit product = getTableView().getItems().get(getIndex()).getCodePro();
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
        
        
        product.setCellValueFactory(new PropertyValueFactory("codePro"));
        qte.setCellValueFactory(new PropertyValueFactory("qte"));
        total.setCellValueFactory(new PropertyValueFactory("prix"));
        
        Callback<TableColumn<Lignefacture,Double>,TableCell<Lignefacture,Double>> unitprice = new Callback() {
            @Override
            public TableCell<Lignefacture,Double> call(Object param) {
                final TableCell<Lignefacture,Double> cell = new TableCell<Lignefacture,Double>(){
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            Produit product = getTableView().getItems().get(getIndex()).getCodePro();
                            setText(product.getPrixVente().toString());
                        }
                    }
                };
                return cell;
            }
        };
        unit.setCellFactory(unitprice);
    }
    void setData(){
        data = FXCollections.observableArrayList(facture.getLignefactureCollection());
        table.setItems(data);
    }
    @FXML
    void initialize() {
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert client != null : "fx:id=\"client\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert product != null : "fx:id=\"product\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert unit != null : "fx:id=\"unit\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert qte != null : "fx:id=\"qte\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert total != null : "fx:id=\"total\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
        assert montant != null : "fx:id=\"montant\" was not injected: check your FXML file 'Facture_Lignes.fxml'.";
    }
}
