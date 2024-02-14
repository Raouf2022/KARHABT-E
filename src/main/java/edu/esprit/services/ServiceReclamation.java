package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IServiceReclamation<Reclamation> {



        Connection cnx = DataSource.getInstance().getCnx();

    public ServiceReclamation(Connection cnx) {
    }

    @Override
    public void ajouterReclamation(Reclamation reclamation) {
        // Vérification de la longueur du sujet et de l'emailUtilisateur
        if (reclamation.getSujet().length() > 255 || reclamation.getEmailUtilisateur().length() > 255) {
            System.out.println("Erreur : La longueur du sujet ou de l'emailUtilisateur dépasse la limite autorisée.");
            return; // Arrêter l'opération si la validation échoue
        }

        try {
            String query = "INSERT INTO Reclamation (sujet, description, dateReclamation, idUser, idVoiture, emailUser) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, reclamation.getSujet());
                preparedStatement.setString(2, reclamation.getDescription());
                preparedStatement.setDate(3, new java.sql.Date(reclamation.getDateReclamation().getTime()));
                preparedStatement.setLong(4, reclamation.getIdUser());
                preparedStatement.setLong(5, reclamation.getIdVoiture());
                preparedStatement.setString(6, reclamation.getEmailUtilisateur());

                preparedStatement.executeUpdate();
                System.out.println("Réclamation ajoutée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//La méthode prepareStatement prend une chaîne query qui est une instruction SQL avec des emplacements
// de paramètres, et renvoie un nouvel objet PreparedStatement qui représente cette instruction SQL précompilée.

    @Override
        public Reclamation getReclamationById(int idR) {
            Reclamation reclamation = null;
            try {
                String query = "SELECT * FROM Reclamation WHERE idR = ?";
                try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                    preparedStatement.setInt(1, idR);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            reclamation = mapResultSetToReclamation(resultSet);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return reclamation;
        }

        @Override
        public List<Reclamation> getAllReclamations() {
            List<Reclamation> reclamations = new ArrayList<>();
            try {
                String query = "SELECT * FROM Reclamation";
                try (Statement statement = cnx.createStatement();
                     //ResultSet est une interface qui représente le résultat d’une requête SQL.
                     // Il contient les données récupérées de la base de données
                     ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) { //vérifie s’il y a une autre ligne dans le ResultSet.
                        Reclamation reclamation = mapResultSetToReclamation(resultSet);
                        reclamations.add(reclamation);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return reclamations;
        }

    @Override
    public void modifierReclamation(Reclamation reclamation, int idR) {
        try {
            String query = "UPDATE Reclamation SET sujet = ?, description = ?, dateReclamation = ?, idUser = ?, idVoiture = ?, emailUser = ? WHERE idR = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, reclamation.getSujet());
                preparedStatement.setString(2, reclamation.getDescription());
                preparedStatement.setDate(3, new java.sql.Date(reclamation.getDateReclamation().getTime()));
                preparedStatement.setLong(4, reclamation.getIdUser());
                preparedStatement.setLong(5, reclamation.getIdVoiture());
                preparedStatement.setString(6, reclamation.getEmailUtilisateur());

                // Utiliser l'ID fourni en paramètre
                preparedStatement.setInt(7, idR);

                preparedStatement.executeUpdate();
                System.out.println("Réclamation modifiée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
        public void supprimerReclamation(int idR) {
            try {
                String query = "DELETE FROM Reclamation WHERE idR = ?";
                try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                    preparedStatement.setLong(1, idR);

                    preparedStatement.executeUpdate();
                    System.out.println("Réclamation supprimée avec succès !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Méthode utilitaire pour mapper un ResultSet à un objet Reclamation
        private Reclamation mapResultSetToReclamation(ResultSet resultSet) throws SQLException {
            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(resultSet.getInt("idR")); // Utiliser getInt pour un ID de type INT
            reclamation.setSujet(resultSet.getString("sujet"));
            reclamation.setDescription(resultSet.getString("description"));
            reclamation.setDateReclamation(resultSet.getDate("dateReclamation"));
            reclamation.setIdUser(resultSet.getInt("idUser"));
            reclamation.setIdVoiture(resultSet.getInt("idVoiture"));
            reclamation.setEmailUtilisateur(resultSet.getString("emailUser"));
            return reclamation;
        }
    }