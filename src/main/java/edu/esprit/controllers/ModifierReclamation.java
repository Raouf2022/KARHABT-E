package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierReclamation {

    @FXML
    private TextField sujetTextField;

    @FXML
    private TextArea taDescription;
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
        taDescription.setText(reclamation.getDescription());
        // Remplissez les autres champs de saisie avec les valeurs actuelles...
    }

    @FXML
    private void handleEnregistrer() {
        // Appliquer les modifications à la réclamation
        reclamation.setSujet(sujetTextField.getText());
        reclamation.setDescription(taDescription.getText());

        // Fetch the user from the database using email or another identifier
        User user =new User();
        user.setIdU(24);

        if (user != null) {
            reclamation.setUser(user);
            // Appliquez les autres modifications aux autres attributs...
            serviceReclamation.update(reclamation);
        } else {
            // Handle the case where the user is not found
            // You may want to show an error message or log the issue
            System.err.println("User not found for email: " + reclamation.getEmailUtilisateur());
        }

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
