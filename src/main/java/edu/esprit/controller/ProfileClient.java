package edu.esprit.controller;

import edu.esprit.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProfileClient {

    @FXML
    private TextField profileMail;

    @FXML
    private TextField profileMdp;

    @FXML
    private DatePicker profileNaissance;

    @FXML
    private TextField profileName;

    @FXML
    private TextField profilePhone;

    @FXML
    private TextField profilePrenom;

    @FXML
    private Button saveBtn;

    private User user;

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            profileMail.setText(user.geteMAIL());
            profileMdp.setText(user.getPasswd()); // Assuming getPassword() returns the password
            Date dateOfBirth = user.getDateNaissance();
            LocalDate localDateOfBirth = LocalDate.ofInstant(dateOfBirth.toInstant(), ZoneId.systemDefault());
            profileNaissance.setValue(localDateOfBirth);
            profileName.setText(user.getNom());
            profilePhone.setText(String.valueOf(user.getNumTel()));
            profilePrenom.setText(user.getPrenom());
        }
    }

    @FXML
    void SaveChangesProfile(ActionEvent event) {


    }

}
