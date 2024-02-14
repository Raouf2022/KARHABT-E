package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceReclamation implements IServiceReclamation<Reclamation> {



        Connection cnx = DataSource.getInstance().getCnx();

    public ServiceReclamation(Connection cnx) {
    }

    @Override
    public void ajouterReclamation(Reclamation reclamation) {
        try {
            // Vérification de la longueur du sujet et de l'emailUtilisateur
            if (isInvalidLength(reclamation.getSujet(), 255) || isInvalidLength(reclamation.getEmailUtilisateur(), 255)) {
                System.out.println("Erreur : La longueur du sujet ou de l'emailUtilisateur dépasse la limite autorisée.");
                return; // Arrêter l'opération si la validation échoue
            }

            // Vérification de la validité de l'email
            if (isInvalidEmail(reclamation.getEmailUtilisateur())) {
                System.out.println("Erreur : L'email Utilisateur n'est pas valide.");
                return;
            }

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
            System.out.println("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Vérifie la validité de la longueur d'une chaîne
    private boolean isInvalidLength(String value, int maxLength) {
        return value == null || value.isEmpty() || value.length() > maxLength;
    }

    // Vérifie la validité de l'email avec une expression régulière
    private boolean isInvalidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return !email.matches(regex);
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
    public Set<Reclamation> getAllReclamations() {
        Set<Reclamation> reclamations = new HashSet<>();

        String query = "SELECT * FROM Reclamation";
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int idR = resultSet.getInt("idR");
                // Modifier les noms des attributs pour correspondre à votre modèle de données
                String sujet = resultSet.getString("sujet");
                String description = resultSet.getString("description");
                Date dateReclamation = resultSet.getDate("dateReclamation");
                int idUser = resultSet.getInt("idUser");
                int idVoiture = resultSet.getInt("idVoiture");
                String emailUser = resultSet.getString("emailUser");

                Reclamation reclamation = new Reclamation(idR, sujet, description, dateReclamation, idUser, idVoiture, emailUser);
                reclamations.add(reclamation);
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