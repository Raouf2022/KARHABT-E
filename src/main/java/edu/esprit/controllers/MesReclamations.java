package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class MesReclamations {

    @FXML
    private TilePane reclamationsTilePane;

    @FXML
    private Label emptyLabel;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    public void initialize() {
        // Appeler votre service pour obtenir la liste de réclamations

        reclamationsTilePane.getChildren().clear();

        // Appeler votre service pour obtenir la liste de réclamations
        Set<Reclamation> reclamations = serviceReclamation.getAll();

        // Gérer l'affichage du message si la liste est vide
        if (reclamations.isEmpty()) {
            emptyLabel.setVisible(true);
            reclamationsTilePane.setVisible(false);
        } else {
            emptyLabel.setVisible(false);
            reclamationsTilePane.setVisible(true);

            // Ajouter des panes pour chaque réclamation
            for (Reclamation reclamation : reclamations) {
                Pane pane = createReclamationPane(reclamation);
                reclamationsTilePane.getChildren().add(pane);
            }
        }
    }

    private Pane createReclamationPane(Reclamation reclamation) {
        Pane pane = new Pane();
        pane.setPrefSize(180, 120);
        pane.getStyleClass().add("pane");  // Ajouter une classe CSS pour le style du pane

        // Ajouter des étiquettes pour afficher les attributs de la réclamation
        Label sujetLabel = new Label("Sujet: " + reclamation.getSujet());
        sujetLabel.getStyleClass().addAll("label", "label-attribute");
        sujetLabel.getStyleClass().add("label");
        sujetLabel.getStyleClass().add("label-title");
        sujetLabel.setLayoutX(10);
        sujetLabel.setLayoutY(40);

        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
   descriptionLabel.getStyleClass().addAll("label", "label-attribute");
        descriptionLabel.getStyleClass().add("label");
        descriptionLabel.setLayoutX(10);
        descriptionLabel.setLayoutY(60);

        Label dateLabel = new Label("Date: " + formatDate(reclamation.getDateReclamation()));
   dateLabel.getStyleClass().addAll("label", "label-attribute");
        dateLabel.getStyleClass().add("label");
        dateLabel.setLayoutX(10);
        dateLabel.setLayoutY(80);

        Label emailLabel = new Label("Email: " + reclamation.getEmailUtilisateur());
     emailLabel.getStyleClass().addAll("label", "label-attribute");
        emailLabel.getStyleClass().add("label");
        emailLabel.setLayoutX(10);
        emailLabel.setLayoutY(100);

        // Ajouter les étiquettes au pane
        pane.getChildren().addAll(sujetLabel, descriptionLabel, dateLabel, emailLabel);

        // Ajouter des boutons de modification et suppression
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("button-modifier");
        modifierButton.setLayoutX(10);
        modifierButton.setLayoutY(130);
        modifierButton.setOnAction(event -> handleModifierButton(reclamation));

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("button-supprimer");
        supprimerButton.setLayoutX(80);
        supprimerButton.setLayoutY(130);
        supprimerButton.setOnAction(event -> handleSupprimerButton(reclamation.getIdR()));

        pane.getChildren().addAll(modifierButton, supprimerButton);

        // Ajouter des gestionnaires d'événements pour les actions sur le Pane si nécessaire

        return pane;
    }

    private void handleModifierButton(Reclamation reclamation) {
        try {
        // Charger la fenêtre de modification depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));





            Pane root = loader.load();

            // Obtenir le contrôleur de la fenêtre de modification
            ModifierReclamation controller = loader.getController();
            controller.setReclamation(reclamation);

            // Créer une nouvelle scène et un nouveau stage pour la fenêtre de modification
            Stage modifierStage = new Stage();
            modifierStage.initModality(Modality.APPLICATION_MODAL);
            modifierStage.setTitle("Modifier Réclamation");
            modifierStage.setScene(new Scene(root));

            // Passer le stage à la fenêtre de modification pour pouvoir le fermer depuis le contrôleur
            controller.setStage(modifierStage);

            // Afficher la fenêtre de modification
            modifierStage.showAndWait();

            // Rafraîchir l'interface principale après la modification
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSupprimerButton(int reclamationId) {
        // Logique pour gérer l'action du bouton Supprimer
        // Utilisez reclamationId pour obtenir l'ID de la réclamation
        // Exemple : serviceReclamation.delete(reclamationId);
        serviceReclamation.delete(reclamationId);
    }

    // Méthode pour formater la date
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
        return sdf.format(date);
    }

    public void openNouvelleReclamation(ActionEvent actionEvent) {
    }

    public void openMessagerie(ActionEvent actionEvent) {
    }
}
