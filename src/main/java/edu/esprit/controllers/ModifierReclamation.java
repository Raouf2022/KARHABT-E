// ModifierReclamationController.java
package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierReclamation {

    @FXML
    private TextField sujetTextField;

    @FXML
    private TextField descriptionTextField;

    // Autres champs de saisie pour les autres attributs...

    private Reclamation reclamation;
    private Stage stage;

    ServiceReclamation serviceReclamation;

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        remplirChamps();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        this.serviceReclamation = new ServiceReclamation();
    }

    private void remplirChamps() {
        sujetTextField.setText(reclamation.getSujet());
        descriptionTextField.setText(reclamation.getDescription());
        // Remplissez les autres champs de saisie avec les valeurs actuelles...
    }

    @FXML
    private void handleEnregistrer() {
        // Appliquer les modifications à la réclamation
        reclamation.setSujet(sujetTextField.getText());
        reclamation.setDescription(descriptionTextField.getText());
        // Appliquez les autres modifications aux autres attributs...
        serviceReclamation.update(reclamation);
        // Fermer la fenêtre de modification
        Stage stage = (Stage) sujetTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnnuler() {
        // Fermer la fenêtre de modification sans appliquer les modifications
        Stage stage = (Stage) sujetTextField.getScene().getWindow();
        stage.close();
    }
}
