package edu.esprit.services;
import edu.esprit.entities.Arrivage;
import edu.esprit.tools.DataSource;
import edu.esprit.entities.Voiture;
import java.util.Date;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


import java.util.Set;

public class ServiceArrivage implements IService <Arrivage> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Arrivage arrivage) {
// Vérifiez que la quantité est positive
        if (arrivage.getQuantite() <= 0) {
            System.out.println("Erreur : la quantité doit être positive.");
            return;
        }

        // Vérifiez que la date d'entrée n'est pas dans le futur
        if (arrivage.getDateEntree().after (new Date())) {
            System.out.println("Erreur : la date d'entrée" +
                    " ne peut pas être dans le futur.");
            return;
        }

        // Vérifiez que la voiture existe dans la base de données
        ServiceVoiture serviceVoiture = new ServiceVoiture();
        Voiture voiture = serviceVoiture.getOneById(arrivage.getV().getIdV());
        if (voiture == null) {
            System.out.println("Erreur : aucune voiture trouvée avec l'ID " + arrivage.getV().getIdV());
            return;
        }

        String req = "INSERT INTO `arrivage`( `quantite`, `DateEntree`, `idV`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, arrivage.getQuantite());
            ps.setDate(2, new java.sql.Date(arrivage.getDateEntree().getTime()));
            ps.setInt(3, arrivage.getV().getIdV());
            ps.executeUpdate();
            System.out.println("Arrivage ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Arrivage arrivage) {
        String req = "UPDATE `Arrivage` SET `quantite` = ?, `DateEntree` = ?, `idV` = ? WHERE `idA` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, arrivage.getQuantite());
            ps.setDate(2, new java.sql.Date(arrivage.getDateEntree().getTime()));
            ps.setInt(3, arrivage.getV().getIdV());
            ps.setInt(4, arrivage.getIdA());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Arrivage avec l'ID " + arrivage.getIdA() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun arrivage trouvé avec l'ID " + arrivage.getIdA());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `Arrivage` WHERE idA = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Arrivage avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun arrivage trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

    @Override
    public Arrivage getOneById(int id) {

        Arrivage arrivage = null;
        String req = "SELECT * FROM Arrivage WHERE idA = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int quantite = res.getInt("quantite");
                Date dateEntree = res.getDate("DateEntree");
                ServiceVoiture serviceVoiture = new ServiceVoiture(); // Créez une instance de ServiceVoiture
                Voiture v = serviceVoiture.getOneById(res.getInt("idV")); // Appelez getOneById sur l'instance
                arrivage = new Arrivage(id, quantite, dateEntree, v);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrivage;
    }



    @Override
    public Set<Arrivage> getAll() {
        Set<Arrivage> arrivages = new HashSet<>();
        ServiceVoiture serviceVoiture = new ServiceVoiture(); // Créez une instance de ServiceVoiture

        String req = "SELECT * FROM Arrivage";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idA = res.getInt("idA");
                int quantite = res.getInt("quantite");
                Date dateEntree = res.getDate("DateEntree");
                Voiture v = serviceVoiture.getOneById(res.getInt("idV")); // Utilisez la méthode getOneById de ServiceVoiture
                Arrivage a = new Arrivage(idA, quantite, dateEntree, v);
                arrivages.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrivages;
    }


}



