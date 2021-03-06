package Views.magazinier;

import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Views.Constants;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Produit_SingleViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label code;
    
    @FXML
    private Label nom;
    
    @FXML
    public Label qte;
    
    
    @FXML
    public VBox vbox;
    
    @FXML
    public HBox buttons;

    
    @FXML
    public ImageView image;

    @FXML
    public JFXButton add;

    @FXML
    public JFXButton reduce;

    public Produit product;
    
    private final String imagePath = Constants.Products_Image_Path;
    
    public void set() throws FileNotFoundException{
        code.setText("N° "+product.getCode()); 
        nom.setText(product.getNomPro());
        try{
            FileInputStream inputstream = new FileInputStream(imagePath + product.getCodePro().toString() + ".jpg"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image);
        }catch(FileNotFoundException e){
            FileInputStream inputstream = new FileInputStream(imagePath +"default.jpeg"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image);
            
        }
        if(product.getWholeOnly()){
            qte.setText("" + product.getQte().intValue()); 
        }else{
            qte.setText(""+product.getQte().doubleValue());
        }
        
    }
    
    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert reduce != null : "fx:id=\"reduce\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";

    }
}
