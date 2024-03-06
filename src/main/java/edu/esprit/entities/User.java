package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class User {
    private int idU;
    private String nom;
    private String prenom;
    private Date DateNaissance;
    private int numTel;
    private String eMAIL;
    private String passwd;
    private String role;

    private  String imageUser;

    public User(int idU) {
    }

    public int getIdU() {
        return this.idU;
    }


    public User(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser) {
        this.nom = nom;
        this.prenom = prenom;
        DateNaissance = dateNaissance;
        this.numTel = numTel;
        this.eMAIL = eMAIL;
        this.passwd = passwd;
        this.role = role;
        this.imageUser= imageUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String geteMAIL() {
        return eMAIL;
    }

    public void seteMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(){

    };

    public User(int idU, String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String role, String imageUser) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        DateNaissance = dateNaissance;
        this.numTel = numTel;
        this.eMAIL = eMAIL;
        this.passwd = passwd;
        this.role = role;
        this.imageUser = imageUser;
    }

    public User(String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String imageUser) {
        this.nom = nom;
        this.prenom = prenom;
        DateNaissance = dateNaissance;
        this.numTel = numTel;
        this.eMAIL = eMAIL;
        this.passwd = passwd;
        this.imageUser = imageUser;

    }

    public User(int idU, String nom, String prenom, Date dateNaissance, int numTel, String eMAIL, String passwd, String imageUser) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        DateNaissance = dateNaissance;
        this.numTel = numTel;
        this.eMAIL = eMAIL;
        this.passwd = passwd;
        this.imageUser = imageUser;
    }

    public User(String eMAIL, String passwd) {
        this.eMAIL = eMAIL;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", DateNaissance=" + DateNaissance +
                ", numTel=" + numTel +
                ", eMAIL='" + eMAIL + '\'' +
                ", passwd='" + passwd + '\'' +
                ", role='" + role + '\'' +
                ", imageUser='" + imageUser + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idU == user.idU &&
                Objects.equals(nom, user.nom) &&
                Objects.equals(prenom, user.prenom) &&
                Objects.equals(DateNaissance, user.DateNaissance) &&
                Objects.equals(eMAIL, user.eMAIL) &&
                Objects.equals(role, user.role);
    }


    @Override
    public int hashCode() {
        return Objects.hash(idU, nom, prenom, DateNaissance, eMAIL, role);
    }
}
