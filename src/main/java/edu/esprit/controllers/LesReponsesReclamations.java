package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.ReponseReclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponseReclamation;
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

public class LesReponsesReclamations {
    @FXML
    private Button LesReclamationButton;

    @FXML
    private Button bEnvoyerMessage;

    @FXML
    private Button bLesMessages;

    @FXML
    private Button bRetourA;

    @FXML
    private Label emptyLabel;

    @FXML
    private Pagination fxPagination;

    @FXML
    private Text textGestion1;

    @FXML
    private TilePane repRecTP;

    User user=new User();



    // Default constructor (no-argument)
    private ServiceReponseReclamation serviceReponseReclamation= new ServiceReponseReclamation();

    // Setter for serviceReponseReclamation

    @FXML
    public void initialize() {
        if (serviceReponseReclamation == null) {
            // Log or handle the case where the service is not injected properly
            System.err.println("ServiceReponseReclamation is not injected!");
            return;
        }


        // Appeler votre service pour obtenir la liste de réclamations
        Set<ReponseReclamation> repReclamations = serviceReponseReclamation.getAll();

        // Gérer l'affichage du message si la liste est vide
        if (repReclamations.isEmpty()) {
            emptyLabel.setVisible(true);
            repRecTP.setVisible(false);
            fxPagination.setVisible(false); // Masquer la pagination si la liste est vide
        } else {
            emptyLabel.setVisible(false);
            repRecTP.setVisible(true);
            fxPagination.setVisible(true); // Afficher la pagination

            // Configurer la pagination
            int itemsPerPage = 6; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) repReclamations.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(repReclamations, pageIndex));
        }
    }








    // Méthode pour créer une page de la pagination
    private Node createPage(Set<ReponseReclamation> repReclamations, int pageIndex) {
        int itemsPerPage = 6; // Nombre d'éléments par page
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, repReclamations.size());

        // Afficher les éléments de la liste dans la plage spécifiée
        List<ReponseReclamation> currentPageData = new ArrayList<>(repReclamations).subList(fromIndex, toIndex);
        updateTilePane(currentPageData);

        return repRecTP; // Retourner le TilePane mis à jour comme une page de la pagination
    }

    // Méthode pour mettre à jour le TilePane avec les éléments de la page actuelle
    private void updateTilePane(List<ReponseReclamation> currentPageData) {
        repRecTP.getChildren().clear();

        for (ReponseReclamation repReclamation : currentPageData) {
            Node pane = createReclamationPane(repReclamation);
            repRecTP.getChildren().add(pane);
        }
    }

    // Méthode de création de page pour la pagination
    private Node createReclamationPane(ReponseReclamation repReclamation) {
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(300, 200);
        mainVBox.getStyleClass().add("ReponseReclamation-pane");
        mainVBox.setSpacing(10);  // Set the vertical spacing between the children

        // VBox for each attribute

        VBox contenuVBox = createAttributeVBox("Contenu", repReclamation.getContenuReponse());

        VBox dateVBox = createAttributeVBox("Date", formatDate(repReclamation.getDateReponseR()));





        // Buttons for modification and deletion
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("button-modifier");
        modifierButton.setOnAction(event -> handleModifierButton(repReclamation));

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("button-supprimer");
        supprimerButton.setOnAction(event -> handleSupprimerButton(repReclamation.getIdReponseR()));

        // Add VBoxes and buttons to the main VBox
        mainVBox.getChildren().addAll(contenuVBox,  dateVBox,  modifierButton, supprimerButton);

        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("ReponseReclamation-pane");

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


    private void handleModifierButton(ReponseReclamation repReclamation) {
        try {
            // Charger la fenêtre de modification depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReponseReclamation.fxml"));

            Pane root = loader.load();

            // Obtenir le contrôleur de la fenêtre de modification
            ModifierReponseReclamation controller = loader.getController();
            controller.setReponseReclamation(repReclamation,serviceReponseReclamation);

            // Créer une nouvelle scène et un nouveau stage pour la fenêtre de modification
            Stage modifierStage = new Stage();
            modifierStage.initModality(Modality.APPLICATION_MODAL);
            modifierStage.setTitle("Modifier la réponse de  Réclamation");
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

    private void handleSupprimerButton(int repReclamationId) {
        // Logique pour gérer l'action du bouton Supprimer
        // Utilisez reclamationId pour obtenir l'ID de la réclamation
        // Exemple : serviceReclamation.delete(reclamationId);
        serviceReponseReclamation.delete(repReclamationId);

        // Supprimer la réclamation du TilePane
        removeReclamationPane(repReclamationId);

        // Rafraîchir l'interface principale après la suppression
        initialize();
    }

    private void removeReclamationPane(int reclamationId) {
        // Recherchez le Pane correspondant à la réclamation avec l'ID donné
        Pane paneToRemove = null;

        for (Node node : repRecTP.getChildren()) {
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                ReponseReclamation repReclamation = getReclamationFromPane(pane);

                if (repReclamation!= null && repReclamation.getIdReponseR() == reclamationId) {
                    paneToRemove = pane;
                    break;
                }
            }
        }

        // Supprimer le Pane du TilePane
        if (paneToRemove != null) {
            repRecTP.getChildren().remove(paneToRemove);
        }
    }




    private ReponseReclamation getReclamationFromPane(Pane pane) {
        // Extrayez les informations de la réclamation du Pane
        // (Vous devrez peut-être ajuster cela en fonction de votre structure de Pane)
        // Exemple : retourner une instance de Reclamation avec les informations extraites
        return null;
    }

    // Méthode pour formater la date
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
        return sdf.format(date);
    }

    @FXML
    void openLesReclamationAdminintern(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'ajout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReclamationsAdmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle et le stage
            Stage currentStage = (Stage) LesReclamationButton.getScene().getWindow();

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
    void retourAcueilAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Get the ProfileClient controller and set the user
        AccueilReclamationAdmin profileController = loader.getController();
        profileController.SetPageAdmin(user);
        System.out.println(user.getIdU());
        profileController.initialize();

        Scene newScene = new Scene(root);
        Stage stage = (Stage) bRetourA.getScene().getWindow();
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


    private int iduser;
    public void SetPageAdmin(User user) {
        this.user=user;
        iduser=user.getIdU();



    }
}
