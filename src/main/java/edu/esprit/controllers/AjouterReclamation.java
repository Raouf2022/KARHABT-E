package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AjouterReclamation {

    @FXML
    private Button bMesReclamations;

    @FXML
    private Button bRetour;

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
    void openMessagerie(ActionEvent event) {

    }

    @FXML
    void quiter(ActionEvent event) {

    }



    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    public void initialize() {
        tfidUser.setText("24");  // Remplacez "1" par l'ID de l'utilisateur que vous voulez tester
    }


    @FXML
    void validerReclamation(ActionEvent event) {
        try {
            String email = tfEmail.getText();
            String sujet = tfSujet.getText();
            String description = taDescription.getText();

            String idUserText = tfidUser.getText();
            int idU;
            if (idUserText != null && !idUserText.isEmpty()) {
                idU = Integer.parseInt(idUserText);
                User user = new User(idU);


                // Créer une nouvelle instance de User avec l'ID récupéré


                // Créer une nouvelle instance de Reclamation
                Reclamation reclamation = new Reclamation(sujet, description, new Date(), email, user);
                // Ajouter la reclamation à la base de données
                serviceReclamation.create(reclamation);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Reclamation ajoutée avec succès !");
                alert.show();
            } else {
                System.out.println("ddddddddd");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

    }




