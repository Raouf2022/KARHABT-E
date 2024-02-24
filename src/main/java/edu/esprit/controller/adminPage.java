

package edu.esprit.controller;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import java.util.Iterator;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class adminPage {
    @FXML
    private HBox userPane;
    public ServiceUser serviceUser = new ServiceUser();

    public adminPage() {
    }

    @FXML
    public void initialize() {
        this.userPane.setSpacing(70.0);
        this.userPane.getChildren().clear();
        Set<User> users = this.serviceUser.getAll();
        if (users.isEmpty()) {
            this.userPane.setVisible(false);
        } else {
            this.userPane.setVisible(true);
            Pane pane = new Pane();
            Iterator var3 = users.iterator();

            while(var3.hasNext()) {
                User user = (User)var3.next();
                Pane usersPane = this.createUsersPane(user);
                this.userPane.getChildren().add(usersPane);
            }

            this.userPane.getChildren().add(pane);
        }

    }

    private Pane createUsersPane(User user) {
        Pane pane = new Pane();
        pane.setMinWidth(Double.NEGATIVE_INFINITY);
        pane.getStyleClass().add("pane");
        Label nomLabel = new Label("Nom: " + user.getNom());
        nomLabel.getStyleClass().add("label");
        nomLabel.setLayoutX(10.0);
        nomLabel.setLayoutY(30.0);
        Label prenomLabel = new Label("Prénom: " + user.getPrenom());
        prenomLabel.getStyleClass().add("label");
        prenomLabel.setLayoutX(10.0);
        prenomLabel.setLayoutY(50.0);
        Label dateNaissanceLabel = new Label("Date de naissance: " + user.getDateNaissance());
        dateNaissanceLabel.getStyleClass().add("label");
        dateNaissanceLabel.setLayoutX(10.0);
        dateNaissanceLabel.setLayoutY(70.0);
        Label numTelLabel = new Label("Numéro de téléphone: " + user.getNumTel());
        numTelLabel.getStyleClass().add("label");
        numTelLabel.setLayoutX(10.0);
        numTelLabel.setLayoutY(90.0);
        Label emailLabel = new Label("E-mail: " + user.geteMAIL());
        emailLabel.getStyleClass().add("label");
        emailLabel.setLayoutX(10.0);
        emailLabel.setLayoutY(110.0);
        Label roleLabel = new Label("Role: " + user.getRole());
        emailLabel.getStyleClass().add("label");
        emailLabel.setLayoutX(10.0);
        emailLabel.setLayoutY(110.0);
        Button delBtn = new Button("Supprimer");
        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            delBtn.setLayoutY(newVal.doubleValue() - delBtn.getHeight());
        });
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            delBtn.setLayoutX((newVal.doubleValue() - delBtn.getWidth()) / 2.0);
        });
        delBtn.setOnAction((event) -> {
            this.serviceUser.supprimerUser(user.getIdU());
            this.userPane.getChildren().remove(pane);
        });
        pane.getChildren().addAll(new Node[]{nomLabel, prenomLabel, dateNaissanceLabel, numTelLabel, emailLabel, roleLabel, delBtn});
        return pane;
    }
}
