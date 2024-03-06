package edu.esprit.services;

import java.util.Set;

public interface IUserService<T> {

    public void ajouterUser(T t);
    public void modifierUser(T t);
    public void supprimerUser(int id);
    public T getOneById(int id);
    public Set<T> getAll();

}
