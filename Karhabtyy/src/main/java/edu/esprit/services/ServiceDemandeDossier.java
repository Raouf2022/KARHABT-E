package edu.esprit.services;

import edu.esprit.entities.DossierDemande;
import edu.esprit.controllers.DemandeDossier;
import edu.esprit.entities.DossierDemande;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
public class ServiceDemandeDossier implements IDemandeDossier<DossierDemande> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(DossierDemande dossierDemande) {
        String req = "INSERT INTO demandedossier ( urlcin, urlCerRetenu,urlAttTravail,urlDecRevenu,urlExtNaissance) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, dossierDemande.getUrlcin());
            ps.setString(2, dossierDemande.getUrlCerRetenu());
            ps.setString(3, dossierDemande.getUrlAttTravail());
            ps.setString(4, dossierDemande.getUrlDecRevenu());
            ps.setString(5, dossierDemande.getUrlExtNaissance());

            ps.executeUpdate();
            System.out.println("Demande added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(int id_demande, DossierDemande dossierDemande) {

        String req = "UPDATE demandedossier SET urlcin= ?, urlCerRetenu = ?, urlAttTravail= ?, urlDecRevenu = ? , urlExtNaissance= ?  WHERE id_demande = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, dossierDemande.getUrlcin());
            ps.setString(2, dossierDemande.getUrlCerRetenu());
            ps.setString(3, dossierDemande.getUrlAttTravail());
            ps.setString(4, dossierDemande.getUrlDecRevenu());
            ps.setString(5, dossierDemande.getUrlExtNaissance());
            ps.setInt(6, id_demande);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dossier with ID " + id_demande + " modified successfully!");
            } else {
                System.out.println("No dossier found with ID " + id_demande);
            }
        } catch (SQLException e) {
            System.out.println("Error modifying dossier: " + e.getMessage());
        }
    }


    @Override
    public void supprimerid(int id_demande) {
        try {
            String requete = "DELETE FROM demandedossier WHERE id_demande =?";
            System.out.println("demande supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_demande);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(DossierDemande dossierDemande) {
        try {
            String requete = "DELETE FROM demandedossier WHERE id_demande =?";
            System.out.println("demande supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, dossierDemande.getId_demande());
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public DossierDemande getOneById(int id_demande) {
        String req = "SELECT * FROM demandedossier WHERE id_demande = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_demande);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                // Create a Dossier object from the retrieved data
                DossierDemande dd = new DossierDemande();
                dd.setId_demande(resultSet.getInt("id_demande"));
                dd.setUrlcin(resultSet.getString("urlcin"));
                dd.setUrlCerRetenu(resultSet.getString("urlCerRetenu"));
                dd.setUrlAttTravail(resultSet.getString("AttTravail"));
                dd.setUrlDecRevenu(resultSet.getString("DecRevenu"));
                dd.setUrlExtNaissance(resultSet.getString("ExtNaissance"));

                return dd;
            } else {
                System.out.println("No dossier found with ID " + id_demande);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving dossier: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Set<DossierDemande> getAll() {
        Set<DossierDemande> demandeSet = new HashSet<>();
        String requete = "SELECT * FROM demandedossier";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                DossierDemande d = new DossierDemande();
                d.setId_demande(rs.getInt("id_demande"));
                d.setUrlcin(rs.getString("urlcin"));
                d.setUrlCerRetenu(rs.getString("urlCerRetenu"));
                d.setUrlAttTravail(rs.getString("AttTravail"));
                d.setUrlDecRevenu(rs.getString("DecRevenu"));
                d.setUrlExtNaissance(rs.getString("ExtNaissance"));

                demandeSet.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return demandeSet;

    }

    public void setText(String string) {
    }
}
