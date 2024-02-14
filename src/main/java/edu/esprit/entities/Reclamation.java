package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Reclamation {


    private int idR;
    private String sujet;
    private String description;
    private Date dateReclamation;
    private String emailUtilisateur;
    private int idUser;
    private int idVoiture;


    public  Reclamation(){}

    public Reclamation(int idR, String sujet, String description, Date dateReclamation, String emailUtilisateur, int idUser, int idVoiture) {
        this.idR = idR;
        this.sujet = sujet;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.emailUtilisateur = emailUtilisateur;
        this.idUser = idUser;
        this.idVoiture = idVoiture;
    }

    public Reclamation(String sujet, String description, Date dateReclamation, String emailUtilisateur, int idUser, int idVoiture) {
        this.sujet = sujet;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.emailUtilisateur = emailUtilisateur;
        this.idUser = idUser;
        this.idVoiture = idVoiture;
    }

    public Reclamation(int idR, String sujet, String description, java.sql.Date dateReclamation, int idUser, int idVoiture, String emailUser) {
    }


    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(int idVoiture) {
        this.idVoiture = idVoiture;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idR=" + idR +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", dateReclamation=" + dateReclamation +
                ", emailUtilisateur='" + emailUtilisateur + '\'' +
                ", idUser=" + idUser +
                ", idVoiture=" + idVoiture +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return Objects.equals(idR, that.idR) && Objects.equals(sujet, that.sujet) && Objects.equals(description, that.description) && Objects.equals(dateReclamation, that.dateReclamation) && Objects.equals(emailUtilisateur, that.emailUtilisateur) && Objects.equals(idUser, that.idUser) && Objects.equals(idVoiture, that.idVoiture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idR, sujet, description, dateReclamation, emailUtilisateur, idUser, idVoiture);
    }
}


