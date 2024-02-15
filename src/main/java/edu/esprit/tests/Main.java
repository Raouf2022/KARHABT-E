package edu.esprit.tests;

import edu.esprit.entities.AvisReclamation;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceAvisReclamation;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.tools.DataSource;

import java.sql.Connection;
import java.util.Date;

public class Main {
    public static void main(String[] args) {


      DataSource dataSource = DataSource.getInstance();
      Connection cnx = dataSource.getCnx();


        ServiceReclamation sr=new ServiceReclamation(cnx);
        ServiceAvisReclamation sar=new ServiceAvisReclamation(cnx);
        ServiceMessagerie sm =new ServiceMessagerie(cnx);

        Reclamation r1= new Reclamation("Probleme de moteur","pppppppppppppppp",new Date(),"/*//-----",1,2);
        Reclamation r2= new Reclamation("couleur non","cccccccccc",new Date(),"mariem.abouda@gmail.com",5,2);
         // sr.ajouterReclamation(r1);
         // System.out.println(sr.getReclamationById(1));

        System.out.println(sr.getAllReclamations());
      //  System.out.println(r1.getIdR());
        //sr.modifierReclamation(r2,2);
        //sr.supprimerReclamation(2);


AvisReclamation av1= new AvisReclamation(3,"positif","belle voiture",new Date());
      AvisReclamation av2= new AvisReclamation(20000,"neutre","pas mal",new Date());



//sar.addAvisReclamation(av2);
System.out.println(sar.getAllAvisReclamations());
//sar.updateCommentaire(1,"couleur cv");


//sar.deleteAvisReclamation(2);

/*
       Messagerie m1=new Messagerie("quel est l'etat de cette voiture", new Date(),1,2,5);
        Messagerie m2=new Messagerie("quel est l'etat de mouteur", new Date(),1,2,5);

 */

              // sm.ajouterMessagerie(m1);
              //System.out.println( sm.getMessagerieById(1));


        System.out.println( sm.getAllMessageries());
        /*
        sm.modifierMessagerie(m2,1);

        sm.supprimerMessagerie(2);


*/

//sr.ajouterReclamation(r1);

      // sm.modifierContenuMessagerie(4,"Encourager moi de l'acheter ");

sar.updateAvisReclamation(av2,6);

    }

}
