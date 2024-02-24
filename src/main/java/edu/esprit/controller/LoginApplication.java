package edu.esprit.controller;


import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import edu.esprit.tools.DataSource;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginApplication {
    @FXML
    private AnchorPane LayerLogin;
    @FXML
    private Pane fPane;

    @FXML
    private TextField fxLogin;

    @FXML
    private Button ToInscri;


    @FXML
    private PasswordField fxPass;

    @FXML
    void loginAccount(ActionEvent event) {
        String email = fxLogin.getText(); // Assuming fxLogin is a TextField for email
        String password = fxPass.getText(); // Assuming fxPass is a PasswordField for password


        ServiceUser serviceUser = new ServiceUser();
        User user = new User();
        if (serviceUser.login(email,password).equals("Admin")){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/adminPage.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            //if mail and passwd matches with DB where role is Client
        } else if(serviceUser.login(email,password).equals("Client")) {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ProfileClient.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }
        else
            System.out.println("Incorrect email or password!");
    }
    @FXML
    void ToInscription(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/inscriptionApplication.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) ToInscri.getScene().getWindow();

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

}
