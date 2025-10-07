package classes;

import personnages.Joueur;

public abstract class Archetype {
    private String m_nom;
    private int m_pvDeBase;

    public Archetype(String nom, int pvDeBase) {
        this.m_nom = nom;
        this.m_pvDeBase = pvDeBase;
    }

    public abstract void genererEquipementDeBase(Joueur joueur);

    public String getNom() {
        return m_nom;
    }

    public int getPvDeBase() {
        return m_pvDeBase;
    }


    @Override
    public String toString() {
        return "Classe: " + m_nom + ", PV de base: " + m_pvDeBase;
    }
}
