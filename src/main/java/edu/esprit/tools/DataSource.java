package edu.esprit.tools;
import java.sql.*;


public class DataSource {
    final String URL="jdbc:mysql://localhost:3306/karhabt-e";
    final String USER ="root";
    final String PASS ="";
    Connection connection ;
    private static DataSource instance ;

    private DataSource(){
        try {
            connection = DriverManager.getConnection(URL,USER ,PASS);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

//solution pour faire la connexion  une seule fois au chaque req
    public static DataSource getInstance (){
       if(instance == null)
            instance =new DataSource ();
       return instance;
        }

       public Connection getConnection(){
           return  connection;
    }
   }

