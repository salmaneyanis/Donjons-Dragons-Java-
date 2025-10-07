package personnages;

import equipements.Equipement;
import monstres.Monstre;
import personnages.entite.Entite;

import java.util.ArrayList;
import java.util.List;

public abstract class Personnage implements Entite {
    private String m_nom;
    private int m_pointDeVie;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;
    Equipement[] m_equipementEquipe = new Equipement[2];

    public Personnage (String nom, int pointDeVie, int force, int dexterite, int vitesse, int initiative) {
        this.m_nom = nom;
        this.m_pointDeVie = pointDeVie;
        this.m_force = force;
        this.m_dexterite = dexterite;
        this.m_vitesse = vitesse;
        this.m_initiative = initiative;

    }

    public abstract void attaquer(Monstre cible);

    public List<Equipement> getEquiper() {
        List<Equipement> equipe = new ArrayList<>();
        for (Equipement e : m_equipementEquipe) {
            if (e != null) equipe.add(e);
        }
        return equipe;
    }


    public abstract void equiper(Equipement equipement);

    @Override
    public String toString() {
        return "Nom : " + m_nom +
                "\nPoints de vie : " + m_pointDeVie +
                "\nForce : " + m_force +
                "\nDextérité : " + m_dexterite +
                "\nVitesse : " + m_vitesse +
                "\nInitiative : " + m_initiative;
    }

    public String getNom() {
        return m_nom;
    }

    public int getPointDeVie() {
        return m_pointDeVie;
    }

    public int getForce() {
        return m_force;
    }

    public int getDexterite() {
        return m_dexterite;
    }

    public int getVitesse() {
        return m_vitesse;
    }

    public int getInitiative() {
        return m_initiative;
    }

    public void setNom(String nom_change) {
        m_nom = nom_change;
    }

    public void setPointDeVie(int pv) {
        m_pointDeVie = pv;
    }

    public void addPdV(int pv) {
        m_pointDeVie += pv;
    }

    public void setForce(int force_change) {
        m_force = force_change;
    }

    public void setVitesse_change(int vitesse_change) {
        m_vitesse += vitesse_change;
    }

    public void setDexterite(int dexterite_change) {
        m_dexterite = dexterite_change;
    }

    public void setVitesse(int vitesse_change) {
        m_vitesse = vitesse_change;
    }

    public void setInitiative(int initiative_change) {
        m_initiative = initiative_change;
    }

    @Override
    public boolean estMort() {
        if (this.m_pointDeVie <= 0) {
            return true;
        }
        else {return false;}
    }

    public abstract void soignerComplet();

}


