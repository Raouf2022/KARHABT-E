package edu.esprit.test;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;

import edu.esprit.services.ServiceAdmin;
import edu.esprit.services.ServiceClient;
import edu.esprit.tools.DataSource;
import edu.esprit.services.ServiceUser;


import java.sql.Connection;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        DataSource dataSource = DataSource.getInstance();
        Connection connection = dataSource.getCnx();

        LocalDate dateNaissance = LocalDate.of(2001, 10, 20);

        //User user1 = new User("raouf" , "mahmoudi" ,java.sql.Date.valueOf(dateNaissance),"96094504","abderraouf.mahmoudi@esprit.tn" , "roufewi123" );
        Admin admin1 = new Admin("monjiiiz","mahmoudi",java.sql.Date.valueOf(dateNaissance),675678,"admin","si za3bolla");
        Client client1 = new Client("client","client",java.sql.Date.valueOf(dateNaissance),12345678,"client@esprit.tn","degla");
        ServiceAdmin serviceAdmin = new ServiceAdmin();
        ServiceClient serviceClient = new ServiceClient();
        ServiceUser serviceUser = new ServiceUser();


        serviceAdmin.ajouterUser(admin1);
        //serviceClient.ajouterUser(client1);
        //serviceUser.supprimerUser(3);
        //client1.setIdU(17);
        //client1.setNom("dhoubeba");
        //serviceClient.modifierUser(client1);
        //serviceClient.supprimerUser(16);
        //System.out.println(serviceAdmin.getOneById(19));
        //System.out.println(serviceAdmin.getAll());
    }
}
