package Sort;

import personnages.Joueur;
import personnages.entite.Entite;

abstract class Sort {
    protected String nom;

    public Sort(String nom)
    {
        this.nom = nom;
    }

    public abstract void utiliser(Joueur cible1, Joueur cible2);
    public abstract void utiliser(Entite cible1, Entite cible2);

    public String getNom()
    {
        return this.nom;
    }

    public void echangerPositions(Entite e1, Entite e2) {
        int x1 = e1.getPosX(), y1 = e1.getPosY();
        int x2 = e2.getPosX(), y2 = e2.getPosY();

        // On échange directement les positions sans vérifier si la case est libre
        e1.setPosSansVerif(x2, y2);
        e2.setPosSansVerif(x1, y1);
    }

}
