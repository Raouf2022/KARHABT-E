package edu.esprit.controllers;

import edu.esprit.entities.Arrivage;
import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceArrivage;
import edu.esprit.services.ServiceVoiture;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CreateArrivageController {

    @FXML
    private TextField quantiteField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Voiture> voitureComboBox;

    private ServiceArrivage serviceArrivage = new ServiceArrivage();
    private ServiceVoiture serviceVoiture = new ServiceVoiture();
    @FXML
    private Button NouvelleV;
    @FXML
    public void initialize()
    {
        loadVoitures();
    }

    private void loadVoitures() {
        Set<Voiture> voitures = serviceVoiture.getAll();
        voitureComboBox.setItems(FXCollections.observableArrayList(voitures));
    }

    @FXML
    void handleAddAction(ActionEvent event) {
        if (voitureComboBox.getValue() == null || datePicker.getValue() == null || quantiteField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,  "Veuillez sélectionner une voiture, une date et entrer une quantité");
            alert.showAndWait();
            return;
        }

        int quantite = Integer.parseInt(quantiteField.getText());
        // Check if quantity is positive
        if (quantite <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La quantité doit être positive.");
            alert.showAndWait();
            return;
        }

        // Convert LocalDate to java.util.Date
        LocalDate selectedDate = datePicker.getValue();
        Date date = java.sql.Date.valueOf(selectedDate);

        // Check if the date is in the future
        if (date.after(new Date())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La date  ne doit être une date future.");
            alert.showAndWait();
            return;
        }

        Voiture voiture = voitureComboBox.getValue();
        Arrivage arrivage = new Arrivage(quantite, date, voiture);
        serviceArrivage.ajouter(arrivage);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Arrivage added successfully.");
        alert.showAndWait();

        // Navigate back to AfficherArrivage
        handleRetourAction(event);
    }

    @FXML
    void handleRetourAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherArrivage.fxml"));
            quantiteField.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Afficher Arrivage view.");
            alert.showAndWait();
        }
    }
    public void navigatetoAfficherArrivageAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherArrivage.fxml"));
            NouvelleV.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé");
            alert.setTitle("Erreur");
            alert.show();


        }
    }
    public void navigatetoAjouterVoitureAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterVoiture.fxml"));
            NouvelleV.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé");
            alert.setTitle("Erreur");
            alert.show();


        }
    }

}

