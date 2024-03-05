package edu.esprit.controller;

import edu.esprit.entities.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.animation.RotateTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class WelcomePage {

    @FXML
    private ImageView wheel;
    private User user;

    // Create a method to set the logged-in user
    public void setLoggedInUser(User user) {
        this.user = user;
    }


    @FXML
    void versPageClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profileClient.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        ProfileClient profileController = loader.getController();
        profileController.SetProfile(user);
        profileController.refreshUserData();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) wheel.getScene().getWindow();
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
    public void rotateWheel() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), wheel);
        rotateTransition.setByAngle(360);  // Rotate by 360 degrees
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);  // Rotate indefinitely
        rotateTransition.setAutoReverse(false);  // Do not reverse the rotation
        rotateTransition.play();  // Start the animation
    }

}