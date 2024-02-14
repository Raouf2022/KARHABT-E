package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Messagerie {

    private int idM;
    private String contenu;
    private Date dateEnvoie;
    private int idUSender;
    private int idUReceiver;
    private int idV;

    public Messagerie(){}

    public Messagerie(int idM, String contenu, Date dateEnvoie, int idUSender, int idUReceiver, int idV) {
        this.idM = idM;
        this.contenu = contenu;
        this.dateEnvoie = dateEnvoie;
        this.idUSender = idUSender;
        this.idUReceiver = idUReceiver;
        this.idV = idV;
    }

    public Messagerie(String contenu, Date dateEnvoie, int idUSender, int idUReceiver, int idV) {
        this.contenu = contenu;
        this.dateEnvoie = dateEnvoie;
        this.idUSender = idUSender;
        this.idUReceiver = idUReceiver;
        this.idV = idV;
    }

    public Messagerie(int idMessagerie, String contenu, java.sql.Date dateEnvoi, int idExpediteur, int idDestinataire) {
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public int getIdUSender() {
        return idUSender;
    }

    public void setIdUSender(int idUSender) {
        this.idUSender = idUSender;
    }

    public int getIdUReceiver() {
        return idUReceiver;
    }

    public void setIdUReceiver(int idUReceiver) {
        this.idUReceiver = idUReceiver;
    }

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    @Override
    public String toString() {
        return "Messagerie{" +
                "idM=" + idM +
                ", contenu='" + contenu + '\'' +
                ", dateEnvoie=" + dateEnvoie +
                ", idUSender=" + idUSender +
                ", idUReceiver=" + idUReceiver +
                ", idV=" + idV +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messagerie that = (Messagerie) o;
        return idM == that.idM && idUSender == that.idUSender && idUReceiver == that.idUReceiver && idV == that.idV && Objects.equals(contenu, that.contenu) && Objects.equals(dateEnvoie, that.dateEnvoie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idM, contenu, dateEnvoie, idUSender, idUReceiver, idV);
    }
}
