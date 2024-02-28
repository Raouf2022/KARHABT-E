package edu.esprit.services;
import edu.esprit.entities.Dossier;
import edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import edu.esprit.entities.etatDeDossier;



    public class ServiceEtatDossier implements IEtatDossier <etatDeDossier> {

        Connection cnx = DataSource.getInstance().getCnx();


        @Override
        public void supprimer(int id_etat) {
            try {
                String requete = "DELETE FROM etatdossier WHERE id_etat=?";
                System.out.println("etat supprimé");
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, id_etat);
                pst.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        @Override
        public void supprimer(etatDeDossier etatDeDossier) {
            try {
                String requete = "DELETE FROM etatdossier WHERE id_etat=?";
                System.out.println("état supprimé");
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1,etatDeDossier.getId_etat());
                pst.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


        @Override
        public etatDeDossier getOneById(int id_etat) {
            String req = "SELECT * FROM etatdossier WHERE id_etat = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id_etat);

                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    // Create a Dossier object from the retrieved data
                    etatDeDossier d = new etatDeDossier();
                    d.setId_etat(resultSet.getInt("id_etat"));
                    d.setEtat(resultSet.getString("etat"));


                    return d;
                } else {
                    System.out.println("No dossier found with ID " + id_etat);
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving dossier: " + e.getMessage());
                return null;
            }
        }

        @Override
        public Set<etatDeDossier> getAll() {
            Set<etatDeDossier> etats = new HashSet<>();

            String req = "Select * from etatdossier";
            try {
                Statement st = cnx.createStatement();
                ResultSet res = st.executeQuery(req);
                while (res.next()) {
                    etatDeDossier d = new etatDeDossier();
                    d.setId_etat(res.getInt(1));
                    d.setEtat(res.getString("etat"));


                    etats.add(d);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return etats;
        }


        @Override
        public void ajouter(etatDeDossier etatDeDossier) {
        /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
            String req = "INSERT INTO etatdossier (etat) VALUES (?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);


                ps.setString(1, etatDeDossier.getEtat());
                ps.executeUpdate();
                System.out.println("Etat de dossier added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        @Override
        public void modifier(int id_etat, etatDeDossier etatDeDossier) {
            String req = "UPDATE etatdossier SET etat = ? WHERE id_etat = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, etatDeDossier.getEtat());
                ps.setInt(2, id_etat);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Dossier with ID " + id_etat + " modified successfully!");
                } else {
                    System.out.println("No dossier found with ID " + id_etat);
                }
            } catch (SQLException e) {
                System.out.println("Error modifying dossier: " + e.getMessage());
            }
        }
    }

/*
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
    }
    @Override
    public void modifier(Dossier dossier) {
        System.out.print(dossier);
        try {
            String requete3 = "UPDATE dossierb SET cin=?,nom=?,prenom=?,region=?,date=? where id_dossier=id_dossier";
            PreparedStatement pst = cnx.prepareStatement(requete3);
            pst.setInt(1,dossier.getCin());
            pst.setString(2,dossier.getNom());
            pst.setString(3,dossier.getPrenom());
            pst.setString(4,dossier.getRegion());
            pst.setString(5, dossier.getDate());
            pst.executeUpdate();

            System.out.println("dossier updated");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
    public void supprimerid(int id_dossier) {
        try {
            String requete = "DELETE FROM dossierb WHERE id_dossier=?";
            System.out.println("dossier supprimé");
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id_dossier);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public etatDeDossier getOneById(int id_etat) {
        return null;
    }

    @Override
    public Set<Dossier> getAll() {
        Set<Dossier> dossiers = new HashSet<>();

        String req = "Select * from dossierb";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Dossier d = new Dossier();
                d.setCin(res.getInt(1));
                d.setNom(res.getString("nom"));
                d.setPrenom( res.getString("prenom"));
                d.setRegion( res.getString("region"));
                d.setDate( res.getString("date"));


                dossiers.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dossiers;
    }
*/


