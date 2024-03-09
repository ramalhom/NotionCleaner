package ctrl;

import static app.Application.UTILISATEUR_COMPTE;
import static app.Application.UTILISATEUR_MDP;
import static app.Application.UTILISATEUR_USER;
import models.PrevisionMeteo;
import models.Voiture;
import services.IServiceLocation;
import services.IServiceMeteo;
import services.ServiceLocation;
import services.ServiceMeteo;
import views.View;
import views.IViewForController;

public class Controller implements IControllerForView {

    private final IViewForController view;
    private final IServiceMeteo serviceMeteo;
    private final IServiceLocation serviceLocation;

    public Controller() {
        view = new View(this);
        serviceMeteo = new ServiceMeteo();
        serviceLocation = new ServiceLocation();
    }

    public void start() {
        view.start();

        if ( serviceLocation.connexion( UTILISATEUR_COMPTE, UTILISATEUR_USER, UTILISATEUR_MDP ) ) {
            view.afficheVoituresDisponibles( serviceLocation.listeDesVoituresDisponibles(), null );
            view.afficheVoituresLouees( serviceLocation.listeDesVoituresLoueesParLeClient(), null );
        }
        else {
            view.messageErreur( "La connexion au service de location a échoué !" );
        }
    }

    @Override
    public void actionRafraichirPrevisionMeteo() {
        PrevisionMeteo dernierePrevision = serviceMeteo.prochainBulletinMeteo();
        view.afficheDernierePrevision( dernierePrevision );
    }

    @Override
    public void actionLouerUneVoiture( Voiture voiture ) {
        if ( serviceLocation.clientLoueUneVoiture( voiture ) ) {
            view.afficheVoituresDisponibles( serviceLocation.listeDesVoituresDisponibles(), null );
            view.afficheVoituresLouees( serviceLocation.listeDesVoituresLoueesParLeClient(), voiture );
            view.messageInfo( "Voiture louée !" );
        }
        else {
            view.messageErreur( "La location de cette voiture n'a pas réussi !" );
        }
    }

    @Override
    public void actionRestituerUneVoiture( Voiture voiture ) {
        if ( serviceLocation.clientRestitueUneVoiture( voiture ) ) {
            view.afficheVoituresDisponibles( serviceLocation.listeDesVoituresDisponibles(), voiture );
            view.afficheVoituresLouees( serviceLocation.listeDesVoituresLoueesParLeClient(), null );
            view.messageInfo( "Voiture restituée !" );
        }
        else {
            view.messageErreur( "La restitution de cette voiture n'a pas réussi !" );
        }
    }

}
