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

        //User admin1 = new Admin("sfvwsbsbvs","thrhrh",java.sql.Date.valueOf(dateNaissance),67567800,"admin@es.tn","si za3bolla");
        //User client1 = new Client("dek  ","khouna",java.sql.Date.valueOf(dateNaissance),12345689,"client@eft.tjbknl","degla");

        ServiceUser serviceUser = new ServiceUser();
        System.out.println(serviceUser.login("admin","admin"));

        //serviceUser.ajouterUser(admin1);
        //serviceUser.ajouterUser(client1);

        //serviceUser.supprimerUser(47);
        //client1.setIdU(26);
        //client1.setNom("dhoubeba");
        serviceUser.modifierUser(new User(131,"aaa","bbbb",java.sql.Date.valueOf(dateNaissance),28304486,"assilhammami36@gmail.com","aaaaa","Admin",""));
        //serviceClient.supprimerUser(16);
        //System.out.println(serviceAdmin.getOneById(19));
        //System.out.println(serviceAdmin.getAll());

    }
}
