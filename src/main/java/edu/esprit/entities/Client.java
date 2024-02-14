package edu.esprit.entities;

import java.util.Date;

public class Client extends User{
    public Client() {
    }

    public Client(int idU, String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role) {
        super(idU, nom, prenom, dateNaissance, numTel, eMAIL, passwd, "Client");
    }

    public Client(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd, "Client");
    }

    public Client(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd);
    }
}
