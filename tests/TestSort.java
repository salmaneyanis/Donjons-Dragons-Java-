import Sort.ArmeMagique;
import classes.Guerrier;
import equipements.Equipement;
import equipements.armes.ArmeCourante;
import equipements.armures.ArmureLegere;
import personnages.Joueur;
import org.junit.jupiter.api.Test;
import races.Elfe;

import static org.junit.jupiter.api.Assertions.*;

public class TestSort {
    @Test
    public void testUtiliser_arme_enchantee() {
        Joueur joueur = new Joueur("Test", new Guerrier(), new Elfe());
        ArmeCourante epee = new ArmeCourante("épée de feu", "1d5");
        joueur.equiper(epee);

        ArmeMagique sort = new ArmeMagique();
        sort.utiliser(joueur, joueur, epee);

        assertEquals(0, epee.getEnchante());
    }

    @Test
    public void testGetArmesDisponibles() {
        Joueur joueur = new Joueur("Test", new Guerrier(), new Elfe());
        ArmeCourante epee = new ArmeCourante("épée de feu", "1d5");
        ArmureLegere armure = new ArmureLegere("Cuirasse", 2);
        joueur.equiper(epee);
        joueur.equiper(armure);

        ArmeMagique sort = new ArmeMagique();
        var armes = sort.getArmesDisponibles(joueur);

        assertEquals(3, armes.size());
        assertEquals("Épée longue", armes.get(0).getNom());
    }
}
