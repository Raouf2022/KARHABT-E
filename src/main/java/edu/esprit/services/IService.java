package edu.esprit.services;

import edu.esprit.entities.ReponseReclamation;

import java.util.List;
import java.util.Set;

public interface IService<T> {


        // Create
        void create(T entity);

        // Read
        T getById(int id);

        Set<T> getAll();

        // Update
        void update(T entity);

        void update2(int id, T entity);

        // Delete
        void delete(int id);



}
