package edu.esprit.entities;
import java.util.Objects;
public class Voiture {

    private int idV;
    private String marque;
    private String modele;
    private String couleur;
    private double prix;
    private String description;

    public int getIdV() {
        return idV;
    }

    public void setIdV(int id) {
        this.idV = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Voiture(){};

    public Voiture(int id, String marque, String modele, String couleur, double prix, String description) {
        this.idV = id;
        this.marque = marque;
        this.modele = modele;
        this.couleur = couleur;
        this.prix = prix;
        this.description = description;
    }

    public Voiture(String marque, String modele, String couleur, double prix, String description) {
        this.marque = marque;
        this.modele = modele;
        this.couleur = couleur;
        this.prix = prix;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", couleur='" + couleur + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voiture voiture = (Voiture) o;
        return idV == voiture.idV && Double.compare(prix, voiture.prix) == 0 && Objects.equals(marque, voiture.marque) && Objects.equals(modele, voiture.modele) && Objects.equals(couleur, voiture.couleur) && Objects.equals(description, voiture.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idV);
    }
}

