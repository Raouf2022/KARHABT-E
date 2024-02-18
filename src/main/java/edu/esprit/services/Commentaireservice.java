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




    public void ajouter(Commentaire commentaire) throws SQLException {
        // Vérification de saisie
        if (commentaire.getContenuec() == null || commentaire.getContenuec().isEmpty()) {
            System.out.println("Le contenu du commentaire ne peut pas être vide.");
            return; // Sortir de la méthode si la saisie est invalide
        }

        // Requête SQL paramétrée
        String req = "INSERT INTO Commentaire (Contenuec,Rating, date_pubc, idU , idAct) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, commentaire.getContenuec());
            preparedStatement.setInt(2, commentaire.getRating());
            preparedStatement.setDate(3, Date.valueOf(commentaire.getDate_pubc()));


            // Supposons que idUtilisateur soit l'identifiant de l'utilisateur associé au commentaire
            preparedStatement.setInt(4, commentaire.getUser().getIdU());
            preparedStatement.setInt(5, commentaire.getAct().getIdAct());

            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }






    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET Contenuec = ?, Rating = ? WHERE idComnt = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, commentaire.getContenuec());
            preparedStatement.setInt(2, commentaire.getRating());
            preparedStatement.setInt(3, commentaire.getIdComnt());

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
            c.setRating(cs.getInt("Rating"));
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
                    int Rating = res.getInt("Rating");
                    commentaire= new Commentaire( contenuc,date_pubc,Rating,  user,actualite);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return commentaire;
        }
    }

