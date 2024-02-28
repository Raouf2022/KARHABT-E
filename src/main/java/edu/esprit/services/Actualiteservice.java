package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Actualiteservice implements Iservice <Actualite> {
    private Connection connection ;
    public Actualiteservice () {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Actualite actualite) throws SQLException {
        // Vérification de saisie

        if (actualite.getTitre() == null || actualite.getTitre().isEmpty() || actualite.getContenue() == null || actualite.getContenue().isEmpty()) {
            System.out.println("Le titre et le contenu ne peuvent pas etre vides.");
            return; // Sortir de la méthode si la saisie est invalide
        }

        // Requête SQL paramétrée
        String req = "INSERT INTO Actualite (titre, Image, rating ,Contenue, date_pub ) VALUES (?, ?, ? ,?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, actualite.getTitre());
            preparedStatement.setString(2, actualite.getImage());
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, actualite.getContenue());
            preparedStatement.setDate(5, Date.valueOf(actualite.getDate_pub()));


            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Actualité ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'actualité : " + e.getMessage());
        }
    }








    @Override
    public void modifier(Actualite actualite) throws SQLException {
        // Vérifier que le titre et le contenu ne sont ni vides ni nuls
        if (actualite.getTitre() != null && !actualite.getTitre().isEmpty() &&
                actualite.getContenue() != null && !actualite.getContenue().isEmpty()) {

            String req = "UPDATE actualite SET titre = ?, Image = ?, Contenue = ?, rating = ? WHERE idAct = ?";
            PreparedStatement prepardstatement = connection.prepareStatement(req);
            prepardstatement.setString(1, actualite.getTitre());
            prepardstatement.setString(2, actualite.getImage());
            prepardstatement.setString(3, actualite.getContenue());
            prepardstatement.setDouble(4, actualite.getRating());
            prepardstatement.setInt(5, actualite.getIdAct());
            prepardstatement.executeUpdate();
            System.out.println("Actualite modifié");

        } else {
            System.out.println("Le titre et le contenu ne peuvent pas être vides ou nuls.");
        }
    }



    @Override
    public void supprimer(int idAct) throws SQLException {
        // Vérifier si l'actualité avec l'ID spécifié existe
        if (existeIdActualite(idAct)) {
            String req = "DELETE FROM `actualite` WHERE idAct = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, idAct);
            preparedStatement.executeUpdate();
            System.out.println("Actualite Supprimer");
        } else {
            System.out.println("L'actualité avec l'ID " + idAct + " n'existe pas. La suppression a été annulée.");
        }
    }

    // Méthode pour vérifier si l'ID de l'actualite existe dans la table
    private boolean existeIdActualite(int idAct) throws SQLException {
        String req = "SELECT COUNT(*) FROM actualite WHERE idAct = ?";
        try (PreparedStatement prepStatement = connection.prepareStatement(req)) {
            prepStatement.setInt(1, idAct);
            try (ResultSet resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }





    @Override
    public List<Actualite> recuperer() throws SQLException {
        String req = "SELECT * FROM actualite";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(req);
        List<Actualite> list = new ArrayList<>();
        while (rs.next()) {
            Actualite actualite = new Actualite();
            actualite.setIdAct(rs.getInt("idAct"));
            actualite.setTitre(rs.getString("titre"));
            actualite.setImage(rs.getString("Image"));
            actualite.setContenue(rs.getString("Contenue"));
            actualite.setRating(rs.getDouble("rating"));
            actualite.setDate_pub(rs.getDate("date_pub").toLocalDate());

            list.add(actualite);
        }
        return list;
    }


    @Override
    public Actualite getOneById(int id) throws SQLException {
        Actualite actualite = null;
        String req = "SELECT a.*, u.nom as nom " +
                "FROM actualite a " +
                "INNER JOIN user u ON a.idU = u.idU WHERE a.idAct = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            User user = new User();
            if (res.next()) {
                LocalDate date_pub = res.getDate("date_pub").toLocalDate();
                String contenu = res.getString("contenue");
                user.setNom(res.getString("nom")); // Fix the column name to user_nom
                String titre = res.getString("titre");
                String Image = res.getString("Image");
                actualite = new Actualite(titre, Image, contenu, date_pub, user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return actualite;
    }

}
