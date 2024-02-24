package edu.esprit.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AccueilReclamation {
    @FXML
    private Button bEnvoyerMA;

    @FXML
    private Button bLesRepRA;

    @FXML
    private Button bListMessagesA;

    @FXML
    private Button bListReclamationA;

    @FXML
    private Button bReponseRA;

    @FXML
    private Button bsaisirR;

    @FXML
    private Text textGestion;

    @FXML
    void SaisirReclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de ajou.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bEnvoyerMA.getScene();

            // Configurer la transition
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
    void openEnvoyerMessageA(ActionEvent event) {

    }

    @FXML
    void openLesReponsesRA(ActionEvent event) {

    }

    @FXML
    void openListMessagesA(ActionEvent event) {

    }


    @FXML
    void openReclamationA(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
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
        void openReponseRA (ActionEvent event){


        }
    }
