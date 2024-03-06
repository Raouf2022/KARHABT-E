//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import java.util.Date;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Date;

import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AjouterDossier {
    @FXML
    private TextField tfcin;

    @FXML
    private ListView<Dossier> tflistview;

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
    private ImageView buttadd;

    @FXML
    private ImageView buttdel;

    @FXML
    private Pane dospane;
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
            Dossier d = new Dossier(cin, nom, prenom, region, date, montant);
            this.sp.ajouter(d);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

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

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /*public void displayDetailsInTextField() {
        tflistview.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tflistview.getSelectionModel().isEmpty()) {
                // Get the selected item
                Dossier selectedItem = tflistview.getSelectionModel().getSelectedItem();
                // Display the details in the text fields
                tfnom.setText(selectedItem.getNom());
                tfprenom.setText(selectedItem.getPrenom());
                tfregion.setText(selectedItem.getRegion());
               // tfdate.setDate(selectedItem.getDate());
               // tfmontant.parseInt(this.tfmontant.setText());
            }
        });
        }*/
    public void SupprimerDossier(ActionEvent actionEvent) {
        final ServiceDossier sp = new ServiceDossier();
        Dossier d = tflistview.getSelectionModel().getSelectedItem();

        if (d != null) {
            try {

                sp.supprimer(d); // Assuming you have a method to delete a dossier
                tflistview.getItems().remove(d); // Remove from the ListView
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Error deleting dossier: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a dossier to delete.");
            alert.showAndWait();
        }
    }

    public void ModifyDossier(ActionEvent actionEvent) {
        Dossier selectedDossier = tflistview.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            // Show a dialog or form to edit the dossier properties (cin, nom, prenom, etc.)
            // For example:
            // - Open a new window with input fields for editing
            // - Populate the input fields with the current values from selectedDossier
            // - When the user submits the changes, update the dossier properties

            // Example: Update the dossier's nom and prenom
            selectedDossier.setNom(selectedDossier.getNom());
            selectedDossier.setPrenom(selectedDossier.getPrenom());
            selectedDossier.setRegion(selectedDossier.getRegion());
            selectedDossier.setDate(selectedDossier.getDate());
            selectedDossier.setCin(selectedDossier.getCin());
            selectedDossier.setNom(selectedDossier.getNom());
            selectedDossier.setMontant(selectedDossier.getMontant());
            try {
                sp.modifier(selectedDossier); // Assuming you have a method to update a dossier
                // Refresh the ListView if needed
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Error modifying dossier: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a dossier to modify.");
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

    public void displayDetailsInTextField() {
        tflistview.setOnMouseClicked(event -> {            if (event.getClickCount() == 2 && (!tflistview.getSelectionModel().isEmpty())) {
                // Get the selected item
                Dossier selectedItem = (Dossier) tflistview.getSelectionModel().getSelectedItem();

                tfnom.setText(selectedItem.getNom());
                tfprenom.setText(selectedItem.getPrenom());
                tfregion.setText(selectedItem.getRegion());
                //tfcin.Integer.parseInt(selectedItem.getText());

                // Display the details in the text fields
               // privateKey.setText(selectedItem.getPrivateKey());
              //  subject.setText(selectedItem.getSubject());
               // title.setText(selectedItem.getTitre());
               // description.setText(selectedItem.getDescription());
            }
        });
    }


}





