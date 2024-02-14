package edu.esprit.services;

import edu.esprit.tools.DataSource;
import edu.esprit.entities.User;

import java.sql.*;
import java.util.List;


public class ServiceUser implements IUserService <User>{

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterUser(User user) {
    }

    @Override
    public void modifierUser(User user) {

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
    public User getOneById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
