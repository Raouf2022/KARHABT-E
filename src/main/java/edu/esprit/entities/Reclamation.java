package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Reclamation {


    private int idR;
    private String sujet;
    private String description;
    private Date dateReclamation;
    private String emailUtilisateur;
    private User user;


    public  Reclamation(){}

    public Reclamation(String sujet, String description, Date dateReclamation, String emailUtilisateur, User user) {
        this.sujet = sujet;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.emailUtilisateur = emailUtilisateur;
        this.user = user;
    }

    public Reclamation(int idR, String sujet, String description, Date dateReclamation, String emailUtilisateur, User user) {
        this.idR = idR;
        this.sujet = sujet;
        this.description = description;
        this.dateReclamation = dateReclamation;
        this.emailUtilisateur = emailUtilisateur;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idR=" + idR +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", dateReclamation=" + dateReclamation +
                ", emailUtilisateur='" + emailUtilisateur + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return idR == that.idR && Objects.equals(sujet, that.sujet) && Objects.equals(description, that.description) && Objects.equals(dateReclamation, that.dateReclamation) && Objects.equals(emailUtilisateur, that.emailUtilisateur) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idR, sujet, description, dateReclamation, emailUtilisateur);
    }
}


