package edu.esprit.controller;


import edu.esprit.entities.Client;
import edu.esprit.services.ServiceUser;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class InscriptionApplication{

    @FXML
    private Label fxTelError;

    @FXML
    private DatePicker fxdate;

    @FXML
    private Label fxerrorMail;

    @FXML
    private TextField fxmail;

    @FXML
    private TextField fxnom;

    @FXML
    private TextField fxprenom;

    @FXML
    private PasswordField fxpwd;

    @FXML
    private TextField fxtel;
    @FXML
    private Button ToLog;
    @FXML
    private ProgressBar progressBar;



    ServiceUser serviceUser = new ServiceUser();
    public void createAccount(ActionEvent actionEvent) {
        int a = Integer.parseInt(fxtel.getText());
        Date d = Date.valueOf(fxdate.getValue());

        try {
            if (!serviceUser.isValidEmail(fxmail.getText())) {

                fxerrorMail.setVisible(true);
                fxTelError.setVisible(false);

            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText())))){
                fxTelError.setVisible(true);
                fxerrorMail.setVisible(false);
            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText()))) && !serviceUser.isValidEmail(fxmail.getText())) {

                fxTelError.setVisible(true);
                fxerrorMail.setVisible(true);

            } else {
            serviceUser.ajouterUser(new Client(fxnom.getText(),fxprenom.getText(),d,a, fxmail.getText(),fxpwd.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
                Parent root = FXMLLoader.load(getClass().getResource("/loginApplication.fxml"));
                Scene newScene = new Scene(root);

                Stage stage = (Stage) ToLog.getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
                fxTelError.setVisible(false);
                fxerrorMail.setVisible(false);}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void ToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/loginApplication.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) ToLog.getScene().getWindow();

        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();
    }

}
