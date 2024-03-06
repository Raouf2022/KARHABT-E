package edu.esprit.entities;

import java.util.Date;

public class Admin extends User{

    public Admin(){
        super();
    }

    public Admin(int idU, String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser){
        super(idU, nom, prenom, dateNaissance, numTel, eMAIL,passwd, "Admin",imageUser);
    }

    public Admin(String eMAIL, String passwd) {
        super(eMAIL,passwd);
    }

    public Admin(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd, "Admin", imageUser);
    }

    public Admin(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String imageUser) {
        super(nom, prenom, dateNaissance, numTel, eMAIL, passwd,imageUser);
    }
}
