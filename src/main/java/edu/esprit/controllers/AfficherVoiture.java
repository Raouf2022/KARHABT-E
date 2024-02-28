package edu.esprit.controllers;

import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceVoiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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


// Appeler votre service pour obtenir la liste de voitures
        Set<Voiture> voitures = sv.getAll();

        // Gérer l'affichage du message si la liste est vide
        if (voitures.isEmpty()) {
            emptyLabel.setVisible(true);
            voitureTitlePane.setVisible(false);
        } else {

            emptyLabel.setVisible(false);
            voitureTitlePane.setVisible(true);
            // Ajouter des panes pour chaque voiture
            TilePane voituresTilePane = new TilePane();
            for (Voiture voiture : voitures) {
                Pane pane = createVoiturePane(voiture);
                voituresTilePane.getChildren().add(pane);
            }
            voitureTitlePane.getChildren().add(voituresTilePane);
        }
    }


// ...

    private Pane createVoiturePane(Voiture voiture) {
        Pane pane = new Pane();
        pane.setPrefSize(200, 240);  // Augmenter la hauteur à 200 pour accueillir l'ImageView
        pane.setStyle("-fx-border-color: blue; -fx-border-width: 2;");  // Ajouter une bordure
        pane.getStyleClass().add("pane");

        // Créer un ImageView pour afficher l'image de la voiture
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);  // Ajuster la largeur de l'ImageView
        imageView.setFitHeight(120);  // Ajuster la hauteur de l'ImageView
        imageView.setLayoutX(20);  // Positionner l'ImageView
        imageView.setLayoutY(10);  // Positionner l'ImageView

        // Définir l'image de l'ImageView à partir du chemin de l'image de la voiture
        Image image = new Image("file:" + voiture.getImg());
        imageView.setImage(image);

        // Ajouter l'ImageView au pane
        pane.getChildren().add(imageView);

        // Ajouter des étiquettes pour afficher les attributs de la voiture
        Label marqueLabel = new Label("Marque: " + voiture.getMarque());
        marqueLabel.getStyleClass().add("label");
        marqueLabel.getStyleClass().add("label-title");
        marqueLabel.setLayoutX(10);
        marqueLabel.setLayoutY(140);

        Label modeleLabel = new Label("Modele: " + voiture.getModele());
        modeleLabel.getStyleClass().add("label");
        modeleLabel.setLayoutX(10);
        modeleLabel.setLayoutY(160);

        Label couleurLabel = new Label("Couleur: " + voiture.getCouleur());
        couleurLabel.getStyleClass().add("label");
        couleurLabel.setLayoutX(10);
        couleurLabel.setLayoutY(180);

        Label prixLabel = new Label("Prix: " + voiture.getPrix());
        prixLabel.getStyleClass().add("label");
        prixLabel.setLayoutX(10);
        prixLabel.setLayoutY(200);

        Label descriptionLabel = new Label("Description: " + voiture.getDescription());
        descriptionLabel.getStyleClass().add("label");
        descriptionLabel.setLayoutX(10);
        descriptionLabel.setLayoutY(220);


        // Ajoutez les étiquettes au pane
        pane.getChildren().addAll(marqueLabel, modeleLabel, couleurLabel, prixLabel, descriptionLabel);
        // Créez un bouton Modifier
        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(120);  // Positionnez le bouton
        modifierButton.setLayoutY(210);  // Positionnez le bouton
        pane.getChildren().addAll(modifierButton);



        return pane;
    }

    @FXML

    public void navigatetoAjouterVoitureAction(ActionEvent event){




        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterVoiture.fxml"));
            NouvelleV.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Désolé");
            alert.setTitle("Erreur");
            alert.show();


        }
    }
}