package edu.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <T> {
    void ajouter ( T t) throws SQLException;
    void modifier (T t) throws SQLException;

    void supprimer (int idAct) throws SQLException;
    List<T> recuperer () throws SQLException;

}
