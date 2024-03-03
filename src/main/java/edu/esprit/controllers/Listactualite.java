package edu.esprit.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.services.Actualiteservice;
import edu.esprit.services.Commentaireservice;
import edu.esprit.services.Reponseservice;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Listactualite {

    @FXML
    private Button Ajouter;

    @FXML
    private VBox actualitesContainer;
    @FXML
    private TextField searchField;
    private Actualiteservice actualiteService;

    private Commentaireservice commentaireservice ;

    private Commentaire currentEditingComment = null;
    Reponseservice reponseService ;

    private Map<Integer, TextField> actualiteToTextFieldMap = new HashMap<>();
    private Map<Integer, Button> actualiteToButtonMap = new HashMap<>();
    @FXML
    void ajouteract(ActionEvent event) throws IOException {
        transitionToScene("/Ajouteractualite.fxml" , Ajouter);
    }
    @FXML
    public void initialize() throws SQLException {
        actualiteService = new Actualiteservice();
        commentaireservice = new Commentaireservice() ;
        reponseService = new Reponseservice() ;
        displayAllActualites();
    }

    private VBox createActualiteItem(Actualite actualite) {

        // Création de HBox
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER_LEFT);


        // Création d'une image view
        ImageView imageView = new ImageView();
        imageView.setFitHeight(105);
        imageView.setFitWidth(111);
        imageView.setPreserveRatio(true);
        try {

            //affichage de l'iamge
            File file = new File("src/main/resources/images/" + actualite.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hbox.getChildren().add(imageView);

        // Formatage de la date de publication
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = actualite.getDate_pub().format(formatter);

        // Création de VBox pour le texte de l'actualité
        VBox textVBox = new VBox(5);
        textVBox.setAlignment(Pos.CENTER_LEFT);

         // Ajout du titre, de la date et du contenu
        Label titleLabel = new Label(actualite.getTitre());
        titleLabel.setFont(new Font("Arial Rounded MT Bold", 12));
        titleLabel.setStyle("-fx-text-fill: white;"); // Définir la couleur du texte en blanc

        Label dateLabel = new Label(formattedDate);
        dateLabel.setFont(new Font("Arial Rounded MT Bold", 12));
        dateLabel.setStyle("-fx-text-fill: white;"); // Définir la couleur du texte en blanc

        Text contentText = new Text(actualite.getContenue());
        contentText.setFont(new Font("Arial Rounded MT Bold", 12));
        contentText.setWrappingWidth(246.85546875);

        textVBox.getChildren().addAll(titleLabel, contentText, dateLabel);
        hbox.getChildren().add(textVBox);


        // Création de HBox pour les boutons

        HBox buttonHBox = new HBox(5);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
          // Création des boutons
        Button deleteButton = createIconButton("TRASH", Color.RED, 20, this::handleDelete);
        deleteButton.setUserData(actualite);
        Button modifyButton = createIconButton("PENCIL", Color.BLACK, 20, this::handleModify);
        modifyButton.setUserData(actualite);

        // Ajout des boutons dans HBox
        buttonHBox.getChildren().addAll(modifyButton, deleteButton);
        hbox.getChildren().add(buttonHBox);


        //Création d'une boîte verticale (VBox) pour contenir l'ensemble de l'actualité
        VBox itemBox = new VBox(hbox);
        itemBox.getStyleClass().add("actualite-item");


        // Création de sections pour l'actualité et les commentaires
        VBox actualiteSection = new VBox();
        actualiteSection.getChildren().add(hbox);

        Label commentsTitle = new Label("Comments");
        commentsTitle.setFont(new Font("Arial", 16));
        commentsTitle.setStyle("-fx-text-fill: black;"); // Définir la couleur du texte en noir

        VBox commentsSection = new VBox(10);
        commentsSection.setPadding(new Insets(10, 0, 0, 20));
        commentsSection.getChildren().add(commentsTitle);



        // Récupération des commentaires pour cette actualité et affichage
        List<Commentaire> comments = commentaireservice.getCommentsForActualite(actualite.getIdAct());
        for (Commentaire comment : comments) {
            VBox commentBox = createCommentBox(comment);
            commentsSection.getChildren().add(commentBox);
        }

        // Création d'un champ de texte et d'un bouton pour ajouter un nouveau commentaire

        TextField newCommentField = new TextField();
        newCommentField.setPromptText("Write a comment...");
        actualiteToTextFieldMap.put(actualite.getIdAct(), newCommentField);


        Button submitCommentButton = new Button("Post");
        actualiteToButtonMap.put(actualite.getIdAct(), submitCommentButton);

        submitCommentButton.setOnAction(event -> {
            String commentText = newCommentField.getText();
            handleCommentAction(actualite, newCommentField, submitCommentButton, commentText, commentsSection);
        });

         // Ajout du champ de texte et du bouton à la section des commentaires
        commentsSection.getChildren().addAll(newCommentField, submitCommentButton);

        Rating rating = new Rating(5);
        rating.setPartialRating(true);

        double actualiteRating = actualite.getRating();

        System.out.println(actualiteRating);
        if (actualiteRating > 0) {
            rating.setRating(actualiteRating);
        } else {
            rating.setRating(0);
        }

        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            actualite.setRating(newValue.doubleValue());
            try {
                actualiteService.modifier(actualite);
                System.out.println("Rating updated for actualité " + actualite.getTitre());
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while updating the rating: " + e.getMessage());
            }
        });
        textVBox.getChildren().add(rating);

// Création d'une boîte verticale (VBox) pour contenir l'actualité et les commentaires
        VBox container = new VBox(actualiteSection, commentsSection);
        container.getStyleClass().add("actualite-item-container");

        return container;
    }


    private Button createIconButton(String iconGlyphName, Color iconColor, int iconSize, EventHandler<ActionEvent> actionHandler) {
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setGlyphName(iconGlyphName);
        icon.setSize(String.valueOf(iconSize));
        icon.setFill(iconColor);

        Button button = new Button();
        button.setGraphic(icon);
        button.setOnAction(actionHandler);

        return button;
    }
    private VBox createCommentBox(Commentaire comment) {

        //Box pour le commentaire et les reponses
        VBox commentBox = new VBox(5);
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);
// Image d'avatar pour l'utilisateur du commentaire
        ImageView avatarIcon = new ImageView(new Image("/images/user.png"));
        avatarIcon.setFitHeight(30);
        avatarIcon.setFitWidth(30);

  //nom de user ( 12 ) dans la base
        Label userName = new Label(comment.getUser().getNom());
        userName.setFont(new Font("Arial", 12));
        userName.setStyle("-fx-text-fill: #7A90A4;");

        // Contenu du commentaire
        Text commentContent = new Text(comment.getContenuec());

        // Icône de trois points
        FontAwesomeIconView threeDotsIcon = new FontAwesomeIconView();
        threeDotsIcon.setGlyphName("ELLIPSIS_V");
        threeDotsIcon.setSize("16");
        Button threeDotsButton = new Button("", threeDotsIcon);

        ContextMenu contextMenu = new ContextMenu();
        // Menu contextuel avec des options "Edit" et "Delete"
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> {
            TextField actualiteCommentField = actualiteToTextFieldMap.get(comment.getAct().getIdAct());
            Button actualiteSubmitButton = actualiteToButtonMap.get(comment.getAct().getIdAct());
            actualiteCommentField.setText(comment.getContenuec());
            actualiteSubmitButton.setText("Update"); //sera nommé Update

            currentEditingComment = comment;
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {

            try {
                // Supprimer le commentaire et masquer la boîte de commentaire
                commentaireservice.supprimer(comment.getIdComnt());
                commentBox.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        contextMenu.getItems().addAll(editItem, deleteItem);
        threeDotsButton.setOnAction(event -> contextMenu.show(threeDotsButton, Side.BOTTOM, 0, 0));

        // Bouton de réponse
        Button responseButton = new Button("Répondre");
        responseButton.getStyleClass().add("button-response");

        responseButton.setOnAction(event -> {
            //aaficahge chap reponse
            displayResponseField(comment, commentBox);
        });

      //  label +nombre de réponses
        Label responseCountLabel = new Label("Les réponses : " + reponseService.getNumberOfResponses(comment.getIdComnt()));
        responseCountLabel.getStyleClass().add("label-response-count");

        responseCountLabel.setOnMouseClicked(event -> {

            // Afficher les réponses au clic sur le nombre de réponses

            displayResponsesForComment(comment, commentBox);
        });
          // Ajout des éléments à la boîte d'en-tête (head)
        headerBox.getChildren().addAll(avatarIcon, userName, threeDotsButton, responseButton, responseCountLabel);
        commentBox.getChildren().addAll(headerBox, commentContent);

        return commentBox;
    }

    private void displayResponseField(Commentaire comment, VBox commentBox) {
        // Création d'un champ de texte pour la réponse
        TextField responseField = new TextField();
        responseField.setPromptText("Votre réponse...");
        responseField.getStyleClass().add("textfield-response");

        // Création d'un bouton d'envoi de réponse
        Button submitButton = new Button("Envoyer");
        submitButton.getStyleClass().add("button-response");

        submitButton.setOnAction(event -> {
            try {
                submitResponse(comment, responseField.getText(), commentBox);
                responseField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible d'ajouter la réponse: " + e.getMessage());
            }
        });

        // Ajout du champ de réponse et du bouton d'envoi à la boîte de commentaire
        commentBox.getChildren().addAll(responseField, submitButton);
    }

    private void displayResponsesForComment(Commentaire comment, VBox commentBox) {
        //chaque comment a responsesForComment

        Optional<VBox> existingResponsesContainer = commentBox.getChildren().stream()
                .filter(node -> node instanceof VBox && node.getStyleClass().contains("response-container"))
                .map(node -> (VBox) node)
                .findFirst();

        if (existingResponsesContainer.isPresent()) {
            //restourne l'affichage

            VBox responsesContainer = existingResponsesContainer.get();
            responsesContainer.setVisible(!responsesContainer.isVisible());
        } else {
            //creation Vbox vide

            VBox responsesContainer = new VBox();
            responsesContainer.getStyleClass().add("response-container");

               //a chaque reponse un hbox pour afficher liste des reps
            List<Reponse> responses = reponseService.getResponsesForComment(comment.getIdComnt());
            for (Reponse response : responses) {
                HBox responseBox = new HBox(5);
                responseBox.getStyleClass().add("response-item");

                // Ajout du nom de l'utilisateur qui a répondu et le contenu de la réponse
                Label responderName = new Label(response.getUser().getNom());
                Label responseContent = new Label(response.getContinueR());
                responseBox.getChildren().addAll(responderName, responseContent);

                // Création de boutons "Edit" et "Delete" pour chaque réponse
                Button editButton = new Button("Edit");
                editButton.setOnAction(event -> handleEditResponse(response, comment, responsesContainer));
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> handleDeleteResponse(response, comment, responsesContainer));


                   // Ajout des boutons à la boîte de réponse
                responseBox.getChildren().addAll(editButton, deleteButton);


                // Ajout de la boîte de réponse au conteneur de réponses
                responsesContainer.getChildren().add(responseBox);
            }


            commentBox.getChildren().add(responsesContainer);
        }
    }

    private void handleEditResponse(Reponse response, Commentaire comment, VBox responsesContainer) {
      //fonction edit
        TextInputDialog dialog = new TextInputDialog(response.getContinueR());
        dialog.setTitle("Edit Response");
        dialog.setHeaderText("Edit your response");
        dialog.setContentText("Response:");

        // Affichage de la boîte de dialogue et récupération de la nouvelle réponse
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newResponse -> {

            try {

                response.setContinueR(newResponse);

                reponseService.modifier(response);

                refreshResponses(comment, responsesContainer);
                showAlert("Response Edited", "The response has been updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while updating the response: " + e.getMessage());
            }
        });
    }

    private void refreshResponses(Commentaire comment, VBox responsesContainer) throws SQLException {
        //refresh reponse apres delete ou update

        List<Reponse> updatedResponses = reponseService.getResponsesForComment(comment.getIdComnt());


        responsesContainer.getChildren().clear();

        for (Reponse response : updatedResponses) {
            HBox responseBox = createResponseBox(response, comment, responsesContainer);
            responsesContainer.getChildren().add(responseBox);
        }
    }

    private void handleDeleteResponse(Reponse response, Commentaire comment, VBox responsesContainer) {
        //fonction delete

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Response");
        confirmAlert.setContentText("Are you sure you want to delete this response?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                reponseService.supprimer(response.getIdR());

                refreshResponses(comment, responsesContainer);

                showAlert("Response Deleted", "The response has been deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while deleting the response: " + e.getMessage());
            }
        }
    }



    private HBox createResponseBox(Reponse response, Commentaire comment, VBox responsesContainer) {
        //cration Hbox de espace reponse
        HBox responseBox = new HBox(5);
        responseBox.getStyleClass().add("response-item");


        Label responderName = new Label(response.getUser().getNom());
        Label responseContent = new Label(response.getContinueR());
        responseBox.getChildren().addAll(responderName, responseContent);


        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditResponse(response, comment, responsesContainer));
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteResponse(response, comment, responsesContainer));


        responseBox.getChildren().addAll(editButton, deleteButton);

        return responseBox;
    }





    private void submitResponse(Commentaire comment, String responseText, VBox responseInputArea) throws SQLException {
        //gère la soumission d'une nouvelle réponse
        if (responseText.isEmpty()) return;
        User user = commentaireservice.fetchUserById(24);
        Reponse reponse = new Reponse(responseText, comment, user);
        try {
            reponseService.ajouter(reponse);

            refreshResponseSection(comment, responseInputArea);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void refreshResponseSection(Commentaire comment, VBox responseContainer) throws SQLException {
        //refresh que la reponsesection quand l'ajout
        responseContainer.getChildren().clear();
            VBox responseBox = createCommentBox( comment);
            responseContainer.getChildren().add(responseBox);
    }
    private void handleModify(ActionEvent event) {
        //modifier actualite
        Button modifyButton = (Button) event.getSource();

        Actualite actualiteToModify = (Actualite) modifyButton.getUserData();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifieractualite.fxml"));
            Parent root = loader.load();


            Modifieractualite modifierController = loader.getController();

            modifierController.setActualiteToModify(actualiteToModify);


            Scene newScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAllActualites() throws SQLException {
        //afficahge de tout les  act
        actualitesContainer.getChildren().clear();
        List<Actualite> actualites = actualiteService.recuperer();
        for (Actualite actualite : actualites) {
            VBox actualiteItem = createActualiteItem(actualite);
            actualitesContainer.getChildren().add(actualiteItem);
        }
    }
    private void handleDelete(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        Actualite actualiteToDelete = (Actualite) sourceButton.getUserData();

          // Création d'une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Actualité");
        alert.setContentText("Are you sure you want to delete this actualité: " + actualiteToDelete.getTitre() + "?");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    actualiteService.supprimer(actualiteToDelete.getIdAct());
                    displayAllActualites(); // Refresh the list of actualités
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Deletion Error");
                    errorAlert.setContentText("There was an error deleting the actualité: " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        });
    }

    private void refreshCommentsSection(Actualite actualite, VBox commentsSection) throws SQLException {
        //refresh section quand ajouter comnt
        List<Commentaire> updatedComments = commentaireservice.getCommentsForActualite(actualite.getIdAct());
        commentsSection.getChildren().removeIf(node -> !(node instanceof TextField || node instanceof Button));
        for (Commentaire comment : updatedComments) {
            VBox commentBox = createCommentBox(comment);
            commentsSection.getChildren().add(0, commentBox);
        }
    }



    private void handleCommentAction(Actualite actualite, TextField newCommentField, Button submitCommentButton, String commentText, VBox commentsSection) {
        if (!commentText.isEmpty()) {
            try {
                if (currentEditingComment != null) {

                    currentEditingComment.setContenuec(commentText);
                    commentaireservice.modifier(currentEditingComment);
                    showAlert("Comment Updated", "Your comment has been updated successfully.");
                } else {

                    User user = commentaireservice.fetchUserById(12);
                    Commentaire newComment = new Commentaire(commentText, user, actualite);
                    commentaireservice.ajouter(newComment);
                }
                refreshCommentsSection(actualite, commentsSection);

                newCommentField.clear();
                submitCommentButton.setText("Post");
                currentEditingComment = null;
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        //fonction boite dialogue
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void onSearchKeyReleased(KeyEvent event) {
        //metierrechrche avec filtre
        String searchText = searchField.getText().toLowerCase();
        actualitesContainer.getChildren().clear();

        try {
            // Filtrage des actualités en fonction du texte de recherche
            List<Actualite> allActualites = actualiteService.recuperer();
            List<Actualite> filteredActualites = allActualites.stream()
                    .filter(actualite -> actualite.getTitre().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            for (Actualite actualite : filteredActualites) {
                VBox actualiteItem = createActualiteItem(actualite);
                actualitesContainer.getChildren().add(actualiteItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            showAlert("Error", "An error occurred while fetching the actualités: " + e.getMessage());
        }
    }

    private void transitionToScene(String fxmlPath , Button button) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene newScene = new Scene(root);
        Stage stage = (Stage) button.getScene().getWindow();
        root.setOpacity(0);
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0);

        fadeOutTransition.setOnFinished(e -> {
            stage.setScene(newScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1);
            fadeInTransition.play();
        });
        fadeOutTransition.play();
    }
}
