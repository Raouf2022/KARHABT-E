package edu.esprit.entities;

import java.time.LocalDate;

public class Actualite {
    private int idAct;
    private String titre;
    private String Contenue;
    private LocalDate date_pub ;
    private  User user;



    public Actualite (String titre, String contenue, User user) {
        this.idAct = idAct;
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = LocalDate.now();
        this.user=user;
    }

    public Actualite(String titre, String contenue) {
        this.titre = titre;
        this.Contenue = contenue;
        this.date_pub = LocalDate.now();
        this.user =user;
    }

    public Actualite() {

    }

    public Actualite(int idAct, String titre, String contenue,User user) {
        this.idAct = idAct;
        this.titre = titre;
        Contenue = contenue;
        this.user =user;
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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "idAct=" + idAct +
                ", titre='" + titre + '\'' +
                ", Contenue='" + Contenue + '\'' +
                ", date_pub=" + date_pub +
                ", user=" + user.getNom() +
                '}';
    }
}
