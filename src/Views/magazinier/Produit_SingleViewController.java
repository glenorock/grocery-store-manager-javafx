package Views.magazinier;

import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Label qte;
    

    @FXML
    public ImageView image;

    @FXML
    public JFXButton add;

    @FXML
    public JFXButton reduce;

    public Produit product = new Produit();
    
    private final String imagePath = "C:\\Users\\jhy\\Documents\\NetBeansProjects\\projetBD\\src\\Views\\magazinier\\images_produits\\";
    
    void set() throws FileNotFoundException{
        code.setText(product.getCodePro().toString()); 
        nom.setText(product.getNomPro());
        try{
            FileInputStream inputstream = new FileInputStream(imagePath + product.getNomPro() + ".jpg"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image);
        }catch(Exception e){
            FileInputStream inputstream = new FileInputStream(imagePath +"default.jpeg"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image);
            
        }
        
        qte.setText(""+product.getQte());
        
    }
    
    void details() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Produit_Modifier.fxml"));
        Parent parent = fxmlLoader.load();
        Produit_ModifierController controller = fxmlLoader.<Produit_ModifierController>getController();
        controller.product = this.product;
        controller.init();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    
    
    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";
        assert reduce != null : "fx:id=\"reduce\" was not injected: check your FXML file 'Produit_SingleView.fxml'.";

    }
}
