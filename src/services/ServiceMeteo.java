package services;

import models.PrevisionMeteo;

public class ServiceMeteo implements IServiceMeteo {

    public ServiceMeteo() {
    }

    public PrevisionMeteo prochainBulletinMeteo() {
        String[] tempPossible = {
                "Beau temps chaud et sec",
                "Beau temps frais et sec",
                "Temps variable",
                "Pluies éparses",
                "Fortes pluies",
                "Orages",
                "Ouragan" };
        int indiceAleatoire = (int) (Math.random() * tempPossible.length);
        return new PrevisionMeteo(tempPossible[indiceAleatoire]);
    }
}
