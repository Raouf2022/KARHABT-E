package edu.esprit.services;

import edu.esprit.entities.AvisReclamation;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceAvisReclamation implements  IServiceAvisReclamation<AvisReclamation> {

    private Connection cnx = DataSource.getInstance().getCnx();

    public ServiceAvisReclamation(Connection cnx) {
    }

    @Override
    public void addAvisReclamation(AvisReclamation avisReclamation) {

        if (avisReclamation == null || avisReclamation.getAvis() == null || avisReclamation.getCommentaire() == null || avisReclamation.getDateAR() == null) {
            System.out.println("Invalid input for adding AvisReclamation. Please provide valid data.");
            return;
        }

        // Check if avis is one of the allowed values
        String allowedAvis[] = {"positif", "negatif", "neutre"};
        String inputAvis = avisReclamation.getAvis().toLowerCase();  // Convert to lowercase for case-insensitive check
//Cette ligne récupère l’avis de la réclamation (probablement une chaîne de caractères) en utilisant la méthode getAvis()
// de l’objet avisReclamation. Elle convertit ensuite cet avis en minuscules avec la méthode toLowerCase().
// Cela permet de rendre la vérification insensible à la casse
        if (!Arrays.asList(allowedAvis).contains(inputAvis)) {
            System.out.println("Invalid value for 'avis'. Please choose from 'positif', 'negatif', or 'neutre'.");
            return;
        }
        String query = "INSERT INTO AvisReclamation (idR, avis, commentaire, dateAR) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, avisReclamation.getIdR());
            preparedStatement.setString(2, avisReclamation.getAvis());
            preparedStatement.setString(3, avisReclamation.getCommentaire());
            preparedStatement.setDate(4, new java.sql.Date(avisReclamation.getDateAR().getTime()));

            preparedStatement.executeUpdate();
            System.out.println("AvisReclamation added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AvisReclamation getAvisReclamationById(int idAR) {
        String query = "SELECT * FROM AvisReclamation WHERE idAR = ?";
        AvisReclamation avisReclamation = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idAR);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                avisReclamation = new AvisReclamation(
                        resultSet.getInt("idAR"),
                        resultSet.getInt("idR"),
                        resultSet.getString("avis"),
                        resultSet.getString("commentaire"),
                        new Date(resultSet.getDate("dateAR").getTime())
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avisReclamation;
    }

    @Override
    public Set<AvisReclamation> getAllAvisReclamations() {
        Set<AvisReclamation> avisReclamations = new HashSet<>();

        String query = "SELECT * FROM AvisReclamation";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idAR = resultSet.getInt("idAR");
                int idR = resultSet.getInt("idR");
                String avis = resultSet.getString("avis");
                String commentaire = resultSet.getString("commentaire");
                Date dateAR = new Date(resultSet.getDate("dateAR").getTime());

                AvisReclamation avisReclamation = new AvisReclamation(idAR, idR, avis, commentaire, dateAR);
                avisReclamations.add(avisReclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avisReclamations;
    }

    @Override
    public void updateCommentaire(int idAR, String newCommentaire) {
        String query = "UPDATE AvisReclamation SET commentaire = ? WHERE idAR = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, newCommentaire);
            preparedStatement.setInt(2, idAR);

            preparedStatement.executeUpdate();
            System.out.println("AvisReclamation commentaire updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAvisReclamation(int idAR) {
        String query = "DELETE FROM AvisReclamation WHERE idAR = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idAR);

            preparedStatement.executeUpdate();
            System.out.println("AvisReclamation deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}