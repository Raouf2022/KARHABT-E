package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
            int itemsPerPage = 4; // Nombre d'éléments par page
            int pageCount = (int) Math.ceil((double) reclamationsTriees.size() / itemsPerPage);
            fxPagination.setPageCount(pageCount);

            // Ajouter une usine de pages pour la pagination
            fxPagination.setPageFactory(pageIndex -> createPage(reclamationsTriees, pageIndex));
        }
    }

    // Méthode pour créer une page de la pagination
    private Node createPage(Set<Reclamation> reclamationsTriees, int pageIndex) {
        int itemsPerPage = 4; // Nombre d'éléments par page
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

        Button DetailsButton = new Button("->Détails");
        DetailsButton.setOnAction(event -> generatePDF(reclamationtriees));


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

            // Charger et ajouter l'image du logo
            try (InputStream logoStream = getClass().getResourceAsStream("/images/logoP.png")) {
                byte[] bytes = IOUtils.toByteArray(logoStream);
                PDImageXObject logoImage = PDImageXObject.createFromByteArray(document, bytes, "logoP");
                contentStream.drawImage(logoImage, 50, 700, 100, 100); // Ajustez la position et la taille selon vos besoins
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement de l'image du logo: " + e.getMessage());
            }

            // Reste du code pour ajouter texte, tableau, etc.

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.newLineAtOffset(200, 800);
            contentStream.showText("Détails de la Réclamation");
            contentStream.endText();

            // Définir la police et la taille pour les labels
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);

            // Positions et dimensions
            float margin = 50;
            float yStart = 750;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20;
            float colWidth = tableWidth / 2;
            float textX = margin + 2;
            float textY = yStart - 100; // Décalage après l'image du logo

            // Dessiner le tableau avec des lignes colorées
            contentStream.setStrokingColor(Color.LIGHT_GRAY); // Couleur des lignes

            // Ajout du texte avec distinction entre labels et valeurs
            String[] labels = {"Sujet:", "Description:", "Date:", "Email:", "Nom:", "Prénom:", "Date de Naissance:", "Numéro de Téléphone:"};
            User user = reclamation.getUser();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String[] values = {
                    reclamation.getSujet(),
                    reclamation.getDescription(),
                    sdf.format(reclamation.getDateReclamation()),
                    reclamation.getEmailUtilisateur(),
                    user.getNom(),
                    user.getPrenom(),
                    sdf.format(user.getDateNaissance()),
                    String.valueOf(user.getNumTel())
            };

            for (int i = 0; i < labels.length; i++) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12); // Labels en gras
                contentStream.newLineAtOffset(textX, textY - rowHeight * i);
                contentStream.showText(labels[i]);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12); // Valeurs en police normale
                contentStream.newLineAtOffset(textX + colWidth, textY - rowHeight * i);
                contentStream.showText(values[i]);
                contentStream.endText();
            }

            // Pied de page
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
            contentStream.setNonStrokingColor(Color.GRAY);
            contentStream.newLineAtOffset(250, 50);
            contentStream.showText("Page 1");
            contentStream.endText();

            contentStream.close();

            // Sauvegarder et ouvrir le document
            File file = new File("details_reclamation.pdf");
            document.save(file);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

