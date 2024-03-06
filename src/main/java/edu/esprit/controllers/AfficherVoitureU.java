package edu.esprit.controllers;

import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceVoiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class AfficherVoitureU {
    @FXML
    private TextField marqueTextField;

    @FXML
    private TextField maxPrixTextField;

    @FXML
    private TextField minPrixTextField;

    @FXML
    private TextField modeleTextField;
    @FXML
    private Button btnArrivages;

    @FXML
    private Button rechercheButton;




    @FXML
    private HBox voitureTitlePane;

    private final ServiceVoiture sv = new ServiceVoiture();


    public void initialize() {
        voitureTitlePane.getChildren().clear();

        Set<Voiture> voitures = sv.getAll();

        if (voitures.isEmpty()) {

            voitureTitlePane.setVisible(false);
        } else {

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
        // Enclose the VBox in a Pane for border/styling if needed
        Pane pane = new Pane(vbox);
        pane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
return pane;
    }
    @FXML
    void rechercheAction(ActionEvent event) {
        Double minPrix = parseDouble(minPrixTextField.getText());
        Double maxPrix = parseDouble(maxPrixTextField.getText());
        String marque = marqueTextField.getText().trim();
        String modele = modeleTextField.getText().trim();

        // Appeler la fonction de recherche avancée
        Set<Voiture> result = sv.advancedSearch(minPrix, maxPrix, marque, modele);

        // Mettre à jour l'affichage avec les résultats de la recherche
        updateTilePane(new ArrayList<>(result));
    }

    private Double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void updateTilePane(ArrayList<Voiture> voitures) {
        voitureTitlePane.getChildren().clear(); // Effacer le contenu actuel

        if (voitures.isEmpty()) {
            // Aucune voiture trouvée, afficher un message ou effectuer une action appropriée
            Label noResultsLabel = new Label("Aucun résultat trouvé.");
            voitureTitlePane.getChildren().add(noResultsLabel);
        } else {
            // Des voitures ont été trouvées, afficher les résultats
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
    @FXML
    void navigatetoArrivageAction(ActionEvent event) {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherArrivageU.fxml"));
            btnArrivages.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé");
            alert.setTitle("Erreur");
            alert.show();


        }

    }









}










