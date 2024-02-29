package edu.esprit.controllers;

import edu.esprit.entities.ReponseReclamation;
import edu.esprit.services.ServiceReponseReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ModifierReponseReclamation {
    @FXML
    private Button annulerButton;

    @FXML
    private TextArea contenuTArea;

    @FXML
    private Button enregistrerButton;

    private ReponseReclamation repReclamation;
    private Stage stage;
    private ServiceReponseReclamation serviceRepReclamation;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    void enregistrerReponseR(ActionEvent event) {
        // Mettre à jour le contenu de la réponse avec le texte de la zone de texte
        repReclamation.setContenuReponse(contenuTArea.getText());

        // Appeler le service pour mettre à jour la réponse dans la base de données
        serviceRepReclamation.update(repReclamation);

        // Fermer la fenêtre de modification
        stage.close();
    }

    @FXML
    private void annulerReponseR() {
        // Fermer la fenêtre de modification sans appliquer les modifications
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public void setReponseReclamation(ReponseReclamation repReclamation, ServiceReponseReclamation serviceRepReclamation) {
        // Initialiser les champs avec les valeurs de la réponse actuelle
        this.repReclamation = repReclamation;
        this.serviceRepReclamation = serviceRepReclamation;
        contenuTArea.setText(repReclamation.getContenuReponse());
    }

}
