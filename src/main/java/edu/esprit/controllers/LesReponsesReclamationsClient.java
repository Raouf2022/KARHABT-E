package edu.esprit.controllers;

import edu.esprit.entities.ReponseReclamation;
import edu.esprit.entities.User;
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

public class LesReponsesReclamationsClient {
    @FXML
    private Button bRetourA;

    @FXML
    private Label emptyLabel;

    @FXML
    private Pagination fxPagination;

    @FXML
    private TilePane repRecTP;

    @FXML
    private Text textGestion1;
    User user=new User();

    private ServiceReponseReclamation serviceReponseReclamation= new ServiceReponseReclamation();

    // Setter for serviceReponseReclamation

    @FXML
    public void initialize() {
        if (serviceReponseReclamation == null) {
            // Log or handle the case where the service is not injected properly
            System.err.println("ServiceReponseReclamation is not injected!");
            return;
        }
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
            int itemsPerPage = 4; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) repReclamations.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(repReclamations, pageIndex));
        }
    }








    // Méthode pour créer une page de la pagination
    private Node createPage(Set<ReponseReclamation> repReclamations, int pageIndex) {
        int itemsPerPage = 4; // Nombre d'éléments par page
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


        // Add VBoxes and buttons to the main VBox
        mainVBox.getChildren().addAll(contenuVBox,  dateVBox);

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
    void retourAcueil(ActionEvent event) {
            try {
                // Charger le fichier FXML de l'accueil
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilReclamation.fxml"));
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
    private int iduser;
    public void SetPage(User user) {
        this.user=user;
        iduser=user.getIdU();


    }

    }

