package edu.esprit.services;
import edu.esprit.entities.Commentaire;
import edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Commentaireservice implements Iservice <Commentaire> {
    private Connection connection ;

    public Commentaireservice () {
        connection = DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String req = "INSERT INTO Commentaire ( Contenuec, date_pubc,nbrLike,reponse) VALUES ('"
                + commentaire.getContenuec() + "', '"
                + commentaire.getDate_pubc() + "', '"
                + commentaire.getNbrLike() + "', '"
                +commentaire.getReponse() + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "Update commentaire set Contenuec = ? , NbrLike = ? , Date_pubc = ? , idComnt =? , Reponse=?  ";
        PreparedStatement  prepardstatement = connection.prepareStatement(req);
        prepardstatement.setString(1,commentaire.getContenuec());
        prepardstatement.setInt(2,commentaire.getNbrLike());
        prepardstatement.setString(3,commentaire.getDate_pubc());
        prepardstatement.setInt(4,commentaire.getIdComnt());
        prepardstatement.setString(5,commentaire.getReponse());
        prepardstatement.executeUpdate();
        System.out.println("Actualite modifié");

    }

    @Override
    public void supprimer(int idComnt) throws SQLException {
        String req = "DELETE FROM `commentaire`  WHERE idcomnt =? ";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,idComnt);
        preparedStatement.executeUpdate();

    }


    @Override
    public List<Commentaire> recuperer() throws SQLException {
        String req = "select * from commentaire";
        Statement statement = connection.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Commentaire> list = new ArrayList<>();
        while (cs.next()) {
            Commentaire c = new Commentaire();
            c.setIdComnt(cs.getInt("idComnt"));
            c.setContenuec(cs.getString("Contenuec"));
            c.setReponse(cs.getString("reponse"));
            c.setDate_pubc(cs.getString("date_pubc"));
            c.setNbrLike(cs.getInt("nbrLike"));

            list.add(c);
        }
        return list;
    }


}
