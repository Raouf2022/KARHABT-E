package edu.esprit.controller;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;

import java.io.IOException;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;


public class adminPage {

    @FXML
    private GridPane grid;

    public ServiceUser serviceUser = new ServiceUser();

    public adminPage() {
    }
    public void loadUsers() {
        Set<User> userList = serviceUser.getAll(); // Assuming this fetches all users
        grid.getChildren().clear(); // Clears existing content before loading new cards
        grid.setHgap(400);
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
    }
}
