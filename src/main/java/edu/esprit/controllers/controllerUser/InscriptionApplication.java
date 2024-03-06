package edu.esprit.controllers.controllerUser;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.esprit.entities.Client;
import edu.esprit.services.ServiceUser;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;
import java.util.Random;

public class InscriptionApplication {

    @FXML
    private Label fxTelError;
    @FXML
    private ImageView fximg;


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

    public static final String ACCOUNT_SID = "ACdeb3ac77312a4d6c224e0572404e95aa";
    public static final String AUTH_TOKEN = "e8deb0ffaa2061a0f7631748de36cc67";
    public static final String TWILIO_PHONE_NUMBER = "+14159699264";
    public String verificationCode; // This will hold the generated verification code


    ServiceUser serviceUser = new ServiceUser();
    public void createAccount(ActionEvent actionEvent) {

        if (fxnom.getText().trim().isEmpty() || fxprenom.getText().trim().isEmpty() || fxmail.getText().trim().isEmpty() ||
                fxtel.getText().trim().isEmpty() || fxpwd.getText().trim().isEmpty() || fxdate.getValue() == null || imagePath == null || imagePath.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText("All fields are required");
            alert.setContentText("Please ensure all fields are filled and a photo is selected.");
            alert.showAndWait();
            return; // Exit the method to prevent further execution
        }
        int a = Integer.parseInt(fxtel.getText());
        Date d = Date.valueOf(fxdate.getValue());

        try {
            // Check if the email already exists
            if (serviceUser.checkUserExists(fxmail.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING); // Correctly instantiate the alert with AlertType.WARNING
                alert.setTitle("WARNING");
                alert.setHeaderText(null);
                alert.setContentText("Email already in use. Please use a different email.");
                alert.showAndWait();

                return; // Exit the method to prevent further execution
            }
            if (!serviceUser.isValidEmail(fxmail.getText())) {

                fxerrorMail.setVisible(true);
                fxTelError.setVisible(false);

            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText()))))
            {
                fxTelError.setVisible(true);
                fxerrorMail.setVisible(false);
            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText()))) && !serviceUser.isValidEmail(fxmail.getText()))
            {
                fxTelError.setVisible(true);
                fxerrorMail.setVisible(true);
            } else {
                this.verificationCode = generateVerificationCode();
                sendVerificationCode(String.valueOf(a), this.verificationCode);
                boolean isCodeVerified = false;
                while (!isCodeVerified) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Verification Code");
                    dialog.setHeaderText("Enter the verification code sent to your phone:");
                    dialog.setContentText("Code:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String inputCode = result.get();
                        if (inputCode.equals(this.verificationCode)) {
                            isCodeVerified = true;
                            //ken shih l code waktha najouti ala rouhi
                            serviceUser.ajouterUser(new Client(fxnom.getText(), fxprenom.getText(), d, a, fxmail.getText(), fxpwd.getText(),imagePath));
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("/loginApplication.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene newScene = new Scene(root);
                            Stage stage = (Stage) ToLog.getScene().getWindow();
                            stage.setScene(newScene);
                            stage.show();
                            fxTelError.setVisible(false);
                            fxerrorMail.setVisible(false);
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Incorrect Code");
                            errorAlert.setContentText("The verification code you entered is incorrect. Please try again.");
                            errorAlert.showAndWait();
                        }
                    } else {
                        // hedhi khaliha ken sakart ysaker wala oumourou manhebech njareb nahiha meme si mahich bch tkalak
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public String generateVerificationCode() {

        return String.format("%06d", new Random().nextInt(999999));
    }
    @FXML
    void ToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/loginApplication.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) ToLog.getScene().getWindow();
        root.setOpacity(0);
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
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
    private void sendVerificationCode(String toPhoneNumber, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String fullPhoneNumber = "+216" + toPhoneNumber;
        Message.creator(
                new PhoneNumber(fullPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "Your verification code is: " + code
        ).create();
    }
    private String imagePath;
    @FXML
    public void ajouterPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        String imagepath=null;
        if (selectedFile != null)
            imagepath = selectedFile.getAbsolutePath();
        imagePath=imagepath;
        String url1 = imagepath;

        if(url1!=null) {
            File file = new File(url1);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(fis);
            fximg.setImage(image);
        }
    }
}
