import equipements.Equipement;
import equipements.GestionEquipements;
import classes.*;
import personnages.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import races.Humain;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestArchetype {

    private Clerc clerc;
    private Joueur joueur;

    @BeforeEach
    void setUp() {
        clerc = new Clerc();
        joueur = new Joueur("TestClerc", clerc, new Humain());
    }

    @Test
    void testNomClasseEtPV() {
        assertEquals("Clerc", clerc.getNom());
        assertEquals(16, clerc.getPvDeBase());
    }

    @Test
    void testToString() {
        String result = clerc.toString();
        assertTrue(result.contains("Clerc"));
    }

    @Test
    void testGenererEquipementDeBase() {
        clerc.genererEquipementDeBase(joueur);
        List<Equipement> equipements = joueur.getEquipements();

        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Masse d'armes")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Armure d'écailles")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Arbalète légère")));
    }

    private Guerrier guerrier;
    private Joueur joueurGuerrier;

    @BeforeEach
    void setUpGuerrier() {
        guerrier = new Guerrier();
        joueurGuerrier = new Joueur("TestGuerrier", guerrier, new Humain());
    }

    @Test
    void testNomClasseEtPVGuerrier() {
        assertEquals("Guerrier", guerrier.getNom());
        assertEquals(20, guerrier.getPvDeBase());
    }

    @Test
    void testToStringGuerrier() {
        String result = guerrier.toString();
        assertTrue(result.contains("Guerrier"));
    }

    @Test
    void testGenererEquipementDeBaseGuerrier() {
        guerrier.genererEquipementDeBase(joueurGuerrier);
        List<Equipement> equipements = joueurGuerrier.getEquipements();

        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Cotte de mailles")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Épée longue")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Arbalète légère")));
    }

    private Magicien magicien;
    private Joueur joueurMagicien;

    @BeforeEach
    void setUpMagicien() {
        magicien = new Magicien();
        joueurMagicien = new Joueur("TestMagicien", magicien, new Humain());
    }

    @Test
    void testNomClasseEtPVMagicien() {
        assertEquals("Magicien", magicien.getNom());
        assertEquals(12, magicien.getPvDeBase());
    }

    @Test
    void testToStringMagicien() {
        String result = magicien.toString();
        assertTrue(result.contains("Magicien"));
    }

    @Test
    void testGenererEquipementDeBaseMagicien() {
        magicien.genererEquipementDeBase(joueurMagicien);
        List<Equipement> equipements = joueurMagicien.getEquipements();

        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Bâton")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Fronde")));
    }

    private Roublard roublard;
    private Joueur joueurRoublard;

    @BeforeEach
    void setUpRoublard() {
        roublard = new Roublard();
        joueurRoublard = new Joueur("TestRoublard", roublard, new Humain());
    }

    @Test
    void testNomClasseEtPVRoublard() {
        assertEquals("Roublard", roublard.getNom());
        assertEquals(16, roublard.getPvDeBase());
    }

    @Test
    void testToStringRoublard() {
        String result = roublard.toString();
        assertTrue(result.contains("Roublard"));
    }

    @Test
    void testGenererEquipementDeBaseRoublard() {
        roublard.genererEquipementDeBase(joueurRoublard);
        List<Equipement> equipements = joueurRoublard.getEquipements();

        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Rapière")));
        assertTrue(equipements.stream().anyMatch(e -> e.getNom().equals("Arc court")));
    }

}
