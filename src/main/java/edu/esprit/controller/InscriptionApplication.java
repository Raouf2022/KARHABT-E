package edu.esprit.controller;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class InscriptionApplication {
    @FXML
    private Label fxTelError;
    @FXML
    private Label fxerrorMail;
    @FXML
    private TextField fxnom;
    @FXML
    private DatePicker fxdate;
    @FXML
    private TextField fxtel;
    @FXML
    private TextField fxmail;
    @FXML
    private PasswordField fxpwd;
    @FXML
    private TextField fxprenom;
    ServiceUser serviceUser = new ServiceUser();
    public void createAccount(ActionEvent actionEvent) {
        int a = Integer.parseInt(fxtel.getText());
        Date d = Date.valueOf(fxdate.getValue());

        try {
            if (!serviceUser.isValidEmail(fxmail.getText())) {

                fxerrorMail.setVisible(true);
                fxTelError.setVisible(false);

            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText())))){
                fxTelError.setVisible(true);
                fxerrorMail.setVisible(false);
            } else if (!(serviceUser.isValidPhoneNumber(Integer.parseInt(fxtel.getText()))) && !serviceUser.isValidEmail(fxmail.getText())) {

                fxTelError.setVisible(true);
                fxerrorMail.setVisible(true);

            } else {
            serviceUser.ajouterUser(new Client(fxnom.getText(),fxprenom.getText(),d,a, fxmail.getText(),fxpwd.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
                fxTelError.setVisible(false);
                fxerrorMail.setVisible(false);}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
