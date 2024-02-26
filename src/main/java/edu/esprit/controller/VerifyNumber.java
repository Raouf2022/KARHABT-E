package edu.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VerifyNumber {

    @FXML
    private TextField codeInput;

    @FXML
    private Button codeSumbit;

    private InscriptionApplication inscriptionController; // You'll need to set this reference when transitioning from registration to verification

    @FXML
    void submitButton(ActionEvent event) {

        // Compare the input code with the generated code
        if (codeInput.getText().equals(inscriptionController.verificationCode)) {
            // Correct code, proceed with the login or next step
        } else {
            // Incorrect code, show error message
        }

    }

}
