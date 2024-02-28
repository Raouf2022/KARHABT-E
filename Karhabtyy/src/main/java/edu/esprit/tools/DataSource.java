package edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataSource {
    private final String URL = "jdbc:mysql://localhost:3306/karhabt-e";
    private final String USER = "root";
    private final String PWD = "";

    private Connection cnx;

    private static DataSource instance;

    private DataSource(){
        try {
            cnx = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connected successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //bch ma yokoodch kol ma nhot instance yyamali connection succeded.
    public static DataSource getInstance(){
        if(instance == null)
            instance = new DataSource();
        return instance;
    }
    // yekhou bel getter mtaa cnx el connection bid'ha w ycompari maa el bd , URL w USER w PWD
// ken shah yaffichi msg connected successfully sinon msg d'exception
    public Connection getCnx(){
        return this.cnx;
    }
}