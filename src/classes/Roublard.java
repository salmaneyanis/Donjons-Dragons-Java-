package classes;

import equipements.Equipement;
import equipements.GestionEquipements;
import personnages.Joueur;

import java.util.List;

public class Roublard extends Archetype {
    public Roublard() {
        super("Roublard", 16);
    }

    @Override
    public void genererEquipementDeBase(Joueur joueur) {
        List<Equipement> equipementsDisponibles = GestionEquipements.initialiserEquipements();
        for (Equipement equipement : equipementsDisponibles) {
            if (equipement.getNom().equals("Rapière") ||
                    equipement.getNom().equals("Arc court")) {
                joueur.ajouterEquipement(equipement);            }
        }
    }
    @Override
    public String toString() {
        return "Classe Roublard: " + super.toString();
    }
}
