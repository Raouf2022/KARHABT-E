package edu.esprit.controllers;
import java.io.IOException;
import edu.esprit.entities.Dossier;
import edu.esprit.entities.etatDeDossier;
import edu.esprit.services.ServiceDossier;
import edu.esprit.services.ServiceEtatDossier;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Date;

public class Etatdossier {

    @FXML
    private ListView<?> tflistview1;
    @FXML
    private Button but_demande;

    @FXML
    private Button but_etat;

    @FXML
    private Button butt_dos;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private HBox root;

    @FXML
    private TextField search;

    @FXML
    private Button tfdelete;

    @FXML
    private TextField tfetat;

    @FXML
    private Pane tfinnerPane;


    @FXML
    private Button tfmodify;

    @FXML
    private Button tfshow;

    @FXML
    private AnchorPane tfsideBar;

    @FXML
    private Button tfvalider;

    private final ServiceEtatDossier sp = new ServiceEtatDossier();

    @FXML
    void ModifierEtatDossier(MouseEvent event) {

    }

    @FXML
    void SaveEtatDossier(ActionEvent event) {

        String etat = this.tfetat.getText();

        try {
            edu.esprit.entities.etatDeDossier d = new etatDeDossier(etat);
            this.sp.ajouter(d);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void SupprimerEtatDossier(ActionEvent event) {
        final ServiceEtatDossier sp = new ServiceEtatDossier();
        etatDeDossier d = (etatDeDossier) tflistview1.getSelectionModel().getSelectedItem();

        if (d != null) {
            try {

                sp.supprimer(d); // Assuming you have a method to delete a dossier
                tflistview1.getItems().remove(d); // Remove from the ListView
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Error deleting etat: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a etat to delete.");
            alert.showAndWait();
        }


    }

    @FXML
    void showEtatdossier(ActionEvent event) {

    }

    @FXML
    void dashdossier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) butt_dos.getScene().getWindow();

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

    public void bttdossier(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) butt_dos.getScene().getWindow();

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

    public void demandedoss(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/DemandeDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) butt_dos.getScene().getWindow();

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
