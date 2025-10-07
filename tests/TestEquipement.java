
import equipements.armes.*;
import equipements.armures.*;
import equipements.Equipement;
import equipements.GestionEquipements;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEquipement {

    @Test
    public void testArmureLegere() {
        ArmureLegere armure = new ArmureLegere("Armure de cuir", 12);
        assertEquals("Armure légère", armure.getType());
        assertEquals(12, armure.getClasseArmure());
        assertTrue(armure.estArmure());
        assertFalse(armure.estArme());
        assertEquals("Aucun", armure.getDegats());
        assertEquals(0, armure.getModificateurForce());
    }

    @Test
    public void testArmureLourde() {
        ArmureLourde armure = new ArmureLourde("Armure de plates", 18);

        assertEquals("Armure lourde", armure.getType());
        assertEquals(18, armure.getClasseArmure());
        assertTrue(armure.estArmure());
        assertFalse(armure.estArme());
        assertEquals("Aucun", armure.getDegats());
        assertEquals(-4, armure.getModificateurVitesse());
        assertEquals(0, armure.getPortee());
        assertEquals("Armure lourde : Armure de plates [Classe d'armure : 18]", armure.toString());
    }

    @Test
    public void testArmeCourante() {
        ArmeCourante epee = new ArmeCourante("Épée courte", "1d6");
        assertEquals("Arme courante", epee.getType());
        assertTrue(epee.estArme());
        assertFalse(epee.estArmure());
        assertEquals("1d6", epee.getDegats());
        assertEquals(1, epee.getPortee());
    }

    @Test
    public void testArmeDeGuerre() {
        ArmeDeGuerre hache = new ArmeDeGuerre("Hache de bataille", "1d12");

        assertEquals("Arme de guerre", hache.getType());
        assertTrue(hache.estArme());
        assertFalse(hache.estArmure());
        assertEquals("1d12", hache.getDegats());
        assertEquals(1, hache.getPortee());
        assertEquals(-2, hache.getModificateurVitesse());
        assertEquals(4, hache.getModificateurForce());
        assertEquals(0, hache.getClasseArmure());
        assertEquals("Arme de guerre : Hache de bataille [Dégâts : 1d12]", hache.toString());
    }

    @Test
    public void testArmeDistance() {
        ArmeDistance arc = new ArmeDistance("Arc long", "1d8", 6);

        assertEquals("Arme à distance", arc.getType());
        assertTrue(arc.estArme());
        assertFalse(arc.estArmure());
        assertEquals("1d8", arc.getDegats());
        assertEquals(6, arc.getPortee());
        assertEquals(0, arc.getClasseArmure());
        assertEquals("Arme à distance : Arc long [Dégâts : 1d8, Portée : 6]", arc.toString());
    }

    @Test
    public void testEnchante() {
        ArmeCourante arme = new ArmeCourante("Épée courte", "1d6");
        assertEquals(0, arme.getEnchante());
        arme.setEnchante(2);
        assertEquals(2, arme.getEnchante());
    }

    @Test
    public void testInitialiserEquipements() {
        List<Equipement> equipements = GestionEquipements.initialiserEquipements();
        assertFalse(equipements.isEmpty());
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Épée longue")));
    }

    @Test
    void testInitialiserEquipementsContientDesExemplesConnus() {
        List<Equipement> equipements = GestionEquipements.initialiserEquipements();

        assertNotNull(equipements);
        assertTrue(equipements.size() >= 10);

        // Vérifie quelques équipements précis
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Épée longue")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Arbalète légère")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Armure d'écailles")));
    }
}
