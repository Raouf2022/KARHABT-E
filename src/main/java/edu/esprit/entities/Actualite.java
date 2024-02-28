package edu.esprit.entities;

import java.time.LocalDate;

public class Actualite {
    private int idAct;
    private String titre;
    private String Contenue;
    private double rating;
    private LocalDate date_pub ;
    private  String Image;






    public Actualite( String titre, String Image ,String contenue, LocalDate date_pub, User user) {
        this.titre = titre;
        this.Image=Image;
        this.Contenue = contenue;
        this.date_pub = date_pub;

    }

    public Actualite(String titre, String Image , String contenue) {
        this.titre = titre;
        this.Image=Image;
        this.Contenue = contenue;
        this.date_pub = LocalDate.now();

    }

    public Actualite() {

    }

    public Actualite(int idAct, String titre, String Image , String contenue) {
        this.idAct = idAct;
        this.titre = titre;
        this.Image=Image;
        Contenue = contenue;

    }

    public Actualite(int idAct) {
        this.idAct = idAct;
    }

    public Actualite(String titre, String contenue, User user) {
        this.titre = titre;
        Contenue = contenue;

    }



    public double getRating() {return rating;}
    public void setRating(double rating) {
        this.rating = rating;
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





    public String getImage() {
        return Image;
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


    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "titre='" + titre + '\'' +
                ", Contenue='" + Contenue + '\'' +
                ", date_pub=" + date_pub +
                ", Image='" + Image +

                '}';
    }
}
