package edu.esprit.test;

import edu.esprit.controller.InscriptionApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;

import static javafx.application.Application.launch;


public class MainFX extends Application{
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginApplication.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Login");
        stage.show();
    }
    public static void main(String[] args) {
        launch();

    }
}