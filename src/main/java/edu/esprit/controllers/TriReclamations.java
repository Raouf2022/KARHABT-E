package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TriReclamations {
    @FXML
    private Label emptyLabel;

    @FXML
    private Pagination fxPagination;
    @FXML
    private TilePane reclamationsTilePane;



    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    private Set<Reclamation> reclamationsTriees;






    // Injectez le serviceReclamation lors de l'initialisation du contrôleur

    @FXML
    public void initialize() {
        // Appeler la méthode de tri lorsque le contrôleur est initialisé
        reclamationsTriees = serviceReclamation.triReclamationsParDate();


        // Gérer l'affichage du message si la liste est vide
        if (reclamationsTriees.isEmpty()) {
            emptyLabel.setVisible(true);
            reclamationsTilePane.setVisible(false);
            fxPagination.setVisible(false); // Masquer la pagination si la liste est vide
        } else {
            emptyLabel.setVisible(false);
            reclamationsTilePane.setVisible(true);
            fxPagination.setVisible(true); // Afficher la pagination

            // Configurer la pagination
            int itemsPerPage = 3; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) reclamationsTriees.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(reclamationsTriees, pageIndex));
        }
    }

    // Méthode pour créer une page de la pagination
    private Node createPage(Set<Reclamation> reclamationsTriees, int pageIndex) {
        int itemsPerPage = 3; // Nombre d'éléments par page
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, reclamationsTriees.size());

        // Afficher les éléments de la liste dans la plage spécifiée
        List<Reclamation> currentPageData = new ArrayList<>(reclamationsTriees).subList(fromIndex, toIndex);
        updateTilePane(currentPageData);

        return reclamationsTilePane; // Retourner le TilePane mis à jour comme une page de la pagination
    }

    // Méthode pour mettre à jour le TilePane avec les éléments de la page actuelle
    private void updateTilePane(List<Reclamation> currentPageData) {
        reclamationsTilePane.getChildren().clear();

        for (Reclamation reclamationtriee : currentPageData) {
            Node pane = createReclamationPane(reclamationtriee);
            reclamationsTilePane.getChildren().add(pane);
        }
    }

    // Méthode de création de page pour la pagination
    private Node createReclamationPane(Reclamation reclamationtriees) {
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(130, 100);
        mainVBox.getStyleClass().add("Reclamation-pane");
        mainVBox.setSpacing(13);  // Set the vertical spacing between the children
        // VBox for each attribute

        VBox sujetVBox = createAttributeVBox("Sujet", reclamationtriees.getSujet());
        VBox descriptionVBox = createAttributeVBox("Description", reclamationtriees.getDescription());
        VBox dateVBox = createAttributeVBox("Date", formatDate(reclamationtriees.getDateReclamation()));
        dateVBox.getChildren().get(0).setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
        VBox emailVBox = createAttributeVBox("Email", reclamationtriees.getEmailUtilisateur());


        Button repondreButton = new Button("Répondre");
        repondreButton.setOnAction(event -> openReponseForm(reclamationtriees));

        mainVBox.getChildren().addAll( sujetVBox, descriptionVBox, dateVBox, emailVBox, repondreButton);



        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("reclamation-pane");

        return mainPane;
    }

    private VBox createAttributeVBox(String label, String value) {
        VBox attributeVBox = new VBox();

        // Label for attribute
        Label attributeLabel = new Label(label + ":");
        attributeLabel.getStyleClass().addAll("label", "label-attribute");
        attributeLabel.getStyleClass().add("label-title");

        // Value for attribute
        Label attributeValueLabel = new Label(value);
        attributeValueLabel.getStyleClass().addAll("label", "label-attribute");

        // Add labels to the VBox
        attributeVBox.getChildren().addAll(attributeLabel, attributeValueLabel);

        return attributeVBox;
    }

    private void openReponseForm(Reclamation reclamation) {
        try {
            // Charger le fichier FXML du formulaire de réponse
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReponseReclamation.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur du formulaire
            AjoutReponseReclamation formulaireReponseController = loader.getController();

            // Passer la réclamation actuelle au contrôleur du formulaire
            formulaireReponseController.setReclamation(reclamation);

            // Créer une nouvelle scène avec le contenu du formulaire de réponse
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre modale
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Rafraîchir la liste des réclamations après la fermeture du formulaire (si nécessaire)
            // Peut-être utiliser un mécanisme d'observation ou actualiser manuellement la liste
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }
    }






    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        return dateFormat.format(date);
    }







}

