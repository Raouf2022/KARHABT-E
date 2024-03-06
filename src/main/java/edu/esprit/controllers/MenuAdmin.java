package edu.esprit.controllers;

import edu.esprit.entities.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MenuAdmin {
    @FXML
    private Button recB;

    private User user;

    // Create a method to set the logged-in user
    public void setLoggedInUser(User user) {
        this.user = user;
    }


    @FXML
    void openReclamationAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       AccueilReclamationAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) recB.getScene().getWindow();
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
    public void SetPageAdmin(User user) {
        this.user=user;
        iduser=user.getIdU();



    }


    public void initialize() {
    }
}

