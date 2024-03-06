package edu.esprit.entities;

import java.util.Objects;
public class etatDeDossier {

    private String etat;
    private int id_etat;
   // private int id_dossier;

    public etatDeDossier(String etat, int id_etat) {
        this.etat=etat;
        this.id_etat=id_etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof etatDeDossier that)) return false;
        return Objects.equals(getEtat(), that.getEtat());
    }

    @Override
    public String toString() {
        return "etatDeDossier{" +
                "etat='" + etat + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(etat);
    }
    public int getId_etat() {
        return id_etat;
    }




    public void setId_etat(int id_etat) {
        this.id_etat = id_etat;
    }
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public etatDeDossier(String etat) {
        this.etat = etat;
    }


}
