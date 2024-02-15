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
        //Set<Voiture> voitures = sv.getAll();
        //for (Voiture v : voitures) {
          //  System.out.println(v);
        //}
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

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date dateEntree = null;
        // try {
        //     dateEntree = sdf.parse("2024-01-01");
        // } catch (ParseException e) {
        //     e.printStackTrace();
        // }


        //Arrivage arrivage = new Arrivage( 10, dateEntree, v);
        //ServiceArrivage sa = new ServiceArrivage();
        //sa.ajouter(arrivage);


    }

}


