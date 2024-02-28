package edu.esprit.entities;

import java.util.Objects;

public class DossierDemande extends Dossier {
    private String urlcin;
    private String urlCerRetenu;
    private String urlAttTravail;
    private String urlDecRevenu;
    private String urlExtNaissance;
    private int id_demande;
    public DossierDemande() {


    }
    public DossierDemande(String urlcin, String urlCerRetenu, String urlAttTravail, String urlDecRevenu, String urlExtNaissance) {

        this.urlcin = urlcin;
        this.urlCerRetenu = urlCerRetenu;
        this.urlAttTravail = urlAttTravail;
        this.urlDecRevenu = urlDecRevenu;
        this.urlExtNaissance = urlExtNaissance;
    }
    public int getId_demande() {
        return id_demande;
    }

    public void setId_demande(int id_demande) {
        this.id_demande = id_demande;
    }
    public String getUrlcin() {
        return urlcin;
    }

    public void setUrlcin(String urlcin) {
        this.urlcin = urlcin;
    }

    public String getUrlCerRetenu() {
        return urlCerRetenu;
    }

    public void setUrlCerRetenu(String urlCerRetenu) {
        this.urlCerRetenu = urlCerRetenu;
    }

    public String getUrlAttTravail() {
        return urlAttTravail;
    }

    public void setUrlAttTravail(String urlAttTravail) {
        this.urlAttTravail = urlAttTravail;
    }

    public String getUrlDecRevenu() {
        return urlDecRevenu;
    }

    public void setUrlDecRevenu(String urlDecRevenu) {
        this.urlDecRevenu = urlDecRevenu;
    }

    public String getUrlExtNaissance() {
        return urlExtNaissance;
    }

    public void setUrlExtNaissance(String urlExtNaissance) {
        this.urlExtNaissance = urlExtNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DossierDemande that)) return false;
        return getId_demande() == that.getId_demande();
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId_demande());

    }

}
