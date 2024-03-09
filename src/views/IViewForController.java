package views;

import models.PrevisionMeteo;
import models.Voiture;
import java.util.List;

public interface IViewForController {

    void start();

    void afficheDernierePrevision( PrevisionMeteo prevision );

    void afficheVoituresDisponibles( List<Voiture> voituresDisponibles, Voiture voitureASelectionner );

    void afficheVoituresLouees( List<Voiture> voituresLouees, Voiture voitureASelectionner );

    void messageInfo( String message );

    void messageErreur( String message );

}
