package equipements.armes;

public class ArmeCourante extends Armes {


    public ArmeCourante(String nom, String degats) {
        super(nom, degats, 1, "Arme courante");
    }

    @Override
    public String toString() {
        return "Arme Courante : " + getNom() + " [Dégâts : " + getDegats() + "]";
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
