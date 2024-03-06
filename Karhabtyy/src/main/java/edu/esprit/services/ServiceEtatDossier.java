package edu.esprit.services;
import edu.esprit.entities.Dossier;
import edu.esprit.tools.DataSource;
import edu.esprit.services.ServiceDossier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.entities.etatDeDossier;


public class ServiceEtatDossier implements IEtatDossier <etatDeDossier> {

    Connection cnx = DataSource.getInstance().getCnx();
    private String etat;


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
            System.out.println("etat supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, d.getId_etat());
            pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error during deletion: " + ex.getMessage());
            ex.printStackTrace(); // Print the full stack trace for debugging
        }
    }

    @Override
    public etatDeDossier getOneById(int id_etat) {
        return null;
    }

    /*
        @Override
        public etatDeDossier getOneById(int id_etat) {
            String req = "SELECT * FROM etatdossier WHERE id_etat = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id_etat);

                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    // Create a Dossier object from the retrieved data
                    etatDeDossier d = new etatDeDossier(etat, id_dossier);
                    d.setId_etat(resultSet.getInt("id_etat"));
                    d.setEtat(resultSet.getString("etat"));
                    d.setId_dossier(resultSet.getInt("id_dossier"));
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


    */
    @Override
    public void ajouter(etatDeDossier etatDeDossier) {
        String req = "INSERT INTO etatdossier (etat ) VALUES (?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            if (etatDeDossier.getEtat() != null && !etatDeDossier.getEtat().isEmpty()) {
                ps.setString(1, etatDeDossier.getEtat());

               // Use the provided id_dossier
                ps.executeUpdate();
                System.out.println("Etat de dossier added!");
            } else {
                throw new IllegalArgumentException("Etat cannot be empty");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public void modifier(etatDeDossier etatDeDossier) {
        String req = "update etatdossier set etat = ?  where id_etat = ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, etatDeDossier.getId_etat());

            ps.executeUpdate();
            //   JOptionPane.showMessageDialog(null, "Joueur Modifié!");
            System.out.println("etat modifié");
            ps.close();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }


    @Override
    public  List<etatDeDossier> getAll() throws SQLException {
        String query = "SELECT * FROM etatdossier";
        List<etatDeDossier> etatSet = new ArrayList<>();
        PreparedStatement preparedStatement = cnx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            etatSet.add(new etatDeDossier(
           resultSet.getString("etat"),
                    resultSet.getInt("id_etat")


            ));


        }

        return etatSet;
    }

}