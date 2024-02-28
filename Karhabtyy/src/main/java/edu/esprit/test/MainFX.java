
package edu.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import edu.esprit.tools.DataSource;

public class MainFX extends Application {

    DataSource dataSource = DataSource.getInstance();
    Connection connection = dataSource.getCnx();
    @Override
    public void start(Stage stage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            String css = getClass().getResource("/Style.css").toExternalForm();
            root.getStylesheets().add(css);
            stage.setTitle("Gestion Dossier");
            stage.show();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}


