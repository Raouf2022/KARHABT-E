package edu.esprit.controllers;

import edu.esprit.controllers.controllerUser.ProfileClient;
import edu.esprit.controllers.controllerUser.WelcomePage;
import edu.esprit.entities.User;
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

    @FXML
    private Button bRetourA;
    User user=new User();

    public void afficherNotificationNouveauMessage() {
        // Créer une notification de type INFORMATION
        Notifications.create()
                .title("Nouveau Message")
                .text("Vous avez reçu un nouveau message.")
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT) // Affichage à côté du bouton peut nécessiter un ajustement de position
                .showInformation();
    }
    public void setLoggedInUser(User user) {
        this.user = user;
    }
    @FXML
    void SaisirReclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        AjouterReclamation profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bsaisirR.getScene().getWindow();
        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);
        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        // Play the fade out transition
        fadeOutTransition.play();
    }
    @FXML
    void openEnvoyerMessageA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessage.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       EnvoyerMessage profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bEnvoyerMA.getScene().getWindow();
        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);
        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        // Play the fade out transition
        fadeOutTransition.play();

    }



    @FXML
    void openListMessagesA(ActionEvent event) throws IOException {

            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageRecuClient.fxml"));
            Parent root = loader.load();

            // Get the ProfileClient controller and set the user
            MessageRecuClient profileController = loader.getController();
            profileController.SetPage(user);
            System.out.println(user.getIdU());
            profileController.initialize();

            Scene newScene = new Scene(root);
            Stage stage = (Stage) bListMessagesA.getScene().getWindow();
            // Set opacity of new scene's root to 0 to make it invisible initially
            root.setOpacity(0);
            // Create a fade transition for the old scene
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
            fadeOutTransition.setToValue(0); // Fade out to fully transparent
            // Set the action to be performed after the transition
            fadeOutTransition.setOnFinished(e -> {
                stage.setScene(newScene);
                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
                fadeInTransition.setToValue(1);
                fadeInTransition.play();
            });
            // Play the fade out transition
            fadeOutTransition.play();
        }





    public void openReclamationA(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        MesReclamations profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage)bListReclamationA.getScene().getWindow();
        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);
        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        // Play the fade out transition
        fadeOutTransition.play();
    }

    public void retourAcueilAdmin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       WelcomePage profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();
        profileController.rotateWheel();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bRetourA.getScene().getWindow();
        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);
        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        // Play the fade out transition
        fadeOutTransition.play();
    }

    private int iduser;
    public void SetPage(User user) {
        this.user=user;
        iduser=user.getIdU();



    }

    public void initialize() {
    }
}




