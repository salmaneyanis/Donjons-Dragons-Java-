import classes.Guerrier;
import org.junit.jupiter.api.Test;
import personnages.*;
import races.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestRace {
    @Test
    public void testHumainAppliquerBonus() {
        Joueur perso = new Joueur("Test", new Guerrier(), new Humain());


        assertTrue(perso.getForce() >= 5 && perso.getForce() <= 21);
        assertTrue(perso.getDexterite() >= 5 && perso.getDexterite() <= 21);
        assertTrue(perso.getVitesse() >= 5 && perso.getVitesse() <= 21);
        assertTrue(perso.getInitiative() >= 5 && perso.getInitiative() <= 21);
    }

    @Test
    public void testNainAppliquerBonus() {
        Joueur perso = new Joueur("Test", new Guerrier(), new Nain());


        assertTrue(perso.getForce() >= 9 && perso.getForce() <= 25); // 4d4+3+6
        assertTrue(perso.getDexterite() >= 3 && perso.getDexterite() <= 19);
        assertTrue(perso.getVitesse() >= 3 && perso.getVitesse() <= 19);
    }

    @Test
    public void testElfeAppliquerBonus() {
        Joueur perso = new Joueur("Test", new Guerrier(), new Elfe());


        assertTrue(perso.getDexterite() >= 9 && perso.getDexterite() <= 25); // +6
    }

    @Test
    public void testHalfelinAppliquerBonus() {
        Joueur perso = new Joueur("Test", new Guerrier(), new Halfelin());


        assertTrue(perso.getDexterite() >= 7 && perso.getDexterite() <= 23);
        assertTrue(perso.getVitesse() >= 5 && perso.getVitesse() <= 21);
    }

    @Test
    public void testToString() {
        Races humain = new Humain();
        String output = humain.toString();
        assertTrue(output.contains("Race: Humain"));
        assertTrue(output.contains("Bonus Force"));
    }
}
