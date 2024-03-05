package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.tools.DataSource;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.controlsfx.control.Notifications.create;

public class EnvoyerMessage {
    @FXML
    private Button EnvoyerButton;

    @FXML
    private Button bAjouterE;

    @FXML
    private Button bLesMessages;

    @FXML
    private Button bMesReclamations;

    @FXML
    private Button bRetourA;

    @FXML
    private TextField contenuMTF;


    @FXML
    private Button messagesEnvoyéesButton;

    @FXML
    private Text textGestion1;

    private Connection cnx = DataSource.getInstance().getCnx();

    @FXML
    void EnvoyerMessageA(ActionEvent event) {
        // Récupérez le contenu du message à partir de l'interface utilisateur
        String contenuMessage = contenuMTF.getText();
        if (contenuMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez remplir le champ de contenu du message !");
            alert.show();
            return; // Return to stop further processing
        }


        // Récupérez l'utilisateur destinataire avec le rôle "Admin"
        User destinataire = getAdminUser();

        if (destinataire != null) {
            User utilisateurEmetteur = new User();
            utilisateurEmetteur.setIdU(24);

            // Créez une instance de Messagerie avec les informations récupérées
            Messagerie nouveauMessage = new Messagerie();
            nouveauMessage.setContenu(contenuMessage);
            nouveauMessage.setDateEnvoie(new Date()); // Utilisez la date actuelle
            nouveauMessage.setSender(utilisateurEmetteur);
            nouveauMessage.setReceiver(destinataire);
            nouveauMessage.setVu(false);
            nouveauMessage.setDeleted(false);

            // Appelez le service CRUD pour enregistrer le message dans la base de données
            ServiceMessagerie serviceMessagerie = new ServiceMessagerie();
            serviceMessagerie.create(nouveauMessage);

            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Message envoyé avec succès à l'administrateur.");
            create()
                    .styleClass(
                            "-fx-background-color: #28a745; " + // Couleur de fond
                                    "-fx-text-fill: white; " + // Couleur du texte
                                    "-fx-background-radius: 5px; " + // Bord arrondi
                                    "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                    "-fx-border-width: 2px;" // Largeur de la bordure
                    )
                    .title("Message envoyé avec succès à l'administrateur.")
                    .position(Pos.TOP_RIGHT) // Modifier la position ici
                    .hideAfter(Duration.seconds(20))
                    .show();
        } else {
            // Si aucun destinataire n'est trouvé
            System.out.println("Aucun administrateur trouvé pour l'envoi du message.");
        }
    }

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
        user.seteMAIL(rs.getString("eMAIL"));
        user.setRole(rs.getString("role"));
        // Add other attributes according to your data model
        return user;
    }


    @FXML
    void openAjouterR(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajout.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bAjouterE.getScene();

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

    @FXML
    void openLesMessages(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageRecuClient.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bLesMessages.getScene();

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

    @FXML
    void openMesReclamations(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bMesReclamations.getScene();

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

    @FXML
    void openMessageEnvoyé(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessagesEnvoyesClient.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bLesMessages.getScene();

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

    @FXML
    void retourAcueil(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bRetourA.getScene();

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
}
