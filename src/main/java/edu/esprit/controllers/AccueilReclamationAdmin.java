package edu.esprit.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AccueilReclamationAdmin {
    @FXML
    private Button bEnvoyerMA;

    @FXML
    private Button bLesRepRA;

    @FXML
    private Button bListMessagesA;

    @FXML
    private Button bListReclamationA;

    @FXML
    private Text textGestion;

    @FXML
    private Text textGestion1;

    @FXML
    void openEnvoyerMessageA(ActionEvent event) {

        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessageAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de MesReclamations.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bLesRepRA.getScene();

            // Configurer la transition de balayage
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
            transition.setFromX(currentScene.getWidth());
            transition.setToX(0);

            // Afficher la nouvelle scène avec une transition
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(scene);

            // Démarrer la transition
            transition.play();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }

    }

    @FXML
    void openLesReponsesRA(ActionEvent event) {
        try {
            // Charger le fichier FXML des réponses aux réclamations
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReponsesReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de LesReponsesReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bLesRepRA.getScene();

            // Configurer la transition de balayage
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
            transition.setFromX(currentScene.getWidth());
            transition.setToX(0);

            // Afficher la nouvelle scène avec une transition
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(scene);

            // Démarrer la transition
            transition.play();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }


    }

    @FXML
    void openListMessagesA(ActionEvent event) {
        try {
            // Charger le fichier FXML des réponses aux réclamations
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageRecuAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de LesReponsesReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bListMessagesA.getScene();

            // Configurer la transition de balayage
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
            transition.setFromX(currentScene.getWidth());
            transition.setToX(0);

            // Afficher la nouvelle scène avec une transition
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(scene);

            // Démarrer la transition
            transition.play();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }



    }



    @FXML
    void openReclamationA(ActionEvent event) {

            try {
                // Charger le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReclamationsAdmin.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec le contenu de MesReclamations.fxml
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir du bouton cliqué
                Scene currentScene = bLesRepRA.getScene();

                // Configurer la transition de balayage
                TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
                transition.setFromX(currentScene.getWidth());
                transition.setToX(0);

                // Afficher la nouvelle scène avec une transition
                Stage stage = (Stage) currentScene.getWindow();
                stage.setScene(scene);

                // Démarrer la transition
                transition.play();

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les exceptions correctement dans votre application
            }
        }


}
