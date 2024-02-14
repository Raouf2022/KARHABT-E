package edu.esprit.entities;

import java.util.Date;

public class Actualite {
    private int idAct;
    private String titre;
    private String Contenue;
    private String date_pub ;


    public Actualite (int idAct, String titre, String contenue, String date_pub) {
        this.idAct = idAct;
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = date_pub;
    }

    public Actualite(String titre, String contenue, String date_pub) {
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = date_pub;
    }

    public Actualite() {

    }

    public Actualite(int idAct) {
        this.idAct = idAct;
    }

    public int getIdAct() {
        return idAct;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenue() {
        return Contenue;
    }

    public String getDate_pub() {
        return date_pub;
    }

    public void setIdAct(int idAct) {
        this.idAct = idAct;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenue(String contenue) {
        Contenue = contenue;
    }



    public void setDate_pub(String date_pub) {
        this.date_pub = date_pub;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "idAct=" + idAct +
                ", titre='" + titre + '\'' +
                ", Contenue='" + Contenue + '\'' +
                ", date_pub=" + date_pub +
                '}';
    }
}
