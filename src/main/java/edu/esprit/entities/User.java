package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class User {
    private int idU;


    public User(){

    }

    public User(int idU) {
        this.idU = idU;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    @Override
    public String toString() {
        return "User{" +
                "idU=" + idU +
                '}';
    }
}
