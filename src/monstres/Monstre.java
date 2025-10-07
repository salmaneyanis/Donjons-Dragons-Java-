package monstres;

import interfacejeu.ContenuCase;
import interfacejeu.MapMilieu;
import personnages.entite.Entite;
import personnages.Joueur;

public abstract class Monstre implements ContenuCase, Entite {
    private String m_espece;
    private int m_numero;
    private int m_pointDeVie;
    private int m_force;
    private int m_dexterite;
    private int m_initiative;
    private int m_classeArmure;
    private String m_typeAttaque;
    private int m_portee;
    private String m_degats;
    private int m_vitesse;
    private int m_posX;
    private int m_posY;

    public Monstre(String espece, int numero, int pointDeVie, int force, int dexterite, int initiative,
                   int classeArmure, String typeAttaque, int portee, String degats, int vitesse) {
        m_espece = espece;
        m_numero = numero;
        m_pointDeVie = pointDeVie;
        m_force = force;
        m_dexterite = dexterite;
        m_initiative = initiative;
        m_classeArmure = classeArmure;
        m_typeAttaque = typeAttaque;
        m_portee = portee;
        m_degats = degats;
        m_vitesse = vitesse;
    }

    // Méthode à implémenter dans chaque monstre
    public abstract CombatResultat attaquerEtObtenirResultat(Joueur cible);

    public abstract int getPvDeBase();

    public abstract String afficher();

    @Override
    public String toString() {
        return m_espece + " #" + m_numero + " [PV: " + m_pointDeVie + ", Attaque: " + m_typeAttaque +
                ", Dégâts: " + m_degats + ", Portée: " + m_portee + "]";
    }

    // === Getters ===
    public int getPointDeVie() { return m_pointDeVie; }

    public int getForce() { return m_force; }

    public int getDexterite() { return m_dexterite; }

    public int getInitiative() { return m_initiative; }

    public int getClasseArmure() { return m_classeArmure; }

    public String getTypeAttaque() { return m_typeAttaque; }

    public int getPortee() { return m_portee; }

    public String getDegats() { return m_degats; }

    public String getEspece() { return m_espece; }

    public int getNumero() { return m_numero; }

    public int getVitesse() { return m_vitesse; }

    public int getPosX() { return m_posX; }

    public int getPosY() { return m_posY; }

    // === Setters et actions ===
    public void setPV(int variation) {
        addPdV(variation);
    }

    public void addPdV(int pv) {
        m_pointDeVie += pv;
        if (m_pointDeVie < 0) m_pointDeVie = 0;
    }

    public boolean setPosXY(int x, int y, MapMilieu map) {
        if (map.isValidPositionAndFree(x, y)) {
            m_posX = x;
            m_posY = y;
            return true;
        }
        return false;
    }

    @Override
    public void setPosSansVerif(int x, int y) {
        m_posX = x;
        m_posY = y;
    }

    // === Interface entite ===
    @Override
    public boolean estMort() {
        return m_pointDeVie <= 0;
    }

    @Override
    public boolean estMonstre() {
        return true;
    }

    @Override
    public String affichageClass() {
        return getClass().toString();
    }

    @Override
    public String getAffichageCourt() {
        return afficher();
    }

    @Override
    public String getAffichageLong() {
        return getEspece() + " (" + getPointDeVie() + "/" + getPvDeBase() + " HP)";
    }

    @Override
    public String getNom() {
        return getEspece() + " n°" + getNumero();
    }

    @Override
    public String afficherPVDB() {
        System.out.print(getPointDeVie() + "/" + getPvDeBase());
        return "";
    }

    public void subirDegats(int degats) {
        m_pointDeVie -= degats;
    }

    // === Résultat d'une attaque ===
    public static class CombatResultat {
        public final int jetAttaque;
        public final int modificateur;
        public final boolean succes;
        public final int degatsInfliges;
        public final int classeArmureCible;

        public CombatResultat(int jetAttaque, int modificateur, boolean succes, int degatsInfliges, int classeArmureCible) {
            this.jetAttaque = jetAttaque;
            this.modificateur = modificateur;
            this.succes = succes;
            this.degatsInfliges = degatsInfliges;
            this.classeArmureCible = classeArmureCible;
        }
    }
}
