package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;

public class Reponseservice implements Iservice<Reponse> {

    private Connection connection ;
    public Reponseservice() {
        connection = DataSource.getInstance().getConnection();
    }



    @Override
    public  void ajouter(Reponse reponse) throws SQLException {
        // Vérification de saisie
        if (reponse.getContinueR() == null || reponse.getContinueR().isEmpty()) {
            System.out.println("Le contenu de la réponse ne peut pas être vide.");
            return; // Sortir de la méthode si la saisie est invalide
        }


        String req = "INSERT INTO Reponse (ContenueR, date_Rep, idComnt,idU) VALUES (?, ?, ?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, reponse.getContinueR());
            preparedStatement.setDate(2, valueOf(reponse.getDate_Rep()));

            preparedStatement.setInt(3, reponse.getComnt().getIdComnt());
            preparedStatement.setInt(4, reponse.getUser().getIdU());

            preparedStatement.executeUpdate();
            System.out.println("Réponse ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {
        String req = "UPDATE reponse SET ContenueR = ?  WHERE idR= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, reponse.getContinueR());
            preparedStatement.setInt(2, reponse.getIdR());

            preparedStatement.executeUpdate();
            System.out.println("Commentaire modifié");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du commentaire : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int idR) throws SQLException {
        String req = "DELETE FROM `reponse`  WHERE idR=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,idR);
        preparedStatement.executeUpdate();
        System.out.println("Reponse supprimer");

    }
    public int getNumberOfResponses(int commentId) {
        int responseCount = 0;
        String query = "SELECT COUNT(*) AS responseCount FROM Reponse WHERE idComnt = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    responseCount = resultSet.getInt("responseCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responseCount;
    }
    public List<Reponse> getResponsesForComment(int commentId) {
        List<Reponse> responses = new ArrayList<>();
        // Update your query to also fetch user details along with comment details
        String query = "SELECT r.*, c.*, u.* FROM Reponse r " +
                "INNER JOIN Commentaire c ON r.idComnt = c.idComnt " +
                "INNER JOIN User u ON r.idU = u.idU " +
                "WHERE r.idComnt = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, commentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reponse response = new Reponse();
                response.setIdR(rs.getInt("idR"));
                response.setContinueR(rs.getString("ContenueR"));
                response.setDate_Rep(rs.getDate("date_Rep").toLocalDate());

                // Set comment details
                Commentaire comnt = new Commentaire();
                comnt.setIdComnt(rs.getInt("idComnt"));
                comnt.setContenuec(rs.getString("contenuec"));
                // Add other fields of Commentaire if necessary
                response.setComnt(comnt);

                // Set user details
                User user = new User();
                user.setIdU(rs.getInt("idU")); // Assuming your User class has this method
                user.setNom(rs.getString("nom")); // Assuming your User class has this method
                // Add other fields of User if necessary
                response.setUser(user);

                responses.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responses;
    }


    @Override
    public List<Reponse> recuperer() throws SQLException {
        String req = "SELECT r.*, c.contenuec , u.nom FROM reponse r  INNER JOIN commentaire c ON r.idcomnt = c.idcomnt INNER JOIN user u ON r.idU= u.idU ";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        List<Reponse> list = new ArrayList<>();
        while (rs.next()) {
            Reponse r = new Reponse();
            r.setIdR(rs.getInt("idR"));
            r.setContinueR(rs.getString("ContenueR"));
            r.setDate_Rep(rs.getDate("date_Rep").toLocalDate());

            Commentaire commentaire = new Commentaire();
            commentaire.setContenuec(rs.getString("contenuec"));
            r.setComnt(commentaire);

            User user= new User();
            user.setNom(rs.getString("nom"));
            r.setUser(user);

            list.add(r);
        }
        return list;
    }

    @Override
    public Reponse getOneById(int id) throws SQLException {
            Reponse reponse = null;
            String req = "SELECT r.*, c.contenuec , u.nom FROM reponse r  INNER JOIN commentaire c ON r.idcomnt = c.idcomnt INNER JOIN user u ON r.idU= u.idU  WHERE r.idR = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setInt(1, id);
                ResultSet res = ps.executeQuery();
                User user = new User();
                Commentaire commentaire= new Commentaire();
                if (res.next()) {
                    LocalDate date_Rep = res.getDate("date_Rep").toLocalDate();
                    String contenueR = res.getString("contenueR");
                    user.setNom(res.getString("nom"));
                 commentaire.setContenuec(res.getString("contenuec"));
                reponse= new Reponse( contenueR,date_Rep,  commentaire,user);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return reponse;
        }
    }


