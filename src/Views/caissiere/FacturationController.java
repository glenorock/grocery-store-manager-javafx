package Views.caissiere;

import Views.Constants;
import Views.LogInController;
import Views.magazinier.Produit_SingleViewController;
import alimentation.Client;
import alimentation.EntityClasses;
import alimentation.Facture;
import alimentation.Lignefacture;
import alimentation.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import pdf.FacturePDF;

public class FacturationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField qty;

    @FXML
    private TextField codePro;

    @FXML
    private ComboBox<Client> client;

    @FXML
    private JFXToggleButton isCash;

    @FXML
    private Label net;

    @FXML
    private TextField recu;

    @FXML
    private Label reliquat;
    
    @FXML
    private Label totalToPay;
    
    @FXML
    private Label remise;
    
    @FXML
    private StackPane area;

    @FXML
    private JFXButton ajouter;

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
    private JFXButton valider;

    @FXML
    private JFXButton apercu;

    @FXML
    private JFXButton anuller;

    @FXML
    private JFXButton supprimer;
    
    private ObservableList<Lignefacture> data = FXCollections.observableArrayList();
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    
    @FXML
    void setClient(ActionEvent event) {
         System.out.println(client.getSelectionModel().getSelectedItem());
    }
    void setColumns(){
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
        clients = FXCollections.observableArrayList(Client.getClients());
        client.setItems(clients);
        client.setValue(clients.get(0)); 
        table.setItems(data);
    }
    
    @FXML
    void ajouter(ActionEvent event) {
        try{
            Produit pro = Produit.getProduitByCode(Integer.valueOf(codePro.getText())); 
            if (pro == null) return;
            data.add(new Lignefacture(
                    Short.valueOf(qty.getText()),
                    pro
            ));
            net.setText(""+ calculNet());
            reliquat.setText(""  + calculReliquat()); 
            codePro.setText(""); 
            qty.setText("1");
            set(event);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    void mode(ActionEvent event) {
        if(isCash.isSelected()){
            isCash.setText("Cash");
        }else{
            isCash.setText("Credit"); 
        }
    }
    
   @FXML
    void set(ActionEvent event) {
        show_produits();
        net.setText(""+ calculNet());
        this.reliquat.setText(""  + calculReliquat()); 
        this.totalToPay.setText("" +calculTotal() + " FCFA" );
        this.remise.setText(calculRemise() * 100 + "%");
    }
    
    void show_produits(){
        area.getChildren().clear();
        try {
            Produit pro = Produit.getProduitByCode(Integer.valueOf(codePro.getText()));
            if (pro == null) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..//magazinier//Produit_SingleView.fxml"));
            VBox grid = loader.load();
            Produit_SingleViewController controller = loader.getController();
            controller.product = pro;
            controller.set();
            controller.qte.setText("Prix Unitaire: " + pro.getPrixVente().toString() + "FCFA");
            controller.vbox.getChildren().removeAll(controller.buttons);
            area.getChildren().add(grid);
        } catch (Exception ex) {
        }
    }
    
    public double calculRemise(){
        Client c = client.getValue();
        if(c.getNom().equalsIgnoreCase(client.getItems().get(0).getNom())) return 0.0;
        if(calculTotal() <= 15000) return 0.0;
        return 0.03;
    }
    
    public double calculNet(){
        return (1-calculRemise()) * calculTotal();
    }
    
    public double calculReliquat(){
        try{
            double given = Double.valueOf(recu.getText());
            if(recu.getText() == null || "".equals(recu.getText())) given = 0.0;
            return given - calculNet();
        }catch(Exception e){
            return 0.0;
        }
        
    }
    
    public double calculTotal(){
        double amount = 0.0;
        for(Lignefacture ligne: data){
            amount += ligne.getPrix().doubleValue();
        }
        return amount;
    }
    
    @FXML
    void anuller(ActionEvent event) {
        data.clear();
        client.setValue(client.getItems().get(0));  
        qty.setText("1"); 
        codePro.setText(null);
        this.recu.setText(null); 
        this.reliquat.setText(null); 
        isCash.setSelected(true); 
        set(event);
    }

    @FXML
    void apercu(ActionEvent event) {
        try {
            double tmp = Double.parseDouble(net.getText());
            BigDecimal montant = BigDecimal.valueOf(tmp);
            BigDecimal remisse = BigDecimal.valueOf(calculReliquat());
            
            Facture fact = new Facture(montant,remisse,isCash.isSelected(),client.getValue(),LogInController.user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Facture_Lignes.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            Facture_LignesController controller = fxmlLoader.<Facture_LignesController>getController();
            controller.facture = fact;
            controller.setColumns();
            controller.set();
            controller.data.addAll(data); 
            controller.client.setText("Recu par:  ***** ");
            controller.date.setText((new Date()).toString());
            controller.montant.setText(net.getText() + " FCFA"); 
            controller.numero.setText("Facture Numero:  ***** "); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception ex) {
        }
    }

    @FXML
    void supprimer(ActionEvent event)  {
        int selected = table.getSelectionModel().getSelectedIndex();
        data.remove(selected);
        set(event);
    }

    @FXML
    void valider(ActionEvent event) {
        try{
            double tmp = calculNet();
            BigDecimal montant = BigDecimal.valueOf(tmp);
            BigDecimal remisse = BigDecimal.valueOf(calculRemise());
            if((remisse.doubleValue() < 0) || data.isEmpty() || !isAvailable()) return;
            
            EntityClasses.add(new Facture(montant,remisse,isCash.isSelected(),client.getValue(),LogInController.user));
            Facture fact = Facture.getMostRecent();
            for(Lignefacture ligne: data){
                EntityClasses.add(
                        new Lignefacture(ligne.getQte(),ligne.getCodePro(),fact)
                );
                ligne.getCodePro().reduce(ligne.getQte()); 
            }
            makeFacture(fact);
            FacturePDF pdf = new FacturePDF(fact,data);
            pdf.print();
            anuller(event);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    void makeFacture(Facture fact){
        try {
            if(fact == null) return;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Facture_Lignes.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            Facture_LignesController controller = fxmlLoader.<Facture_LignesController>getController();
            controller.facture = fact;
            controller.init();
            controller.data.addAll(data);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FacturationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isAvailable(){
        for(Lignefacture fac:data){
            if(fac.getCodePro().getQte() < fac.getQte()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Insufficient products in stock for: " + fac.getCodePro()
                        + "\nAmount available in stock: " + fac.getCodePro().getQte());
                alert.setHeaderText(null);
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }
    
    void init(){
        setColumns();
        setData();
        isCash.setSelected(true);
        isCash.setText("Cash");
        this.remise.setText( "0.0 %");
        qty.setText("1");
        client.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client object) {
                return object.toString();
            }
 
            @Override
            public Client fromString(String string) {
                Client c = client.getItems().filtered(item ->
                        item.getId().toString().equals(string) ||
                        item.getNom().toLowerCase().contains(string.toLowerCase())
                ).get(0);
                if(c == null) return clients.get(0);
                return c;
            }
        });
    }

    @FXML
    void initialize() {
        assert qte != null : "fx:id=\"qte\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert codePro != null : "fx:id=\"codePro\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert client != null : "fx:id=\"client\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert isCash != null : "fx:id=\"isCash\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert net != null : "fx:id=\"net\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert recu != null : "fx:id=\"recu\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert reliquat != null : "fx:id=\"remise\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert area != null : "fx:id=\"area\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert ajouter != null : "fx:id=\"ajouter\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert product != null : "fx:id=\"product\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert unit != null : "fx:id=\"unit\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert total != null : "fx:id=\"total\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert valider != null : "fx:id=\"valider\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert apercu != null : "fx:id=\"apercu\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert anuller != null : "fx:id=\"anuller\" was not injected: check your FXML file 'Facturation.fxml'.";
        assert supprimer != null : "fx:id=\"supprimer\" was not injected: check your FXML file 'Facturation.fxml'.";
        init();
        anuller(new ActionEvent());
    }
}
