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

import static org.controlsfx.control.Notifications.create;

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

    User user=new User();


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
            utilisateurEmetteur.setIdU(user.getIdU());

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
            System.out.println(nouveauMessage);
            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Message envoyé avec succès ");
            create()
                    .styleClass(
                            "-fx-background-color: #28a745; " + // Couleur de fond
                                    "-fx-text-fill: white; " + // Couleur du texte
                                    "-fx-background-radius: 5px; " + // Bord arrondi
                                    "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                    "-fx-border-width: 2px;" // Largeur de la bordure
                    )
                    .title("Message envoyé avec succès")
                    .position(Pos.TOP_RIGHT) // Modifier la position ici
                    .hideAfter(Duration.seconds(20))
                    .show();
            // Après avoir envoyé le message, notifier que le message a été envoyé
            GestionnaireNotifications.setMessageEnvoye(true);
// Affiche
            // Affichez un message ou effectuez toute autre action nécessaire après l'enregistrement du message
            System.out.println("Message envoyé avec succès à un destinataire spécifique.");
        } else {
            // Si aucun destinataire n'est sélectionné
            System.out.println("Veuillez sélectionner un destinataire.");
        }
    }


    @FXML
    void openLesMessages(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessagesEnvoyesAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        MessagesEnvoyesAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bLesMessages.getScene().getWindow();
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

    @FXML
    void openLesReclamationAdminintern(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReclamationsAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        LesReclamationsAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) LesReclamationButton.getScene().getWindow();
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

    @FXML
    void openLesReponsesR(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReponsesReclamations.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        LesReponsesReclamations profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bLesReponsesR.getScene().getWindow();
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

    @FXML
    void retourAcueilAdmin(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        AccueilReclamationAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bRetourA.getScene().getWindow();
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

    @FXML
    void openMessageEnvoyé(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesssagesEnvoyesAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
       MessageRecuAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bLesMessages.getScene().getWindow();
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
    private int iduser;
    public void SetPageAdmin(User user) {
        this.user=user;
        iduser=user.getIdU();



    }

    public void initialize() {
    }
}
