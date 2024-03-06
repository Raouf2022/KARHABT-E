package edu.esprit.controllers;

import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Dashboard {

    @FXML
    private Button but_demande;

    @FXML
    private Button but_etat;

    @FXML
    private Button butt_dos;

    @FXML
    private GridPane grid;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private HBox root;

    @FXML
    private TextField search;

    @FXML
    private Pane tfinnerPane;

    @FXML
    private AnchorPane tfsideBar;

    @FXML
    void Etatdossier(ActionEvent event) {

    }

    @FXML
    void demandedoss(ActionEvent event) {

    }

    public Dashboard() {
    }

    ServiceDossier serviceDossier = new ServiceDossier();

    public void loadDashboard() {

       Set<Dossier> dossierSet = serviceDossier.getAll();// Assuming this fetches all users

        grid.getChildren().clear(); // Clears existing content before loading new cards

        grid.setHgap(400);
        grid.setVgap(10);
        int row = 1;
        int col = 0;

        for (Dossier dossier : dossierSet) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DossierCard.fxml"));

                Parent DossierCard = loader.load();

                DossierCard controller = loader.getController();

                controller.setDossier(dossier);
                grid.add(DossierCard, col, row);
                col++;
                if (col == 1) {
                    col = 0;
                    grid.setVgap(20);
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize(){
        loadDashboard();
    }


}

