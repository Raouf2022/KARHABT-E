package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.esprit.entities.Actualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class Modifieractualite {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnmodiff;

    @FXML
    private TextArea contenue;

    @FXML
    private TextField image;

    @FXML
    private TextField titre;

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
    void Modifieractualite(ActionEvent event) {
       
            // Récupérer les valeurs des champs
            String titreValue = titre.getText();
            String imageValue = image.getText();
            String contenueValue = contenue.getText();

            // Vérifier si les champs nécessaires sont non vides
            if (!titreValue.isEmpty() && !contenueValue.isEmpty()) {
                // Créer un objet Actualite avec les valeurs des champs
                Actualite actualite = new Actualite();
                actualite.setTitre(titreValue);
                actualite.setImage(imageValue);
                actualite.setContenue(contenueValue);

                // Appeler la méthode modifier de votre classe (ActualiteService ?)
                modifier(actualite);
            } else {
                System.out.println("Le titre et le contenu ne peuvent pas être vides.");
            }
        }

    private void modifier(Actualite actualite) {
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


    @FXML
    void initialize() {
        assert btnmodiff != null : "fx:id=\"btnmodiff\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert contenue != null : "fx:id=\"contenue\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert titre != null : "fx:id=\"titre\" was not injected: check your FXML file 'Modifieractualite.fxml'.";

    }

}
