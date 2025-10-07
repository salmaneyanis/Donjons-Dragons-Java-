package races;

import personnages.Personnage;
import des.Des;

public abstract class Races {
    protected String m_nom;
    protected int m_bonusForce;
    protected int m_bonusDexterite;
    protected int m_bonusVitesse;
    protected int m_bonusPV;
    protected int m_bonusInitiative;

    public Races(String nom, int bonusForce, int bonusDexterite, int bonusVitesse, int bonusInitiative) {
        this.m_nom = nom;
        this.m_bonusForce = bonusForce;
        this.m_bonusDexterite = bonusDexterite;
        this.m_bonusVitesse = bonusVitesse;
        this.m_bonusInitiative = bonusInitiative;
    }

    public void appliquerBonus(Personnage personnage) {
        personnage.addPdV(m_bonusPV);
        personnage.setInitiative(calculerStat(m_bonusInitiative));
        personnage.setForce(calculerStat(m_bonusForce));
        personnage.setDexterite(calculerStat(m_bonusDexterite));
        personnage.setVitesse(calculerStat(m_bonusVitesse));
    }

    private int calculerStat(int bonus) {
        return Des.lancerDes("4d4") + 3 + bonus;
    }

    public String getNom() {
        return m_nom;
    }

    @Override
    public String toString() {
        return "Race: " + m_nom + ", Bonus Force: " + m_bonusForce + ", Bonus Dextérité: " + m_bonusDexterite + ", Bonus Vitesse: " + m_bonusVitesse + ", Bonus PV: " + m_bonusPV + ", Bonus Initiative: " + m_bonusInitiative;
    }
}
