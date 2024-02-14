package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.tools.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceAdmin implements IUserService <Admin> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterUser(Admin admin) {
        String req = "INSERT INTO User(`nom`, `prenom` , `dateNaissance` , `numTel` , `eMail` , `passwd` , `role`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,admin.getNom());
            ps.setString(2,admin.getPrenom());
            // convertit b string.valueOf bch nekhou el date khaterha mahich string
            ps.setString(3, String.valueOf(admin.getDateNaissance()));
            ps.setString(4,admin.getNumTel());
            ps.setString(5,admin.geteMAIL());
            ps.setString(6,admin.getPasswd());
            ps.setString(7,"Admin");
            ps.executeUpdate();
            System.out.println("Admin added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierUser(Admin admin) {
        String req = "UPDATE User SET `nom`=?, `prenom`=?, `dateNaissance`=?, `numTel`=?, `eMail`=?, `passwd`=? WHERE `idU`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            // Convertir la date en String pour l'insertion dans la base de données
            ps.setString(3, String.valueOf(admin.getDateNaissance()));
            ps.setString(4, admin.getNumTel());
            ps.setString(5, admin.geteMAIL());
            ps.setString(6, admin.getPasswd());
            // Supposons que l'ID de l'utilisateur soit disponible dans la classe Admin
            ps.setInt(7, admin.getIdU());
            ps.executeUpdate();
            System.out.println("Admin updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerUser(int id) {
        String req = "DELETE FROM `User` WHERE idU = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User with ID " + id + " has been deleted successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Admin getOneById(int id) {
        return null;
    }

    @Override
    public List<Admin> getAll() {


        return null;
    }
}
