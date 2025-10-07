package equipements.armes;

public class ArmeDistance extends Armes {

    public ArmeDistance(String nom, String degats, int portee) {
        super(nom, degats, portee, "Arme à distance");
    }

    @Override
    public String toString() {
        return "Arme à distance : " + getNom() + " [Dégâts : " + getDegats() + ", Portée : " + getPortee() + "]";
    }

    @Override
    public int getClasseArmure() {
        return 0;
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
