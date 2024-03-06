package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.ReponseReclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponseReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Date;

import static org.controlsfx.control.Notifications.*;

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

    User user=new User();

    private Reclamation reclamation; // Réclamation à laquelle on répond

    private ServiceReponseReclamation serviceReponseReclamation = new ServiceReponseReclamation();

    // Méthode pour initialiser la réclamation à laquelle on répond
    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        tfIdR.setText(String.valueOf(reclamation.getIdR())); // Afficher l'ID de la réclamation dans le champ texte
    }

    @FXML
    void enregistrerReponse() {
        try {
            // Récupérer le contenu de la réponse depuis le champ TextArea
            String contenuReponse = contenuReponseField.getText();

            // Vérifier si le contenu de la réponse est vide
            if (contenuReponse.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.show();
                return; // Return to stop further processing
            }

            // Check if a similar response already exists in the database
            if (!serviceReponseReclamation.exists(reclamation.getIdR())) {
                // Créer un objet ReponseReclamation
                ReponseReclamation reponseReclamation = new ReponseReclamation();
                reponseReclamation.setReclamation(reclamation);
                reponseReclamation.setContenuReponse(contenuReponse);
                reponseReclamation.setDateReponseR(new Date());

                // Enregistrer la réponse dans la base de données
                serviceReponseReclamation.create(reponseReclamation);

               // Alert alert = new Alert(Alert.AlertType.INFORMATION);
              //  alert.setTitle("Success");
               // alert.setContentText("Reponse ajoutée avec succès !");
               // alert.show();
                create()
                        .styleClass(
                                "-fx-background-color: #28a745; " + // Couleur de fond
                                        "-fx-text-fill: white; " + // Couleur du texte
                                        "-fx-background-radius: 5px; " + // Bord arrondi
                                        "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                        "-fx-border-width: 2px;" // Largeur de la bordure
                        )
                        .title("la réponse est ajouté avec succès")
                        .position(Pos.TOP_RIGHT) // Modifier la position ici
                        .hideAfter(Duration.seconds(20))
                        .show();

                // Fermer la fenêtre du formulaire après l'enregistrement

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Une réponse pour cette réclamation existe déjà !");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private int iduser;
    public void SetPage(User user) {
        this.user=user;
        iduser=user.getIdU();



    }
}