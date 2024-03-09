package ctrl;

import models.Voiture;

public interface IControllerForView {

    void actionRafraichirPrevisionMeteo();

    void actionLouerUneVoiture( Voiture voiture );

    void actionRestituerUneVoiture( Voiture voiture );

}
