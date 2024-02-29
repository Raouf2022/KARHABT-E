package edu.esprit.controller;
import javafx.scene.control.Alert;

import edu.esprit.services.ServiceUser;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;


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
        String email = fxLogin.getText();
        String password = fxPass.getText();
        ServiceUser serviceUser = new ServiceUser();
        if (serviceUser.login(email, password).equals("Admin")) {
            loadScene("/adminPage.fxml", event);
        } else if (serviceUser.login(email, password).equals("Client")) {
            // Generate OTP
            String otp = generateOTP();
            // Assuming you send the OTP via email
            sendEmailWithOTP(email, otp);
            // Dialog to enter OTP
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("2FA Verification");
            dialog.setHeaderText("Two-Factor Authentication");
            dialog.setContentText("Please, Enter the Code sent to your email:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && result.get().equals(otp)) {
                loadScene("/ProfileClient.fxml", event);
            } else {
                showAlert("Incorrect OTP", "Please try again.");
            }
        } else {
            showAlert("Login Failed", "Incorrect email or password!");
        }
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
    private void sendEmailWithOTP(String toEmail, String otp) {
        final String username = "karhabte@gmail.com";
        final String password = "eara rwqr nlyi zuyl";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //T7el communication chiffrée bel tls (protocol de transport)
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP server
        props.put("mail.smtp.port", "587"); //port hedha howa li lezm yekhdm lel envoi mtaa l mails.

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("karhabte@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("2FA Authentication");
            message.setText("Dear user,\n\nYour 2FA code is: " + otp);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String generateOTP() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return sb.toString();
    }
    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
