package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(180, 120);
        mainVBox.getStyleClass().add("pane");

        // Labels for the main reclamation details
        Label sujetLabel = new Label("Sujet: " + reclamation.getSujet());
        sujetLabel.getStyleClass().addAll("label", "label-attribute");
        sujetLabel.getStyleClass().add("label-title");

        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
        descriptionLabel.getStyleClass().addAll("label", "label-attribute");

        Label dateLabel = new Label("Date: " + formatDate(reclamation.getDateReclamation()));
        dateLabel.getStyleClass().addAll("label", "label-attribute");

        Label emailLabel = new Label("Email: " + reclamation.getEmailUtilisateur());
        emailLabel.getStyleClass().addAll("label", "label-attribute");

        // Buttons for modification and deletion
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("button-modifier");
        modifierButton.setOnAction(event -> handleModifierButton(reclamation));

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("button-supprimer");
        supprimerButton.setOnAction(event -> handleSupprimerButton(reclamation.getIdR()));

        // Nested Pane for additional details
        Pane detailsPane = new Pane();
        detailsPane.getStyleClass().add("details-pane");
        detailsPane.setLayoutY(130); // Adjust the layout position as needed

        // Add details to the nested Pane
        Label additionalDetailsLabel = new Label("Additional Details:");
        additionalDetailsLabel.getStyleClass().add("label-title");
        additionalDetailsLabel.setLayoutX(10);
        additionalDetailsLabel.setLayoutY(10);

        // Add additional details specific to your application
        // For example:
        // Label detail1Label = new Label("Detail 1: " + reclamation.getDetail1());
        // detail1Label.getStyleClass().addAll("label", "label-attribute");
        // detail1Label.setLayoutX(10);
        // detail1Label.setLayoutY(30);

        // Add additional details labels to the nested Pane
        // detailsPane.getChildren().addAll(detail1Label);

        // Add labels and buttons to the main VBox
        mainVBox.getChildren().addAll(sujetLabel, descriptionLabel, dateLabel, emailLabel, modifierButton, supprimerButton, detailsPane);

        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);

        return mainPane;
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
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajout.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) reclamationsTilePane.getScene().getWindow();

            // Créer une transition de fondu
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            // Définir la nouvelle scène sur le stage et l'afficher
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openMessagerie(ActionEvent actionEvent) {
    }
}
