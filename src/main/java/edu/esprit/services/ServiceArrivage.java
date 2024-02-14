package edu.esprit.services;
import edu.esprit.entities.Arrivage;
import edu.esprit.tools.DataSource;
import edu.esprit.entities.Voiture;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;


import java.util.Set;

public class ServiceArrivage implements IService <Arrivage> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Arrivage arrivage) {

        String req = "INSERT INTO `Arrivage`( `quantite`, `DateEntree`, `idV`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, arrivage.getQuantite());
            ps.setDate(2, new java.sql.Date(arrivage.getDateEntree().getTime()));
            ps.setInt(3, arrivage.getV().getIdV());
            ps.executeUpdate();
            System.out.println("Arrivage ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Arrivage arrivage) {
        String req = "UPDATE `Arrivage` SET `quantite` = ?, `DateEntree` = ?, `idV` = ? WHERE `idA` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, arrivage.getQuantite());
            ps.setDate(2, new java.sql.Date(arrivage.getDateEntree().getTime()));
            ps.setInt(3, arrivage.getV().getIdV());
            ps.setInt(4, arrivage.getIdA());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Arrivage avec l'ID " + arrivage.getIdA() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun arrivage trouvé avec l'ID " + arrivage.getIdA());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `Arrivage` WHERE idA = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Arrivage avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun arrivage trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

    @Override
    public Arrivage getOneById(int id) {
        return null;
    }

    @Override
    public Set<Arrivage> getAll() {
        return null;
    }


}

    @Override
    public Arrivage getOneById(int id){
    return null;
    }

    @Override
    public Set<Arrivage> getAll() {
        return null;
    }
}
