package edu.esprit.services;

import edu.esprit.entities.Messagerie;

import java.util.List;

public interface IServiceMessagerie <M>{

    void ajouterMessagerie(Messagerie messagerie);

    Messagerie getMessagerieById(int idM);

    List<Messagerie> getAllMessageries();

    void modifierMessagerie(Messagerie messagerie, int idM);

    void supprimerMessagerie(int idM);
}
