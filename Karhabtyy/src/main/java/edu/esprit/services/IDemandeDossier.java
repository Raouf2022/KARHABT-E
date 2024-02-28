package edu.esprit.services;

import java.util.Set;


public interface IDemandeDossier<T> {

    public void ajouter(T t);

    public void modifier(int id_dossier, T t);

    public void supprimerid(int id_demande);

    public T getOneById(int id_demande);

    public Set<T> getAll();

}

