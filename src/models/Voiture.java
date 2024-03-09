package models;

public class Voiture {

    private final String marque;
    private final String modele;

    public Voiture( String marque, String modele ) {
        this.marque = marque;
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    @Override
    public String toString() {
        return marque + " - " + modele;
    }

}
