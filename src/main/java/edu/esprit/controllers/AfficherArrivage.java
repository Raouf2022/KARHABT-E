package edu.esprit.controllers;

import edu.esprit.entities.Arrivage;
import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceArrivage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.fx.FXGraphics2D;

import java.io.IOException;
import java.util.Set;

public class AfficherArrivage {

    @FXML
    private TilePane arrivageTilePane;

    @FXML
    private Label emptyLabel;

    @FXML
    private VBox sidebar;
    @FXML
    private Button NouvelleV;
    @FXML
    private Canvas chartCanvas;
    private final ServiceArrivage serviceArrivage = new ServiceArrivage();

    @FXML
    public void initialize() {
        loadArrivages();
        drawChart();
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

        // Create the delete button with an action to delete this Arrivage
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(event -> deleteArrivage(arrivage.getIdA()));
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("modify-button");
        modifierButton.setOnAction(event -> navigateToModifierArrivage(arrivage));

        // Layout for buttons
        HBox buttonLayout = new HBox(10, modifierButton, deleteButton);

        // Add all the elements to the VBox
        box.getChildren().addAll(quantiteLabel, dateEntreeLabel, marqueLabel, modeleLabel, couleurLabel, prixLabel, buttonLayout);

        return box;
    }

    private void navigateToModifierArrivage(Arrivage arrivage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierArrivage.fxml"));
            Parent root = loader.load();

            ModifierArrivageController modifierArrivageController = loader.getController();
            modifierArrivageController.setCurrentArrivage(arrivage);
            modifierArrivageController.loadArrivageDetails(); // Ensure details are loaded after setting currentArrivage

            // Set the scene or add to the layout as needed
            Stage stage = (Stage) arrivageTilePane.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Modifier Arrivage view.");
            alert.showAndWait();
        }
    }



    private void deleteArrivage(int id) {
        // Show confirmation dialog before deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this arrivage?");
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceArrivage.supprimer(id);
                //loadArrivages();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/AfficherArrivage.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sidebar.getScene().setRoot(root);
            }
        });
    }

    @FXML
    private void retourAction(ActionEvent event) {
        try {
            // Load the Afficher Voiture view
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoiture.fxml"));
            sidebar.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the Afficher Voiture view.");
            alert.showAndWait();
        }
    }

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

    public void navigatetoAjouterArrivageAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterArrivage.fxml"));
            NouvelleV.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé");
            alert.setTitle("Erreur");
            alert.show();


        }
    }
    private void drawChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Assume serviceArrivage.getAll() returns all Arrivage instances
        Set<Arrivage> arrivages = serviceArrivage.getAll();
        for (Arrivage arrivage : arrivages) {
            dataset.addValue(arrivage.getQuantite(), "Quantité", arrivage.getV().getModele());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Quantité par Modèle de Voiture", // Chart title
                "Modèle", // Domain axis label
                "Quantité", // Range axis label
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        GraphicsContext gc = chartCanvas.getGraphicsContext2D();
        FXGraphics2D g2d = new FXGraphics2D(gc);
        g2d.clearRect(0, 0, (int) chartCanvas.getWidth(), (int) chartCanvas.getHeight());
        barChart.draw(g2d, new java.awt.Rectangle((int) chartCanvas.getWidth(), (int) chartCanvas.getHeight()));
    }

}