package edu.esprit.controllers;


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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;




import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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


    private Connection cnx = DataSource.getInstance().getCnx();


    @FXML
    void openMesReclamations(ActionEvent event) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/MesReclamations.fxml"));


            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bMesReclamations.getScene().getWindow();


            // Créer une transition de fondu
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            // Définir la nouvelle scène sur le stage et l'afficher
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    public void initialize() {
        tfidUser.setText("24");  // Remplacez "1" par l'ID de l'utilisateur que vous voulez tester

    }


    @FXML
    void validerReclamation(ActionEvent event) throws IOException {
        try {
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

            int idU = Integer.parseInt(idUserText);
            User user = new User();
            user.setIdU(idU);

            int idUA = 27;
            User userA = new User();
            user.setIdU(idUA);

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
                            .title("achat Ajouté avec succès")
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
    void retourAcueilR(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bRetour.getScene();

            // Configurer la transition
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
            transition.setFromX(-currentScene.getWidth());
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

    public void openNouvelleMessage(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/EnvoyerMessage.fxml"));


            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bnouveauMessage.getScene().getWindow();


            // Créer une transition de fondu
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            // Définir la nouvelle scène sur le stage et l'afficher
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenMesMessages(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/MessageRecuClient.fxml"));


            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bMesMessages.getScene().getWindow();


            // Créer une transition de fondu
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            // Définir la nouvelle scène sur le stage et l'afficher
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}




