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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class LesReclamationsAdmin {

    @FXML
    private Button statistiqueButton;
    @FXML
    private BarChart<String, Number> voirLesStatistiquess;



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
    private DatePicker datePicker;

    @FXML
    private Button searchButton;




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
            int itemsPerPage = 3; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) reclamations.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(reclamations, pageIndex));

            int itemsPerPagee = 3; // Nombre d'éléments par page
            int pageCountt = (int) Math.ceil((double) reclamations.size() / itemsPerPagee);
            fxPagination.setPageCount(pageCountt);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(reclamations, pageIndex));

            // Configurer le gestionnaire d'événements pour le bouton de recherche
            searchButton.setOnAction(this::searchByDate);

            loadStatistiques();


        }
    }

    // Méthode pour créer une page de la pagination
    private Node createPage(Set<Reclamation> reclamations, int pageIndex) {
        int itemsPerPage = 3; // Nombre d'éléments par page
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
        mainVBox.setPrefSize(200, 100);
        mainVBox.getStyleClass().add("Reclamation-pane");
        mainVBox.setSpacing(13);  // Set the vertical spacing between the children
        // VBox for each attribute

        VBox sujetVBox = createAttributeVBox("Sujet", reclamation.getSujet());
        VBox descriptionVBox = createAttributeVBox("Description", reclamation.getDescription());
        VBox dateVBox = createAttributeVBox("Date", formatDate(reclamation.getDateReclamation()));
        VBox emailVBox = createAttributeVBox("Email", reclamation.getEmailUtilisateur());
        

        Button repondreButton = new Button("Répondre");
        repondreButton.setOnAction(event -> openReponseForm(reclamation));

        Button DetailsButton = new Button("->Détails");
        DetailsButton.setOnAction(event -> generatePDF(reclamation));


        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10);  // Set the horizontal spacing between the buttons
        // Ajout des boutons à l'HBox
        buttonHBox.getChildren().addAll(repondreButton, DetailsButton);

        // Ajout de tous les éléments au VBox principal
        mainVBox.getChildren().addAll(sujetVBox, descriptionVBox, dateVBox, emailVBox, buttonHBox);


  
        // Add main VBox to a Pane
        Pane mainPane = new Pane(mainVBox);
        mainPane.getStyleClass().add("reclamation-pane");

        return mainPane;
    }
    private void generatePDF(Reclamation reclamation) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Définir la police et la taille du texte
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Positions et dimensions
            float margin = 50;
            float yStart = 750;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20;
            float colWidth = tableWidth / 2;
            float textX = margin + 2;
            float textY = yStart - 15;

            // Dessiner le tableau
            // Lignes horizontales
            float nextY = yStart;
            for (int i = 0; i <= 4; i++) {
                contentStream.moveTo(margin, nextY);
                contentStream.lineTo(margin + tableWidth, nextY);
                contentStream.stroke();
                nextY -= rowHeight;
            }

            // Lignes verticales
            contentStream.moveTo(margin, yStart);
            contentStream.lineTo(margin, yStart - 4 * rowHeight);
            contentStream.moveTo(margin + colWidth, yStart);
            contentStream.lineTo(margin + colWidth, yStart - 4 * rowHeight);
            contentStream.moveTo(margin + tableWidth, yStart);
            contentStream.lineTo(margin + tableWidth, yStart - 4 * rowHeight);
            contentStream.stroke();

            // Ajouter le texte
            String[] labels = {"Sujet:", "Description:", "Date:", "Email:"};
            String[] values = {
                    reclamation.getSujet(),
                    reclamation.getDescription(),
                    formatDate(reclamation.getDateReclamation()),
                    reclamation.getEmailUtilisateur()
            };

            for (int i = 0; i < labels.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textX, textY - i * rowHeight);
                contentStream.showText(labels[i]);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12); // Police normale pour les valeurs
                contentStream.newLineAtOffset(textX + colWidth, textY - i * rowHeight);
                contentStream.showText(values[i]);
                contentStream.endText();
            }

            contentStream.close();

            // Sauvegarder le document PDF
            File file = new File("details_reclamation.pdf");
            document.save(file);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
    ////////metier :
    @FXML
    void voirLesStatistiques(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page des statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TriReclamations.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre modale
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement dans votre application
        }
    }

    @FXML
    void searchByDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            // Convert LocalDate to java.sql.Date
            java.sql.Date searchDate = java.sql.Date.valueOf(selectedDate);

            // Call your service to get the list of reclamations filtered by date
            Set<Reclamation> filteredReclamations = serviceReclamation.getByDate(searchDate);

            // Update the displayed list
            updateTilePane(new ArrayList<>(filteredReclamations));
        } else {
            // Handle the case where no date is selected
            System.out.println("Veuillez sélectionner une date");
        }
    }


    private void loadStatistiques() {
        // Supposons que getStatistiqueReclamations() est appelé ici et retourne Map<Date, Integer>
        Map<java.sql.Date, Integer> statistiques = serviceReclamation.getStatistiqueReclamations();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre de réclamations par date"); // Nom de la série

        for (Map.Entry<java.sql.Date, Integer> entry : statistiques.entrySet()) {
            // Convertir Date en String ou autre format si nécessaire
            String dateAsString = entry.getKey().toString();
            Integer count = entry.getValue();

            series.getData().add(new XYChart.Data<>(dateAsString, count));
        }

        voirLesStatistiquess.getData().add(series);
    }

}
