package classes;

import equipements.Equipement;
import equipements.GestionEquipements;
import personnages.Joueur;

import java.util.List;

public class Clerc extends Archetype {
    public Clerc() {
        super("Clerc", 16);
    }

    @Override
    public void genererEquipementDeBase(Joueur joueur) {
        List<Equipement> equipementsDisponibles = GestionEquipements.initialiserEquipements();
        for (Equipement equipement : equipementsDisponibles) {
            if (equipement.getNom().equals("Masse d'armes") ||
                    equipement.getNom().equals("Armure d'écailles") ||
                    equipement.getNom().equals("Arbalète légère")) {
                joueur.ajouterEquipement(equipement);
            }
        }
    }

    @Override
    public String toString() {
        return "Classe Clerc: " + super.toString();
    }
}
