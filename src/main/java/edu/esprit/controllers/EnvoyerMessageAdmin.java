package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.tools.DataSource;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EnvoyerMessageAdmin implements Initializable {

    @FXML
    private Button EnvoyerButton;

    @FXML
    private Button LesReclamationButton;

    @FXML
    private Button bLesMessages;

    @FXML
    private Button bLesReponsesR;

    @FXML
    private Button bRetourA;

    @FXML
    private TextField contenuMTF;

    @FXML
    private ChoiceBox<User> destinataireChoiceBox;

    @FXML
    private Text textGestion1;


    @FXML
    private Button messagesEnvoyéesButton;
    private Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisez le ChoiceBox lors du chargement du contrôleur
        initialiserChoiceBox();
    }

    private void initialiserChoiceBox() {
        // Créez un faux utilisateur pour représenter l'option "Tous les clients"
        User tousLesClientsOption = new User();
        tousLesClientsOption.setNom("Tous les clients");
        tousLesClientsOption.setPrenom(""); // Vous pouvez laisser le prénom vide, ou mettre un texte explicite

        // Ajoutez l'option "Tous les clients" au début de la liste
        destinataireChoiceBox.getItems().add(tousLesClientsOption);

        // Utilisez votre connexion à la base de données pour récupérer les utilisateurs de rôle "client"
        List<User> clients = getUsersByRole("client");

        // Ajoutez les utilisateurs clients à la liste
        destinataireChoiceBox.getItems().addAll(clients);

        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        destinataireChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                return user != null ? user.getNom() + " " + user.getPrenom() : "";
            }

            @Override
            public User fromString(String string) {
                // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                return null;
            }
        });

        // Définissez la première option comme sélectionnée par défaut
        destinataireChoiceBox.getSelectionModel().selectFirst();

        // Ajoutez un écouteur pour gérer le changement de sélection
        destinataireChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (tousLesClientsOption.equals(newValue)) {
                // Si l'option "Tous les clients" est sélectionnée, ne faites rien ici
                // Vous pouvez ajouter un comportement supplémentaire si nécessaire
            } else {
                // Traitez la sélection normale ici
            }
        });
    }



    private List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM User WHERE role = ?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, role);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("idU"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setDateNaissance(rs.getDate("DateNaissance"));
        user.seteMAIL(rs.getString("eMAIL"));
        user.setRole(rs.getString("role"));
        // Ajoutez les autres attributs selon votre modèle de données
        return user;
    }


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


        // Récupérez l'utilisateur destinataire à partir du ChoiceBox
        User destinataire = destinataireChoiceBox.getValue();

        // Vérifiez si l'option "Tous les clients" est sélectionnée
        if (destinataire != null && destinataire.getNom().equals("Tous les clients")) {
            // Si "Tous les clients" est sélectionné, envoyez le message à tous les utilisateurs de rôle client
            List<User> clients = getUsersByRole("client");

            // Créez une instance de Messagerie pour chaque utilisateur client
            for (User client : clients) {
                User utilisateurEmetteur = new User();
                utilisateurEmetteur.setIdU(27);

                Messagerie nouveauMessage = new Messagerie();
                nouveauMessage.setContenu(contenuMessage);
                nouveauMessage.setDateEnvoie(new Date());
                nouveauMessage.setSender(utilisateurEmetteur);
                nouveauMessage.setReceiver(client); // Utilisez le client actuel comme destinataire
                nouveauMessage.setVu(false);
                nouveauMessage.setDeleted(false);

                // Appelez le service CRUD pour enregistrer le message dans la base de données
                ServiceMessagerie serviceMessagerie = new ServiceMessagerie();
                serviceMessagerie.create(nouveauMessage);
            }

            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Messages envoyés à tous les clients avec succès.");
        } else if (destinataire != null) {
            // Si un destinataire spécifique est sélectionné, envoyez le message uniquement à ce destinataire
            User utilisateurEmetteur = new User();
            utilisateurEmetteur.setIdU(27);

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
            MessageRecuClient messageRecuClientController = new MessageRecuClient() ;/* récupérez la référence au contrôleur MessageRecuClient */;
            messageRecuClientController.ajouterMessageRecu(nouveauMessage);
            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Message envoyé avec succès à l'administrateur.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Message Envoyé  avec succès !");
            alert.show();
// Affiche
            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Message envoyé avec succès à un destinataire spécifique.");
        } else {
            // Si aucun destinataire n'est sélectionné
            System.out.println("Veuillez sélectionner un destinataire.");
        }
    }



    @FXML
    void openLesMessages(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessagesEnvoyesAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) LesReclamationButton.getScene().getWindow();

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

    @FXML
    void openLesReclamationAdminintern(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReclamationsAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) LesReclamationButton.getScene().getWindow();

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

    @FXML
    void openLesReponsesR(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReponsesReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bLesReponsesR.getScene().getWindow();

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

    @FXML
    void retourAcueilAdmin(ActionEvent event) {

        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
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

    @FXML
    void openMessageEnvoyé(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessagesEnvoyesAdmin.fxml"));
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
