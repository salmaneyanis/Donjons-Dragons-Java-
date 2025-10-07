package equipements.armes;

public class ArmeDeGuerre extends Armes {

    public ArmeDeGuerre(String nom, String degats) {
        super(nom, degats, 1, "Arme de guerre");
    }

    @Override
    public int getModificateurVitesse() {
        return -2;  // Réduction de la vitesse
    }

    @Override
    public int getModificateurForce() {
        return 4;   // Augmentation de la force
    }

    @Override
    public int getClasseArmure() {
        return 0;
    }

    @Override
    public String toString() {
        return "Arme de guerre : " + getNom() + " [Dégâts : " + getDegats() + "]";
    }

    @Override
    public boolean estArme() {
        return true;
    }
    @Override
    public boolean estArmure() {
        return false;
    }

    @Override
    public boolean estParticipant() {
        return false;
    }
}
