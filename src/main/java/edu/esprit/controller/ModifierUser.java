package edu.esprit.controller;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class ModifierUser {

    @FXML
    private TextField fxxImg;

    @FXML
    private TextField fxxMail;

    @FXML
    private DatePicker fxxNaissance;

    @FXML
    private TextField fxxPass;

    @FXML
    private TextField fxxRole;

    @FXML
    private TextField fxxTel;

    @FXML
    private TextField fxxnom;

    @FXML
    private TextField fxxprenom;

    @FXML
    private Button modifBtn;
    private User user;
    private int iduser;

    @FXML
    void ModifAttributs(ActionEvent event) {

        ServiceUser serviceUser = new ServiceUser();
        int a = Integer.parseInt(fxxTel.getText());
        Date d = Date.valueOf(fxxNaissance.getValue());

            if (!serviceUser.isValidEmail(fxxMail.getText())) {
                System.out.println("mail invalid!");

            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxxTel.getText()))))
            {
                System.out.println("tel invalid!");

            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxxTel.getText()))) && !serviceUser.isValidEmail(fxxMail.getText()))
            {
                System.out.println("tel & mail invalid!");

            } else {                User user = new User(iduser,fxxnom.getText(), fxxprenom.getText(), d, a, fxxMail.getText(), fxxPass.getText(),fxxRole.getText(),fxxImg.getText());
                serviceUser.modifierUser(user);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/adminPage.fxml"));
                    fxxnom.getScene().setRoot(root);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }
            }


    }

    @FXML
    void SetUsers(User user) {
        this.user = user;
        iduser=user.getIdU();
        fxxnom.setText(user.getNom());
        fxxprenom.setText(user.getPrenom());
        fxxNaissance.setValue(LocalDate.parse(String.valueOf(user.getDateNaissance())));
        fxxTel.setText(Integer.toString(user.getNumTel()));
        fxxMail.setText(user.geteMAIL());
        fxxPass.setText(user.getPasswd());
        fxxRole.setText(user.getRole());
        fxxImg.setText(user.getImageUser());

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
            fxxImg.setText(imagepath);
        }

    }

}
