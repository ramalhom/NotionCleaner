package services;

import models.Voiture;
import java.util.ArrayList;
import java.util.List;

public class ServiceLocation implements IServiceLocation {

    private List<Voiture> voituresDisponibles;
    private List<Voiture> voituresLoueesParLeClient;
    private boolean estConnecte;

    public ServiceLocation() {
        estConnecte = false;
        voituresDisponibles = new ArrayList<>();
        voituresLoueesParLeClient = new ArrayList<>();
    }

    public boolean connexion( String compte, String utilisateur, String mdp ) {

        // Typiquement ces données viendraient d'un cloud ou d'une BD
        if ( !estConnecte ) {
            voituresDisponibles = new ArrayList<>();
            voituresLoueesParLeClient = new ArrayList<>();
            voituresDisponibles.add( new Voiture( "Audi", "S3" ) );
            voituresDisponibles.add( new Voiture( "Audi", "S4" ) );
            voituresDisponibles.add( new Voiture( "BMW", "X5" ) );
            voituresDisponibles.add( new Voiture( "BMW", "530e" ) );
            voituresDisponibles.add( new Voiture( "BMW", "320i" ) );
            voituresDisponibles.add( new Voiture( "Bugatti", "Veyron" ) );
            voituresDisponibles.add( new Voiture( "Seat", "Cupra sport" ) );
            voituresDisponibles.add( new Voiture( "Seat", "Leon" ) );
            voituresDisponibles.add( new Voiture( "Skoda", "Fabia break" ) );
            estConnecte = true;
        }

        return estConnecte;
    }

    public void deconnexion() {
        if ( estConnecte ) {
            voituresDisponibles.clear();
            voituresLoueesParLeClient.clear();
            estConnecte = false;
        }
    }

    public List<Voiture> listeDesVoituresDisponibles() {
        // Typiquement ces données viendraient d'un cloud ou d'une BD
        return new ArrayList<>( voituresDisponibles );
    }

    public List<Voiture> listeDesVoituresLoueesParLeClient() {
        // Typiquement ces données viendraient d'un cloud ou d'une BD
        return new ArrayList<>( voituresLoueesParLeClient );
    }

    public boolean clientLoueUneVoiture( Voiture voiture ) {

        boolean reussi = false;

        // Typiquement ces données viendraient d'un cloud ou d'une BD
        if ( voituresDisponibles.contains( voiture ) ) {
            voituresDisponibles.remove( voiture );
            voituresLoueesParLeClient.add( voiture );
            reussi = true;
        }

        return reussi;
    }

    public boolean clientRestitueUneVoiture( Voiture voiture ) {

        boolean reussi = false;

        // Typiquement ces données viendraient d'un cloud ou d'une BD
        if ( voituresLoueesParLeClient.contains( voiture ) ) {
            voituresLoueesParLeClient.remove( voiture );
            voituresDisponibles.add( voiture );
            reussi = true;
        }

        return reussi;
    }
}
