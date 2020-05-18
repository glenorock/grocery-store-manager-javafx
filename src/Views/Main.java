/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import alimentation.EntityClasses;
/**
 *
 * @author jhy
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityClasses.init();
        launch(args); 
        EntityClasses.emf.close();
        System.out.println("Closed");
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL u=getClass().getResource("LogIn.fxml");
        Parent root = FXMLLoader.load(u);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("DOMS");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
