package edu.esprit.entities;

import java.util.Date;

public class Client extends User{
    public Client() {
    }

    public Client(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd, "Client", imageUser);
    }

    public Client(int idU, String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser) {
        super(idU, nom, prenom, dateNaissance, numTel, eMAIL, passwd, "Client", imageUser);
    }

    public Client(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String imageUser) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd, imageUser);
    }
}
