package edu.esprit.controllers;

import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceVoiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.io.File;
public class AjouterVoiture {

    @FXML
    private TextField TFcouleur;

    @FXML
    private TextField TFdescription;

    @FXML
    private TextField TFimage;

    @FXML
    private TextField TFmarque;

    @FXML
    private TextField TFmodele;

    @FXML
    private TextField TFprix;

    @FXML
    private Button btnAjouterV;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnImporter;






private final ServiceVoiture sv = new ServiceVoiture();
@FXML
    void ajouterVoitureAction(ActionEvent event)  {

    try {


        double prix = Double.parseDouble(TFprix.getText());

        sv.ajouter(new Voiture(TFmarque.getText(), TFmodele.getText(), TFcouleur.getText(), prix, TFimage.getText(), TFdescription.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("voiture ajouté avec succés");
        alert.show();
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoiture.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) btnAjouterV.getScene().getWindow();
        stage.setScene(newScene);  // Définir la nouvelle scène pour la fenêtre

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Exception");
        alert.setContentText(e.getMessage());
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

    @FXML
    private void retourAction(ActionEvent event) {
        try {
            // Load the Afficher Voiture view
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoiture.fxml"));
            btnRetour.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Afficher Voiture view.");
            alert.showAndWait();
        }
    }


}
