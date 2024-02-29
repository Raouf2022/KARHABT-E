package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.ReponseReclamation;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.*;

public class ServiceReponseReclamation implements  IService<ReponseReclamation> {

    private Connection cnx = DataSource.getInstance().getCnx();

    public ServiceReponseReclamation(Connection cnx) {
    }

    public ServiceReponseReclamation() {

    }

    @Override
    public void create(ReponseReclamation entity) {

        String query = "INSERT INTO ReponseReclamation (idR, contenuReponse, DateReponseR) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(query, new String[]{"idReponseR"})) {
            pstmt.setInt(1, entity.getReclamation().getIdR());
            pstmt.setString(2, entity.getContenuReponse());

            pstmt.setDate(3, new java.sql.Date(entity.getDateReponseR().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setIdReponseR(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        @Override
        public ReponseReclamation getById ( int id){
            ReponseReclamation reponseReclamation = null;
            try {
                String query = "SELECT * FROM ReponseReclamation WHERE idReponseR = ?";
                PreparedStatement pstmt = cnx.prepareStatement(query);
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    reponseReclamation = mapResultSetToAvisReclamation(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return reponseReclamation;
        }

        @Override
        public Set<ReponseReclamation> getAll () {
            Set<ReponseReclamation> reponseReclamations= new HashSet<>();
            try {
                String query = "SELECT * FROM ReponseReclamation";
                Statement stmt = cnx.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    ReponseReclamation reponseReclamation = mapResultSetToAvisReclamation(rs);
                    reponseReclamations.add(reponseReclamation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return reponseReclamations;
        }

    @Override
    public void update(ReponseReclamation entity) {
        try {
            String query = "UPDATE ReponseReclamation SET  ContenuReponse=?, DateReponseR=? WHERE idReponseR=?";
            PreparedStatement pstmt = cnx.prepareStatement(query);

            pstmt.setString(1, entity.getContenuReponse());
            pstmt.setDate(2, new java.sql.Date(entity.getDateReponseR().getTime()));
            pstmt.setInt(3, entity.getIdReponseR());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update2(int id, ReponseReclamation newReponse) {
        try {
            String query = "UPDATE ReponseReclamation SET idR=?, ContenuReponse=?, DateReponseR=? WHERE idReponseR=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setInt(1, newReponse.getReclamation().getIdR());
                pst.setString(2, newReponse.getContenuReponse());
                pst.setDate(3, new java.sql.Date(newReponse.getDateReponseR().getTime()));
                pst.setInt(4, id);  // Utilisez l'ID fourni en paramètre pour identifier la réponse à mettre à jour.

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Assurez-vous de traiter correctement l'exception SQLException dans votre application.
        }
    }


    @Override
        public void delete ( int id){
            try {
               String query = "DELETE FROM ReponseReclamation WHERE idReponseR=?";
                PreparedStatement pstmt = cnx.prepareStatement(query);
                pstmt.setInt(1, id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    private ReponseReclamation mapResultSetToAvisReclamation(ResultSet rs) throws SQLException {
        ReponseReclamation reponseReclamation = new ReponseReclamation();
        reponseReclamation.setIdReponseR(rs.getInt("idReponseR"));

        // Assuming idR is the foreign key linking ReponseReclamation to Reclamation
        int reclamationId = rs.getInt("idR");

        // Instantiate a ServiceReponseReclamation and get the Reclamation object by id
        ServiceReclamation serviceReclamation= new ServiceReclamation();
        Reclamation reclamation = serviceReclamation.getById(reclamationId);

        // Set the Reclamation object in ReponseReclamation
        reponseReclamation.setReclamation(reclamation);

        reponseReclamation.setContenuReponse(rs.getString("contenuReponse"));
        reponseReclamation.setDateReponseR(rs.getDate("DateReponseR"));

        return reponseReclamation;
    }

}
