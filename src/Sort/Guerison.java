package Sort;

import personnages.entite.Entite;
import personnages.Joueur;
import des.*;

public class Guerison extends Sort{

    public Guerison() {
        super("Guérison");
    }

    @Override
    public void utiliser(Joueur cible1, Joueur cible2) {
        int des = Des.lancerDes("1d10");
        int pvmax = cible2.getPVdebase();
        int pvbase = cible2.getPointDeVie();
        if ((cible2.getPointDeVie() + des) < pvmax)
        {
            cible2.addPdV(des);
            System.out.println(cible2.getNom() + " a ete soigner de " + des + "(" + cible1.getPointDeVie() + "/" + pvmax + ")");
        }
        else
        {
            cible2.addPdV((pvmax-pvbase));
            System.out.println(cible2.getNom() + " a ete soigner de " + (pvmax-pvbase) + "(" + cible1.getPointDeVie() + "/" + pvmax + ")");
        }
    }

    @Override
    public void utiliser(Entite cible1, Entite cible2) {
        
    }
}
