package edu.esprit.controllers;
import java.io.IOException;

import edu.esprit.entities.Dossier;
import edu.esprit.entities.DossierDemande;
import edu.esprit.services.ServiceDemandeDossier;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DemandeDossier {

    @FXML
    private Button but_demande;

    @FXML
    private ListView<DossierDemande> tflistview2;
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
    private Pane tfinnerPane;


    @FXML
    private Button tfmodify;
    @FXML
    private Button etaatbutt;

    @FXML
    private Button tfshowdemande;

    @FXML
    private AnchorPane tfsideBar;

    @FXML
    private Button tfvalider;

    @FXML
    private TextField urlAttTravail;

    @FXML
    private TextField urlCerRetenu;

    @FXML
    private TextField urlDecRevenu;

    @FXML
    private TextField urlExtNaissance;

    @FXML
    private TextField urlcin;
    private final ServiceDemandeDossier sp = new ServiceDemandeDossier();
    @FXML
    void Etatdossier(ActionEvent event) {


    }

    @FXML
    void ModifierDossier(MouseEvent event) {

    }

    @FXML
    void SaveEtatDossier(ActionEvent event) {

        String Cin = this.urlcin.getText();
        String cerRetenu = this.urlCerRetenu.getText();
        String AttTravail = this.urlAttTravail.getText();
        String DecRevenu = this.urlDecRevenu.getText();
        String ExtNaissance = this.urlExtNaissance.getText();

        try {
            DossierDemande d = new DossierDemande(Cin, cerRetenu, AttTravail, DecRevenu,ExtNaissance);
          this.sp.ajouter(d);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }



    @FXML
    void demandeDossier(ActionEvent event) {

    }



    public void dossierback(ActionEvent actionEvent) throws IOException {

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

    public void Etatdossierback(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/EtatDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) etaatbutt.getScene().getWindow();

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
    void SupprimerDemande(ActionEvent event) {
        final ServiceDemandeDossier sp = new ServiceDemandeDossier();
        edu.esprit.entities.DossierDemande d = (DossierDemande) tflistview2.getSelectionModel().getSelectedItem();

        if (d != null) {
            try {

                sp.supprimer(d); // Assuming you have a method to delete a dossier
                tflistview2.getItems().remove(d); // Remove from the ListView
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



    @FXML
    void showdemandedossier(ActionEvent event) {

        ObservableList<edu.esprit.entities.DossierDemande> dossierList = FXCollections.observableArrayList();

        String urlCin = this.urlcin.getText();
        String cerRetenu = this.urlCerRetenu.getText();
        String AttTravail = this.urlAttTravail.getText();
        String DecRevenu = this.urlDecRevenu.getText();
        String ExtNaissance = this.urlExtNaissance.getText();
        try {

            edu.esprit.entities.DossierDemande d = new DossierDemande(urlCin, cerRetenu, AttTravail, DecRevenu,ExtNaissance);
            dossierList.add(d);
            tflistview2.setItems(dossierList);
            if (d != null) {
                tfshowdemande.setGraphic(null);
            } else {
                GridPane gridPane = new GridPane();

                gridPane.add(new Text("Cin:"), 0, 1);
                gridPane.add(new Text(d.getUrlcin()), 1, 1);
                gridPane.add(new Text("cerRetenu:"), 0, 2);
                gridPane.add(new Text(d.getUrlCerRetenu()), 1, 2);
                gridPane.add(new Text("AttTravail:"), 0, 3);
                gridPane.add(new Text(d.getUrlAttTravail()), 1, 3);
                gridPane.add(new Text("DecRevenu:"), 0, 3);
                gridPane.add(new Text(d.getUrlDecRevenu()), 1, 3);
                gridPane.add(new Text("ExtNaissance:"), 0, 3);
                gridPane.add(new Text(d.getUrlExtNaissance()), 1, 3);

                tfshowdemande.setGraphic(gridPane);
            }
        } catch (Exception e) {
            // Set the items in the TableView

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

