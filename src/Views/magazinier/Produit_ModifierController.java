package Views.magazinier;

import alimentation.Categorie;
import alimentation.Fournisseur;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Produit_ModifierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView image;

    @FXML
    private JFXButton change;

    @FXML
    private TextArea desc;

    @FXML
    private Label code;

    @FXML
    private TextField nom;

    @FXML
    private ComboBox<Categorie> cat;

    @FXML
    private ComboBox<Fournisseur> four;

    @FXML
    private Label qte;

    @FXML
    private Label cost;
    
   
    @FXML
    private Label date;

    @FXML
    private TextField sell;

    @FXML
    private JFXToggleButton state;

    @FXML
    private JFXButton ok;

    @FXML
    private JFXButton apply;

    @FXML
    private JFXButton cancel;
    
    public Produit product;

    @FXML
    void apply(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void changeImage(ActionEvent event) {

    }

    @FXML
    void changeState(ActionEvent event) {
        if(state.isSelected()){
            state.setText("Actif");
        }else{
            state.setText("Inactif");
        }
    }

    @FXML
    void valider(ActionEvent event) {

    }
    ObservableList<Categorie> datac;
    ObservableList<Fournisseur> dataf;
    void init(){
        datac = FXCollections.observableArrayList(Categorie.getCategories());
        cat.setItems(datac);
        dataf = FXCollections.observableArrayList(Fournisseur.getFournisseurs());
        four.setItems(dataf);
        set();
        
    }
    
    private void set(){
        
        datac.stream().filter((cate) -> (product.getIdCategorie().equals(cate))).forEachOrdered((cate) -> {
            System.out.println(cate);
           // cat.setValue(cate);
        });
        dataf.stream().filter((fourr) -> (product.getCodeFour().equals(fourr))).forEachOrdered((fourr) -> {
            System.out.println(fourr);
        });
        
        code.setText(product.getId().toString());
        nom.setText(product.getNomPro());
        qte.setText(""+product.getQte());
        cost.setText(""+product.getPrixAchat());
        sell.setText(""+ product.getPrixVente());
        date.setText(""+product.getDateInsertion().getTime());
        desc.setText(product.getDescription());
        state.setSelected(product.getActif());
        changeState(new ActionEvent());
        
    }
    @FXML
    void initialize() {
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert change != null : "fx:id=\"change\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert code != null : "fx:id=\"code\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert cat != null : "fx:id=\"cat\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert four != null : "fx:id=\"four\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert qte != null : "fx:id=\"qte\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert cost != null : "fx:id=\"cost\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert sell != null : "fx:id=\"set\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert state != null : "fx:id=\"state\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert apply != null : "fx:id=\"apply\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'Produit_Modifier.fxml'.";

    }
}
