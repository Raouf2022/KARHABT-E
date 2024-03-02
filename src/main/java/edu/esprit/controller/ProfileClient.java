package edu.esprit.controller;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProfileClient {

    @FXML
    private DatePicker dateU;

    @FXML
    private ImageView imageU;

    @FXML
    private TextField mailU;

    @FXML
    private TextField mdpU;

    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField telU;
    ServiceUser serviceUser = new ServiceUser();
    private User user;private int iduser;


    @FXML
    void saveUserDetails(ActionEvent event) {

    }
    @FXML
    public void SetProfile(User user) {
this.user=user;
        iduser=user.getIdU();
        nomU.setText(user.getNom());
        prenomU.setText(user.getPrenom());
        dateU.setValue(LocalDate.parse(String.valueOf(user.getDateNaissance())));
        telU.setText(Integer.toString(user.getNumTel()));
        mailU.setText(user.geteMAIL());
        mdpU.setText(user.getPasswd());
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
        imageU.setImage(image);

    }
    private String imagePath;

    @FXML
    void choisirImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        String imagepath=null;
        if (selectedFile != null)
            imagepath = selectedFile.getAbsolutePath();
        imagePath=imagepath;
        System.out.println(imagepath);
        String url1 = imagepath;

        if(url1!=null) {
            File file = new File(url1);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(fis);
            imageU.setImage(image);
        }

    }
}
