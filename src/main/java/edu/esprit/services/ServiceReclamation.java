package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();


    public ServiceReclamation(Connection cnx) {
    }

    public ServiceReclamation() {

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
            pstmt.setDate(3, new Date(entity.getDateReclamation().getTime()));
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
            String query = "SELECT r.idR, r.sujet, r.description, r.dateReclamation, " +
                    "r.emailUser, " +
                    "u.idU AS userId, u.nom AS userNom, u.prenom AS userPrenom, " +
                    "u.DateNaissance AS userDateNaissance, u.numTel AS userNumTel, " +
                    "u.eMAIL AS userEmail, u.passwd AS userPasswd, u.role AS userRole " +
                    "FROM reclamation r " +
                    "JOIN user u ON r.idU = u.idU " +
                    "WHERE r.idR = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reclamation = new Reclamation();
                reclamation.setIdR(rs.getInt("idR"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                reclamation.setEmailUtilisateur(rs.getString("emailUser"));

                // Créer un utilisateur associé à la réclamation
                User user = new User();
                user.setIdU(rs.getInt("userId"));
                user.setNom(rs.getString("userNom"));
                user.setPrenom(rs.getString("userPrenom"));
                user.setDateNaissance(rs.getDate("userDateNaissance"));
                user.setNumTel(rs.getInt("userNumTel"));
                user.seteMAIL(rs.getString("userEmail"));
                user.setPasswd(rs.getString("userPasswd"));
                user.setRole(rs.getString("userRole"));

                // Associer l'utilisateur à la réclamation
                reclamation.setUser(user);
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
            String query = "SELECT r.idR, r.sujet, r.description, r.dateReclamation, r.emailUser, " +
                    "u.nom AS userNom, u.prenom AS userPrenom, " +
                    "u.DateNaissance AS userDateNaissance, u.numTel AS userNumTel, " +
                    "u.eMAIL AS userEmail, u.passwd AS userPasswd, u.role AS userRole " +
                    "FROM reclamation r " +
                    "JOIN user u ON r.idU = u.idU";


            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdR(rs.getInt("idR"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                reclamation.setEmailUtilisateur(rs.getString("emailUser"));

                // Créer un utilisateur associé à la réclamation
                User user = new User();
                user.setNom(rs.getString("userNom"));
                user.setPrenom(rs.getString("userPrenom"));
                user.setDateNaissance(rs.getDate("userDateNaissance"));
                user.setNumTel(rs.getInt("userNumTel"));
                user.seteMAIL(rs.getString("userEmail"));
                user.setPasswd(rs.getString("userPasswd"));
                user.setRole(rs.getString("userRole"));
                // Ajouter d'autres attributs de l'utilisateur si nécessaire

                // Associer l'utilisateur à la réclamation
                reclamation.setUser(user);

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
            if (entity.getUser() == null || entity.getUser().getIdU() <= 0) {
                // Handle the case where the User is null or has an invalid idU
                // You may want to throw an exception or log an error
                return;
            }

            String query = "UPDATE reclamation SET sujet=?, description=?, dateReclamation=?, emailUser=?, idU=? WHERE idR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.setString(1, entity.getSujet());
            pstmt.setString(2, entity.getDescription());
            pstmt.setDate(3, new Date(entity.getDateReclamation().getTime()));
            pstmt.setString(4, entity.getEmailUtilisateur());
            pstmt.setInt(5, entity.getUser().getIdU());
            pstmt.setInt(6, entity.getIdR());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update2(int id, Reclamation newReclamation) {
        try {
            String query = "UPDATE reclamation SET sujet=?, description=?, dateReclamation=?, emailUser=?, idU=? WHERE idR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.setString(1, newReclamation.getSujet());
            pstmt.setString(2, newReclamation.getDescription());
            pstmt.setDate(3, new java.sql.Date(newReclamation.getDateReclamation().getTime()));
            pstmt.setString(4, newReclamation.getEmailUtilisateur());
            pstmt.setInt(5, newReclamation.getUser().getIdU());
            pstmt.setInt(6, id);  // Utilisez l'ID fourni en paramètre pour identifier la réclamation à mettre à jour.

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Assurez-vous de traiter correctement l'exception SQLException dans votre application.
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
        user.setIdU(rs.getInt("user"));
        reclamation.setUser(user);

        return reclamation;
    }
    // In ServiceReclamation class

    public boolean exists(String email, String sujet, String description) {
        try {
            String query = "SELECT COUNT(*) FROM reclamation WHERE emailUser = ? AND sujet = ? AND description = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, sujet);
            pstmt.setString(3, description);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    ////////////////METIER
    public Set<Reclamation> triReclamationsParDate() {
        Set<Reclamation> reclamationsTriees = new TreeSet<>(Comparator.comparing(Reclamation::getDateReclamation));

        try {
            String query = "SELECT r.idR, r.sujet, r.description, r.dateReclamation, r.emailUser, " +
                    "u.nom AS userNom, u.prenom AS userPrenom, " +
                    "u.DateNaissance AS userDateNaissance, u.numTel AS userNumTel, " +
                    "u.eMAIL AS userEmail, u.passwd AS userPasswd, u.role AS userRole " +
                    "FROM reclamation r " +
                    "JOIN user u ON r.idU = u.idU " +
                    "ORDER BY r.dateReclamation ASC";

            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdR(rs.getInt("idR"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                reclamation.setEmailUtilisateur(rs.getString("emailUser"));

                // Créer un utilisateur associé à la réclamation
                User user = new User();
                user.setNom(rs.getString("userNom"));
                user.setPrenom(rs.getString("userPrenom"));
                user.setDateNaissance(rs.getDate("userDateNaissance"));
                user.setNumTel(rs.getInt("userNumTel"));
                user.seteMAIL(rs.getString("userEmail"));
                user.setPasswd(rs.getString("userPasswd"));
                user.setRole(rs.getString("userRole"));

                // Associer l'utilisateur à la réclamation
                reclamation.setUser(user);

                reclamationsTriees.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamationsTriees;
    }



}
