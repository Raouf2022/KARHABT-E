package edu.esprit.test;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.services.Actualiteservice;
import edu.esprit.services.Commentaireservice;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        //DataSource d= DataSource.getInstance();

        // deja fait dans la classe actualite


        Actualiteservice as = new Actualiteservice();
        Commentaireservice cs =new Commentaireservice();



/*

        try {
            as.ajouter(new Actualite("cherry", "contenue"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




       try {
            as.modifier(new Actualite( 3, "xxxx", "contenue2"));
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }




        try {
            as.supprimer(new Actualite( 1 ).getIdAct());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





        try {
            System.out.println((as.recuperer()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


*/
///////////////Commentaire ////////////////////////


/*

        try {
            cs.ajouter(new Commentaire( "Leila" ,4,"hhhh"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        /*


        try {
            cs.ajouter(new Commentaire( "mmmm" , "nnnnn",4,"fffff"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



*/
        try {
            cs.modifier(new Commentaire( 2,"xxxxxx" ,6,"ok"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



/*
        try {
            cs.supprimer(new Commentaire( 1 ).getIdComnt());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



        try {
            System.out.println((cs.recuperer()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        */




    }
}

