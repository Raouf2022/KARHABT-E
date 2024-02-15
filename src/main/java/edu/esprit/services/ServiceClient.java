package edu.esprit.services;


import edu.esprit.entities.Client;
import edu.esprit.tools.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ServiceClient implements IUserService <Client>{

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterUser(Client client) {
        String req = "INSERT INTO `User`(`nom`, `prenom` , `dateNaissance` , `numTel` , `eMail` , `passwd`,`role`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,client.getNom());
            ps.setString(2,client.getPrenom());
            // convertit b string.valueOf bch nekhou el date khaterha mahich string
            ps.setString(3, String.valueOf(client.getDateNaissance()));
            if (!isValidPhoneNumber(client.getNumTel())) {
                System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                return;
            }
            ps.setInt(4,client.getNumTel());
            if (!isValidEmail(client.geteMAIL())) {
                System.out.println("Adresse e-mail invalide !");
                return;
            }
            ps.setString(5,client.geteMAIL());
            ps.setString(6,client.getPasswd());
            ps.setString(7,"Client");
            ps.executeUpdate();
            System.out.println("Client added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierUser(Client client) {

        // Vérification que tous les champs sont remplis
        if (client.getNom().isEmpty() ||
                client.getPrenom().isEmpty() ||
                client.getDateNaissance() == null ||
                client.geteMAIL().isEmpty() ||
                client.getPasswd().isEmpty()) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        String req = "UPDATE User SET `nom`=?, `prenom`=?, `dateNaissance`=?, `numTel`=?, `eMail`=?, `passwd`=? WHERE `idU`=? AND `Role`='Client'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            // Convertir la date en String pour l'insertion dans la base de données
            ps.setString(3, String.valueOf(client.getDateNaissance()));
            if (!isValidPhoneNumber(client.getNumTel())) {
                System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                return;
            }
            ps.setInt(4, client.getNumTel());
            // Vérification de la validité de l'adresse e-mail
            if (!isValidEmail(client.geteMAIL())) {
                System.out.println("Adresse e-mail invalide !");
                return;
            }
            ps.setString(5, client.geteMAIL());
            ps.setString(6, client.getPasswd());
            // Supposons que l'ID de l'utilisateur soit disponible dans la classe Admin
            ps.setInt(7, client.getIdU());
            ps.executeUpdate();
            System.out.println("Admin updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimerUser(int id) {
        String req = "DELETE FROM `User` WHERE idU = ? AND Role = 'Client'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client with ID " + id + " has been deleted successfully.");
            } else {
                System.out.println("No client found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Client getOneById(int id) {
        return null;
    }

    @Override
    public Set<Client> getAll() {
        return null;
    }

    private boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière pour vérifier le format de l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    // Vérifie si le numéro de téléphone contient exactement 8 chiffres
    private boolean isValidPhoneNumber(int numTel) {
        // Conversion du numéro de téléphone en une chaîne pour obtenir sa longueur
        String numTelStr = String.valueOf(numTel);
        return numTelStr.length() == 8;
    }
}
