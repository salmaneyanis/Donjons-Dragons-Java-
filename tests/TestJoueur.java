import classes.*;
import equipements.Equipement;
import equipements.armes.ArmeCourante;
import equipements.armures.ArmureLegere;
import interfacejeu.MapMilieu;
import monstres.Gobelin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personnages.Joueur;
import races.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestJoueur {
    private Joueur joueur;
    private Gobelin gobelin;
    private MapMilieu map;

    @BeforeEach
    public void setUp() {
        Archetype guerrier = new Guerrier(); // Doit implémenter Archetype
        Races humain = new Humain();         // Doit implémenter Races
        joueur = new Joueur("Alice", guerrier, humain);
        gobelin = new Gobelin(1); // Exemple
        map = new MapMilieu(6, 6); // carte 6x6
        joueur.setPosXY(2, 2, map);
        joueur.setPosSansVerif(2, 2);
    }

    @Test
    public void testNomClasseRace() {
        assertEquals("Alice", joueur.getNom());
        assertEquals("Guerrier", joueur.getClasse().getNom());
        assertEquals("Humain", joueur.getRace().getNom());
    }

    @Test
    public void testEquipementInitial() {
        Equipement arme = joueur.getEquiper().get(0);
        Equipement armure = joueur.getEquiper().get(1);
        assertNotNull(arme);
        assertNotNull(armure);
        assertEquals("Sans Arme", arme.getNom());
        assertEquals("Sans Armure", armure.getNom());
    }

    @Test
    public void testAjouterEquipement() {
        Equipement nouveau = new ArmeCourante("Épée Test", "1d6");
        joueur.ajouterEquipement(nouveau);
        assertTrue(joueur.getEquipements().contains(nouveau));
    }

    @Test
    public void testEquiperRemplaceEtModifieStats() {
        int forceAvant = joueur.getForce();
        Equipement epee = new ArmeCourante("Épée", "1d6");
        epee.setEnchante(2);
        joueur.ajouterEquipement(epee);
        joueur.equiper(epee);
        assertEquals(forceAvant, joueur.getForce());
        assertEquals("Épée", joueur.getEquiper().get(0).getNom());
    }

    @Test
    public void testDeplacementValide() {
        joueur.seDeplacer("haut", map, 1);
        assertEquals(1, joueur.getPosX());
        assertEquals(2, joueur.getPosY());
    }

    @Test
    public void testDeplacementInvalide() {
        boolean changed = joueur.setPosXY(10, 10, map);
        assertFalse(changed);
    }

    /*@Test
    public void testRamassageEquipement() {
        Equipement epee = new ArmeCourante("Épée", "1d6");
        map.addEquipment(2, 3, epee);
        joueur.ramasserEquipement(map);
        assertTrue(joueur.getEquipements().contains(epee));
    }*/

    @Test
    public void testAttaqueHorsPorteeEchoue() {
        gobelin.setPosSansVerif(0, 0);
        joueur.attaquer(gobelin);
        assertEquals(10, gobelin.getPointDeVie()); // Pas de dégâts
    }

    @Test
    public void testSetEtAffichagePV() {
        joueur.setPV(-5);
        assertEquals(joueur.getClasse().getPvDeBase() - 5, joueur.getPointDeVie());
    }

    @Test
    public void testSoignerComplet() {
        joueur.setPV(-10);
        joueur.soignerComplet();
        assertEquals(joueur.getClasse().getPvDeBase(), joueur.getPointDeVie());
    }

    @Test
    public void testAffichageLong() {
        String affichage = joueur.getAffichageLong();
        assertTrue(affichage.contains("Alice"));
        assertTrue(affichage.contains("Guerrier"));
        assertTrue(affichage.contains("Humain"));
    }

    @Test
    public void testTypeContenuEtParticipant() {
        assertEquals("Joueur", joueur.getTypeContenu());
        assertTrue(joueur.estParticipant());
        assertFalse(joueur.estMonstre());
    }
}
