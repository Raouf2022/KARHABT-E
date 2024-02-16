package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    public ServiceReclamation(Connection cnx) {
    }

    @Override
    public void create(Reclamation entity) {
        try {
            // Validate email format
            if (!isValidEmail(entity.getEmailUtilisateur())) {
                System.out.println("Invalid email format");
                return; // You might want to throw an exception or handle it differently
            }

            String query = "INSERT INTO reclamation (sujet, description, dateReclamation, emailUser, idU) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, entity.getSujet());
            pstmt.setString(2, entity.getDescription());
            pstmt.setDate(3, new java.sql.Date(entity.getDateReclamation().getTime()));
            pstmt.setString(4, entity.getEmailUtilisateur());
            pstmt.setInt(5, entity.getUser().getIdU());

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setIdR(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method for email validation using a simple regular expression
    private boolean isValidEmail(String email) {
        // Adjust the regular expression as needed for your email format requirements
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @Override
    public Reclamation getById(int id) {
        Reclamation reclamation = null;
        try {
            String query = "SELECT * FROM reclamation WHERE idR = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reclamation = mapResultSetToReclamation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamation;
    }

    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();
        try {
            String query = "SELECT * FROM reclamation";
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Reclamation reclamation = mapResultSetToReclamation(rs);
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamations;
    }

    @Override
    public void update(Reclamation entity) {
        try {
            String query = "UPDATE reclamation SET sujet=?, description=?, dateReclamation=?, emailUser=?, idU=? WHERE idR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.setString(1, entity.getSujet());
            pstmt.setString(2, entity.getDescription());
            pstmt.setDate(3, new java.sql.Date(entity.getDateReclamation().getTime()));
            pstmt.setString(4, entity.getEmailUtilisateur());
            pstmt.setInt(5, entity.getUser().getIdU());
            pstmt.setInt(6, entity.getIdR());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM reclamation WHERE idR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Reclamation mapResultSetToReclamation(ResultSet rs) throws SQLException {
        Reclamation reclamation = new Reclamation();
        reclamation.setIdR(rs.getInt("idR"));
        reclamation.setSujet(rs.getString("sujet"));
        reclamation.setDescription(rs.getString("description"));
        reclamation.setDateReclamation(rs.getDate("dateReclamation"));
        reclamation.setEmailUtilisateur(rs.getString("emailUser"));
        User user = new User();
        user.setIdU(rs.getInt("idU"));
        reclamation.setUser(user);

        return reclamation;
    }
}
