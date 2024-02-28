package edu.esprit.services;

import edu.esprit.entities.Dossier;

import edu.esprit.tools.DataSource;

import java.sql.*;
        import java.util.HashSet;
import java.util.Set;


 public class ServiceDossier implements IDossierService<Dossier> {

                Connection cnx = DataSource.getInstance().getCnx();

                @Override
                public void ajouter(Dossier dossier) {

                    String req = "INSERT INTO dossierb ( cin,nom, prenom,region,date,montant) VALUES (?,?,?,?,?,?)";
                    try {
                        PreparedStatement ps = cnx.prepareStatement(req);
                        if (dossier.getCin() > 0) {
                            ps.setInt(1, dossier.getCin());
                        } else {
                            throw new IllegalArgumentException("Invalid CIN (must be positive)");
                        }
                        if (dossier.getNom() != null && !dossier.getNom().isEmpty()) {
                            ps.setString(2, dossier.getNom());
                        } else {
                            throw new IllegalArgumentException("Nom cannot be empty");
                        }


                        if (dossier.getPrenom() != null && !dossier.getPrenom().isEmpty()) {
                            ps.setString(3, dossier.getPrenom());
                        } else {
                            throw new IllegalArgumentException("Prenom cannot be empty");
                        }

                        if (dossier.getRegion() != null && !dossier.getRegion().isEmpty()) {
                            ps.setString(4, dossier.getRegion());
                        } else {
                            throw new IllegalArgumentException("Region cannot be empty");
                        }

                       ps.setDate(5, (Date) dossier.getDate());
                        ps.setInt(6, dossier.getMontant());


                        ps.executeUpdate();
                        System.out.println("Dossier added !");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }



     /*@Override
     public void modifier(int cin) {
         try {
             String requete3 = "UPDATE dossier SET nom=nomn WHERE id_event=?";
             PreparedStatement pst = cnx.prepareStatement(requete3);
             pst.setInt(1, cin);
             pst.executeUpdate();
             System.out.println("dossier updated");
         } catch (Exception ex) {
             System.out.println(ex.getMessage());
         }
     }*/
                @Override
                public void modifier(Dossier dossier) {
                    System.out.print(dossier);
                    try {
                        String requete3 = "UPDATE dossierb SET cin=?,nom=?,prenom=?,date=? ,region=?, montant=? where id_dossier=id_dossier";
                        PreparedStatement pst = cnx.prepareStatement(requete3);
                        pst.setInt(1, dossier.getCin());
                        pst.setString(2, dossier.getNom());
                        pst.setString(3, dossier.getPrenom());
                        pst.setDate(4, (Date) dossier.getDate());
                        pst.setString(5, dossier.getRegion());
                        pst.setInt(6, dossier.getMontant());

                        pst.executeUpdate();

                        System.out.println("dossier updated");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                public void modifierD(int id_dossier, Dossier dossier) {
                    String req = "UPDATE dossierb SET nom = ?, prenom = ?, region = ?, date = ? , montant=? WHERE id_dossier = ?";
                    try {
                        PreparedStatement ps = cnx.prepareStatement(req);
                        ps.setString(1, dossier.getNom());
                        ps.setString(2, dossier.getPrenom());
                        ps.setString(3, dossier.getRegion());
                        ps.setDate(4, (Date) dossier.getDate());
                        ps.setInt(5, dossier.getMontant());
                        ps.setInt(6, id_dossier);

                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Dossier with ID " + id_dossier + " modified successfully!");
                        } else {
                            System.out.println("No dossier found with ID " + id_dossier);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error modifying dossier: " + e.getMessage());

                    }
                }

                @Override
                public void supprimer(int cin) {
                    try {
                        String requete = "DELETE FROM dossierb WHERE cin=?";
                        System.out.println("dossier supprimé");
                        PreparedStatement pst = cnx.prepareStatement(requete);
                        pst.setInt(1, cin);
                        pst.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

    @Override
    public void supprimer(Dossier d) {
        try {
            String requete = "DELETE FROM dossierb WHERE cin=?";
            System.out.println("dossier supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,d.getCin() );
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


   /*@Override
    public void supprimerid(Dossier d) {
        try {
            String requete = "DELETE FROM dossierb WHERE id_dossier=?";
            System.out.println("Dossier supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, d.getId_dossier()); // Assuming getCin() returns the cin value from the Dossier object
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }*/


                @Override
                public void supprimerid(int id_dossier) {
                    try {
                        String requete = "DELETE FROM dossierb WHERE id_dossier=?";
                        System.out.println("dossier DELETED");
                        PreparedStatement pst = cnx.prepareStatement(requete);
                        pst.setInt(1, id_dossier);
                        pst.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }


                @Override
                public Dossier getOneById(int id_dossier) {
                    String req = "SELECT * FROM dossierb WHERE id_dossier = ?";
                    try {
                        PreparedStatement ps = cnx.prepareStatement(req);
                        ps.setInt(1, id_dossier);

                        ResultSet resultSet = ps.executeQuery();
                        if (resultSet.next()) {
                            // Create a Dossier object from the retrieved data
                            Dossier dossier = new Dossier();
                            dossier.setId_dossier(resultSet.getInt("id_dossier"));
                            dossier.setCin(resultSet.getInt("cin"));
                            dossier.setNom(resultSet.getString("nom"));
                            dossier.setPrenom(resultSet.getString("prenom"));
                            dossier.setRegion(resultSet.getString("region"));
                            dossier.setDate(resultSet.getDate("date"));

                            return dossier;
                        } else {
                            System.out.println("No dossier found with ID " + id_dossier);
                            return null;
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving dossier: " + e.getMessage());
                        return null;
                    }
                }


                @Override
                public Set<Dossier> getAll() {
                    Set<Dossier> dossierSet = new HashSet<>();
                    String requete = "SELECT * FROM dossierb";
                    try {
                        Statement st = cnx.createStatement();
                        ResultSet rs = st.executeQuery(requete);
                        while (rs.next()) {
                            Dossier d = new Dossier();
                            d.setId_dossier(rs.getInt("id_dossier"));
                            d.setCin(rs.getInt("cin"));
                            d.setNom(rs.getString("nom"));
                            d.setPrenom(rs.getString("prenom"));
                            d.setRegion(rs.getString("region"));
                            d.setDate(rs.getDate("date"));

                            dossierSet.add(d);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }

                    return dossierSet;

                }

     public int getId_dossier() {
                    return  getId_dossier();
     }
 }

