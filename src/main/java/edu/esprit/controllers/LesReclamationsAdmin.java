package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class LesReclamationsAdmin {

    @FXML
    private Button statistiqueButton;

    @FXML
    private ChoiceBox<String> trichoiceBox;
    @FXML
    private BarChart<String, Number> voirLesStatistiquess;

    @FXML
    private Button telechargerstat;

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
        trichoiceBox.getItems().addAll("Date", "Email");
        trichoiceBox.setValue("Date"); // Valeur par défaut

        // Ajouter un écouteur sur le ChoiceBox pour détecter les changements de sélection
        trichoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> trierReclamations(newVal));

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
            appliquerAnimationBouton(statistiqueButton);



        }
    }

    private void trierReclamations(String critereDeTri) {
        Set<Reclamation> reclamationsTriees;

        switch (critereDeTri) {
            case "Email":
                reclamationsTriees = serviceReclamation.triReclamationsParEmail();
                break;
            case "Date":
            default:
                reclamationsTriees = serviceReclamation.triReclamationsParDate();
                break;
        }

        // Mise à jour de l'affichage
        updateTilePane(new ArrayList<>(reclamationsTriees));
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

        Label titleLabel = new Label("Réclamation");
        titleLabel.getStyleClass().add("reclamation-title"); // Utilisez cette classe dans votre CSS pour styliser le titre


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
        Map<Date, Integer> statistiques = serviceReclamation.getStatistiqueReclamations();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre de réclamations par date"); // Nom de la série

        for (Map.Entry<Date, Integer> entry : statistiques.entrySet()) {
            // Convertir Date en String ou autre format si nécessaire
            String dateAsString = entry.getKey().toString();
            Integer count = entry.getValue();

            series.getData().add(new XYChart.Data<>(dateAsString, count));
        }

        voirLesStatistiquess.getData().add(series);

        // Assurez-vous que la mise à jour des couleurs se fait après l'ajout des données
        updateBarChartColors(voirLesStatistiquess);
    }

    // Assurez-vous d'inclure la méthode updateBarChartColors dans la même classe que loadStatistiques
    private void updateBarChartColors(BarChart<String, Number> barChart) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                double value = data.getYValue().doubleValue();
                if (value > 5) {
                    node.setStyle("-fx-bar-fill: red;"); // Rouge si la valeur dépasse 7
                } else {
                    node.setStyle("-fx-bar-fill: green;"); // Vert autrement
                }
            }
        }
    }

    private void appliquerAnimationBouton(Button bouton) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), bouton);
        fadeTransition.setFromValue(1.0); // Complètement opaque
        fadeTransition.setToValue(0.3); // Partiellement transparent
        fadeTransition.setAutoReverse(true); // Revenir à l'état initial après la transition
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE); // Répéter indéfiniment
        fadeTransition.play(); // Démarrer l'animation
    }



    @FXML
    public void telechargerStatististiques(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder les statistiques");
        fileChooser.setInitialFileName("statistiques_reclamations.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null); // Remplacer null par votre Stage

        if (file != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toURI()));
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withHeader("Date", "Nombre de Réclamations"))) {
                Map<Date, Integer> statistiques = serviceReclamation.getStatistiqueReclamations();
                statistiques.forEach((date, count) -> {
                    try {
                        csvPrinter.printRecord(date.toString(), count);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                csvPrinter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
