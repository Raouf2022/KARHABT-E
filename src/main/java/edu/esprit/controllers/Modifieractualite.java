package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import edu.esprit.entities.Actualite;
import edu.esprit.services.Actualiteservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    // Attribut pour stocker l'actualité à modifier
    @FXML
    private Actualite actualiteToModify;

    // Méthode pour définir l'actualité à modifier et mettre à jour le formulaire

    //pré-remplir les champs avec les valeurs de l'actualité à modifier.
    public void setActualiteToModify(Actualite actualite) {
        this.actualiteToModify = actualite;
        updateForm(); //Appel de la méthode pour mettre à jour le formulaire avec les données de l'actualité
    }





   // afficher les détails de l'actualité existante dans le formulaire, permettant à l'utilisateur de voir les informations actuelles
    private void updateForm() {
        if (actualiteToModify != null) {
            titre.setText(actualiteToModify.getTitre());
            image.setText(actualiteToModify.getImage()); // Assuming this is the path to the image
            contenue.setText(actualiteToModify.getContenue());
        }
    }

    @FXML
    void Modifieractualite(ActionEvent event) {
        // Récupérer les valeurs des champs
        String titreValue = titre.getText();
        String imageValue = image.getText();
        String contenueValue = contenue.getText();

        // Vérifier si les champs nécessaires sont non vides
        if (!titreValue.isEmpty() && !contenueValue.isEmpty() && actualiteToModify != null) {
            // Mettre à jour l'objet Actualite avec les nouvelles valeurs
            actualiteToModify.setTitre(titreValue);
            actualiteToModify.setImage(imageValue);
            actualiteToModify.setContenue(contenueValue);

            // Appeler la méthode modifier de votre classe (ActualiteService ?)
            modifier(actualiteToModify);

            goBack(event);
        } else {
            System.out.println("Le titre et le contenu ne peuvent pas être vides.");
        }
    }

    private void modifier(Actualite actualite) {

        Actualiteservice service = new Actualiteservice();
        try {
            // Appel de la méthode du service pour effectuer la modification dans la base de données
            service.modifier(actualite);
            System.out.println("Actualité modifiée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de l'actualité.");
        }
    }

    @FXML
    void Importerimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {

                Path resourcesDir = Path.of("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }


                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);


                image.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
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


    @FXML
    void initialize() {
        assert btnmodiff != null : "fx:id=\"btnmodiff\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert contenue != null : "fx:id=\"contenue\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Modifieractualite.fxml'.";
        assert titre != null : "fx:id=\"titre\" was not injected: check your FXML file 'Modifieractualite.fxml'.";

    }

}
