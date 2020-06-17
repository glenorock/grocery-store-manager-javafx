package Views.magazinier;

import Views.LogInController;
import alimentation.Categorie;
import alimentation.EntityClasses;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import alimentation.Gestionstock;
import java.io.FileNotFoundException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProduitController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Categorie> categorie;

    @FXML
    private JFXButton add;

    @FXML
    private FlowPane displayArea;
    
    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton search_but;

     @FXML
    private Pagination pagination;


    ObservableList<Produit> data;
    
    @FXML
    void add(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Produit_Ajout.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        
        Produit_AjoutController controller = fxmlLoader.<Produit_AjoutController>getController();
        controller.add.setOnAction(new EventHandler() {
            @Override
            public void handle(Event evt) {
                controller.add();
                try {
                    show_Allproduits();
                } catch (IOException ex) {
                    Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.close();
                        
                
            }
        });
        controller.image.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    controller.change_pic();
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }); 
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    @FXML
    void search(ActionEvent event) throws IOException {
        String key = searchBox.getText();
        categorie.setValue(null);
        show_Allproduits();
        if(key == null || "".equals(key)) return;
        show_produits(data.filtered(item -> item.getNomPro().toLowerCase().contains(key.toLowerCase()))); 
    }
    
    
    

    @FXML
    void set_by_categorie(ActionEvent event) throws IOException {
        if(categorie.getValue() == null) return;
        data = FXCollections.observableList(Produit.getProduits());
        show_produits(
                data.filtered(item -> (item.getIdCategorie().getNomCat() == null ? categorie.getValue().getNomCat() == null : item.getIdCategorie().getNomCat().equals(categorie.getValue().getNomCat())))
        ); 
    }
    
    
    
    void show_produits(ObservableList<Produit> dat) throws IOException{
        displayArea.getChildren().clear();
        for(Produit item: dat){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit_SingleView.fxml"));
            VBox grid = loader.load();
            Produit_SingleViewController controller = loader.getController();
            controller.product = item;
            controller.set();
            controller.add.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Dialog<Boolean> dialog = new Dialog<>();
                    dialog.setTitle(null);
                    dialog.setHeaderText(null);
                    dialog.setResizable(false);
                    dialog.initStyle(StageStyle.DECORATED);
                    StackPane content = new StackPane();
                    content.setAlignment(Pos.CENTER);
                    content.setPrefWidth(100);
                    JFXTextField text = new JFXTextField();
                    text.setPrefWidth(100);
                    content.getChildren().add(text);
                    ButtonType validate = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Anuller",ButtonBar.ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().addAll(validate,cancel);
                    dialog.getDialogPane().setContent(content);
                    dialog.setResultConverter(new Callback<ButtonType,Boolean>(){
                        @Override
                        public Boolean call(ButtonType b) {
                            if(b.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                                try{
                                    double qte = Double.parseDouble(text.getText());
                                    controller.product.increase(qte);
                                    Gestionstock stock = new Gestionstock(qte,true,controller.product,LogInController.user);
                                    EntityClasses.add(stock);
                                    return true;
                                }catch(Exception e){
                                    
                                    return false;
                                }
                            }
                            return false;
                        }
                    });

                    Optional<Boolean> result = dialog.showAndWait();
                    try {
                        if(categorie.getValue() == null){
                            show_Allproduits();
                        }else{
                            set_by_categorie(new ActionEvent());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            controller.reduce.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Dialog<Boolean> dialog = new Dialog<>();
                    dialog.setTitle(null);
                    dialog.setHeaderText(null);
                    dialog.setResizable(false);
                    dialog.initStyle(StageStyle.DECORATED);
                    StackPane content = new StackPane();
                    content.setAlignment(Pos.CENTER);
                    content.setPrefWidth(100);
                    JFXTextField text = new JFXTextField();
                    text.setPrefWidth(100);
                    content.getChildren().add(text);
                    ButtonType validate = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Annuler",ButtonBar.ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().addAll(validate,cancel);
                    dialog.getDialogPane().setContent(content);
                    dialog.setResultConverter(new Callback<ButtonType,Boolean>(){
                        @Override
                        public Boolean call(ButtonType b) {
                            if(b.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                                try{
                                    double qte = Double.valueOf(text.getText());
                                    controller.product.reduce(qte);
                                    Gestionstock stock = new Gestionstock(qte,false,controller.product,LogInController.user);
                                    EntityClasses.add(stock);
                                    return true;
                                }catch(Exception e){
                                    return false;
                                }
                            }
                            return false;
                        }
                    });

                    Optional<Boolean> result = dialog.showAndWait();
                    try {
                        if(categorie.getValue() == null){
                            show_Allproduits();
                        }else{
                            set_by_categorie(new ActionEvent());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            displayArea.getChildren().add(grid);
        }
        
    }
    
    void show_Allproduits() throws IOException{
        data = FXCollections.observableList(Produit.getProduits());
        data.sort(Produit.sortByIdDesc); 
        show_produits(data);
    }
    
    
    @FXML
    void initialize() throws IOException {
        assert categorie != null : "fx:id=\"categorie\" was not injected: check your FXML file 'Produit.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Produit.fxml'.";
        assert displayArea != null : "fx:id=\"displayArea\" was not injected: check your FXML file 'Produit.fxml'.";
        show_Allproduits();
        ObservableList<Categorie> datac = FXCollections.observableArrayList(Categorie.getCategories());
        categorie.setItems(datac);
 
    }
}
