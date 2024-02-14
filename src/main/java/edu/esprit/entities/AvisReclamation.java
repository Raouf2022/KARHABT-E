package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class AvisReclamation {
    private int idAR;
    private int idR;
    private String avis;
    private String commentaire;
    private Date dateAR;


    public AvisReclamation(){

    }
    public AvisReclamation(int idAR, int idR, String avis, String commentaire, Date dateAR) {
        this.idAR = idAR;
        this.idR = idR;
        this.avis = avis;
        this.commentaire = commentaire;
        this.dateAR = dateAR;
    }

    public AvisReclamation(int idR, String avis, String commentaire, Date dateAR) {
        this.idR = idR;
        this.avis = avis;
        this.commentaire = commentaire;
        this.dateAR = dateAR;
    }


    public int getIdAR() {
        return idAR;
    }

    public void setIdAR(int idAR) {
        this.idAR = idAR;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateAR() {
        return dateAR;
    }

    public void Date(Date dateAR) {
        this.dateAR = dateAR;
    }

    @Override
    public String toString() {
        return "AvisReclamation{" +
                "idAR=" + idAR +
                ", idR=" + idR +
                ", avis='" + avis + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", dateAR='" + dateAR + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisReclamation that = (AvisReclamation) o;
        return idAR == that.idAR && idR == that.idR && Objects.equals(avis, that.avis) && Objects.equals(commentaire, that.commentaire) && Objects.equals(dateAR, that.dateAR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAR, idR, avis, commentaire, dateAR);
    }
}
