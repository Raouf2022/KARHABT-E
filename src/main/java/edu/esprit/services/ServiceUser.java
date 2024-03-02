package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;




import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IUserService<User> {

    private Connection cnx ;

    public Connection getCnx() {
        return cnx;
    }

    public ServiceUser() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouterUser(User user) {
        if (user instanceof Admin) {
            String req = "INSERT INTO User(`nom`, `prenom`, `dateNaissance`, `numTel`, `eMail`, `passwd`, `role`, `imageUser`) VALUES (?, ? , ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, user.getNom());
                ps.setString(2, user.getPrenom());
                ps.setString(3, String.valueOf(user.getDateNaissance()));
                if (!isValidPhoneNumber(user.getNumTel())) {
                    System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                    return;
                }
                ps.setInt(4, user.getNumTel());
                if (!isValidEmail(user.geteMAIL())) {
                    System.out.println("Adresse e-mail invalide !");
                    return;
                }
                ps.setString(5, user.geteMAIL());
                ps.setString(6, user.getPasswd());
                ps.setString(7, "Admin");
                ps.setString(8, user.getImageUser());
                ps.executeUpdate();
                System.out.println("Admin ajouté !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'admin : " + e.getMessage());
            }
        } else if (user instanceof Client) {
            String req = "INSERT INTO User(`nom`, `prenom`, `dateNaissance`, `numTel`, `eMail`, `passwd`, `role`,`imageUser`) VALUES (?, ?, ?, ?, ? ,?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, user.getNom());
                ps.setString(2, user.getPrenom());
                ps.setString(3, String.valueOf(user.getDateNaissance()));
                if (!isValidPhoneNumber(user.getNumTel())) {
                    System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                    return;
                }
                ps.setInt(4, user.getNumTel());
                if (!isValidEmail(user.geteMAIL())) {
                    System.out.println("Adresse e-mail invalide !");
                    return;
                }
                ps.setString(5, user.geteMAIL());
                ps.setString(6, user.getPasswd());
                ps.setString(7, "Client");
                ps.setString(8, user.getImageUser());
                ps.executeUpdate();
                System.out.println("Client ajouté !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
            }
        } else {
            System.out.println("Type d'utilisateur non pris en charge.");
        }
    }

    @Override
    public void modifierUser(User user) {
        String req = "UPDATE User SET `nom`=?, `prenom`=?, `dateNaissance`=?, `numTel`=?, `eMAIL`=?, `passwd`=? ,`role`=?, `imageUser`=? WHERE `idU`=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, String.valueOf(user.getDateNaissance()));
            if (!isValidPhoneNumber(user.getNumTel())) {
                System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                return;
            }
            ps.setInt(4, user.getNumTel());
            if (!isValidEmail(user.geteMAIL())) {
                System.out.println("Adresse e-mail invalide !");
                return;
            }
            ps.setString(5, user.geteMAIL());
            ps.setString(6, user.getPasswd());
            ps.setString(8, user.getImageUser());
            ps.setInt(9, user.getIdU());
            ps.setString(7, user.getRole());


            ps.executeUpdate();
            System.out.println("Utilisateur mis à jour !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void supprimerUser(int id) {
        String req = "DELETE FROM `User` WHERE idU = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public User getOneById(int id) {
        String req = "SELECT * FROM `User` WHERE idU=?";
        User user = null;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int idU = res.getInt("idU");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateNaissance = res.getDate("dateNaissance");
                int numTel = res.getInt("numTel");
                String eMail = res.getString("eMail");
                String passwd = res.getString("passwd");
                String role = res.getString("role");
                String imageUser = res.getString("imageUser");
                if (role.equals("Admin")) {
                    user = new Admin(idU, nom, prenom, dateNaissance, numTel, eMail, passwd, role,imageUser );
                } else if (role.equals("Client")) {
                    user = new Client(idU, nom, prenom, dateNaissance, numTel, eMail, passwd, role, imageUser);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return user;
    }
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        String query = "SELECT * FROM `User` WHERE eMail=? AND passwd=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idU = rs.getInt("idU");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date dateNaissance = rs.getDate("dateNaissance");
                int numTel = rs.getInt("numTel");
                String role = rs.getString("role");
                String imageUser = rs.getString("imageUser");

                // Instantiate the User object based on the retrieved data
                if (role.equals("Admin")) {
                    user = new Admin(idU, nom, prenom, dateNaissance, numTel, email, password, role, imageUser);
                } else if (role.equals("Client")) {
                    user = new Client(idU, nom, prenom, dateNaissance, numTel, email, password, role, imageUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public Set<User> getAll() {
        Set<User> users = new HashSet<>();
        String req = "SELECT * FROM `User`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idU = res.getInt("idU");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateNaissance = res.getDate("dateNaissance");
                int numTel = res.getInt("numTel");
                String eMail = res.getString("eMail");
                String passwd = res.getString("passwd");
                String role = res.getString("role");
                String imageUser = res.getString("imageUser");
                if (role.equals("Admin")) {
                    users.add(new Admin(idU, nom, prenom, dateNaissance, numTel, eMail, passwd, role,imageUser));
                } else if (role.equals("Client")) {
                    users.add(new Client(idU, nom, prenom, dateNaissance, numTel, eMail, passwd, role,imageUser));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return users;
    }
    public boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière pour vérifier le format de l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*\\.?[a-zA-Z0-9_+&*-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    // Vérifie si le numéro de téléphone contient exactement 8 chiffres
    public boolean isValidPhoneNumber(int numTel) {
        // Conversion du numéro de téléphone en une chaîne pour obtenir sa longueur
        String numTelStr = String.valueOf(numTel);
        return numTelStr.length() == 8;
    }

    public String login (String email , String password){
        String req = "SELECT * FROM `User` WHERE eMAIL=? AND passwd=?";
        String role = "";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                role = res.getString("role");
            }

            }catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return role;
    }
    public boolean updatePassword(String email, String newPassword) {
        String req = "UPDATE User SET passwd = ? WHERE eMAIL = ?";
        try {
            // Assuming you have a method to get the database connection
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, newPassword); // You should hash the password
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkUserExists(String email) {
        String req = "SELECT count(1) FROM `User` WHERE eMAIL=?";
        boolean exists = false;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                // If count is greater than 0, then the user exists
                exists = res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
        }
        return exists;
    }

}