package edu.esprit.entities;

import java.time.LocalDate;

public class Commentaire {
    private int idComnt;
    private String Contenuec;
    private LocalDate date_pubc ;
    private  int nbrLike ;
    private  String reponse ;

    public Commentaire(int idComnt, String contenuec, LocalDate date_pubc, int nbrLike, String reponse) {
        this.idComnt = idComnt;
        this.Contenuec = contenuec;
        this.date_pubc = date_pubc;
        this.nbrLike = nbrLike;
        this.reponse = reponse;
    }

    public Commentaire(String contenuec, int nbrLike, String reponse) {
        this.Contenuec = contenuec;
        this.date_pubc = LocalDate.now();
        this.nbrLike = nbrLike;
        this.reponse = reponse;
    }

    public Commentaire(int idComnt) {
        this.idComnt = idComnt;
    }

    public Commentaire() {

    }

    public Commentaire(int idComnt, String contenuec, int nbrLike, String reponse) {
        this.idComnt = idComnt;
        this.Contenuec = contenuec;
        this.nbrLike = nbrLike;
        this.reponse = reponse;
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

    public int getNbrLike() {
        return nbrLike;
    }

    public String getReponse() {
        return reponse;
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

    public void setNbrLike(int nbrLike) {
        this.nbrLike = nbrLike;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "idComnt=" + idComnt +
                ", Contenuec='" + Contenuec + '\'' +
                ", date_pubc='" + date_pubc + '\'' +
                ", nbrLike=" + nbrLike +
                ", reponse='" + reponse + '\'' +
                '}';
    }
}


