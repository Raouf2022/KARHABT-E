package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;


public class Messagerie {

    private int idMessage;
    private String contenu;
    private Date dateEnvoie;
    private User sender;
    private User receiver;
    private boolean vu;
    private boolean deleted;

    public Messagerie(){}

    public Messagerie(int idMessage, String contenu, Date dateEnvoie, User sender, User receiver, boolean vu, boolean deleted) {
        this.idMessage = idMessage;
        this.contenu = contenu;
        this.dateEnvoie = dateEnvoie;
        this.sender = sender;
        this.receiver = receiver;
        this.vu = vu;
        this.deleted = deleted;
    }

    public Messagerie(String contenu, Date dateEnvoie, User sender, User receiver, boolean vu, boolean deleted) {
        this.contenu = contenu;
        this.dateEnvoie = dateEnvoie;
        this.sender = sender;
        this.receiver = receiver;
        this.vu = vu;
        this.deleted = deleted;
    }

    public Messagerie(int sender, String contenu, java.sql.Date dateEnvoie, int sender1) {
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public User getSender() {
        return sender;
    }

    public int getIDSender() {
        if (getSender() != null) {
            return getSender().getIdU();
        } else {
            // Handle the case where sender is null (return a default value or throw an exception)
            throw new IllegalStateException("Sender is null in messagerie.");
        }
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isVu() {
        return vu;
    }

    public void setVu(boolean vu) {
        this.vu = vu;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getSenderId() {
        return sender.getIdU();
    }

    public String getReceiverName() {
        return receiver.getNom() + " " + receiver.getPrenom();
    }

    @Override
    public String toString() {
        return "Messagerie{" +
                "idMessage=" + idMessage +
                ", contenu='" + contenu + '\'' +
                ", dateEnvoie=" + dateEnvoie +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", vu=" + vu +
                ", deleted=" + deleted +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messagerie that = (Messagerie) o;
        return idMessage == that.idMessage && vu == that.vu && deleted == that.deleted && Objects.equals(contenu, that.contenu) && Objects.equals(dateEnvoie, that.dateEnvoie) && Objects.equals(sender, that.sender) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, contenu, dateEnvoie, sender, receiver, vu, deleted);
    }

}
