package equipements;

import equipements.armes.*;
import equipements.armures.*;
import personnages.Joueur;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionEquipements {

    public static List<Equipement> initialiserEquipements() {
        List<Equipement> equipements = new ArrayList<>();

        // Armures légères
        equipements.add(new ArmureLegere("Armure d'écailles", 9));
        equipements.add(new ArmureLegere("Demi-plate", 10));

        // Armures lourdes
        equipements.add(new ArmureLourde("Cotte de mailles", 11));
        equipements.add(new ArmureLourde("Harnois", 12));

        // Armes courantes
        equipements.add(new ArmeCourante("Bâton", "1d6"));
        equipements.add(new ArmeCourante("Masse d'armes", "1d6"));

        // Armes de guerre
        equipements.add(new ArmeDeGuerre("Épée longue", "1d8"));
        equipements.add(new ArmeDeGuerre("Rapière", "1d8"));
        equipements.add(new ArmeDeGuerre("Epée à deux mains", "2d6"));

        // Armes à distance
        equipements.add(new ArmeDistance("Arbalète légère", "1d8", 16));
        equipements.add(new ArmeDistance("Fronde", "1d4", 6));
        equipements.add(new ArmeDistance("Arc court", "1d6", 16));

        return equipements;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Liste des équipements disponibles :\n");
        List<Equipement> equipements = initialiserEquipements();
        for (Equipement equipement : equipements) {
            sb.append(equipement.getNom()).append(" - ").append(equipement.getType()).append("\n");
        }
        return sb.toString();
    }

    public static void equiperPremiereArmeEtArmure(Joueur personnage) {
        List<Equipement> armes = new ArrayList<>();
        List<Equipement> armures = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

          for (Equipement eq : personnage.getEquipements()) {
            if (eq.getType().contains("Arme")) {
                armes.add(eq);
            } else if (eq.getType().contains("Armure")) {
                armures.add(eq);
            }
        }

        // Choix de l'arme
        if (!armes.isEmpty()) {
            System.out.println("Choisissez une arme à équiper :");
            for (int i = 0; i < armes.size(); i++) {
                System.out.println(i + " - " + armes.get(i).getNom());
            }

            int choixArme = lireChoix(scanner, armes.size());
            if (choixArme != -1) {
                Armes armeChoisie = (Armes) armes.get(choixArme);
                personnage.getEquiper().remove(armeChoisie);
                personnage.equiper(armeChoisie);
            }
        } else {
            System.out.println("Aucune arme disponible.");
        }

        // Choix de l'armure
        if (!armures.isEmpty()) {
            System.out.println("Choisissez une armure à équiper :");
            for (int i = 0; i < armures.size(); i++) {
                System.out.println(i + " - " + armures.get(i).getNom());
            }

            int choixArmure = lireChoix(scanner, armures.size());
            if (choixArmure != -1) {
                Armure armureChoisie = (Armure) armures.get(choixArmure);
                personnage.getEquiper().remove(armureChoisie);
                personnage.equiper(armureChoisie);
            }
        } else {
            System.out.println("Aucune armure disponible.");
        }
    }


    private static int lireChoix(Scanner scanner, int tailleListe) {
        int choix = -1;
        try {
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(scanner.nextLine());
            if (choix < 0 || choix >= tailleListe) {
                System.out.println("Numéro invalide.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide.");
            return -1;
        }
        return choix;
    }

}
