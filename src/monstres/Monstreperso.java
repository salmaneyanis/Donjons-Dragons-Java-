package monstres;

import des.Des;
import personnages.Joueur;

public class Monstreperso extends Monstre {

    private String icone;
    private final int m_PvDeBase;

    public Monstreperso(String espece, int numero, int pointDeVie, int force, int dexterite, int initiative,
                        int classeArmure, String typeAttaque, int portee, String degats, String icone, int vitesse) {
        super(espece, numero, pointDeVie, force, dexterite, initiative, classeArmure, typeAttaque, portee, degats, vitesse);
        this.icone = icone;
        this.m_PvDeBase = pointDeVie;
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

    public void afficherResultatAttaque(CombatResultat resultat, Joueur cible) {
        System.out.println(getNom() + " attaque " + cible.getNom() + " avec " + getTypeAttaque() + " !");
        System.out.println("Jet d'attaque : " + resultat.jetAttaque + " (modificateur : " + resultat.modificateur + ")");
        System.out.println(getPortee() == 1 ? "Attaque corps à corps." : "Attaque à distance.");

        if (resultat.succes) {
            System.out.println("Attaque réussie !");
            System.out.println("Dégâts infligés : " + resultat.degatsInfliges);
            System.out.println(cible.getNom() + " PV restants : " + cible.getPointDeVie());
        } else {
            System.out.println("Attaque échouée !");
        }
    }

    public void setIcone(String ico) {
        icone = ico;
    }

    public String getIcone() {
        return icone;
    }

    @Override
    public String getTypeContenu() {
        return "";
    }

    @Override
    public String afficher() {
        return getIcone();
    }

    @Override
    public int getPvDeBase() {
        return m_PvDeBase;
    }

    @Override
    public boolean estParticipant() {
        return true;
    }
}
