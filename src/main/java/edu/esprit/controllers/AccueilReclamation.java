package edu.esprit.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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



    public void afficherNotificationNouveauMessage() {
        // Créer une notification de type INFORMATION
        Notifications.create()
                .title("Nouveau Message")
                .text("Vous avez reçu un nouveau message.")
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT) // Affichage à côté du bouton peut nécessiter un ajustement de position
                .showInformation();
    }

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
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessage.fxml"));
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
    void openListMessagesA(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageRecuClient.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de ajou.fxml
            Scene scene = new Scene(root);
            if (GestionnaireNotifications.estMessageEnvoye()) {
                afficherNotificationNouveauMessage();
                GestionnaireNotifications.setMessageEnvoye(false); // Réinitialiser pour les futurs messages
            }


            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bEnvoyerMA.getScene();
            afficherNotificationNouveauMessage();
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



    public void openReclamationA(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de MesReclamations.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bListReclamationA.getScene();

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



