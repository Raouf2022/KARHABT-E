package edu.esprit.services;

import edu.esprit.controllers.DemandeDossier;

import java.util.Set;


public interface IDemandeDossier<T> {

    public void ajouter(T t);

    public void modifier(int id_dossier, T t);

    public void supprimerid(int id_demande);

    public void supprimer(T t);



    public T getOneById(int id_demande);

    public Set<T> getAll();

}

