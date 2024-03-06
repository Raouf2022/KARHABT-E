package edu.esprit.entities;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;
public class Dossier {

    private int cin;
    private String nom;
    private String prenom ;
    private String region ;
    private Date date ;
    private int id_dossier;
    private int montant;


    public Dossier(int cin, String nom, String prenom , String region , Date date , int montant) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.region=region;
        this.date=date;
        this.montant=montant;
    }

    public Dossier(int cin, String nom, String prenom, String region, Date date, int id_dossier, int montant) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.date = date;
        this.id_dossier = id_dossier;
        this.montant = montant;
    }

    public Dossier() {
    }

    public int getCin() {
        return cin;
    }
    public int getMontant() {
        return montant;
    }
    public int getId_dossier() {
        return id_dossier;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRegion() {
        return region;
    }

    public Date getDate() {
        return date;
    }


    public void setCin(int cin) {
        this.cin = cin;
    }
    public void setMontant(int montant) {
        this.montant = montant;
    }
    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return "Dossier{" +
                "cin=" + cin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", region='" + region + '\'' +
                ", date=" + date +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dossier dossier = (Dossier) o;

        return id_dossier == dossier.id_dossier;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id_dossier);
    }


}
