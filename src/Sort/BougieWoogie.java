package Sort;

import personnages.entite.Entite;
import personnages.Joueur;

public class BougieWoogie extends Sort{

    public BougieWoogie() {
        super("Bougie-Woogie");
    }

    public void utilisermap(Entite cible1, Entite cible2) {
        int cible1PosX = cible1.getPosX();
        int cible1PosY = cible1.getPosY();

        echangerPositions(cible1, cible2);

    }

    @Override
    public void utiliser(Joueur cible1, Joueur cible2) {

    }

    @Override
    public void utiliser(Entite cible1, Entite cible2) {

    }
}
