package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Listactualite {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text contenue;

    @FXML
    private ImageView image;
    @FXML
    private Button modifier;
    @FXML
    private ImageView btn;


    @FXML
    private Label titre;
    @FXML
    private Button modif;
    @FXML
    private Button Ajouter;





    @FXML
    void Modifieractualite(ActionEvent event) throws IOException {


            Parent root = FXMLLoader.load(getClass().getResource("/Modifieractualite.fxml"));
            Scene newScene = new Scene(root);
            Stage stage = (Stage) modifier.getScene().getWindow();
            root.setOpacity(0);
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
            fadeOutTransition.setToValue(0); // Fade out to fully transparent

            fadeOutTransition.setOnFinished(e -> {

                stage.setScene(newScene);

                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
                fadeInTransition.setToValue(1);
                fadeInTransition.play();
            });

            fadeOutTransition.play();
        }












    @FXML
    void supprimer(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

    public void ajouteract(ActionEvent actionEvent) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/Ajouteractualite.fxml"));
        Scene newScene = new Scene(root);
        Stage stage = (Stage) modifier.getScene().getWindow();
        root.setOpacity(0);
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        fadeOutTransition.setOnFinished(e -> {

            stage.setScene(newScene);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });

        fadeOutTransition.play();
    }

}


