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

//sr.create(r2);
//System.out.println(sr.getById(14));

//System.out.println(sr.getAll());

//sr.delete(16);
       // Reclamation rr= new Reclamation();
       // rr.setIdR(15);
//AvisReclamation av1= new AvisReclamation(rr,"positive","non panne",new Date());

//sar.create(av1);


       // sar.delete(16);

        Messagerie m1= new Messagerie("c quoi letatat de moteur",new Date(),u1,u2,false,false);
        Messagerie m2= new Messagerie("couleur bonne",new Date(),u1,u2,true,false);
        sm.create(m2);

        //System.out.println(sm.getById(9));
        //System.out.println(sm.getAll());
        //sm.delete(11);

    }



}
