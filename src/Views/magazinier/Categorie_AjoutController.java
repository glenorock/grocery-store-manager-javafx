package Views.magazinier;

import Views.Constants;
import alimentation.Categorie;
import alimentation.EntityClasses;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Categorie_AjoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField name;

    @FXML
    public ImageView image;

    @FXML
    public JFXButton add;

    @FXML
    private JFXButton cancel;
    
    private boolean changedImage = false;
    private String imageFile;
    private final String imagePath = Constants.Category_Image_Path;
    
    
    @FXML
    void cancel(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    
    void change_pic() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif","*.Bmp","*.Jpeg")
        );
        Stage stage = new Stage();
        stage.setTitle("Image du Produit");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            imageFile = file.getAbsolutePath();
            FileInputStream fileInputStream = new FileInputStream(file);
            image.setImage(new Image(fileInputStream)); 
            changedImage = true;
        }
    }
    
    
    public void add(){
        try{
            EntityClasses.add(new Categorie(name.getText()));
            if(changedImage){
                Categorie cat = Categorie.getCategorieByName(name.getText());
                File source = new File(imageFile);
                File dest = new File(imagePath + cat.getIdCat().toString() + ".jpg");
                dest.createNewFile();
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while((length = fis.read(buffer)) > 0){
                    fos.write(buffer,0, length);
                }
            }
        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.toString());
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'Categorie_Ajout.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Categorie_Ajout.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Categorie_Ajout.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'Categorie_Ajout.fxml'.";

    }
}
