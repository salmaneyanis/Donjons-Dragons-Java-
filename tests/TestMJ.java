import maitredujeux.Maitredujeux;
import monstres.Monstre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMJ {
    @Test
    public void testAjouterLignesEtAfficherLignes() {
        Maitredujeux mj = new Maitredujeux();
        mj.ajouterLignes("Ceci est une ligne.");
        mj.ajouterLignes("Deuxième ligne de test.");

        // On vérifie uniquement que ça ne lance pas d'exception
        // Car afficherLignes() utilise System.out
        assertDoesNotThrow(() -> mj.afficherLignes());
    }

    @Test
    public void testCreerMonstreEspece() {
        Maitredujeux mj = new Maitredujeux();
        Monstre m1 = mj.creerMonstreEspece("gobelin");
        assertEquals("gobelin n°1", m1.getNom().toLowerCase());

        Monstre m2 = mj.creerMonstreEspece("gobelin");
        assertEquals("gobelin n°2", m2.getNom().toLowerCase());

        Monstre m3 = mj.creerMonstreEspece("dragon");
        assertEquals("dragon n°1", m3.getNom().toLowerCase());
    }

    @Test
    public void testCreerMonstreEspeceInconnue() {
        Maitredujeux mj = new Maitredujeux();
        Monstre m = mj.creerMonstreEspece("troll");

        assertEquals("troll n°1", m.getNom().toLowerCase());
        assertEquals(50, m.getPointDeVie());
    }
}
