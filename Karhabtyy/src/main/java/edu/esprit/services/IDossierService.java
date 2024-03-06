package edu.esprit.services;


import edu.esprit.entities.Dossier;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IDossierService <T> {

    public void ajouter(T t);
    //public void modifierDos(T t);
    public void modifier( T t);
    public void supprimer(int cin);

    void supprimer(Dossier d);

    //void supprimerid(Dossier d);

    public void supprimerid(int id_dossier);
    public T getOneById(int cin);
    public  List<T> getAll() throws SQLException;

}
