package edu.esprit.controllers;

import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceVoiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ModifierVoiture {

    @FXML
    private TextField TFmarque;
    @FXML
    private TextField TFmodele;
    @FXML
    private TextField TFcouleur;
    @FXML
    private TextField TFprix;
    @FXML
    private TextField TFimage;
    @FXML
    private TextField TFdescription;

    private Voiture voiture;
    private ServiceVoiture serviceVoiture;

    @FXML
    public void initialize() {
        serviceVoiture = new ServiceVoiture() ;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
        // Set the values from the Voiture object to the text fields
        TFmarque.setText(voiture.getMarque());
        TFmodele.setText(voiture.getModele());
        TFcouleur.setText(voiture.getCouleur());
        TFprix.setText(String.valueOf(voiture.getPrix()));
        TFimage.setText(voiture.getImg());
        TFdescription.setText(voiture.getDescription());
    }

    @FXML
    void ModifierVoitureAction(ActionEvent event) {
        // Update the Voiture object with new data from the text fields
        voiture.setMarque(TFmarque.getText());
        voiture.setModele(TFmodele.getText());
        voiture.setCouleur(TFcouleur.getText());
        voiture.setPrix(Double.parseDouble(TFprix.getText()));
        voiture.setImg(TFimage.getText());
        voiture.setDescription(TFdescription.getText());

        // Call the modifier method from the service to update the car
        serviceVoiture.modifier(voiture);

        // Show confirmation alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Voiture updated successfully.");
        alert.setOnCloseRequest(dialogEvent -> retourAfficherVoiture()); // Redirect when the alert is closed
        alert.showAndWait();
    }

    private void retourAfficherVoiture() {
        try {
            // Load the Afficher Voiture view
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoiture.fxml"));
            TFmarque.getScene().setRoot(root); // Assuming TFmarque is part of the current scene
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Afficher Voiture view.");
            alert.showAndWait();
        }
    }


    @FXML
    void ImageImportAction(ActionEvent event) {
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


                TFimage.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
}
}
