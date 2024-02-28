package edu.esprit.controllers;

import edu.esprit.entities.Arrivage;
import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceArrivage;
import edu.esprit.services.ServiceVoiture;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class ModifierArrivageController {

    @FXML
    private TextField quantiteField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Voiture> voitureComboBox;
    @FXML
    private Button addButton;

    private ServiceArrivage serviceArrivage = new ServiceArrivage();
    private ServiceVoiture serviceVoiture = new ServiceVoiture();
    @FXML
    private Button NouvelleV;
    private Arrivage currentArrivage;

    @FXML
    public void initialize() {
        loadVoitures();
    }
    private void loadVoitures() {
        voitureComboBox.setItems(FXCollections.observableArrayList(serviceVoiture.getAll()));
    }

    public void setCurrentArrivage(Arrivage arrivage) {
        this.currentArrivage = arrivage;
    }

    public void loadArrivageDetails() {
        if (currentArrivage != null) {
            quantiteField.setText(String.valueOf(currentArrivage.getQuantite()));

            // Convert java.sql.Date to java.util.Date, then to LocalDate
            java.util.Date utilDate = new java.util.Date(currentArrivage.getDateEntree().getTime());
            datePicker.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            voitureComboBox.getSelectionModel().select(currentArrivage.getV());
        }
    }



    @FXML
    void handleAddAction(ActionEvent event) {
        int quantite = Integer.parseInt(quantiteField.getText());
        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Voiture selectedVoiture = voitureComboBox.getValue();

        currentArrivage.setQuantite(quantite);
        currentArrivage.setDateEntree(date);
        currentArrivage.setV(selectedVoiture);

        serviceArrivage.modifier(currentArrivage);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Arrivage updated successfully.");
        alert.showAndWait();

        navigateToAfficherArrivage();
    }

    private void navigateToAfficherArrivage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherArrivage.fxml"));
            addButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not load the Afficher Arrivage view.").showAndWait();
        }
    }

    @FXML
    void handleRetourAction(ActionEvent event) {
        navigateToAfficherArrivage();
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
}
