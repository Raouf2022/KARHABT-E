package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Actualite {
    private int idAct;
    private String titre;
    private String Contenue;
    private LocalDate date_pub ;


    public Actualite (int idAct, String titre, String contenue, LocalDate date_pub) {
        this.idAct = idAct;
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = date_pub;
    }

    public Actualite(String titre, String contenue) {
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = LocalDate.now();
    }

    public Actualite() {

    }

    public Actualite(int idAct, String titre, String contenue) {
        this.idAct = idAct;
        this.titre = titre;
        Contenue = contenue;
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

    public LocalDate getDate_pub() {
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



    public void setDate_pub(LocalDate date_pub) {
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
