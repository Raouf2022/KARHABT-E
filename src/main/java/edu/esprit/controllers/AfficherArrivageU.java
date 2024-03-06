package edu.esprit.controllers;

import edu.esprit.entities.Arrivage;
import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceArrivage;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.Set;

public class AfficherArrivageU {

    @FXML
    private TilePane arrivageTilePane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox sidebar;
    @FXML
    private Label emptyLabel;

    private final ServiceArrivage serviceArrivage = new ServiceArrivage();


    public void initialize() {
        loadArrivages();
    }

    private void loadArrivages() {
        Set<Arrivage> arrivages = serviceArrivage.getAll();
        if (arrivages.isEmpty()) {
            emptyLabel.setVisible(true);
        } else {
            emptyLabel.setVisible(false);
            for (Arrivage arrivage : arrivages) {
                VBox arrivageBox = createArrivageBox(arrivage);
                arrivageTilePane.getChildren().add(arrivageBox);
            }
        }
    }

    private VBox createArrivageBox(Arrivage arrivage) {
        VBox box = new VBox(5); // VBox with spacing of 5 between elements
        box.getStyleClass().add("arrivage-box");
        box.setPadding(new Insets(10)); // Set padding for the VBox

        // Create and add labels for Arrivage details
        Label quantiteLabel = new Label("Quantité: " + arrivage.getQuantite());
        Label dateEntreeLabel = new Label("Date d'Entrée: " + arrivage.getDateEntree().toString());

        // Create and add labels for associated Voiture details
        Voiture voiture = arrivage.getV(); // Get the associated Voiture
        Label marqueLabel = new Label("Marque: " + voiture.getMarque());
        Label modeleLabel = new Label("Modèle: " + voiture.getModele());
        Label couleurLabel = new Label("Couleur: " + voiture.getCouleur());
        Label prixLabel = new Label("Prix: " + voiture.getPrix());

        // Style the labels (assuming you have appropriate CSS classes defined)
        quantiteLabel.getStyleClass().add("arrivage-detail");
        dateEntreeLabel.getStyleClass().add("arrivage-detail");
        marqueLabel.getStyleClass().add("voiture-detail");
        modeleLabel.getStyleClass().add("voiture-detail");
        couleurLabel.getStyleClass().add("voiture-detail");
        prixLabel.getStyleClass().add("voiture-detail");
        box.getChildren().addAll(quantiteLabel, dateEntreeLabel, marqueLabel, modeleLabel, couleurLabel, prixLabel);
return box;
    }
}

