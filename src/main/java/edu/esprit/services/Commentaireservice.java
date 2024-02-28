package edu.esprit.services;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commentaireservice implements Iservice <Commentaire> {
    private Connection connection ;

    public Commentaireservice () {
        connection = DataSource.getInstance().getConnection();
    }


    public List<Commentaire> getCommentsForActualite(int actualiteId) {
        List<Commentaire> comments = new ArrayList<>();
        String req = "SELECT c.*, u.nom FROM commentaire c JOIN user u ON c.idU = u.idU WHERE c.idAct = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, actualiteId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idComnt = rs.getInt("idComnt");
                String contenuec = rs.getString("Contenuec");
                LocalDate date_pubc = rs.getDate("date_pubc").toLocalDate();
                String userName = rs.getString("nom");

                User user = new User();
                user.setNom(userName);
                // Assuming User class has a method to set the name. You might need to adjust based on your User class implementation.

                Actualite act = new Actualite();
                act.setIdAct(actualiteId); // Setting only ID, assuming there's a method setIdAct in Actualite class

                Commentaire commentaire = new Commentaire(idComnt, contenuec, user);
                commentaire.setDate_pubc(date_pubc);
                commentaire.setAct(act);

                comments.add(commentaire);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching comments for Actualite ID " + actualiteId + ": " + e.getMessage());
        }

        return comments;
    }


    public void ajouter(Commentaire commentaire) throws SQLException {
        // Vérification de saisie
        if (commentaire.getContenuec() == null || commentaire.getContenuec().isEmpty()) {
            System.out.println("Le contenu du commentaire ne peut pas être vide.");
            return; // Sortir de la méthode si la saisie est invalide
        }

        // Requête SQL paramétrée
        String req = "INSERT INTO Commentaire (Contenuec,date_pubc, idU, idAct) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, commentaire.getContenuec());
            preparedStatement.setDate(2, Date.valueOf(commentaire.getDate_pubc()));
            preparedStatement.setInt(3, commentaire.getUser().getIdU()); // Ensure this is 12
            preparedStatement.setInt(4, commentaire.getAct().getIdAct());

            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }







    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET Contenuec = ? WHERE idComnt = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, commentaire.getContenuec());
            preparedStatement.setInt(2, commentaire.getIdComnt());

            preparedStatement.executeUpdate();
            System.out.println("Commentaire modifié");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du commentaire : " + e.getMessage());
        }
    }



    @Override
    public void supprimer(int idComnt) throws SQLException {
        String req = "DELETE FROM `commentaire`  WHERE idcomnt =? ";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,idComnt);
        preparedStatement.executeUpdate();
        System.out.println("Commentaire supprimer");

    }

    @Override
    public List<Commentaire> recuperer() throws SQLException {
        String req = "SELECT c.*, u.nom , a.titre  " +
                "FROM commentaire c " +
                "INNER JOIN user u ON c.idU = u.idU " +
                "INNER JOIN actualite a ON c.idAct = a.idAct";
        Statement statement = connection.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Commentaire> list = new ArrayList<>();
        while (cs.next()) {
            Commentaire c = new Commentaire();
            c.setIdComnt(cs.getInt("idComnt"));

            c.setContenuec(cs.getString("Contenuec"));
            c.setDate_pubc(cs.getDate("date_pubc").toLocalDate());

            // Créer un objet User
            User user = new User();
            user.setNom(cs.getString("nom"));
            c.setUser(user);

            // Créer un objet Actualite
            Actualite act = new Actualite();
            act.setTitre(cs.getString("titre"));
            c.setAct(act);

            list.add(c);
        }
        return list;
    }

    @Override
    public Commentaire getOneById(int id) throws SQLException {
            Commentaire commentaire = null;
            String req = "SELECT c.*, u.nom , a.titre  FROM commentaire c   INNER JOIN user u ON c.idU = u.idU INNER JOIN actualite a ON c.idAct = a.idAct WHERE c.idComnt = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setInt(1, id);
                ResultSet res = ps.executeQuery();
                User user = new User();
                Actualite actualite= new Actualite();
                if (res.next()) {
                    LocalDate date_pubc = res.getDate("date_pubc").toLocalDate();
                    String contenuc = res.getString("contenuec");
                    user.setNom(res.getString("nom"));
                    actualite.setTitre(res.getString("titre"));
                    commentaire= new Commentaire( contenuc,date_pubc,  user,actualite);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return commentaire;
        }
    public User fetchUserById(int id) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE idU = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setIdU(resultSet.getInt("idU"));
                user.setNom(resultSet.getString("nom"));

            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
            throw e;
        }

        return user;
    }

}

