package edu.esprit.entities;

public class User {

    int idU;

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
