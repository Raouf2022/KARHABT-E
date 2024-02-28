package edu.esprit.services;

import edu.esprit.entities.Voiture;
import edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
public class ServiceVoiture implements IService <Voiture>{
    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Voiture voiture) {
        String req = "INSERT INTO `Voiture`( `marque`, `modele`, `couleur`, `prix`,`img`, `description`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setInt(1, voiture.getIdV());
            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getModele());
            ps.setString(3, voiture.getCouleur());
            ps.setDouble(4, voiture.getPrix());
            ps.setString(5, voiture.getImg());
            ps.setString(6, voiture.getDescription());
            ps.executeUpdate();
            System.out.println("Voiture ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Voiture voiture) {

        String req = "UPDATE `Voiture` SET `marque` = ?, `modele` = ?, `couleur` = ?, `prix` = ?, `img`=?,`description` = ? WHERE `idV` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getModele());
            ps.setString(3, voiture.getCouleur());
            ps.setDouble(4, voiture.getPrix());
            ps.setString(5, voiture.getImg());
            ps.setString(6, voiture.getDescription());
            ps.setInt(7, voiture.getIdV());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Voiture avec l'ID " + voiture.getIdV() + " a été mise à jour avec succès.");
            } else {
                System.out.println("Aucune voiture trouvée avec l'ID " + voiture.getIdV());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `Voiture` WHERE idV = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {

                System.out.println("Voiture avec l'ID " + id + " a été supprimée avec succès.");
            }
            else {
                System.out.println("Aucune voiture trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public  Voiture getOneById(int id) {
        Voiture voiture = null;
        String req = "SELECT * FROM Voiture WHERE idV = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String marque = res.getString("marque");
                String modele = res.getString("modele");
                String couleur = res.getString("couleur");
                double prix = res.getDouble("prix");
                String img = res.getString("img");
                String description = res.getString("description");
                voiture = new Voiture(id, marque, modele, couleur, prix,img, description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return voiture;
    }

    @Override
    public Set<Voiture> getAll() {
        Set<Voiture> voitures = new HashSet<>();

        String req = "Select * from voiture";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idV = res.getInt("idV");
                String marque = res.getString("marque");
                String modele = res.getString("modele");
                String couleur = res.getString("couleur");
                double prix = res.getDouble("prix");
                String img = res.getString("img");
                String description = res.getString("description");
                Voiture v = new Voiture(idV,marque,modele,couleur,prix,img,description);
                voitures.add(v);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return voitures;
    }
}
