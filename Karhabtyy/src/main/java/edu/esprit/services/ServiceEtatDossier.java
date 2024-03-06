package edu.esprit.services;
import edu.esprit.entities.Dossier;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.esprit.entities.etatDeDossier;
import javafx.scene.control.Alert;

import javax.swing.*;


public class ServiceEtatDossier implements IEtatDossier <etatDeDossier> {

    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void supprimer(int id_etat) {
        try {
            String requete = "DELETE FROM etatdossier WHERE id_etat=?";
            System.out.println("etat supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_etat);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerD(etatDeDossier d) {
        try {
            String requete = "DELETE FROM etatdossier WHERE id_etat=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, d.getId_etat());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Dossier successfully deleted.");
            } else {
                System.out.println("No records found for deletion.");
            }
        } catch (Exception ex) {
            System.out.println("Error during deletion: " + ex.getMessage());
            ex.printStackTrace(); // Print the full stack trace for debugging
        }
    }


    @Override
    public etatDeDossier getOneById(int id_etat) {
        String req = "SELECT * FROM etatdossier WHERE id_etat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_etat);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                // Create a Dossier object from the retrieved data
                etatDeDossier d = new etatDeDossier();
                d.setId_etat(resultSet.getInt("id_etat"));
                d.setEtat(resultSet.getString("etat"));


                return d;
            } else {
                System.out.println("No dossier found with ID " + id_etat);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving dossier: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Set<etatDeDossier> getAll() {
        return null;
    }


    @Override
    public void ajouter(etatDeDossier etatDeDossier) {

        /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        String req = "INSERT INTO etatdossier (etat) VALUES (?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            if (etatDeDossier.getEtat() != null && !etatDeDossier.getEtat().isEmpty()) {
                ps.setString(1, etatDeDossier.getEtat());
            } else {
                throw new IllegalArgumentException("Etat cannot be empty");
            }
            ps.setString(1, etatDeDossier.getEtat());
            ps.executeUpdate();
            System.out.println("Etat de dossier added !");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir des valeurs numériques valides pour le montant et le CIN.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(etatDeDossier etatDeDossier) {
        String req = "update etatdossier set etat = ? where id_etat = ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, etatDeDossier.getId_etat());

            ps.executeUpdate();
            //   JOptionPane.showMessageDialog(null, "Joueur Modifié!");
            System.out.println("joueur modifier");
            ps.close();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }


    /*
        /*@Override
        public void modifier(int cin) {
            try {
                String requete3 = "UPDATE dossier SET nom=nomn WHERE id_event=?";
                PreparedStatement pst = cnx.prepareStatement(requete3);
                pst.setInt(1, cin);
                pst.executeUpdate();
                System.out.println("dossier updated");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        @Override
        public void modifier(Dossier dossier) {
            System.out.print(dossier);
            try {
                String requete3 = "UPDATE dossierb SET cin=?,nom=?,prenom=?,region=?,date=? where id_dossier=id_dossier";
                PreparedStatement pst = cnx.prepareStatement(requete3);
                pst.setInt(1,dossier.getCin());
                pst.setString(2,dossier.getNom());
                pst.setString(3,dossier.getPrenom());
                pst.setString(4,dossier.getRegion());
                pst.setString(5, dossier.getDate());
                pst.executeUpdate();

                System.out.println("dossier updated");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        @Override
        public void supprimer(int cin) {
            try {
                String requete = "DELETE FROM dossierb WHERE cin=?";
                System.out.println("dossier supprimé");
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, cin);
                pst.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        @Override
        public void supprimerid(int id_dossier) {
            try {
                String requete = "DELETE FROM dossierb WHERE id_dossier=?";
                System.out.println("dossier supprimé");
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, id_dossier);
                pst.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        @Override
        public etatDeDossier getOneById(int id_etat) {
            return null;
        }
    *//*
    @Override
    public Set<etatDeDossier> getAll() {
        String query = "SELECT * FROM etatdossier";
        List<etatDeDossier> dossierSet = new ArrayList<>();
        PreparedStatement preparedStatement = cnx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            dossierSet.add(new etatDeDossier(

                    resultSet.getString("etat")

            ));
        }

        return dossierSet;
    }
}
/*
    @Override
    public Set<etatDeDossier> getAll() {
        Set<etatDeDossier> etats = new HashSet<>();

        String req = "Select * from etatdossier";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                etatDeDossier d = new etatDeDossier();
                d.setId_etat(res.getInt(1));
                d.setEtat(res.getString("etat"));


                etats.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return etats;
    }
    */

}