package monstres;

import des.Des;
import personnages.Joueur;

public class Dragon extends Monstre {
    public Dragon(int numero) {
        super("Dragon", numero, 100, 0, 18, 10,
                15, "souffle de feu", 6, "2d10", 1);
    }

    @Override
    public CombatResultat attaquerEtObtenirResultat(Joueur cible) {
        int distance = Math.abs(getPosX() - cible.getPosX()) + Math.abs(getPosY() - cible.getPosY());

        if (distance > getPortee()) {
            return new CombatResultat(0, 0, false, 0, cible.getClasseArmureActuelle());
        }

        int jetAttaque = Des.lancerDes("1d20");
        int modificateur = (getPortee() == 1) ? getForce() : getDexterite();
        jetAttaque += modificateur;

        boolean succes = jetAttaque > cible.getClasseArmureActuelle();
        int degatsInfliges = succes ? Des.lancerDes(getDegats()) : 0;

        if (succes) {
            cible.addPdV(-degatsInfliges);
        }

        return new CombatResultat(jetAttaque, modificateur, succes, degatsInfliges, cible.getClasseArmureActuelle());
    }

    public void afficherResultatAttaque(CombatResultat r, Joueur cible) {
        System.out.println(getNom() + " attaque " + cible.getNom() + " avec " + getTypeAttaque() + " !");
        System.out.println("Jet d'attaque : " + r.jetAttaque + " (modificateur : " + r.modificateur + ")");
        System.out.println(getPortee() == 1 ? "Attaque corps à corps." : "Attaque à distance.");
        if (r.succes) {
            System.out.println("Attaque réussie ! Dégâts infligés : " + r.degatsInfliges);
            System.out.println("PV restants de " + cible.getNom() + " : " + cible.getPointDeVie());
        } else {
            System.out.println("Attaque échouée !");
        }
    }

    @Override
    public int getPvDeBase() {
        return 100;
    }

    @Override
    public String getTypeContenu() {
        return "";
    }

    @Override
    public String afficher() {
        return "🐉 ";
    }

    @Override
    public boolean estParticipant() {
        return true;
    }
}
