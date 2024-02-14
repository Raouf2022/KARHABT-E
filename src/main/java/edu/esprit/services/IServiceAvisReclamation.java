package edu.esprit.services;

import edu.esprit.entities.AvisReclamation;
import edu.esprit.entities.Reclamation;

import java.util.List;
import java.util.Set;

public interface IServiceAvisReclamation <T>{

    void addAvisReclamation(T avisReclamation);

    // Read operation
    T getAvisReclamationById(int idAR);
    Set<AvisReclamation> getAllAvisReclamations();

    // Update operation


    void updateCommentaire(int idAR, String newCommentaire);

    // Delete operation
    void deleteAvisReclamation(int idAR);


}
