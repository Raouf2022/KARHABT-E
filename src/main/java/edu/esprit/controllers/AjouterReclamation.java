package edu.esprit.controllers;


import edu.esprit.controllers.controllerUser.WelcomePage;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.tools.DataSource;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;




import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import static org.controlsfx.control.Notifications.create;


public class AjouterReclamation {

    @FXML
    private Button bMesReclamations;

    @FXML
    private Button bRetour;
    @FXML
    private Button bMesMessages;


    @FXML
    private Button bnouveauMessage;
    @FXML
    private Button bvalider;

    @FXML
    private Text textGestion1;

    @FXML
    private TextArea taDescription;


    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfidUser;

    @FXML
    private WebView webb;


    private Connection cnx = DataSource.getInstance().getCnx();
    User user=new User();


    @FXML
    void openMesReclamations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       MesReclamations profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bMesReclamations.getScene().getWindow();
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


    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    public void initialize() {

        this.user=user;
        tfidUser.setText(String.valueOf(user.getIdU()));
        webb.getEngine().load("http://localhost/captcha.html");
        webb.getEngine().setOnError(event -> {
            System.out.println("Erreur de chargement : " + event.getMessage());
        });
        webb.getEngine().getLoadWorker().exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                System.out.println("Exception lors du chargement de la page : " + newValue.getMessage());
            }
        });
    }


    @FXML
    void validerReclamation(ActionEvent event) throws IOException {
        try {
            tfEmail.setText(user.geteMAIL());
            String email = tfEmail.getText();
            String sujet = tfSujet.getText();
            String description = taDescription.getText();
            String idUserText = tfidUser.getText();

            // Check if any of the fields are empty
            if (email.isEmpty() || sujet.isEmpty() || description.isEmpty() || idUserText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.show();
                return; // Return to stop further processing
            }
            try{
                WebEngine engine = webb.getEngine();
                String result = (String) engine.executeScript(
                        "function isRecaptchaVerified() {" +
                                " var isVerified = grecaptcha.getResponse().length > 0;" +
                                " return String(isVerified);" +
                                "} " +
                                "isRecaptchaVerified();"
                );
                if(result.equals("false")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("captcha");
                    alert.setContentText("s'il vous plais resoudre le captcha");
                    alert.showAndWait();
                    System.out.println("erreur");
                    return;

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            int idU = Integer.parseInt(idUserText);
            User user = new User();
            user.setIdU(idU);
            user.getIdU();
           // int idUA = 27;
            User userA = new User();
           // user.setIdU(idUA);

            System.out.println(userA.geteMAIL());
            // Validate email format
            if (isValidEmail(email)) {
                // Check if a similar Reclamation already exists in the database
                if (!serviceReclamation.exists(email, sujet, description)) {
                    // Créer une nouvelle instance de Reclamation
                    Reclamation reclamation = new Reclamation(sujet, description, new Date(), email, user);

                    System.out.println(reclamation);
                    // Ajouter la reclamation à la base de données
                    serviceReclamation.create(reclamation);

                    create()
                            .styleClass(
                                    "-fx-background-color: #28a745; " + // Couleur de fond
                                            "-fx-text-fill: white; " + // Couleur du texte
                                            "-fx-background-radius: 5px; " + // Bord arrondi
                                            "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                            "-fx-border-width: 2px;" // Largeur de la bordure
                            )
                            .title("la réclamation est ajouté avec succès")
                            .position(Pos.TOP_RIGHT) // Modifier la position ici
                            .hideAfter(Duration.seconds(20))
                            .show();
                    System.out.println(userA.getIdU());

                    System.out.println(userA.geteMAIL());


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Cette réclamation existe déjà !");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Adresse email invalide !");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    @FXML
    void retourAcueilR(ActionEvent event) throws IOException {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamation.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        AccueilReclamation profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bRetour.getScene().getWindow();
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


    public void openNouvelleMessage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessage.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       EnvoyerMessage profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bnouveauMessage.getScene().getWindow();
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


    public void OpenMesMessages(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageRecuClient.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        MessageRecuClient profileController = loader.getController();
        profileController.SetPage(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bMesMessages.getScene().getWindow();
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

    ////////////user :

    private User getAdminUser() {
        try {
            String query = "SELECT * FROM User WHERE role = 'Admin' LIMIT 1";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("idU"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setDateNaissance(rs.getDate("DateNaissance"));
        user.setNumTel(rs.getInt("numTel"));  // Ajoutez le numéro de téléphone si nécessaire
        user.seteMAIL(rs.getString("eMAIL"));
        user.setPasswd(rs.getString("passwd"));  // Correction ici
        user.setRole(rs.getString("role"));
        // Ajoutez d'autres attributs selon votre modèle de données
        return user;
    }
private int iduser;
    public void SetPage(User user) {
        this.user=user;
        iduser=user.getIdU();
        tfEmail.setText(user.geteMAIL());

    }
}




