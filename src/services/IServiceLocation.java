package services;

import java.util.List;

import models.Voiture;

public interface IServiceLocation {

    boolean connexion(String compte, String utilisateur, String mdp);

    void deconnexion();

    List<Voiture> listeDesVoituresDisponibles();

    List<Voiture> listeDesVoituresLoueesParLeClient();

    boolean clientLoueUneVoiture(Voiture voiture);

    boolean clientRestitueUneVoiture(Voiture voiture);
}
