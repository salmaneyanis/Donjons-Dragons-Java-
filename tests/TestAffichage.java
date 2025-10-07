import classes.Guerrier;
import equipements.Equipement;
import equipements.armes.ArmeCourante;
import interfacejeu.*;
import monstres.Gobelin;
import personnages.entite.Entite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personnages.Joueur;
import races.Elfe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAffichage {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private BarreHaut barreHaut;
    private Entite joueurActif;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        barreHaut = new BarreHaut();

        joueurActif = new Joueur("Joueur", new Guerrier(), new Elfe()); // en supposant un constructeur entite(nom, hp)
    }

    @Test
    public void testSautLigneAfficheDesLignesVides() {
        barreHaut.sautLigne(3);
        String[] lines = outputStream.toString().split(System.lineSeparator());
        assertTrue(lines.length >= 3);
    }

    @Test
    public void testAfficherBarreHautAfficheLeNomDuJoueur() {
        List<Entite> participants = List.of(joueurActif);
        barreHaut.Affichage(joueurActif, 1, participants, 1);
        String output = outputStream.toString();
        assertTrue(output.contains("Donjon 1"));
        assertTrue(output.contains("Tour 1"));
        assertTrue(output.contains(joueurActif.getAffichageLong()));
    }

    @Test
    public void testNouvelleCaseEstVide() {
        Case c = new Case();
        assertTrue(c.estVide());
        assertTrue(c.passable());
        assertTrue(c.accessibleParJoueur());
        assertEquals(" . ", c.afficher());
    }

    @Test
    public void testCaseAvecContenuNonEquipement() {
        Case c = new Case();
        Gobelin gobelin = new Gobelin(1);
        c.setM_contenu(gobelin);

        assertFalse(c.estVide());
        assertFalse(c.passable());
        assertFalse(c.accessibleParJoueur());
        assertEquals("\uD83D\uDC7A ", c.afficher());
    }

    @Test
    public void testCaseAvecEquipement() {
        Case c = new Case();
        ArmeCourante test = new ArmeCourante("arme test", "1d1");
        c.setM_contenu(test);

        assertFalse(c.estVide());
        assertFalse(c.passable());
        assertFalse(c.accessibleParJoueur());
        assertEquals(" \uD83D\uDDE1\uFE0F", c.afficher());
    }

    @Test
    public void testInitialisationEtAccesCases() {
        MapMilieu map = new MapMilieu(3, 3);
        assertNotNull(map.getCase(0, 0));
        assertTrue(map.getCase(1, 1).estVide());
    }

    @Test
    public void testAjoutObstacle() {
        MapMilieu map = new MapMilieu(3, 3);
        map.addObstacle(1, 1);
        assertFalse(map.getCase(1, 1).estVide());
        assertEquals("Obstacle", map.getCase(1, 1).getM_contenu().getTypeContenu());
    }

    @Test
    public void testAjoutEquipementEtRecup() {
        MapMilieu map = new MapMilieu(3, 3);
        Equipement arme = new ArmeCourante("Épée", "1d6");

        map.addEquipment(2, 2, arme); // dans [1][1]
        Equipement contenu = (Equipement) map.getCase(1, 1).getM_contenu();

        assertNotNull(contenu);
        assertEquals("Épée", contenu.getNom());
    }

    @Test
    public void testVideCase() {
        MapMilieu map = new MapMilieu(2, 2);
        Equipement arme = new ArmeCourante("Hache", "1d8");
        map.addEquipment(2, 2, arme); // dans [1][1]
        map.videCase(1, 1);
        assertTrue(map.getCase(1, 1).estVide());
    }
}
