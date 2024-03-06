package edu.esprit.controllers.controllerUser;

import edu.esprit.controllers.MenuAdmin;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextField fxLogin;
    @FXML
    private Button ToInscri;
    @FXML
    private PasswordField fxPass;
    @FXML
    void loginAccount(ActionEvent event) throws IOException {
        String email = fxLogin.getText();
        String password = fxPass.getText();
        ServiceUser serviceUser = new ServiceUser();
        User loggedInUser = serviceUser.getUserByEmailAndPassword(email, password);

        if (loggedInUser != null) {
            if (loggedInUser.getRole().equals("Admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuAdmin.fxml"));
                Parent root = loader.load();

               MenuAdmin controller = loader.getController();
                controller.setLoggedInUser(loggedInUser);
                controller.SetPageAdmin(loggedInUser);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else if (loggedInUser.getRole().equals("Client")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomePage.fxml"));
                    Parent root = loader.load();

                    WelcomePage controller = loader.getController();
                    controller.setLoggedInUser(loggedInUser);
                    controller.rotateWheel();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the modification page.");
                    alert.showAndWait();
                }
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
                new Authenticator() {
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

    @FXML
    void changePassword(ActionEvent event) {
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Password Reset");
        emailDialog.setHeaderText("Verify Your Email");
        emailDialog.setContentText("Please enter your email:");
        Optional<String> emailResult = emailDialog.showAndWait();

        emailResult.ifPresent(email -> {
            ServiceUser serviceUser = new ServiceUser();
            if (serviceUser.checkUserExists(email)) {
                // If email exists, generate OTP
                String otp = generateOTP();
                // Send the OTP via email
                sendEmailWithOTP(email, otp);
                // Dialog to enter OTP
                TextInputDialog otpDialog = new TextInputDialog();
                otpDialog.setTitle("OTP Verification");
                otpDialog.setHeaderText("OTP for Password Reset");
                otpDialog.setContentText("Please, enter the OTP sent to your email:");
                Optional<String> otpResult = otpDialog.showAndWait();
                if (otpResult.isPresent() && otpResult.get().equals(otp)) {
                    // If OTP is correct, prompt for new password
                    TextInputDialog newPasswordDialog = new TextInputDialog();
                    newPasswordDialog.setTitle("Change Password");
                    newPasswordDialog.setHeaderText("Enter New Password");
                    newPasswordDialog.setContentText("New Password:");
                    Optional<String> newPasswordResult = newPasswordDialog.showAndWait();
                    newPasswordResult.ifPresent(newPassword -> {
                        // Update the password in the database
                        boolean updateSuccess = serviceUser.updatePassword(email, newPassword);
                        if (updateSuccess) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Your password has been changed successfully");
                            alert.showAndWait();
                        } else {
                            showAlert("Error", "There was a problem changing your password. Please try again.");
                        }
                    });
                } else {
                    showAlert("Incorrect OTP", "The OTP you entered is incorrect. Please try again.");
                }
            } else {
                showAlert("Invalid Email", "The email you entered does not exist in our system.");
            }
        });
    }
    ServiceUser serviceUser = new ServiceUser();
    @FXML
    void FaceID(ActionEvent event){

    }
}
