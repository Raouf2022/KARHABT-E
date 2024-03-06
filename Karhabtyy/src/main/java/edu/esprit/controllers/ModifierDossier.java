package edu.esprit.controllers;

import com.sun.javafx.charts.Legend;
import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javax.swing.*;
/*
    public class ModifierDossier {
        @FXML
        private ListView<Dossier> tflistview; // Assuming this is your ListView

        private final ServiceDossier sp = new ServiceDossier();

        @FXML
        void modifySelectedDossier(ActionEvent event) {
            Dossier selectedDossier = tflistview.getSelectionModel().getSelectedItem();
            if (selectedDossier != null) {
                // Show a dialog or form to edit the dossier properties (cin, nom, prenom, etc.)
                // For example:
                // - Open a new window with input fields for editing
                // - Populate the input fields with the current values from selectedDossier
                // - When the user submits the changes, update the dossier properties

                // Example: Update the dossier's nom and prenom
                selectedDossier.setNom("New Nom");
                selectedDossier.setPrenom("New Prenom");

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

        public void SaveModifDossier(ActionEvent actionEvent) {
            Dossier selectedDossier = tflistview.getSelectionModel().getSelectedItem();
            if (selectedDossier != null) {
                // Show a dialog or form to edit the dossier properties (cin, nom, prenom, etc.)
                // For example:
                // - Open a new window with input fields for editing
                // - Populate the input fields with the current values from selectedDossier
                // - When the user submits the changes, update the dossier properties

                // Example: Update the dossier's nom and prenom
                selectedDossier.setNom("New Nom");
                selectedDossier.setPrenom("New Prenom");

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
    }


*/


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ModifierDossier {

    // Other fields and methods...

    @FXML
    private Button tfSaveModifDossier;

    @FXML
    private TextField tfcin;

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
    private Button tfbackmenu;
    @FXML
    private TableColumn<Dossier, Integer> cin_col;

    @FXML
    private TableColumn<Dossier, Date> date_col;

    @FXML
    private TableView<Dossier> dossier_table;

    @FXML
    private TableColumn<Dossier, Integer> montant_col;

    @FXML
    private TableColumn<Dossier, String> nom_col;

    @FXML
    private TableColumn<Dossier, String> prenom_col;

    @FXML
    private TableColumn<Dossier, String> region_col;


    @FXML
    private TextField tfcinm;

    @FXML
    private DatePicker tfdatem;

    @FXML
    private TextField tfmontantm;

    @FXML
    private TextField tfnomm;

    @FXML
    private TextField tfprenomm;

    @FXML
    private TextField tfregionm;

    @FXML
    private Button tfsaveDossier;

    @FXML
    void ModifierDossier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();
            ServiceDossier d = new ServiceDossier();


            // Assuming the root node in other_fxml_file.fxml is the ListView
            // TableView<Dossier> otherListView = (dossier_table<Dossier>) root;
            // otherListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//Dossier d = tflistview.getSelectionModel().getSelectedItem();
            // otherListViewgetSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Dossier>) c -> selectionChanged());
            //}

       /* private void selectionChanged() {
            ObservableList<Dossier> selectedItems = listView.getSelectionModel().getSelectedItems();
            String selectedText = selectedItems.isEmpty() ? "No Selected Item" : selectedItems.toString();
            selection.setText(selectedText);
        }*/
            // Now you can manipulate otherListView as needed
            // For example:
            int montant = Integer.parseInt(this.tfmontant.getText());
            int cin = Integer.parseInt(this.tfcin.getText());
            String nom = this.tfnom.getText();
            String prenom = this.tfprenom.getText();
            Date date = Date.valueOf(tfdate.getValue());
            String region = this.tfregion.getText();


            //  otherListView.getItems().add(new Dossier (cin, nom, prenom, region, date, montant));
            //  d.modifier(Dossier);

            ServiceDossier sd = new ServiceDossier();
            Dossier Dossier = new Dossier(cin, nom, prenom, region, date, montant);
            int montantm = Integer.parseInt(this.tfmontantm.getText());
            int cinm = Integer.parseInt(this.tfcinm.getText());
            String nomm = this.tfnomm.getText();
            String prenomm = this.tfprenomm.getText();
            Date datem = Date.valueOf(tfdatem.getValue());
            String regionm = this.tfregionm.getText();

            sd.modifier(Dossier);

            // Set up the stage and show the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void showdossier() throws SQLException {

        final ServiceDossier sp = new ServiceDossier();
        ObservableList<Dossier> dossierList = FXCollections.observableArrayList(sp.getAll());
        try {

            dossier_table.setItems(dossierList);
            cin_col.setCellValueFactory(new PropertyValueFactory<>("cin"));
            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            region_col.setCellValueFactory(new PropertyValueFactory<>("region"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            montant_col.setCellValueFactory(new PropertyValueFactory<>("montant"));
            dossier_table.setItems(dossierList);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    void backAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AjouterDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) tfbackmenu.getScene().getWindow();

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


