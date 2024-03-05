package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageRecuAdmin {

    @FXML
    private Label emptyLabel;

    @FXML
    private Pagination fxPagination;

    @FXML
    private Button rButton;

    @FXML
    private TilePane repRecTP;

    @FXML
    private Text textGestion1;

    private ServiceMessagerie serviceMessagerie = new ServiceMessagerie();

    @FXML
    public void initialize() {
        // Appeler votre service pour obtenir la liste de messages
        // Replace with the actual sender's ID

        int receiverId = 27; // Exemple d'ID de récepteur
        List<Messagerie> messagesByReceiver = serviceMessagerie.getActiveMessagesByReceiver(receiverId);
        // Continuez avec la logique d'affichage existante, mais utilisez `messagesActifs`


        // Gérer l'affichage du message si la liste est vide
        if (messagesByReceiver.isEmpty()) {
            emptyLabel.setVisible(true);
            repRecTP.setVisible(false);
            fxPagination.setVisible(false); // Masquer la pagination si la liste est vide
        } else {
            emptyLabel.setVisible(false);
            repRecTP.setVisible(true);
            fxPagination.setVisible(true); // Afficher la pagination

            // Configurer la pagination
            int itemsPerPage = 2; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) messagesByReceiver.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(messagesByReceiver, pageIndex));
        }
    }

    // Méthode pour créer une page de la pagination
    private Node createPage(List<Messagerie> messages, int pageIndex) {
        int itemsPerPage = 2; // Nombre d'éléments par page
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, messages.size());

        // Convert the set to a list for subList
        List<Messagerie> messagesList = new ArrayList<>(messages);

        // Afficher les éléments de la liste dans la plage spécifiée
        List<Messagerie> currentPageData = messagesList.subList(fromIndex, toIndex);
        updateTilePane(currentPageData);

        return repRecTP; // Retourner le TilePane mis à jour comme une page de la pagination
    }

    // Méthode pour mettre à jour le TilePane avec les éléments de la page actuelle
    private void updateTilePane(List<Messagerie> currentPageData) {
        repRecTP.getChildren().clear();

        for (Messagerie message : currentPageData) {
            Node pane = createMessageriePane(message);
            repRecTP.getChildren().add(pane);
        }
    }

    // Méthode de création de page pour la pagination
    private Node createMessageriePane(Messagerie message) {
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(300, 200);
        mainVBox.getStyleClass().add("Messagerie-pane");
        mainVBox.setSpacing(10);  // Set the vertical spacing between the children

        // VBox for each attribute
        VBox contenuVBox = createAttributeVBox("Contenu", message.getContenu());
        VBox dateVBox = createAttributeVBox("Date", formatDate(message.getDateEnvoie()));



        // Buttons for modification and deletion

        // Add VBoxes and buttons to the main VBox
        mainVBox.getChildren().addAll(contenuVBox, dateVBox);
        if(message.isDeleted()) {
            Label deletedLabel = new Label("Supprimé");
            deletedLabel.getStyleClass().addAll("label", "label-deleted"); // Ensure you have CSS for this
            mainVBox.getChildren().add(deletedLabel);
        }

        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("Messagerie-pane");

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





    @FXML
    void retourEcrireMessages(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton cliqué
            Scene currentScene = rButton.getScene();

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

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
        return sdf.format(date);
    }

}
