package edu.esprit.test;
import  edu.esprit.entities.Dossier;
import edu.esprit.services.IDossierService;

import edu.esprit.tools.DataSource;
import edu.esprit.services.ServiceDossier;
import  edu.esprit.entities.etatDeDossier;
import edu.esprit.services.IEtatDossier;

import edu.esprit.tools.DataSource;
import edu.esprit.services.ServiceEtatDossier;

import java.sql.Connection;
import java.sql.Date;

public class Main {



    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        Connection connection = dataSource.getCnx();

        IDossierService d = new ServiceDossier();
      //  System.out.println(d.getAll());

        IEtatDossier e = new ServiceEtatDossier() ;

      //  System.out.println(d.getAll());
        Dossier d1 =new Dossier(12423142,"eva","eoeo","Gafsa", new Date(2425,3,4), 2);
        Dossier d2 =new Dossier(24365483,"BOBO","TEJ","Bizert", new Date(28,02,2002),2);
        Dossier d3 =new Dossier(73528463,"RGE","RBT","SOUSSE", new Date(28,02,2002),4);
        etatDeDossier e2 = new etatDeDossier("maaaarysssy");
        etatDeDossier e3 = new etatDeDossier("ABOUDA");




       // d.ajouter(d2);
    // d.supprimerid(d2.getId_dossier());
       /* d.ajouter(d2);
        d.ajouter(d3);*/
//sd.supprimer(12423142);
        //sd.supprimer(24365483);
        // sd.supprimer(73528463);
        //sd.modifier(d3);
        //sd.supprimerid(26);

        //sd.getAll();


    }
}
