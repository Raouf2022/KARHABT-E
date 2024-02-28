package edu.esprit.controllers;

import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Date;


public class Dashboard {
    @FXML
    private TextField tfcin;
    @FXML
    private ListView<?> tflistview2;
    @FXML
    private Button but_etat;

    @FXML
    private Button tfshow;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfmontant;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfregion;
    @FXML
    private Button tfvalider;
    @FXML
    private Button tfdelete;
    @FXML
    private Button tfmodify;
    @FXML
    private Button but_demande;


    @FXML
    private Button butt_dos;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private Pane most_inner_pane1;

    @FXML
    private HBox root;

    @FXML
    private TextField search;

    @FXML
    private Pane tfinnerPane;
    @FXML
    private ListView<Dossier> tflistview;
    @FXML
    private AnchorPane tfsideBar;
    private final ServiceDossier sp = new ServiceDossier();

    @FXML
    void SaveDossier(ActionEvent actionEvent) {
        int montant = Integer.parseInt(this.tfmontant.getText());
        int cin = Integer.parseInt(this.tfcin.getText());
        String nom = this.tfnom.getText();
        String prenom = this.tfprenom.getText();
        Date date = Date.valueOf(tfdate.getValue());
        String region = this.tfregion.getText();


        try {
            edu.esprit.entities.Dossier d = new Dossier(cin, nom, prenom, region, date, montant);
            this.sp.ajouter(d);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void ModifierDossier(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ModifierDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) tfmodify.getScene().getWindow();

        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();


    }

    @FXML
    void showdossier(ActionEvent event) {
        ObservableList<Dossier> dossierList = FXCollections.observableArrayList();
        int montant = Integer.parseInt(this.tfmontant.getText());
        int cin = Integer.parseInt(this.tfcin.getText());
        String nom = this.tfnom.getText();
        String prenom = this.tfprenom.getText();
        Date date = Date.valueOf(tfdate.getValue());
        String region = this.tfregion.getText();
        try {

            Dossier dossier = new Dossier(cin, nom, prenom, region, date, montant);
            dossierList.add(dossier);
            tflistview.setItems(dossierList);
            if (dossier != null) {
                tfshow.setGraphic(null);
            } else {
                GridPane gridPane = new GridPane();
                gridPane.add(new Text("CIN:"), 0, 0);
                //gridPane.add(new Text(dossier.getCin()), 0, 0);
                gridPane.add(new Text("Nom:"), 0, 1);
                gridPane.add(new Text(dossier.getNom()), 1, 1);
                gridPane.add(new Text("Prenom:"), 0, 2);
                gridPane.add(new Text(dossier.getPrenom()), 1, 2);
                gridPane.add(new Text("Region:"), 0, 3);
                gridPane.add(new Text(dossier.getRegion()), 1, 3);

                tfshow.setGraphic(gridPane);
            }
        } catch (Exception e) {
            // Set the items in the TableView

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void Etatdossier(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Etatdossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) but_etat.getScene().getWindow();

        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();

    }

    @FXML
    void SupprimerDossier(ActionEvent event) {

        final ServiceDossier sp = new ServiceDossier();
        Dossier d = (Dossier) tflistview.getSelectionModel().getSelectedItem();

        if (d != null) {
            try {

                sp.supprimer(d); // Assuming you have a method to delete a dossier
                tflistview.getItems().remove(d); // Remove from the ListView
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Error deleting dossier: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a dossier to delete.");
            alert.showAndWait();
        }


    }


    public void demandedoss(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/DemandeDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) but_demande.getScene().getWindow();

        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();

    }


}
