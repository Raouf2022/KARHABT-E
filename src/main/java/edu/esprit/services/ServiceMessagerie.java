package edu.esprit.services;

import edu.esprit.entities.Messagerie;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceMessagerie implements IServiceMessagerie<Messagerie> {

    Connection cnx = DataSource.getInstance().getCnx();

    public ServiceMessagerie(Connection cnx) {
    }

    @Override
    public void ajouterMessagerie(Messagerie messagerie) {
        try {
            String query = "INSERT INTO Messagerie (contenu, dateEnvoie, idUSender, idUReceiver, idV) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, messagerie.getContenu());
                preparedStatement.setDate(2, new java.sql.Date(messagerie.getDateEnvoie().getTime()));
                preparedStatement.setInt(3, messagerie.getIdUSender());
                preparedStatement.setInt(4, messagerie.getIdUReceiver());
                preparedStatement.setInt(5, messagerie.getIdV());

                preparedStatement.executeUpdate();

                // Récupérer l'ID généré pour la nouvelle messagerie
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idM = generatedKeys.getInt(1);
                        messagerie.setIdM(idM);
                        System.out.println("Messagerie ajoutée avec succès, ID : " + idM);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Messagerie getMessagerieById(int idM) {
        Messagerie messagerie = null;
        try {
            String query = "SELECT * FROM Messagerie WHERE idM = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, idM);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        messagerie = mapResultSetToMessagerie(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messagerie;
    }

    @Override
    public Set<Messagerie> getAllMessageries() {
        Set<Messagerie> messageries = new HashSet<>();

        String query = "SELECT * FROM Messagerie";
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int idMessagerie = resultSet.getInt("idM");
                String contenu = resultSet.getString("contenu");
                Date dateEnvoi = resultSet.getDate("dateEnvoie");
                // Modifier les noms des attributs pour correspondre à votre modèle de données
                int idExpediteur = resultSet.getInt("idUSender");
                int idDestinataire = resultSet.getInt("idUReceiver");

                Messagerie messagerie = new Messagerie(idMessagerie, contenu, dateEnvoi, idExpediteur, idDestinataire);
                messageries.add(messagerie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageries;
    }


    @Override
    public void modifierMessagerie(Messagerie messagerie, int idM) {
        try {
            String query = "UPDATE Messagerie SET contenu = ?, dateEnvoie = ?, idUSender = ?, idUReceiver = ?, idV = ? WHERE idM = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, messagerie.getContenu());
                preparedStatement.setDate(2, new java.sql.Date(messagerie.getDateEnvoie().getTime()));
                preparedStatement.setInt(3, messagerie.getIdUSender());
                preparedStatement.setInt(4, messagerie.getIdUReceiver());
                preparedStatement.setInt(5, messagerie.getIdV());
                preparedStatement.setInt(6, idM);

                preparedStatement.executeUpdate();
                System.out.println("Messagerie modifiée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerMessagerie(int idM) {
        try {
            String query = "DELETE FROM Messagerie WHERE idM = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, idM);

                preparedStatement.executeUpdate();
                System.out.println("Messagerie supprimée avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void modifierContenuMessagerie(int idM, String nouveauContenu) {
        try {
            String query = "UPDATE Messagerie SET contenu = ?, dateEnvoie = CURRENT_DATE WHERE idM = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, nouveauContenu);
                preparedStatement.setInt(2, idM);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Messagerie (ID: " + idM + ") modifiée avec succès !");
                } else {
                    System.out.println("Aucune messagerie trouvée avec l'ID : " + idM);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //La méthode mapResultSetToReclamation(ResultSet resultSet) est utilisée pour convertir une ligne de cette table (représentée par le ResultSet) en un objet Messagerie.
    private Messagerie mapResultSetToMessagerie(ResultSet resultSet) throws SQLException {
        Messagerie messagerie = new Messagerie();
        messagerie.setIdM(resultSet.getInt("idM"));
        messagerie.setContenu(resultSet.getString("contenu"));
        messagerie.setDateEnvoie(resultSet.getDate("dateEnvoie"));
        messagerie.setIdUSender(resultSet.getInt("idUSender"));
        messagerie.setIdUReceiver(resultSet.getInt("idUReceiver"));
        messagerie.setIdV(resultSet.getInt("idV"));
        return messagerie;
    }
}
