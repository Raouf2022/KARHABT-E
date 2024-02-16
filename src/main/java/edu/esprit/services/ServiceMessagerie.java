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

                pst.executeUpdate();

                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    entity.setIdMessage(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

