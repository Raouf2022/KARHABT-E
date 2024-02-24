package edu.esprit.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Actualite;
import edu.esprit.services.Actualiteservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddAct {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;

    @FXML
    private TextArea contenue;

    @FXML
    private ImageView img;

    @FXML
    private TextField importerimage;

    @FXML
    private TextField titre;

    @FXML
    void Annuler(ActionEvent event) {

    }

    @FXML
    void ajouterActualite(ActionEvent event) {
//        Actualite actualite = new Actualite(titre.getText(), contenue.getText());
//        Actualiteservice actualiteservice = new Actualiteservice();
//        try {
//            actualiteservice.ajouter(actualite);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("l'actualite a été ajouté avec succés");
//            alert.show();
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText(e.getMessage());
//            alert.show(); }

    }

    @FXML
    void importerimage(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}