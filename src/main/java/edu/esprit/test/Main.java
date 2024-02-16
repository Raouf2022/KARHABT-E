package edu.esprit.test;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;

import edu.esprit.entities.User;
import edu.esprit.tools.DataSource;
import edu.esprit.services.ServiceUser;


import java.sql.Connection;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        DataSource dataSource = DataSource.getInstance();
        Connection connection = dataSource.getCnx();

        LocalDate dateNaissance = LocalDate.of(2001, 10, 20);

        User admin1 = new Admin("fahmiiiii","riahii",java.sql.Date.valueOf(dateNaissance),67567800,"admin@es.tn","si za3bolla");
        User client1 = new Client("client","client",java.sql.Date.valueOf(dateNaissance),12345689,"client@eft.tjbknl","degla");

        ServiceUser serviceUser = new ServiceUser();

        //serviceUser.ajouterUser(admin1);
        //serviceUser.ajouterUser(client1);
        //serviceUser.supprimerUser(3);
        //client1.setIdU(26);
        //client1.setNom("dhoubeba");
        //serviceClient.modifierUser(client1);
        //serviceClient.supprimerUser(16);
        //System.out.println(serviceAdmin.getOneById(19));
        //System.out.println(serviceAdmin.getAll());

    }
}
