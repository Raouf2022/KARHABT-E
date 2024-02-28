
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
        fileChooser.setTitle("Sélectionner une image");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            TFimage.setText(imagePath); // Mettez à jour le champ de texte avec le chemin de l'image
        }

    }












}
