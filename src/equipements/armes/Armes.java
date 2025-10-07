package equipements.armes;
import equipements.*;

public abstract class Armes implements Equipement{
    private String m_nom;
    private String m_degats;
    private int m_portee;
    private String m_type;
    private int enchante;

    public Armes(String nom, String degats, int portee, String type) {
        this.m_nom = nom;
        this.m_degats = degats;
        this.m_portee = portee;
        this.m_type = type;
        this.enchante = 0;
    }

    @Override
    public int getEnchante() {
        return this.enchante;
    }

    @Override
    public void setEnchante(int enchante) {
        this.enchante = enchante;
    }

    @Override
    public String getDegats() {
        return m_degats;
    }

    @Override
    public int getPortee() {
        return m_portee;
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
    public String toString() {
        return "Arme : " + getNom() + " (Dégâts : " + getDegats() + ", Portée : " + getPortee() + ")";
    }

    @Override
    public String getTypeContenu() {
        return "Equipement";
    }

    @Override
    public  String afficher(){
        return " \uD83D\uDDE1\uFE0F";
    }



}
