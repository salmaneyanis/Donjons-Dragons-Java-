package equipements;

import interfacejeu.ContenuCase;

public interface Equipement  extends ContenuCase {
    int getEnchante();
    void setEnchante(int enchante);
    String getNom();
    String getType();
    int getModificateurVitesse();
    int getModificateurForce();
    int getPortee();
    String getDegats();
    int getClasseArmure();
    boolean estArme();
    boolean estArmure();



}
