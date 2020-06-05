package Views.magazinier;

import Views.Constants;
import alimentation.Categorie;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Categorie_SingleViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label nom;

    @FXML
    public ImageView image;
    
    public Categorie cat;
    
    private final String imagePath = Constants.Category_Image_Path;
    
    public void set() throws FileNotFoundException{
        nom.setText(cat.getNomCat());
        try{
            FileInputStream inputstream = new FileInputStream(imagePath + cat.getIdCat().toString() + ".jpg"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image);
        }catch(FileNotFoundException e){
            FileInputStream inputstream = new FileInputStream(imagePath +"default.png"); 
            Image pro_image = new Image(inputstream); 
            image.setImage(pro_image); 
        }
    }
            
    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Categorie_SingleView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Categorie_SingleView.fxml'.";

    }
}
        