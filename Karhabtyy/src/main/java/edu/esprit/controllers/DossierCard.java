package edu.esprit.controllers;

import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class DossierCard {

    @FXML
    private Button buttadd;

    @FXML
    private Button buttdel;

    @FXML
    private TextField fxx2name;

    @FXML
    private TextField fxxcin;

    @FXML
    private DatePicker fxxdate;

    @FXML
    private TextField fxxmontant;

    @FXML
    private TextField fxxname;

    @FXML
    private TextField fxxregion;
    ServiceDossier serviceDossier = new ServiceDossier();


private Dossier dossier = new Dossier();
    public void setDossier(Dossier d){

        fxxcin.setText(String.valueOf(d.getCin()));
        fxxname.setText(d.getNom());
        fxx2name.setText(d.getPrenom());
        fxxdate.setValue(d.getDate().toLocalDate());
        fxxregion.setText(d.getRegion());
        fxxmontant.setText(String.valueOf(d.getMontant()));
    buttdel.setOnAction((event) -> {
        this.serviceDossier.supprimerid(d.getId_dossier());
    });

    }
    private void loadScene(String fxmlPath, ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
