package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class LesReclamationsAdmin {

    @FXML
    private Button bEnvoyerMessage;

    @FXML
    private Button bLesMessages;

    @FXML
    private Button bReponsesA;

    @FXML
    private Button bRetourA;

    @FXML
    private Label emptyLabel;


    @FXML
    private TilePane reclamationsTilePane;

    @FXML
    private Pagination fxPagination;

    @FXML
    private Text textGestion1;

    @FXML
    void openEnvoyerMessage(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessageAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bEnvoyerMessage.getScene().getWindow();

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

    @FXML
    void openLesMessages(ActionEvent event) {

    }

    @FXML
    void openLesReponsesAdmin(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReponsesReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) bReponsesA.getScene().getWindow();

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



    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    private Reclamation reclamation;

    @FXML
    public void initialize() {
        // Appeler votre service pour obtenir la liste de réclamations
        Set<Reclamation> reclamations = serviceReclamation.getAll();

        // Gérer l'affichage du message si la liste est vide
        if (reclamations.isEmpty()) {
            emptyLabel.setVisible(true);
            reclamationsTilePane.setVisible(false);
            fxPagination.setVisible(false); // Masquer la pagination si la liste est vide
        } else {
            emptyLabel.setVisible(false);
            reclamationsTilePane.setVisible(true);
            fxPagination.setVisible(true); // Afficher la pagination

            // Configurer la pagination
            int itemsPerPage = 4; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) reclamations.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(reclamations, pageIndex));
        }
    }

    // Méthode pour créer une page de la pagination
    private Node createPage(Set<Reclamation> reclamations, int pageIndex) {
        int itemsPerPage = 2; // Nombre d'éléments par page
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, reclamations.size());

        // Afficher les éléments de la liste dans la plage spécifiée
        List<Reclamation> currentPageData = new ArrayList<>(reclamations).subList(fromIndex, toIndex);
        updateTilePane(currentPageData);

        return reclamationsTilePane; // Retourner le TilePane mis à jour comme une page de la pagination
    }

    // Méthode pour mettre à jour le TilePane avec les éléments de la page actuelle
    private void updateTilePane(List<Reclamation> currentPageData) {
        reclamationsTilePane.getChildren().clear();

        for (Reclamation reclamation : currentPageData) {
            Node pane = createReclamationPane(reclamation);
            reclamationsTilePane.getChildren().add(pane);
        }
    }

    // Méthode de création de page pour la pagination
    private Node createReclamationPane(Reclamation reclamation) {
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(300, 200);
        mainVBox.getStyleClass().add("Reclamation-pane");
        mainVBox.setSpacing(10);  // Set the vertical spacing between the children
        // VBox for each attribute

        VBox sujetVBox = createAttributeVBox("Sujet", reclamation.getSujet());
        VBox descriptionVBox = createAttributeVBox("Description", reclamation.getDescription());
        VBox dateVBox = createAttributeVBox("Date", formatDate(reclamation.getDateReclamation()));
        VBox emailVBox = createAttributeVBox("Email", reclamation.getEmailUtilisateur());
        

        Button repondreButton = new Button("Répondre");
        repondreButton.setOnAction(event -> openReponseForm(reclamation));

        mainVBox.getChildren().addAll( sujetVBox, descriptionVBox, dateVBox, emailVBox, repondreButton);


  
        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("reclamation-pane");

        return mainPane;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
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



    @FXML
    void retourAcueilAdmin(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = bRetourA.getScene();

            // Configurer la transition
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
            transition.setFromX(-currentScene.getWidth());
            transition.setToX(0);

            // Afficher la nouvelle scène avec une transition
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(scene);

            // Démarrer la transition
            transition.play();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }

    }
}
