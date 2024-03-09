package app;

import ctrl.Controller;

public class Application {

    public final static String UTILISATEUR_COMPTE = "23d3-12ad-58ea-44d1";
    public final static String UTILISATEUR_USER = "emf";
    public final static String UTILISATEUR_MDP = "emf123";

    /**
     * Modèle d'application "MVC" pour 226A et suivants, avec interfaces et ihm
     * JavaFX.
     *
     * @author EMF-Informatique
     */
    public static void main(String[] args) {
        new Controller().start();
    }

}
