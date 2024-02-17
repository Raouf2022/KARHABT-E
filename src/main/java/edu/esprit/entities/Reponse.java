package edu.esprit.entities;

import java.time.LocalDate;

public class Reponse {
    private int idR ;
    private  String continueR;
    private LocalDate Date_Rep;
    private  Commentaire comnt;


    public Reponse(int idR, String continueR, LocalDate date_Rep) {
        this.idR = idR;
        this.continueR = continueR;
        Date_Rep = date_Rep;
    }

    public Reponse(String continueR, Commentaire comnt) {
        this.continueR = continueR;
        this.comnt = comnt;
        Date_Rep = LocalDate.now();
    }

    public Reponse(String continueR) {
        this.continueR = continueR;
    }

    public Reponse(String continueR, LocalDate date_Rep) {
        this.continueR = continueR;
        Date_Rep = LocalDate.now();
    }

    public Reponse(int idR) {
        this.idR = idR;
    }

    public Reponse() {

    }

    public Reponse(int idR, String continueR, Commentaire comnt) {
        this.idR = idR;
        this.continueR = continueR;
        this.comnt = comnt;
    }

    public Commentaire getComnt() {
        return comnt;
    }

    public void setComnt(Commentaire comnt) {
        this.comnt = comnt;
    }

    public int getIdR() {
        return idR;
    }

    public LocalDate getDate_Rep() {
        return Date_Rep;
    }

    public void setDate_Rep(LocalDate date_Rep) {
        Date_Rep = date_Rep;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getContinueR() {
        return continueR;
    }

    public void setContinueR(String continueR) {
        this.continueR = continueR;
    }


    @Override
    public String toString() {
        return "Reponse{" +
                "idR=" + idR +
                ", continueR='" + continueR + '\'' +
                ", Date_Rep=" + Date_Rep +
                ", comnt=" + comnt.getContenuec() +
                '}';
    }
}
