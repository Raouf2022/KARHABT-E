package edu.esprit.entities;

public class User {
    private  int idU;
    private  String nom;

    public User(int idU) {
    }

    public User() {

    }


    public int getIdU() {
        return idU;
    }

    public String getNom() {
        return nom;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "User{" +
                "idU=" + idU +
                ", nom='" + nom + '\'' +
                '}';
    }
}
