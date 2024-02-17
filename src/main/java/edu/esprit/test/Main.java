package edu.esprit.test;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.services.Actualiteservice;
import edu.esprit.services.Commentaireservice;
import edu.esprit.services.Reponseservice;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        //DataSource d= DataSource.getInstance();

        // deja fait dans la classe actualite


        Actualiteservice as = new Actualiteservice();
        Commentaireservice cs =new Commentaireservice();
        Reponseservice rs =new Reponseservice();
        User u1 = new User();
        User u2 =  new User ();
        u1.setIdU(24);
        u2.setIdU(12);
        Actualite act=new Actualite();
       act.setIdAct(5);
      Commentaire comt =new Commentaire();
      comt.setIdComnt(7);




                      ///////////// Actualite ///////////

        try {
            as.ajouter(new Actualite("hamdoulhh", "hamdoulh123" , u2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




//       try {
//            as.modifier(new Actualite( 5, "beeeehi", "beeeehi123" ,u1));
//       } catch (SQLException e) {
//           System.out.println(e.getMessage());
//       }



//        try {
//            as.supprimer(new Actualite( 26 ).getIdAct());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }




//        try {
//            System.out.println((as.recuperer()));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



                       //////////////Commentaire ////////////////////////









//        try {
//            cs.ajouter(new Commentaire( "okok" ,15,u2,act));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }




//        try {
//            cs.modifier(new Commentaire( 11,2,"tsssss", u2, act));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



//        try {
//            cs.supprimer(new Commentaire( 10).getIdComnt());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



//        try {
//            System.out.println((cs.recuperer()));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



/*

                      ///////////////Reponse //////////////


//        try {
//            rs.ajouter(new Reponse( "nnnnnn" ,comt ));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }


//        try {
//           rs.modifier(new Reponse( 2,"sssssoooo",comt));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



//        try {
//            rs.supprimer(new Reponse( 2 ).getIdR());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//      }
*/


//        try {
//            System.out.println((rs.recuperer()));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }




    }
}

