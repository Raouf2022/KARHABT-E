package edu.esprit.entities;

import java.time.LocalDate;

public class Commentaire {
    private int idComnt;
    private String Contenuec;
    private LocalDate date_pubc;
    private User user;
    private Actualite act;

    public Commentaire( String contenuec, LocalDate date_pubc, User user, Actualite act) {
        this.Contenuec = contenuec;
        this.date_pubc = date_pubc;
        this.user = user;
        this.act = act;
    }




    public Commentaire(String contenuec,  User user, Actualite act) {
        this.Contenuec = contenuec;

        this.date_pubc = LocalDate.now();
        this.user = user;
        this.act = act;
    }

    public Commentaire(String contenuec) {
        this.Contenuec = contenuec;
        this.date_pubc = LocalDate.now();

    }

    public Commentaire(int idComnt,  String contenuec, User user, Actualite act ) {
        this.idComnt = idComnt;
        this.Contenuec = contenuec;

        this.date_pubc = LocalDate.now();
        this.user = user;
        this.act = act;
    }



    public Commentaire(int idComnt) {
        this.idComnt = idComnt;
    }

    public Commentaire() {

    }

    public Commentaire(int idComnt, String contenuec,  User user) {
        this.idComnt = idComnt;
        this.Contenuec = contenuec;
        this.user = user;
    }

    public int getIdComnt() {
        return idComnt;
    }

    public String getContenuec() {
        return Contenuec;
    }

    public LocalDate getDate_pubc() {
        return date_pubc;
    }



    public User getUser() {
        return user;
    }

    public Actualite getAct() {
        return act;
    }


    public void setIdComnt(int idComnt) {
        this.idComnt = idComnt;
    }


    public void setContenuec(String contenuec) {
        Contenuec = contenuec;
    }

    public void setDate_pubc(LocalDate date_pubc) {
        this.date_pubc = date_pubc;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public void setAct(Actualite act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                " Contenuec='" + Contenuec + '\'' +
                ", date_pubc=" + date_pubc +
                ", user=" + user.getNom() +
                ", act=" + act.getTitre() +
                '}';
    }
}




