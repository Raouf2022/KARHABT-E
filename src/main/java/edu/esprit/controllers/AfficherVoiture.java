package edu.esprit.controllers;

import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceVoiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class AfficherVoiture {

    @FXML
    private Label emptyLabel;

    @FXML
    private HBox voitureTitlePane;
    @FXML
    private Button NouvelleV;

    private final ServiceVoiture sv = new ServiceVoiture();


    @FXML
    public void initialize() {
        voitureTitlePane.getChildren().clear();

        Set<Voiture> voitures = sv.getAll();

        if (voitures.isEmpty()) {
            emptyLabel.setVisible(true);
            voitureTitlePane.setVisible(false);
        } else {
            emptyLabel.setVisible(false);
            voitureTitlePane.setVisible(true);

            TilePane voituresTilePane = new TilePane();
            voituresTilePane.setPrefColumns(3); // Set the preferred number of columns to 3
            voituresTilePane.setHgap(10); // Horizontal gap between elements
            voituresTilePane.setVgap(10); // Vertical gap between elements
            voituresTilePane.setAlignment(Pos.TOP_CENTER); // Align to the top center

            for (Voiture voiture : voitures) {
                Pane pane = createVoiturePane(voiture);
                voituresTilePane.getChildren().add(pane);
            }

            voitureTitlePane.getChildren().add(voituresTilePane);
        }
    }



    private Pane createVoiturePane(Voiture voiture) {
        // Use VBox for vertical layout
        VBox vbox = new VBox(10); // Use VBox for vertical layout with spacing of 10
        vbox.setAlignment(Pos.CENTER); // Center the content
        vbox.setPadding(new Insets(10)); // Add padding around the VBox
        vbox.setPrefSize(200, 300); // Set a preferred size for VBox
        vbox.getStyleClass().add("car-pane"); // Add the CSS class for styling

        // Create an ImageView for the car image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(180);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("car-image");
        try {
            File file = new File("src/main/resources/images/" + voiture.getImg());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            // Consider having a default image if the specific image is not found.
        }

        vbox.getChildren().add(imageView); // Add ImageView to VBox

        // Create labels for car attributes
        Label marqueLabel = new Label("Marque: " + voiture.getMarque());
        Label modeleLabel = new Label("Modele: " + voiture.getModele());
        Label couleurLabel = new Label("Couleur: " + voiture.getCouleur());
        Label prixLabel = new Label("Prix: " + voiture.getPrix());
        Label descriptionLabel = new Label("Description: " + voiture.getDescription());

        // Add labels to VBox
        vbox.getChildren().addAll(marqueLabel, modeleLabel, couleurLabel, prixLabel, descriptionLabel);

        // Create a Modify button
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("modify-button");
        modifierButton.setOnAction(event -> navigatetoModifierVoiture(voiture));
        Button deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/delete_icon.png"))); // Replace with your delete icon path
        deleteIcon.setFitWidth(15);
        deleteIcon.setFitHeight(15);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(event -> confirmAndDeleteVoiture(voiture.getIdV()));
        HBox buttonLayout = new HBox(10, modifierButton, deleteButton);

        vbox.getChildren().add(buttonLayout);

        // Enclose the VBox in a Pane for border/styling if needed
        Pane pane = new Pane(vbox);
        pane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        return pane;
    }
    private void confirmAndDeleteVoiture(int voitureId) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this car?");
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sv.supprimer(voitureId);
            refreshVoitures(); // Refresh the list of cars
        }
    }
    private void refreshVoitures() {
        // Similar to the initial load, clear the existing content and repopulate
        voitureTitlePane.getChildren().clear();

        Set<Voiture> voitures = sv.getAll();
        if (voitures.isEmpty()) {
            emptyLabel.setVisible(true);
            voitureTitlePane.setVisible(false);
        } else {
            emptyLabel.setVisible(false);
            voitureTitlePane.setVisible(true);

            TilePane voituresTilePane = new TilePane();
            voituresTilePane.setPrefColumns(3);
            voituresTilePane.setHgap(10);
            voituresTilePane.setVgap(10);
            voituresTilePane.setAlignment(Pos.TOP_CENTER);

            for (Voiture voiture : voitures) {
                Pane pane = createVoiturePane(voiture);
                voituresTilePane.getChildren().add(pane);
            }

            voitureTitlePane.getChildren().add(voituresTilePane);
        }
    }

    private void navigatetoModifierVoiture(Voiture voiture) {
        try {
            // Load the modifier view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVoiture.fxml"));
            Parent root = loader.load();

            // Get the controller of the modify view and set the voiture
            ModifierVoiture controller = loader.getController();
            controller.setVoiture(voiture); // You will need to implement this method in ModifierVoiture

            // Set the scene to the modify view
            NouvelleV.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Modify Car view.");
            alert.showAndWait();
        }
    }

    @FXML

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