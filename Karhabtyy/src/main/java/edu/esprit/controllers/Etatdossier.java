package edu.esprit.controllers;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import edu.esprit.entities.Dossier;
import edu.esprit.entities.etatDeDossier;
import edu.esprit.services.ServiceDossier;
import edu.esprit.services.ServiceEtatDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;

public class Etatdossier {

    @FXML
    private TableView<etatDeDossier> etat_table;
    @FXML
    private ComboBox<?> combo;
    @FXML
    private ListView<etatDeDossier> tflistview1;
    @FXML
    private Button but_demande;
    @FXML
    private TableColumn<?, ?> etat_col;
    @FXML
    private Button but_etat;
    @FXML
    private Pane dossier_table1;
    @FXML
    private Button butt_dos;

    @FXML
    private TableColumn<?, ?> idEtat_col;
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
    private Button tfshowetat;

    @FXML
    private AnchorPane tfsideBar;

    @FXML
    private Button tfvalider;

    private final ServiceEtatDossier sp = new ServiceEtatDossier();


    @FXML
    void ModifierEtatDossier(MouseEvent event) {
        try {
            ServiceEtatDossier sd = new ServiceEtatDossier();
            String etat = (String) combo.getValue();

            // Création d'un objet Dossier modifié




            // Appel d'une méthode de service ou de repository pour mettre à jour le dossier dans la base de données


            // Actualisation de l'affichage

        } catch (NumberFormatException e) {
        }

    }
    private Dossier d;
    public  void setId_dossier(Dossier d)
    {
        this.d=d;
    }
    @FXML
    void SaveEtatDossier(ActionEvent event) {
        String etat = String.valueOf(this.combo.getValue());

        if (etat != null && !etat.isEmpty()) {
            try {

                edu.esprit.entities.etatDeDossier d = new etatDeDossier(etat);

                this.sp.ajouter(d);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("Etat saved successfully!");
                successAlert.showAndWait();
            } catch (Exception e) {
                // Handle exceptions (e.g., database errors)
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setContentText("An error occurred while saving: " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            // Value is null or empty, show an alert
            Alert nullValueAlert = new Alert(Alert.AlertType.WARNING);
            nullValueAlert.setTitle("Warning");
            nullValueAlert.setContentText("Please select an etat before saving.");
            nullValueAlert.showAndWait();
        }
    }


    @FXML
    void SupprimerEtatDossier(ActionEvent event) throws SQLException {
/*
       final  ServiceEtatDossier sp = new ServiceEtatDossier();
   etatDeDossier e = (etatDeDossier) etat_table.getSelectionModel().getSelectedItem();

       // ObservableList<etatDeDossier> etatList = FXCollections.observableArrayList(sp.getAll());
        if(e != null)
        try {
            sp.supprimerD(e);

        } catch (Exception ee) {
            System.err.println(ee.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(ee.getMessage());
            alert.showAndWait();
        }
*/
        final ServiceEtatDossier sp = new ServiceEtatDossier();
        etatDeDossier d = etat_table.getSelectionModel().getSelectedItem();
        System.out.println(d.getId_etat());
        if (d != null) {
            try {
                //System.out.println(d.getId_etat());
                sp.supprimerD(d);

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
    public void showEtatdossier() throws SQLException {
        ServiceEtatDossier sp = new ServiceEtatDossier();

        ObservableList<etatDeDossier> etatList = FXCollections.observableArrayList(sp.getAll());

        try {
            for (etatDeDossier etatDeDossier : etatList) {
                System.out.println(etatList);
            }
            etat_table.setItems(etatList);
            etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
            idEtat_col.setCellValueFactory(new PropertyValueFactory<>("id_etat"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    public void displayDetailsInTextField() {
        etat_table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!etat_table.getSelectionModel().isEmpty())) {

                etatDeDossier selectedItem = etat_table.getSelectionModel().getSelectedItem();
                System.out.println(selectedItem.getId_etat());


            }
        });
    }

    @FXML
    void dashdossier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AdminDossier.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/AdminDossier.fxml"));
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

        Parent root = FXMLLoader.load(getClass().getResource("/AdminDemandeDossier.fxml"));
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


    public class MyController {
        @FXML
        private ComboBox<String> comboBox; // Your ComboBox in the FXML

        public void initialize() {
            // Clear existing items (optional)
            comboBox.getItems().clear();


            // Add choices
            comboBox.getItems().addAll("En cours", "Validé", "Non validé");

            // Set an initial selection (optional)
           // comboBox.getSelectionModel().select("Option B");
        }
    }
    public void initialize() throws SQLException {

        idEtat_col.setVisible(false);
    }
  }