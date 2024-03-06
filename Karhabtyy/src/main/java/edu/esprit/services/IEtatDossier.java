package edu.esprit.services;
import edu.esprit.controllers.Etatdossier;
import edu.esprit.entities.Dossier;
import edu.esprit.entities.etatDeDossier;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
public interface IEtatDossier <T> {

    public void ajouter(T t);
  //  public void modifier(int id_etat,T t);
    public void modifier(T t);
    public void supprimer(int id_etat);
    public void supprimerD(T t);

    public T getOneById(int id_etat);
    public List<T> getAll() throws SQLException;

    void ajouter(etatDeDossier etatDeDossier);
}
