package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Actualiteservice implements Iservice <Actualite> {
        private Connection connection ;
public Actualiteservice () {
    connection = DataSource.getInstance().getConnection();
}

    @Override
    public void ajouter(Actualite actualite) throws SQLException {
        String req = "INSERT INTO Actualite (titre, Contenue, date_pub) VALUES ('"
                + actualite.getTitre() + "', '"
                + actualite.getContenue() + "', '"
                + Date.valueOf(actualite.getDate_pub()) + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(req);


    }



    @Override
    public void modifier(Actualite actualite) throws SQLException {
        String req = "UPDATE actualite SET titre = ?, Contenue = ?, idAct = ? WHERE idAct = ?";
        PreparedStatement  prepardstatement = connection.prepareStatement(req);
        prepardstatement.setString(1, actualite.getTitre());
        prepardstatement.setString(2, actualite.getContenue());
        prepardstatement.setInt(3, actualite.getIdAct());
        prepardstatement.setInt(4, actualite.getIdAct());
        prepardstatement.executeUpdate();
        System.out.println("Actualite modifié");
    }





    @Override
    public void supprimer(int idAct) throws SQLException {
        String req = "DELETE FROM `actualite`  WHERE idAct =? ";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,idAct);
        preparedStatement.executeUpdate();

    }


    @Override
    public List<Actualite> recuperer() throws SQLException {
        String req = "select * from actualite";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(req);
        List<Actualite> list = new ArrayList<>();
        while (rs.next()) {
            Actualite a = new Actualite();
            a.setIdAct(rs.getInt("idAct"));
            a.setTitre(rs.getString("titre"));
            a.setContenue(rs.getString("Contenue"));
            a.setDate_pub(rs.getDate("date_pub").toLocalDate());
            list.add(a);
        }
        return list;
    }


    }



