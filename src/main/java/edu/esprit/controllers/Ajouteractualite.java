package edu.esprit.controllers;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.User;
import edu.esprit.services.Actualiteservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Ajouteractualite {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea contenue;

    @FXML
    private TextField image;

    @FXML
    private TextField titre;




//
//    public void initialize() {
//        // Initialisez le champ iduser avec l'ID de l'utilisateur
//        iduser.setText("12");
//
//    }





    @FXML
    void Ajouteractualite(ActionEvent event) {

        try {
            // Récupération des valeurs des champs
            String titreText = titre.getText();
            String contenueText = contenue.getText();
            String imageText = image.getText();
           // String idUserText = iduser.getText();

            // Validation des champs
            if (titreText.isEmpty() || contenueText.isEmpty() || imageText.isEmpty() ) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs.");
            }

            // Conversion de la chaîne ID utilisateur en entier
//            int idU =12;
//            User user =new User(idU);


            // Création de l'objet Actualite
            Actualite actualite = new Actualite(titreText, imageText,contenueText );

            // Appel du service pour ajouter l'actualité dans la base de données
            Actualiteservice actualiteservice = new Actualiteservice();
            actualiteservice.ajouter(actualite);

            // Affichage d'une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'actualité a été ajoutée avec succès");
            alert.show();
        } catch (Exception e) {
            // En cas d'erreur, affichage d'une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de l'ajout de l'actualité : " + e.getMessage());
            alert.show();
        }
    }



        @FXML
        void Importerimage(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                // Utilisez la variable de classe 'image' pour stocker le chemin de l'image
                image.setText(selectedFile.getAbsolutePath());
                // Vous pouvez également afficher l'image dans un ImageView si nécessaire
                // Exemple : imageView.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
            }
        }



    @FXML
    void goBack(ActionEvent event) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/listeactualite.fxml"));

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Obtenir le stage à partir de la scène actuelle
            Stage currentStage = (Stage) currentScene.getWindow();

            // Définir la nouvelle scène sur le stage et l'afficher
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}














