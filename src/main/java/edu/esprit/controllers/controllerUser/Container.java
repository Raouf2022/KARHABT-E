package edu.esprit.controllers.controllerUser;


import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Container {

    @FXML
    private ImageView contImg;

    @FXML
    private TextField contMail;

    @FXML
    private TextField contNaissance;

    @FXML
    private TextField contNom;

    @FXML
    private TextField contPass;

    @FXML
    private TextField contPrenom;

    @FXML
    private TextField contRole;

    @FXML
    private TextField contTel;
    @FXML
    private Button delBtn;
    @FXML
    private Button versPageModif;
    @FXML
    private Button banUser;

    ServiceUser serviceUser = new ServiceUser();
    private User user;

    public void setContainer(User user) {
        contNom.setText(user.getNom());

        contPrenom.setText(user.getPrenom());
        contNaissance.setText(String.valueOf(user.getDateNaissance()));
        contTel.setText(Integer.toString(user.getNumTel()));
        contMail.setText(user.geteMAIL());
        contPass.setText(user.getPasswd());
        contRole.setText(user.getRole());
        String url1 = user.getImageUser();
        Image image;


        if (url1 != null && !url1.isEmpty()) {
            try {
                // If it's a file path
                File file = new File(url1);
                if (file.exists()) {
                    image = new Image(new FileInputStream(file));
                } else {
                    // Alternatively, if the URL is expected to be an HTTP URL, you can load it directly
                    image = new Image(url1);
                }
            } catch (FileNotFoundException e) {
                // Log error and load a default image
                System.err.println("File not found: " + e.getMessage());
                image = new Image(getClass().getResourceAsStream(user.getImageUser())); // Adjust this path
            }
        } else {
            // Load a default image if URL is null or empty
            image = new Image(getClass().getResourceAsStream(user.getImageUser())); // Adjust this path
        }

        // Set the image in your ImageView
        contImg.setImage(image);
        delBtn.setOnAction((event) -> {
            this.serviceUser.supprimerUser(user.getIdU());
            loadScene("/adminPage.fxml", event);

        });

        banUser.setOnAction((event)->{
            //System.out.println(user.getIdU());
            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierUser.fxml"));
            Parent root = loader.load();

            ModifierUser controller = loader.getController();
            controller.SetUsers(user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the modification page.");
            alert.showAndWait();
        }
        });

    }
    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

