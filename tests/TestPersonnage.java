
import classes.*;
import org.junit.Test;
import personnages.Joueur;
import personnages.Personnage;
import races.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPersonnage {

    @Test
    public void testGuerrier() {
        Guerrier guerrier = new Guerrier();
        Joueur joueur = new Joueur("Test", guerrier, new Humain());
        guerrier.genererEquipementDeBase(joueur);
        assertEquals("Guerrier", guerrier.getNom());
        assertEquals(20, guerrier.getPvDeBase());
        assertTrue(joueur.getEquipements().stream().anyMatch(e -> e.getNom().equals("Cotte de mailles")));
    }
}
