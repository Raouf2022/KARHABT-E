package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.ReponseReclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponseReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Date;

public class AjoutReponseReclamation {

    @FXML
    private Button bEnregistrer;

    @FXML
    private Button bRetourA;

    @FXML
    private TextArea contenuReponseField;

    @FXML
    private Text textGestion1;

    @FXML
    private TextField tfIdR;

    private Reclamation reclamation; // Réclamation à laquelle on répond

    private ServiceReponseReclamation serviceReponseReclamation = new ServiceReponseReclamation();

    // Méthode pour initialiser la réclamation à laquelle on répond
    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        tfIdR.setText(String.valueOf(reclamation.getIdR())); // Afficher l'ID de la réclamation dans le champ texte
    }

    @FXML
    void enregistrerReponse() {
        // Récupérer le contenu de la réponse depuis le champ TextArea
        String contenuReponse = contenuReponseField.getText();

        // Vérifier si le contenu de la réponse est vide
        if (contenuReponse.isEmpty()) {
            // Gérer le cas où le champ de réponse est vide (afficher un message d'erreur, etc.)
            System.out.println("Le champ de réponse ne peut pas être vide.");
            return;
        }

        // Créer un objet ReponseReclamation
        ReponseReclamation reponseReclamation = new ReponseReclamation();
        reponseReclamation.setReclamation(reclamation);
        reponseReclamation.setContenuReponse(contenuReponse);
        reponseReclamation.setDateReponseR(new Date());

        // Enregistrer la réponse dans la base de données
        serviceReponseReclamation.create(reponseReclamation);

        // Fermer la fenêtre du formulaire après l'enregistrement
        closeForm();
    }

    @FXML
    void retourAccueilAdmin() {
        // Fermer la fenêtre du formulaire sans enregistrer
        closeForm();
    }

    private void closeForm() {
        // Obtenir la scène actuelle à partir du bouton "Enregistrer" ou "Retour"
        Stage stage = (Stage) bEnregistrer.getScene().getWindow();

        // Fermer la fenêtre du formulaire
        stage.close();
    }
}