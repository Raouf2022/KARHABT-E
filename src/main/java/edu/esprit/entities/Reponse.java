package edu.esprit.entities;

import java.time.LocalDate;

public class Reponse {
    private int idR ;
    private  String continueR;
    private LocalDate Date_Rep;
    private  Commentaire comnt;
    private  User user;


    public Reponse(int idR, String continueR, LocalDate date_Rep) {
        this.idR = idR;
        this.continueR = continueR;
        Date_Rep = date_Rep;
    }

    public Reponse(String continueR, Commentaire comnt , User user) {
        this.continueR = continueR;
        this.user=user;
        this.comnt = comnt;
        this.Date_Rep = LocalDate.now();
    }

    public Reponse(String continueR, LocalDate date_Rep, Commentaire comnt, User user) {
        this.continueR = continueR;
        this.Date_Rep = date_Rep;
        this.comnt = comnt;
        this.user = user;
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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "continueR='" + continueR + '\'' +
                ", Date_Rep=" + Date_Rep +
                ", comnt=" + comnt.getContenuec() +
                ", user=" + user.getNom() +
                '}';
    }
}
