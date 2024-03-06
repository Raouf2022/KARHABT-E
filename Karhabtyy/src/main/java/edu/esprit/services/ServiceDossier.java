package edu.esprit.services;

import edu.esprit.entities.Dossier;

import edu.esprit.tools.DataSource;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class ServiceDossier implements IDossierService<Dossier> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Dossier dossier) {

        String req = "INSERT INTO dossierb ( cin,nom, prenom,region,date,montant) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            if (dossier.getCin() > 0) {
                ps.setInt(1, dossier.getCin());
            } else {
                throw new IllegalArgumentException("Invalid CIN (must be positive)");
            }
            if (dossier.getNom() != null && !dossier.getNom().isEmpty()) {
                ps.setString(2, dossier.getNom());
            } else {
                throw new IllegalArgumentException("Nom cannot be empty");
            }


            if (dossier.getPrenom() != null && !dossier.getPrenom().isEmpty()) {
                ps.setString(3, dossier.getPrenom());
            } else {
                throw new IllegalArgumentException("Prenom cannot be empty");
            }

            if (dossier.getRegion() != null && !dossier.getRegion().isEmpty()) {
                ps.setString(4, dossier.getRegion());
            } else {
                throw new IllegalArgumentException("Region cannot be empty");
            }

            ps.setDate(5, (Date) dossier.getDate());
            ps.setInt(6, dossier.getMontant());


            ps.executeUpdate();
            System.out.println("Dossier added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir des valeurs valides ");
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
        }
        }




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
    }*/
    @Override
    public void modifier(Dossier dossier) {
        try {
            System.out.println(dossier);
            String requete3 = "UPDATE dossierb SET cin=?,nom=?,prenom=?,date=? ,region=?, montant=? where id_dossier=?";
            PreparedStatement pst = cnx.prepareStatement(requete3);
            pst.setInt(1, dossier.getCin());
            pst.setString(2, dossier.getNom());
            pst.setString(3, dossier.getPrenom());
            pst.setDate(4, (Date) dossier.getDate());
            pst.setString(5, dossier.getRegion());
            pst.setInt(6, dossier.getMontant());
            pst.setInt(7, dossier.getId_dossier());

            pst.executeUpdate();

            System.out.println("dossier updated");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


/*
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
    }*/


/*
    @Override
    public void supprimer(Dossier d) {
        try {
            String requete = "DELETE FROM dossierb WHERE cin=?";
            System.out.println("dossier supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, d.getCin());
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
*/

   /*@Override
    public void supprimerid(Dossier d) {
        try {
            String requete = "DELETE FROM dossierb WHERE id_dossier=?";
            System.out.println("Dossier supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, d.getId_dossier()); // Assuming getCin() returns the cin value from the Dossier object
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }*/


    @Override
    public void supprimerid(int id) {
        try {
            String requete = "DELETE  FROM dossierb WHERE id_dossier=?";
            System.out.println("dossier DELETED");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public Dossier getOneById(int id_dossier) {
        String req = "SELECT * FROM dossierb WHERE id_dossier = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_dossier);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                // Create a Dossier object from the retrieved data
                Dossier dossier = new Dossier();
                dossier.setId_dossier(resultSet.getInt("id_dossier"));
                dossier.setCin(resultSet.getInt("cin"));
                dossier.setNom(resultSet.getString("nom"));
                dossier.setPrenom(resultSet.getString("prenom"));
                dossier.setRegion(resultSet.getString("region"));
                dossier.setDate(resultSet.getDate("date"));

                return dossier;
            } else {
                System.out.println("No dossier found with ID " + id_dossier);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving dossier: " + e.getMessage());
            return null;
        }
    }


    @Override
    public Set<Dossier> getAll()  {
        Set<Dossier> dossierSet = new HashSet<>();
        String query = "SELECT * FROM dossierb";
try {
    PreparedStatement preparedStatement = cnx.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
        Dossier dossier = new Dossier(
                resultSet.getInt("cin"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("region"),
                resultSet.getDate("date"),
                resultSet.getInt("MONTANT")
        );
        dossierSet.add(dossier);
    }
}catch (SQLException e) {
            System.out.println("Error retrieving dossier: " + e.getMessage());

        }
        return dossierSet;
    }

    public int getId_dossier() {
        return getId_dossier();
    }
}

