package equipements.armures;

public class ArmureLourde extends Armure {

    public ArmureLourde(String nom, int classeArmure) {
        super(nom, classeArmure, "Armure lourde");
    }

    @Override
    public int getEnchante() {
        return 0;
    }

    @Override
    public void setEnchante(int enchante) {

    }

    @Override
    public int getModificateurVitesse() {
        return -4;  // Réduction de la vitesse
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
        return "Armure lourde : " + getNom() + " [Classe d'armure : " + getClasseArmure() + "]";
    }

    @Override
    public int getClasseArmure() {
        return super.getClasseArmure();
    }
    @Override
    public boolean estArmure() {
        return true;
    }

    @Override
    public boolean estArme() {
        return false;
    }

    @Override
    public boolean estParticipant() {
        return false;
    }
}
