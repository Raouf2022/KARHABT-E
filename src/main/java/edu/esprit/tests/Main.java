package edu.esprit.tests;

import edu.esprit.entities.AvisReclamation;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
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


User u1 = new User();
u1.setIdU(24);
User u2 =new User();
u2.setIdU(25);
Reclamation r1=new Reclamation("moteur en panne","mmmmmmmmmm",new Date(),"mariem.abouda@gmail.com",u1);
        Reclamation r2=new Reclamation("couleur pas bonne","heyyyyy",new Date(),"mariem.abouda@gmail.com",u1);
        Reclamation r3=new Reclamation("mauvais service","pas respect ",new Date(),"hafsa.alia@gmail.com",u1);
        Reclamation r4=new Reclamation("stock limité ","pourquoi",new Date(),"heifa.wahbi@gmail.com",u2);

//sr.create(r3);
//sr.create(r4);
//System.out.println(sr.getById(14));

//System.out.println(sr.getAll());

//sr.delete(17);
       Reclamation rr1= new Reclamation();
        Reclamation rr2= new Reclamation();
        rr1.setIdR(15);
        rr2.setIdR(22);
        AvisReclamation av1= new AvisReclamation(rr1,"positive","non panne",new Date());
        AvisReclamation av2= new AvisReclamation(rr2,"negative","oui mauvais service",new Date());
        AvisReclamation av3= new AvisReclamation(rr2,"neutreEEE","jai pas une idée",new Date());

      // sar.create(av2);
        sar.create(av3);


       // sar.delete(16);

        Messagerie m1= new Messagerie("c quoi letatat de moteur",new Date(),u1,u2,false,false);
        Messagerie m2= new Messagerie("couleur bonne",new Date(),u1,u2,true,false);
        Messagerie m3= new Messagerie("pouvez vous de me parler sur la voiture",new Date(),u1,u2,false,true);

     //  sm.create(m3);

       // System.out.println(sm.getById(9));
      // System.out.println(sm.getAll());
        //sm.delete(11);

    }



}
