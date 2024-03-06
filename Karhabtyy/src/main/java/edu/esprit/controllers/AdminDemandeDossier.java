package edu.esprit.controllers;

import com.itextpdf.text.DocumentException;
import com.sun.javafx.iio.ImageFrame;
import edu.esprit.entities.Dossier;
import edu.esprit.entities.DossierDemande;
import edu.esprit.services.IDemandeDossier;
import edu.esprit.services.ServiceDemandeDossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AdminDemandeDossier {
    @FXML
    private ImageView cin_img;
    @FXML
    private Button AttesTrav;

    @FXML
    private ImageView AttesTrav_img;

    @FXML
    private Button DecReve;

    @FXML
    private ImageView DecReve_img;

    @FXML
    private TableColumn<?, ?> att_col;

    @FXML
    private Button but_demande;

    @FXML
    private Button butt_dos;

    @FXML
    private Button cerRetn;

    @FXML
    private ImageView cerRetn_img;

    @FXML
    private TableColumn<?, ?> certif_col;

    @FXML
    private Button cin;

    @FXML
    private TableColumn<?, ?> cin_col;



    @FXML
    private TableColumn<?, ?> dec_col;

    @FXML
    private Button etaatbutt;

    @FXML
    private TableColumn<?, ?> ext_col;

    @FXML
    private Button extnais;

    @FXML
    private ImageView extnais_img;

    @FXML
    private Pane most_inner_pane;


    @FXML
    private HBox root;

    @FXML
    private ListView<DossierDemande> tflistview2;
    @FXML
    private Button but_etat;


    @FXML
    private TextField search;

    @FXML
    private Button tfdelete;

    @FXML
    private Pane tfinnerPane;


    @FXML
    private Button tfmodify;


    @FXML
    private Button tfshowdemande;

    @FXML
    private AnchorPane tfsideBar;

    @FXML
    private Button tfvalider;


    private final ServiceDemandeDossier sp = new ServiceDemandeDossier();


    @FXML
    void ModifierDossier(MouseEvent event) {

    }

    @FXML
    void SaveEtatDossier(ActionEvent event) {

        Image Cin = this.cin_img.getImage();
        Image cerRetenu = this.cerRetn_img.getImage();
        Image AttTravail = this.AttesTrav_img.getImage();
        Image DecRevenu = this.DecReve_img.getImage();
        Image ExtNaissance = this.extnais_img.getImage();
        //ImageFrame.class.getResource()
        try {
            DossierDemande d = new DossierDemande(Cin.getUrl(), cerRetenu.getUrl(), AttTravail.getUrl(), DecRevenu.getUrl(), ExtNaissance.getUrl());
            this.sp.ajouter(d);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("Dossier added successfully!");
            successAlert.showAndWait();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void dossierback(ActionEvent actionEvent) throws IOException {

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

    public void Etatdossierback(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AdminEtatDossier.fxml"));
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


    }
    public void chooseImagecin () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image and display it
            Image imageCin = new Image(selectedFile.toURI().toString());
            cin_img.setImage(imageCin);

        }
    }
    @FXML
    void generateInvoice(Dossier event) throws IOException, DocumentException, SQLException {
        Dossier d = new Dossier();
        ServiceDossier sd = new ServiceDossier();
        sd.getAll();

        generateInvoice(d);
    }
    @FXML
    void chooseImageAttTrav(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            Image imageAttesTrav = new Image(selectedFile.toURI().toString());

            AttesTrav_img.setImage(imageAttesTrav);

        }
    }
    @FXML
    void chooseImageCertRev (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image and display it
            ;
            Image imagecerRetn = new Image(selectedFile.toURI().toString());

            cerRetn_img.setImage(imagecerRetn);
        }
    }

    @FXML
    void chooseImageDecRev (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image and display it

            Image imageDecReve = new Image(selectedFile.toURI().toString());

            DecReve_img.setImage(imageDecReve);

        }
    }

    @FXML
    void chooseImageExtNai (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image and display it

            Image imageExtNaiss = new Image(selectedFile.toURI().toString());

            extnais_img.setImage(imageExtNaiss);

        }
    }
    @FXML
    void demandeDossier(ActionEvent event) {

    }


}
