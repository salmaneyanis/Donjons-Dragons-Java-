package Sort;

import equipements.Equipement;
import personnages.Joueur;
import personnages.entite.Entite;

import java.util.ArrayList;

public class ArmeMagique extends Sort {

    public ArmeMagique() {
        super("Arme Magique");
    }

    // Nouvelle méthode : améliore une arme déjà sélectionnée
    public void utiliser(Joueur lanceur, Joueur cible, Equipement armeChoisie) {
        if (armeChoisie != null && cible.getEquipements().contains(armeChoisie)) {
            if (armeChoisie.getType().toLowerCase().startsWith("arme")) {
                armeChoisie.setEnchante(armeChoisie.getEnchante() + 1);
            }
        }
    }

    @Override
    public void utiliser(Joueur cible1, Joueur cible2) {

    }

    @Override
    public void utiliser(Entite cible1, Entite cible2) {

    }

    public ArrayList<Equipement> getArmesDisponibles(Joueur joueur) {
        ArrayList<Equipement> armes = new ArrayList<>();
        for (Equipement e : joueur.getEquipements()) {
            if (e.getType().toLowerCase().startsWith("arme")) {
                armes.add(e);
            }
        }
        return armes;
    }
}
