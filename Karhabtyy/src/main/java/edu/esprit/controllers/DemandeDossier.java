package edu.esprit.controllers;
import java.io.IOException;

import edu.esprit.entities.DossierDemande;
import edu.esprit.services.ServiceDemandeDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ListView<?> tflistview;
    @FXML
    private ListView<DossierDemande> tflistview2;

    @FXML
    private Button tfmodify;
    @FXML
    private Button etaatbutt;

    @FXML
    private Button tfshow;

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
    void SupprimerDossier(ActionEvent event) {

    }

    @FXML
    void demandeDossier(ActionEvent event) {

    }

    @FXML
    void showdossier(ActionEvent event) {
        ObservableList<edu.esprit.entities.DossierDemande> dossierList = FXCollections.observableArrayList();


        String Cin = this.urlcin.getText();
        String cerRetenu = this.urlCerRetenu.getText();
        String AttTravail = this.urlAttTravail.getText();
        String DecRevenu = this.urlDecRevenu.getText();
        String ExtNaissance = this.urlExtNaissance.getText();
        try {

            edu.esprit.entities.DossierDemande d = new DossierDemande(Cin, cerRetenu, AttTravail, DecRevenu,ExtNaissance);
            dossierList.add(d);
            tflistview2.setItems(dossierList);
            if (d != null) {
                tfshow.setGraphic(null);
            } else {
                GridPane gridPane = new GridPane();
                gridPane.add(new Text("CIN:"), 0, 0);
                //gridPane.add(new Text(dossier.getCin()), 0, 0);
                gridPane.add(new Text("CIN:"), 0, 1);
                gridPane.add(new Text(d.getUrlcin()), 1, 1);
                gridPane.add(new Text("Certificat de retenu :"), 0, 2);
                gridPane.add(new Text(d.getUrlCerRetenu()), 1, 2);
                gridPane.add(new Text("Attestation de travail:"), 0, 3);
                gridPane.add(new Text(d.getUrlAttTravail()), 1, 3);
                gridPane.add(new Text("Desc de revenu :"), 0, 4);
                gridPane.add(new Text(d.getUrlDecRevenu()), 1, 4);
                gridPane.add(new Text("extrait de naissance :"), 0, 5);
                gridPane.add(new Text(d.getUrlExtNaissance()), 1, 5);



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


}

