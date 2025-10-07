package equipements.armures;
import equipements.*;

public abstract class Armure implements Equipement {
    private String m_nom;
    private int m_classeArmure;
    private String m_type;

    public Armure(String nom, int classeArmure, String type) {
        this.m_nom = nom;
        this.m_classeArmure = classeArmure;
        this.m_type = type;
    }

    @Override
    public int getClasseArmure() {
        return m_classeArmure;
    }

    @Override
    public String getNom() {
        return m_nom;
    }

    @Override
    public String getType() {
        return m_type;
    }

    @Override
    public int getModificateurVitesse() {
        return 0;
    }

    @Override
    public int getModificateurForce() {
        return 0;
    }

    @Override
    public int getPortee() {
        return 0;
    }

    @Override
    public String getDegats() {
        return "Aucun";
    }

    @Override
    public String toString() {
        return "Armure : " + m_nom + " (Classe d'armure : " + m_classeArmure + ")";
    }

    @Override
    public String getTypeContenu() {
        return "Equipement";
    }

    @Override
    public  String afficher(){
        return " \uD83D\uDEE1\uFE0F";
    }


}
