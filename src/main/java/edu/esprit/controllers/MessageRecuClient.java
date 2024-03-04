package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
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

public class MessageRecuClient {




        @FXML
        private Button bEnvoyerMessage;

        @FXML
        private Button bLesReclamations;

        @FXML
        private Button bReponsesA;

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

        @FXML
        void openEnvoyerMessage(ActionEvent event) {
                try {
                        // Charger le fichier FXML de l'accueil
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerMessage.fxml"));
                        Parent root = loader.load();

                        // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
                        Scene scene = new Scene(root);

                        // Obtenir la scène actuelle à partir du bouton cliqué
                        Scene currentScene = bEnvoyerMessage.getScene();

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

        @FXML
        void openLesReclamations(ActionEvent event) {
                try {
                        // Charger le fichier FXML de l'accueil
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
                        Parent root = loader.load();

                        // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
                        Scene scene = new Scene(root);

                        // Obtenir la scène actuelle à partir du bouton cliqué
                        Scene currentScene = bLesReclamations.getScene();

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

        @FXML
        void openLesReponses(ActionEvent event) {

                        try {
                                // Charger le fichier FXML de l'accueil
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesReponesReclamations.fxml"));
                                Parent root = loader.load();

                                // Créer une nouvelle scène avec le contenu de AccuielReclamation.fxml
                                Scene scene = new Scene(root);

                                // Obtenir la scène actuelle à partir du bouton cliqué
                                Scene currentScene = bReponsesA.getScene();

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
        private List<Messagerie> messagesRecus = new ArrayList<>();

        // ... Autres champs et méthodes existants ...

        private void updateTilePane(List<Messagerie> currentPageData) {
                repRecTP.getChildren().clear();

                for (Messagerie message : currentPageData) {
                        Node pane = createMessagePane(message);
                        repRecTP.getChildren().add(pane);
                }
        }

        // Méthode de création de la structure d'affichage pour chaque message
        private Node createMessagePane(Messagerie message) {
                VBox mainVBox = new VBox();
                mainVBox.setPrefSize(300, 200);
                mainVBox.getStyleClass().add("message-pane");
                mainVBox.setSpacing(10);  // Set the vertical spacing between the children

                VBox contenuVBox = createAttributeVBox("Contenu", message.getContenu());
                VBox dateVBox = createAttributeVBox("Date", formatDate(message.getDateEnvoie()));
                VBox senderVBox = createAttributeVBox("Sender", message.getSender().getNom()); // Assurez-vous d'avoir la méthode getNom() dans la classe User

                mainVBox.getChildren().addAll(contenuVBox, dateVBox, senderVBox);

                // Add main VBox to a Pane
                Pane mainPane = new Pane(mainVBox);
                mainPane.getStyleClass().add("message-pane");

                return mainPane;
        }


        private String formatDate(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
                return sdf.format(date);
        }

        // Méthode utilitaire pour créer un VBox pour un attribut spécifique
        private VBox createAttributeVBox(String attributeName, String attributeValue) {
                VBox vBox = new VBox();
                Label attributeLabel = new Label(attributeName + ": ");
                Text attributeText = new Text(attributeValue);
                vBox.getChildren().addAll(attributeLabel, attributeText);

            return vBox;
        }


        public void ajouterMessageRecu(Messagerie nouveauMessage) {
                // Ajoutez le nouveau message à la liste des messages reçus
                messagesRecus.add(nouveauMessage);

                // Mettez à jour l'interface utilisateur pour afficher le nouveau message
                mettreAJourInterfaceUtilisateur();
        }

        private void mettreAJourInterfaceUtilisateur() {
                // Mettez à jour l'interface utilisateur en utilisant la liste des messages reçus
                System.out.println(repRecTP);

                repRecTP.getChildren().clear();

                // Parcourez la liste des messages reçus et ajoutez-les à l'interface utilisateur
                for (Messagerie message : messagesRecus) {
                        Node pane = createMessagePane(message);
                        repRecTP.getChildren().add(pane);
                }
        }



}

