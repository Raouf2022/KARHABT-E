package edu.esprit.test;

import edu.esprit.entities.Arrivage;
import edu.esprit.entities.Voiture;
import edu.esprit.services.ServiceArrivage;
import edu.esprit.services.ServiceVoiture;
import edu.esprit.tools.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;

import java.util.HashSet;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        Connection connection = dataSource.getCnx();


        // Voiture v = new Voiture("suzuki","celerio", "Noire",20000,"neuve,efficace");

        //Set<Voiture> voitures = new HashSet<>();


        //ServiceVoiture sv = new ServiceVoiture();
        //voitures = sv.getAll();
        //for (Voiture v : voitures) {
        //  System.out.println(v);

        //ServiceArrivage sa = new ServiceArrivage();
        //Set<Arrivage> arrivages = sa.getAll();
        //for(Arrivage a : arrivages){
        //System.out.println(a);

        //}
        //Arrivage arrivage = sa.getOneById(3);
        //System.out.println(arrivage);


        //System.out.println(v);
        //serviceVoiture.ajouter(v);
        //v.setIdV(25);
        //v.setMarque("marcedes");
        //v.setModele("benz");
        //v.setCouleur("Rouge");
        //v.setPrix(25000); // Remplacez par le nouveau prix
        //v.setDescription("neuve,efficace");
        //serviceVoiture.modifier(v);
        ServiceVoiture sv = new ServiceVoiture();
        sv.supprimer(28);
    }
}
        //ServiceVoiture serviceVoiture = new ServiceVoiture();
        //Voiture voiture = serviceVoiture.getOneById(30); // Remplacez 28 par l'ID de la voiture que vous voulez tester

       // Date dateEntree = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //try {
          //  dateEntree = sdf.parse("2024-02-15"); // Remplacez "2024-02-15" par la date que vous voulez tester

        //} catch (ParseException e) {
         //   e.printStackTrace();
        //}
        //Arrivage arrivage = new Arrivage(10, dateEntree, v);
        //ServiceArrivage serviceArrivage = new ServiceArrivage();
        //serviceArrivage.ajouter(arrivage);












        /*ServiceArrivage serviceArrivage = new ServiceArrivage();

        // Créez une instance de ServiceVoiture
        ServiceVoiture sv = new ServiceVoiture();

        Arrivage arrivage = serviceArrivage.getOneById(3);

        // Utilisez la méthode getOneById pour obtenir la Voiture existante
        Voiture v = sv.getOneById(26);

        arrivage.setQuantite(30);
        serviceArrivage.modifier(arrivage);
        /*ServiceVoiture sv = new ServiceVoiture();
        Voiture v = sv.getOneById(26);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateEntree = null;
        try {
            dateEntree = sdf.parse("2024-02-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
          Arrivage arrivage = new Arrivage(18,dateEntree,v);
        ServiceArrivage serviceArrivage = new ServiceArrivage();
serviceArrivage.ajouter(arrivage);*/
//ServiceVoiture sv = new ServiceVoiture();
     //Voiture voiture = new Voiture("toyota","agya","gris",20000,"neuve, petite, efficace");
     //sv.ajouter(voiture);

     /*ServiceArrivage sa = new ServiceArrivage();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateEntree = null;
        try
            dateEntree = sdf.parse("2024-02-16");
         catch (ParseException e) {
            e.printStackTrace();
        }
     Arrivage a = new Arrivage(12,dateEntree,v);
     sa.ajouter(a);*/









