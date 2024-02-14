package edu.esprit.entities;
import edu.esprit.entities.Voiture;
import edu.esprit.tools.DataSource;
import java.util.Date;
import java.util.Objects;

public class Arrivage {
    int idA;
    int quantite;
    Date DateEntree;
    Voiture v;

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateEntree() {
        return DateEntree;
    }
    public void setDateEntree(Date dateEntree) {
        DateEntree = dateEntree;
    }

    public Voiture getV() {
        return v;
    }
    public void setV(Voiture v) {
        this.v = v;
    }
    public Arrivage(){}

    public Arrivage(int idA, int quantite, Date dateEntree, Voiture v) {
        this.idA = idA;
        this.quantite = quantite;
        DateEntree = dateEntree;
        this.v = v;
    }

    public Arrivage(int quantite, Date dateEntree, Voiture v) {
        this.quantite = quantite;
        DateEntree = dateEntree;
        this.v = v;
    }

    @Override
    public String toString() {
        return "Arrivage" +
                "quantite=" + quantite +
                ", DateEntree=" + DateEntree +
                ", v=" + v +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrivage arrivage = (Arrivage) o;
        return idA == arrivage.idA && quantite == arrivage.quantite && Objects.equals(DateEntree, arrivage.DateEntree) && Objects.equals(v, arrivage.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idA, quantite, DateEntree, v);
    }
}
