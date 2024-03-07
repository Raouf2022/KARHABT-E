package edu.esprit.controller;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class adminPage {

    @FXML
    private GridPane grid;
    @FXML
    private TextField searchField;


    public ServiceUser serviceUser = new ServiceUser();

    public adminPage() {
    }
    public void loadUsers() {
        Set<User> userList = serviceUser.getAll(); // Assuming this fetches all users
        grid.getChildren().clear(); // Clears existing content before loading new cards
        grid.setHgap(300);
        grid.setVgap(10);
        int row = 1;
        int col = 0;
        for (User user : userList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Container.fxml"));
                Parent userCard = loader.load();
                Container controller = loader.getController();
                controller.setContainer(user);
                grid.add(userCard, col, row);
                col++;
                if (col == 2) {
                    col = 0;
                    grid.setVgap(20);
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {
        loadUsers();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la méthode de recherche avec le nouveau texte
            searchUsers(newValue);
        });
    }
    public void searchUsers(String searchText) {
        Set<User> userList = serviceUser.getAll();
        // Filtrer la liste en fonction du texte de recherche
        Stream<User> filteredList = userList.stream()
                .filter(user -> user.getNom().toLowerCase().contains(searchText.toLowerCase())
                        || user.getPrenom().toLowerCase().contains(searchText.toLowerCase())
                        || user.geteMAIL().toLowerCase().contains(searchText.toLowerCase()));

        // Mettre à jour l'affichage avec les utilisateurs filtrés
        updateDisplay(filteredList.collect(Collectors.toSet()));
    }

    public void updateDisplay(Set<User> users) {
        grid.getChildren().clear();
        grid.setHgap(300);
        grid.setVgap(10);
        int row = 1;
        int col = 0;
        for (User user : users) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Container.fxml"));
                Parent userCard = loader.load();
                Container controller = loader.getController();
                controller.setContainer(user);
                grid.add(userCard, col, row);
                col++;
                if (col == 2) {
                    col = 0;
                    grid.setVgap(20);
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
