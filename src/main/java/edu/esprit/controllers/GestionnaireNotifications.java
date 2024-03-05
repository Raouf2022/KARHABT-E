package edu.esprit.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GestionnaireNotifications {
    private static final BooleanProperty messageEnvoye = new SimpleBooleanProperty(false);

    public static boolean estMessageEnvoye() {
        return messageEnvoye.get();
    }

    public static void setMessageEnvoye(boolean value) {
        messageEnvoye.set(value);
    }

    public static BooleanProperty messageEnvoyeProperty() {
        return messageEnvoye;
    }
}
