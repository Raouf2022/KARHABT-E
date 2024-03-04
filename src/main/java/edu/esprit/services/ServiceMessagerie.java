package edu.esprit.services;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceMessagerie implements IService<Messagerie> {

    Connection cnx = DataSource.getInstance().getCnx();

    public ServiceMessagerie(Connection cnx) {
    }
public ServiceMessagerie(){}

    @Override
    public void create(Messagerie entity) {
        try {
            String query = "INSERT INTO Messagerie (contenu, dateEnvoie, Sender, Receiver, vu, deleted) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getContenu());
                pst.setTimestamp(2, new Timestamp(entity.getDateEnvoie().getTime()));
                pst.setInt(3, entity.getSender().getIdU());
                pst.setInt(4, entity.getReceiver().getIdU());
                pst.setBoolean(5, entity.isVu());
                pst.setBoolean(6, entity.isDeleted());

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet rs = pst.getGeneratedKeys()) {
                        if (rs.next()) {
                            entity.setIdMessage(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handle the exception (throw custom exception, log, etc.)
            e.printStackTrace();
        }
    }




    public List<Messagerie> getMessagesBySender(int senderId) {
        List<Messagerie> messages = new ArrayList<>();

        String query = "SELECT m.idMessage, m.contenu, m.dateEnvoie, m.Sender, m.Receiver, m.vu, m.deleted, "
                + "uSender.idU AS senderId, uSender.nom AS senderNom, uSender.prenom AS senderPrenom, "
                + "uSender.DateNaissance AS senderDateNaissance, uSender.numTel AS senderNumTel, "
                + "uSender.eMAIL AS senderEmail, uSender.passwd AS senderPasswd, uSender.role AS senderRole, "
                + "uReceiver.nom AS receiverNom, uReceiver.prenom AS receiverPrenom "
                + "FROM Messagerie m "
                + "JOIN User uSender ON m.Sender = uSender.idU "
                + "JOIN User uReceiver ON m.Receiver = uReceiver.idU "
                + "WHERE m.Sender = ?";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, senderId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Messagerie messagerie = new Messagerie();
                    messagerie.setIdMessage(rs.getInt("idMessage"));
                    messagerie.setContenu(rs.getString("contenu"));
                    messagerie.setDateEnvoie(rs.getTimestamp("dateEnvoie"));

                    // Set sender data
                    User sender = new User(
                            rs.getInt("senderId"),
                            rs.getString("senderNom"),
                            rs.getString("senderPrenom"),
                            rs.getDate("senderDateNaissance"),
                            rs.getInt("senderNumTel"),
                            rs.getString("senderEmail"),
                            rs.getString("senderPasswd"),
                            rs.getString("senderRole")
                    );
                    messagerie.setSender(sender);

                    // Set receiver data
                    User receiver = new User();
                    receiver.setIdU(rs.getInt("Receiver"));
                    receiver.setNom(rs.getString("receiverNom"));
                    receiver.setPrenom(rs.getString("receiverPrenom"));
                    messagerie.setReceiver(receiver);

                    messagerie.setVu(rs.getBoolean("vu"));
                    messagerie.setDeleted(rs.getBoolean("deleted"));

                    messages.add(messagerie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }



    @Override
    public Messagerie getById(int id) {
        Messagerie messagerie = null;
        try {
            String query = "SELECT * FROM Messagerie WHERE idMessage = ?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    messagerie = mapResultSetToMessagerie(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messagerie;
    }

    @Override
    public Set<Messagerie> getAll() {
        Set<Messagerie> messages = new HashSet<>();
        try {
            String query = "SELECT * FROM Messagerie";
            try (Statement st = cnx.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Messagerie messagerie = mapResultSetToMessagerie(rs);
                    messages.add(messagerie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }



    @Override
    public void update(Messagerie entity) {
        try {
            String query = "UPDATE Messagerie SET contenu=?, dateEnvoie=?, Sender=?, Receiver=?, vu=?, deleted=? WHERE idMessage=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, entity.getContenu());
                pst.setTimestamp(2, new Timestamp(entity.getDateEnvoie().getTime()));
                pst.setInt(3, entity.getSender().getIdU());
                pst.setInt(4, entity.getReceiver().getIdU());
                pst.setBoolean(5, entity.isVu());
                pst.setBoolean(6, entity.isDeleted());
                pst.setInt(7, entity.getIdMessage());

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void update2(int id, Messagerie newMessagerie) {
        try {
            String query = "UPDATE Messagerie SET contenu=?, dateEnvoie=?, Sender=?, Receiver=?, vu=?, deleted=? WHERE idMessage=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, newMessagerie.getContenu());
                pst.setTimestamp(2, new Timestamp(newMessagerie.getDateEnvoie().getTime()));
                pst.setInt(3, newMessagerie.getSender().getIdU());
                pst.setInt(4, newMessagerie.getReceiver().getIdU());
                pst.setBoolean(5, newMessagerie.isVu());
                pst.setBoolean(6, newMessagerie.isDeleted());
                pst.setInt(7, id);  // Utilisez l'ID fourni en paramètre pour identifier le message à mettre à jour.

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Assurez-vous de traiter correctement l'exception SQLException dans votre application.
        }
    }


    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM Messagerie WHERE idMessage=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Messagerie mapResultSetToMessagerie(ResultSet rs) throws SQLException {
        Messagerie messagerie = new Messagerie();
        messagerie.setIdMessage(rs.getInt("idMessage"));
        messagerie.setContenu(rs.getString("contenu"));
        messagerie.setDateEnvoie(rs.getTimestamp("dateEnvoie"));
        // Set sender and receiver using User IDs and retrieve User objects from the database
        messagerie.setSender(getUserById(rs.getInt("Sender")));
        messagerie.setReceiver(getUserById(rs.getInt("Receiver")));
        messagerie.setVu(rs.getBoolean("vu"));
        messagerie.setDeleted(rs.getBoolean("deleted"));
        return messagerie;
    }

    private User getUserById(int userId) throws SQLException {
        // Implement logic to retrieve User from the database using the provided userId
        // This is just a placeholder, replace it with your actual logic
        User user = new User();
        user.setIdU(userId);
        return user;
    }
}

