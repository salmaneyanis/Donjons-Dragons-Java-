package equipements.armures;

public class ArmureLegere extends Armure {

    public ArmureLegere(String nom, int classeArmure) {
        super(nom, classeArmure, "Armure légère");
    }

    @Override
    public String toString() {
        return "Armure légère : " + getNom() + " [Classe d'armure : " + getClasseArmure() + "]";
    }

    @Override
    public int getEnchante() {
        return 0;
    }

    @Override
    public void setEnchante(int enchante) {

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
