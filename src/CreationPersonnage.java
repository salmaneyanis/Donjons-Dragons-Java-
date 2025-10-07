import classes.*;
import personnages.Joueur;
import races.*;

import java.util.Scanner;

public class CreationPersonnage {

    public static Joueur creerPersonnage(Scanner scanner, int numeroJoueur) {
        System.out.println("\n--- Joueur " + numeroJoueur + " ---");

        System.out.print("Entrez le nom du personnage : ");
        String nom = scanner.nextLine();
        while (nom.length() < 3) {
            System.out.print("Entrez le nom du personnage (min. 3 caractères) : ");
            nom = scanner.nextLine();
        }

        Races race = null;
        while (race == null) {
            System.out.println("Choisissez une race :");
            System.out.println("1 - Humain\n2 - Elfe\n3 - Nain\n4 - Halfelin");
            String choix = scanner.nextLine();
            race = switch (choix) {
                case "1" -> new Humain();
                case "2" -> new Elfe();
                case "3" -> new Nain();
                case "4" -> new Halfelin();
                default -> {
                    System.out.println("Choix invalide.");
                    yield null;
                }
            };
        }

        Archetype classe = null;
        while (classe == null) {
            System.out.println("Choisissez une classe :");
            System.out.println("1 - Guerrier\n2 - Roublard\n3 - Clerc\n4 - Magicien");
            String choix = scanner.nextLine();
            classe = switch (choix) {
                case "1" -> new Guerrier();
                case "2" -> new Roublard();
                case "3" -> new Clerc();
                case "4" -> new Magicien();
                default -> {
                    System.out.println("Choix invalide.");
                    yield null;
                }
            };
        }

        Joueur joueur = new Joueur(nom, classe, race);
        System.out.println("Personnage créé : " + joueur.getNom() +
                " (" + classe.getNom() + " " + race.getNom() + ")");
        return joueur;
    }
}
