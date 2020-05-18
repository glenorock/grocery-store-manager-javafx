package Views.magazinier;

import alimentation.Categorie;
import alimentation.EntityClasses;
import alimentation.Fournisseur;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Produit_AjoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public ImageView image;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField cost;

    @FXML
    private JFXTextField sell;

    @FXML
    private JFXComboBox<Fournisseur> four;

    @FXML
    private JFXComboBox<Categorie> cat;

    @FXML
    private JFXTextArea desc;

    @FXML
    public JFXButton add;

    @FXML
    private JFXButton cancel;
    
    private final String imagePath = "C:\\Users\\jhy\\Documents\\NetBeansProjects\\projetBD\\src\\Views\\magazinier\\images_produits\\";
    
    public void add() {
        try{
            EntityClasses.add(new Produit(
                name.getText(),
                Integer.parseInt(sell.getText()),
                Integer.parseInt(cost.getText()),
                desc.getText(),
                (Fournisseur)four.getValue(),
                (Categorie)cat.getValue()
            ));
            if(changedImage){
                File source = new File(imageFile);
                File dest = new File(imagePath + name.getText() + ".jpg");
                dest.createNewFile();
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while((length = fis.read(buffer)) > 0){
                    fos.write(buffer,0, length);
                }
            }
        }catch(IOException | NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.toString());
                alert.setHeaderText(null);
                alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private boolean changedImage = false;
    private String imageFile;
    
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
    
    private void set(){
        ObservableList<Categorie> datac = FXCollections.observableArrayList(Categorie.getCategories());
        cat.setItems(datac);
        ObservableList<Fournisseur> dataf = FXCollections.observableArrayList(Fournisseur.getFournisseurs());
        four.setItems(dataf);
    }

    @FXML
    void initialize() {
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert cost != null : "fx:id=\"cost\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert sell != null : "fx:id=\"sell\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert four != null : "fx:id=\"four\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert cat != null : "fx:id=\"cat\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'Produit_Ajout.fxml'.";
        set();
    }
}
