package edu.esprit.services;

import edu.esprit.entities.AvisReclamation;
import edu.esprit.entities.AvisType;
import edu.esprit.entities.Reclamation;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceAvisReclamation implements  IService<AvisReclamation> {

    private Connection cnx = DataSource.getInstance().getCnx();

    public ServiceAvisReclamation(Connection cnx) {
    }
    @Override
    public void create(AvisReclamation entity) {
        try {
            if (!isValidAvis(entity.getAvis())) {
                System.out.println("Invalid 'avis' value. Must be one of: positive, negative, neutre.");
                return;
            }

            String query = "INSERT INTO AvisReclamation (idR, avis, commentaire, DateAR) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = cnx.prepareStatement(query, new String[]{"idAR"})) {
                pstmt.setInt(1, entity.getReclamation().getIdR());
                pstmt.setString(2, entity.getAvis());
                pstmt.setString(3, entity.getCommentaire());
                pstmt.setDate(4, new java.sql.Date(entity.getDateAR().getTime()));

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            entity.setIdAR(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate AvisReclamation using Enum
    private boolean isValidAvis(String avis) {
        try {
            AvisType.valueOf(avis.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid 'avis' value. Must be one of: positive, negative, neutre.");
            return false;
        }
    }


    @Override
    public AvisReclamation getById(int id) {
        AvisReclamation avisReclamation = null;
        try {
            String query = "SELECT * FROM AvisReclamation WHERE idAR = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                avisReclamation = mapResultSetToAvisReclamation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisReclamation;
    }

    @Override
    public Set<AvisReclamation> getAll() {
        Set<AvisReclamation> avisReclamations = new HashSet<>();
        try {
            String query = "SELECT * FROM AvisReclamation";
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                AvisReclamation avisReclamation = mapResultSetToAvisReclamation(rs);
                avisReclamations.add(avisReclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisReclamations;
    }

    @Override
    public void update(AvisReclamation entity) {
        try {
            String query = "UPDATE AvisReclamation SET idR=?, avis=?, commentaire=?, DateAR=? WHERE idAR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.setInt(1, entity.getReclamation().getIdR());
            pstmt.setString(2, entity.getAvis());
            pstmt.setString(3, entity.getCommentaire());
            pstmt.setDate(4, new java.sql.Date(entity.getDateAR().getTime()));
            pstmt.setInt(5, entity.getIdAR());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM AvisReclamation WHERE idAR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AvisReclamation mapResultSetToAvisReclamation(ResultSet rs) throws SQLException {
        AvisReclamation avisReclamation = new AvisReclamation();
        avisReclamation.setIdAR(rs.getInt("idAR"));


        avisReclamation.setAvis(rs.getString("avis"));
        avisReclamation.setCommentaire(rs.getString("commentaire"));
        avisReclamation.setDateAR(rs.getDate("DateAR"));
        return avisReclamation;
    }
}