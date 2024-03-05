package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifierMessagerieAdmin {

    @FXML
    private Button annulerB;

    @FXML
    private TextField contenumodifTF;

    @FXML
    private Button modifButton;

    @FXML
    private Text textGestion1;

    private Messagerie messagerie;
    private ServiceMessagerie serviceMessagerie;
    private Stage modifierStage;
    @FXML
    private ChoiceBox<?> trichoiceBox;

    public void setMessagerie(Messagerie messagerie, ServiceMessagerie serviceMessagerie) {
        this.messagerie = messagerie;
        this.serviceMessagerie = serviceMessagerie;

        // Initialize UI components with values from the Messagerie object
        contenumodifTF.setText(messagerie.getContenu());
        // You can initialize other UI components here if needed
    }

    public void setStage(Stage modifierStage) {
        this.modifierStage = modifierStage;
    }

    @FXML
    private void initialize() {
        // Additional initialization logic, if needed
    }

    @FXML
    private void modifiercontenuModif(ActionEvent event) {
        // Check if Messagerie object is not null before updating
        if (messagerie != null) {
            // Appliquer les modifications à la messagerie
            messagerie.setContenu(contenumodifTF.getText());
            // Appliquez les autres modifications aux autres attributs...

            // Check if the Sender is not null (assuming IDSender is a property of Messagerie)
            if (messagerie.getIDSender() != 0) {
                // Update the Messagerie object in the database
                serviceMessagerie.update(messagerie);

                // Fermer la fenêtre de modification
                modifierStage.close();
            } else {
                // Handle the case where Sender is null (log an error, show a message, etc.)
                System.err.println("Sender is null in messagerie.");
            }
        } else {
            // Handle the case where Messagerie object is null (log an error, show a message, etc.)
            System.err.println("Messagerie object is null.");
        }
    }

    @FXML
    private void handleAnnuler() {
        // Fermer la fenêtre de modification sans appliquer les modifications
        modifierStage.close();
    }
}