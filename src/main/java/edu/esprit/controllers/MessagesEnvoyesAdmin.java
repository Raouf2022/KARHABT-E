package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceMessagerie;
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


public class MessagesEnvoyesAdmin {

    @FXML
    private Button rButton;


    @FXML
    private Label emptyLabel;

    @FXML
    private Pagination fxPagination;

    @FXML
    private TilePane repRecTP;

    @FXML
    private Text textGestion1;
    private ServiceMessagerie serviceMessagerie;

    User user=new User();


    public MessagesEnvoyesAdmin() {
        // Initialize your service here
        this.serviceMessagerie = new ServiceMessagerie();
    }

    @FXML
    public void initialize() {
        // Appeler votre service pour obtenir la liste de messages
    // Replace with the actual sender's ID
     int idU=user.getIdU();
     List<Messagerie> messagesBySender = serviceMessagerie.getMessagesBySender(idU);

        // Gérer l'affichage du message si la liste est vide
        if (messagesBySender.isEmpty()) {
            emptyLabel.setVisible(true);
            repRecTP.setVisible(false);
            fxPagination.setVisible(false); // Masquer la pagination si la liste est vide
        } else {
            emptyLabel.setVisible(false);
            repRecTP.setVisible(true);
            fxPagination.setVisible(true); // Afficher la pagination

            // Configurer la pagination
            int itemsPerPage = 2; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) messagesBySender.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(messagesBySender, pageIndex));
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
        VBox receiverVBox = createAttributeVBox("Receiver", message.getReceiver().getNom() + " " + message.getReceiver().getPrenom());

        String vuStatus = message.isVu() ? "Vu" : "Non vu";
        VBox vuVBox = createAttributeVBox("Vu", vuStatus);

        // Buttons for modification and deletion
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("button-modifier");
        modifierButton.setOnAction(event -> handleModifierButton(message));

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("button-supprimer");
        supprimerButton.setOnAction(event -> handleSupprimerButton(message.getIdMessage()));

        // Add VBoxes and buttons to the main VBox
        mainVBox.getChildren().addAll(contenuVBox, dateVBox, receiverVBox,vuVBox, modifierButton,supprimerButton);

        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("Messagerie-pane");

        return mainPane;
    }

    private String formatDate(Date dateEnvoie) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
        return sdf.format(dateEnvoie);
    }

    private void handleSupprimerButton(int idMessage) {
        serviceMessagerie.delete(idMessage);
        // Supprimer la réclamation du TilePane
        removeMessageriePane(idMessage);

        // Rafraîchir l'interface principale après la suppression
        initialize();
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

    private void handleModifierButton(Messagerie message) {
        try {
            // Charger la fenêtre de modification depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMessagerieAdmin.fxml"));
            Pane root = loader.load();

            // Obtenir le contrôleur de la fenêtre de modification
            ModifierMessagerieAdmin controller = loader.getController();
            controller.setMessagerie(message, serviceMessagerie);

            // Créer une nouvelle scène et un nouveau stage pour la fenêtre de modification
            Stage modifierStage = new Stage();
            modifierStage.initModality(Modality.APPLICATION_MODAL);
            modifierStage.setTitle("Modifier le message");
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

    private void removeMessageriePane(int mssgId) {
        // Recherchez le Pane correspondant à la réclamation avec l'ID donné
        Pane paneToRemove = null;

        for (Node node : repRecTP.getChildren()) {
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                Messagerie messg = getMessagerieFromPane(pane);

                if (messg != null && messg.getIdMessage() == mssgId) {
                    paneToRemove = pane;
                    break;
                }
            }
        }


    // ... other methods ...

    // Méthode pour formater la date


}
    @FXML
    void retourEcrireMessages(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        AccueilReclamationAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) rButton.getScene().getWindow();
        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);
        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent
        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        // Play the fade out transition
        fadeOutTransition.play();
    }
    private Messagerie getMessagerieFromPane(Pane pane) {
        return null;
    }

    private int iduser;
    public void SetPageAdmin(User user) {
        this.user=user;
        iduser=user.getIdU();



    }

}
