package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.tools.DataSource;

import java.sql.Connection;
import java.util.List;

public interface IServiceReclamation<T> {



    void ajouterReclamation(T reclamation);


    T getReclamationById(int idR);
    List<T> getAllReclamations();





    void modifierReclamation(T reclamation, int idR);



    void supprimerReclamation(int idR);
}
