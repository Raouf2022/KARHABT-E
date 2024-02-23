package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class ReponseReclamation {
    private int idReponseR;
    private Reclamation reclamation;
    private String contenuReponse;

    private Date dateReponseR;


    public ReponseReclamation() {
    }


    public ReponseReclamation(int idReponseR, Reclamation reclamation, String contenuReponse, Date dateReponseR) {
        this.idReponseR = idReponseR;
        this.reclamation = reclamation;
        this.contenuReponse = contenuReponse;
        this.dateReponseR = dateReponseR;
    }

    public ReponseReclamation(Reclamation reclamation, String contenuReponse, Date dateReponseR) {
        this.reclamation = reclamation;
        this.contenuReponse = contenuReponse;
        this.dateReponseR = dateReponseR;
    }

    public ReponseReclamation(Reclamation rr1, String positive, String nonPanne, Date dateReponseR) {
    }

    public int getIdReponseR() {
        return idReponseR;
    }

    public void setIdReponseR(int idReponseR) {
        this.idReponseR = idReponseR;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public String getContenuReponse() {
        return contenuReponse;
    }

    public void setContenuReponse(String contenuReponse) {
        this.contenuReponse = contenuReponse;
    }

    public Date getDateReponseR() {
        return dateReponseR;
    }

    public void setDateReponseR(Date dateReponseR) {
        this.dateReponseR = dateReponseR;
    }

    @Override
    public String toString() {
        return "ReponseReclamation{" +
                "idReponseR=" + idReponseR +
                ", reclamation=" + reclamation +
                ", contenuReponse='" + contenuReponse + '\'' +
                ", dateReponseR=" + dateReponseR +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReponseReclamation that = (ReponseReclamation) o;
        return idReponseR == that.idReponseR && Objects.equals(reclamation, that.reclamation) && Objects.equals(contenuReponse, that.contenuReponse) && Objects.equals(dateReponseR, that.dateReponseR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReponseR, reclamation, contenuReponse, dateReponseR);
    }
}