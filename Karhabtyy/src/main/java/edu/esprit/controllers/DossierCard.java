package edu.esprit.controllers;

import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import edu.esprit.services.ServiceEtatDossier;
import edu.esprit.test.MainFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

    private Dossier d = new Dossier();

    private void setDossier() {

        fxxcin.setText(String.valueOf(d.getCin()));
        fxxname.setText(d.getNom());
        fxx2name.setText(d.getPrenom());
        fxxdate.setValue(d.getDate().toLocalDate());
        fxxregion.setText(d.getRegion());
        fxxmontant.setText(String.valueOf(d.getMontant()));
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ModifierDossier.fxml"));
            Parent root = fxmlLoader.load();
            ModifierDossier controller = fxmlLoader.getController();
           // controller.initialize(Dossier);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Commande");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

