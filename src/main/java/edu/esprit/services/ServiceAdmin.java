package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String req = "SELECT *  from `User` where idU="+id;
        Admin admin=null;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            if (res.next())
            {
                int idU = res.getInt("idU");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");

                Date dateNaissance = res.getDate("dateNaissance");
                String numTel = res.getString("numTel");
                String eMail  = res.getString("eMAIL");

                String passwd = res.getString("passwd");
                String role = res.getString("role");
                admin = new Admin(idU,nom,prenom,dateNaissance,numTel,eMail,passwd,role);            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }

    @Override
    public Set<Admin> getAll() {
        Set<Admin> admin = new HashSet<>();

        String req = "Select * from User";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idU = res.getInt("idU");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");

                Date dateNaissance = res.getDate("dateNaissance");
                String numTel = res.getString("numTel");
                String eMail  = res.getString("eMAIL");

                String passwd = res.getString("passwd");
                String role = res.getString("role");
                Admin a = new Admin(idU,nom,prenom,dateNaissance,numTel,eMail,passwd,role);
                admin.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return admin;
    }
}
