package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class AvisReclamation {
    private int idAR;
    private Reclamation reclamation;
    private String avis;
    private String commentaire;
    private Date dateAR;


    public AvisReclamation() {}

    public AvisReclamation(Reclamation reclamation, String avis, String commentaire, Date dateAR) {
        this.reclamation = reclamation;
        this.avis = avis;
        this.commentaire = commentaire;
        this.dateAR = dateAR;
    }

    public AvisReclamation(int idAR, Reclamation reclamation, String avis, String commentaire, Date dateAR) {
        this.idAR = idAR;
        this.reclamation = reclamation;
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

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
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

    public void setDateAR(Date dateAR) {
        this.dateAR = dateAR;
    }

    @Override
    public String toString() {
        return "AvisReclamation{" +
                "idAR=" + idAR +
                ", reclamation=" + reclamation +
                ", avis='" + avis + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", dateAR=" + dateAR +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisReclamation that = (AvisReclamation) o;
        return idAR == that.idAR && Objects.equals(reclamation, that.reclamation) && Objects.equals(avis, that.avis) && Objects.equals(commentaire, that.commentaire) && Objects.equals(dateAR, that.dateAR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAR, reclamation, avis, commentaire, dateAR);
    }

}
