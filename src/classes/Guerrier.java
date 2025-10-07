package classes;

import equipements.Equipement;
import equipements.GestionEquipements;
import personnages.Joueur;

import java.util.List;

public class Guerrier extends Archetype {
    public Guerrier() {
        super("Guerrier", 20);
    }

    @Override
    public void genererEquipementDeBase(Joueur joueur) {
        List<Equipement> equipementsDisponibles = GestionEquipements.initialiserEquipements();
        for (Equipement equipement : equipementsDisponibles) {
            if (equipement.getNom().equals("Cotte de mailles") ||
                    equipement.getNom().equals("Épée longue") ||
                    equipement.getNom().equals("Arbalète légère")) {
                joueur.ajouterEquipement(equipement);
            }
        }
    }
    @Override
    public String toString() {
        return "Classe Guerrier: " + super.toString();
    }
}
