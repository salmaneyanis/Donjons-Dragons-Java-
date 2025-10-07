import classes.*;
import equipements.Equipement;
import equipements.GestionEquipements;
import maitredujeux.Maitredujeux;
import monstres.*;
import personnages.*;
import interfacejeu.*;
import des.*;

import java.util.*;
import java.util.List;

import personnages.entite.Entite;
import Sort.*;

import interfacejeu.MapMilieu;

import static equipements.GestionEquipements.initialiserEquipements;

public class Jeu {
    public void demarrer(Scanner scanner, List<Entite> participants, List<Joueur> joueurs, List<Monstre> monstres, Maitredujeux mj, int numeroDonjon, MapMilieu map) {
        String art = """
                ▀██▀▀█▄                       ██                                      ▄      ▀██▀▀█▄                                                   
                 ██   ██    ▄▄▄   ▄▄ ▄▄▄     ▄▄▄   ▄▄▄   ▄▄ ▄▄▄    ▄▄▄▄       ▄▄▄▄  ▄██▄      ██   ██  ▄▄▄ ▄▄   ▄▄▄▄     ▄▄▄ ▄   ▄▄▄   ▄▄ ▄▄▄    ▄▄▄▄  
                 ██    ██ ▄█  ▀█▄  ██  ██     ██ ▄█  ▀█▄  ██  ██  ██▄ ▀     ▄█▄▄▄██  ██       ██    ██  ██▀ ▀▀ ▀▀ ▄██   ██ ██  ▄█  ▀█▄  ██  ██  ██▄ ▀  
                 ██    ██ ██   ██  ██  ██     ██ ██   ██  ██  ██  ▄ ▀█▄▄    ██       ██       ██    ██  ██     ▄█▀ ██    █▀▀   ██   ██  ██  ██  ▄ ▀█▄▄ 
                ▄██▄▄▄█▀   ▀█▄▄█▀ ▄██▄ ██▄    ██  ▀█▄▄█▀ ▄██▄ ██▄ █▀▄▄█▀     ▀█▄▄▄▀  ▀█▄▀    ▄██▄▄▄█▀  ▄██▄    ▀█▄▄▀█▀  ▀████▄  ▀█▄▄█▀ ▄██▄ ██▄ █▀▄▄█▀ 
                                           ▄▄ █▀                                                                       ▄█▄▄▄▄▀                         
                                            ▀▀                                                                                                         
                """;
        String credit = "Par SALMANE Yanis, DUBLANC--SOUBIGOU Yvann et SIHR Victor";

        System.out.println(art);
        System.out.println(credit);

        System.out.println("=== Création des personnages ===");

        int numeroJoueur = 1;
        while (true) {
            System.out.print("\nCréer un nouveau personnage ? (oui/non) : ");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (reponse.equals("non")) {
                break;
            }
            if (!reponse.equals("oui")) {
                System.out.println("Réponse invalide. Veuillez répondre par 'oui' ou 'non'.");
                continue;
            }

            Joueur joueur = CreationPersonnage.creerPersonnage(scanner, numeroJoueur);
            joueurs.add(joueur);
            participants.add(joueur);
            GestionEquipements.equiperPremiereArmeEtArmure(joueur);
            numeroJoueur++;
        }
    }


    public void partie(Scanner scanner, List<Entite> participants, List<Joueur> joueurs, List<Monstre> monstres, Maitredujeux mj, int numeroDonjon, MapMilieu map, int numeroTour) {
        int finijeux = 0;
        while (!joueurs.isEmpty() || finijeux!=1) {
            // Nettoyer les morts
            monstres.removeIf(Monstre::estMort);
            participants.removeIf(e -> e.estMort());

            if (numeroDonjon <= 3) {
                boolean partiegagné = false;
                participants.removeIf(participant -> participant.estMort());
                map = choisirNouvelleCarte(scanner);
                for (Entite participant : participants) {
                    if (!participant.estMonstre()) {
                        Joueur joueur = (Joueur) participant;
                        joueur.soignerComplet();
                        int x;
                        int y;
                        while (true) {
                            System.out.println("Maître du jeu, placez le joueur " + joueur.getNom() + " sur la carte !");
                            System.out.print("Entrez la coordonnée X (ligne) : ");
                            x = scanner.nextInt();
                            System.out.print("Entrez la coordonnée Y (colonne) : ");
                            y = scanner.nextInt();
                            scanner.nextLine();
                            if (joueur.setPosXY(x, y, map)) {
                                System.out.println("Joueur " + joueur.getNom() + " placé en (" + x + ", " + y + ").");
                                break;
                            } else {
                                System.out.println("Mauvaise coordonnées");
                            }
                        }
                    }
                }

                if (joueurs.isEmpty()) {
                    System.out.println("Aucun joueur n'a été créé. Fin du programme.");
                    return;
                }

                System.out.println("\n=== Création des monstres par le Maître du Jeu ===");
                while (true) {
                    System.out.print("Créer un nouveau monstre ? (oui/non) : ");
                    String reponse = scanner.nextLine().trim().toLowerCase();
                    if (reponse.equals("non")) {
                        break;
                    }
                    ;
                    if (!reponse.equals("oui")) {
                        System.out.println("Réponse invalide. Veuillez répondre par 'oui' ou 'non'.");
                        continue;
                    }

                    Monstre monstre = mj.creerMonstre();
                    participants.add(monstre);
                    System.out.println("Monstre " + monstre.getEspece() + " créé !");

                    int x;
                    int y;
                    while (true) {
                        System.out.println("Maître du jeu, placez le monstre sur la carte !");
                        System.out.print("Entrez la coordonnée X (ligne) : ");
                        x = scanner.nextInt();
                        System.out.print("Entrez la coordonnée Y (colonne) : ");
                        y = scanner.nextInt();
                        scanner.nextLine();
                        if (monstre.setPosXY(x, y, map)) {
                            break;
                        } else {
                            System.out.println("Mauvaise coordonnées");
                        }
                    }
                    System.out.println("Monstre placé en (" + x + ", " + y + ").");
                }

                if (participants.isEmpty()) {
                    System.out.println("Aucun participant. Fin du programme.");
                    return;
                }

                System.out.println("\n=== Introduction par le Maître du Jeu ===");
                String commentaire = scanner.nextLine();
                mj.ajouterLignes("MJ : " + commentaire);
                System.out.println( commentaire);


                boolean partieEnCours = true;
                while (partieEnCours && partiegagné == false) {
                    System.out.println("\n=== Donjon " + numeroDonjon + " ===");

                    Map<Entite, Integer> initiativeMap = new HashMap<>();
                    for (Entite p : participants) {
                        int mod = p.getInitiative();
                        int score = Des.lancerDes("1d20") + mod;
                        initiativeMap.put(p, score);
                        System.out.println(p.getNom() + " initiative : " + score);
                    }
                    participants.sort((a, b) -> Integer.compare(initiativeMap.get(b), initiativeMap.get(a)));

                    BarreHaut barre = new BarreHaut();
                    System.out.println("\n=== Début du donjon ===");

                    while (true) {
                        System.out.print("Souhaitez-vous ajouter un obstacle ? (oui/non) : ");
                        String reponse = scanner.nextLine().trim().toLowerCase();

                        if (!reponse.equals("oui")) {
                            break;
                        }

                        System.out.print("Entrez la coordonnée X de l'obstacle : ");
                        int x = scanner.nextInt();

                        System.out.print("Entrez la coordonnée Y de l'obstacle : ");
                        int y = scanner.nextInt();
                        scanner.nextLine();

                        map.addObstacle(x, y);
                        map.UpdateCase(x,y,new Obstacle());
                        System.out.println("Obstacle ajouté en (" + x + ", " + y + ").");
                    }

                    System.out.println("Fin de l'ajout des obstacles.");

                    Scanner scanner2 = new Scanner(System.in);
                    List<Equipement> equipements = initialiserEquipements();
                    Random random = new Random();

                    while (true) {
                        System.out.print("Souhaitez-vous ajouter un équipement sur la carte ? (oui/non) : ");
                        String reponse = scanner.nextLine().trim().toLowerCase();

                        if (!reponse.equals("oui")) {
                            break;
                        }

                        System.out.print("Entrez la coordonnée X (ligne) : ");
                        int x = scanner.nextInt();
                        System.out.print("Entrez la coordonnée Y (colonne) : ");
                        int y = scanner.nextInt();
                        scanner.nextLine();

                        // Tirage aléatoire d'un équipement
                        Equipement equipementAleatoire = equipements.get(random.nextInt(equipements.size()));

                        // Placement de l'équipement
                        map.addEquipment(x, y, equipementAleatoire);
                        map.UpdateCase(x,y,equipementAleatoire);

                        System.out.println("Équipement ajouté en (" + x + ", " + y + ") : " + equipementAleatoire.getNom());
                    }

                    System.out.println("Fin de l'ajout des équipements.");


                    barre.Affichage(joueurs.get(0), numeroDonjon, participants, numeroTour);

                    map.Print(participants);

                    int index = 0;
                    while (true) {
                        Entite p = participants.get(index);

                        if (!p.estMonstre() && !p.estMort()) {
                            Joueur joueur = (Joueur) p;
                            System.out.println("\nC'est le tour de " + joueur.getNom() + " !");
                            int actionsRestantes = 3;
                            while (actionsRestantes > 0) {
                                System.out.println("Actions restantes : " + actionsRestantes);
                                System.out.println("1. Attaquer\n2. S'équiper\n3. Rammasser\n4. Se déplacer\n5. Sort\n6. Passer");

                                System.out.print("Choisissez une action : ");
                                String action = scanner.nextLine();

                                switch (action) {
                                    case "1" -> {
                                        System.out.println("Qui voulez-vous attaquer ?");
                                        List<Monstre> cibles = participants.stream()
                                                .filter(m -> m.estMonstre() && !m.estMort())
                                                .map(m -> (Monstre) m) // Ce cast reste nécessaire mais sûr car tu viens de filtrer estMonstre() == true
                                                .toList();


                                        if (cibles.isEmpty()) {
                                            System.out.println("\n=== Tous les monstres sont vaincus ! Victoire ! ===");
                                            for (Joueur j : joueurs) {
                                                j.soignerComplet();
                                            }
                                            System.out.println("Préparation du donjon suivant...\n");
                                            numeroDonjon++;
                                            partieEnCours = false;
                                            partiegagné = true;
                                            partie(scanner, participants, joueurs, monstres,mj, numeroDonjon, map, numeroTour);
                                            break;
                                        }

                                        for (int i = 0; i < cibles.size(); i++) {
                                            System.out.println((i + 1) + ". " + cibles.get(i).getEspece());
                                        }

                                        int choix = Integer.parseInt(scanner.nextLine()) - 1;
                                        if (choix >= 0 && choix < cibles.size()) {
                                            joueur.attaquer(cibles.get(choix));
                                            mj.intervenir(participants, map);
                                            actionsRestantes--;
                                        }

                                        // Vérifie si tous les monstres sont morts après l'attaque
                                        boolean tousLesMonstresMorts = participants.stream()
                                                .filter(Entite::estMonstre)
                                                .allMatch(Entite::estMort);

                                        if (tousLesMonstresMorts) {
                                            System.out.println("\n=== Tous les monstres sont vaincus ! Victoire ! ===");
                                            for (Joueur j : joueurs) {
                                                j.soignerComplet();
                                            }
                                            System.out.println("Préparation du donjon suivant...\n");
                                            numeroDonjon++;
                                            partieEnCours = false;
                                            partiegagné = true;
                                            partie(scanner, participants, joueurs, monstres,mj, numeroDonjon, map, numeroTour);
                                            break;
                                        }

                                        // Vérifie si un joueur est mort après l’action
                                        boolean joueurMort = joueurs.stream().anyMatch(Joueur::estMort);
                                        if (joueurMort) {
                                            System.out.println("\n=== Un joueur est mort... Défaite ! ===");
                                            partieEnCours = false;
                                            partiegagné = false;
                                        }
                                    }
                                    case "2" -> {
                                        joueur.afficherInventaire();

                                        if (!joueur.getEquipements().isEmpty()) {
                                            System.out.println("Quel équipement souhaitez-vous équiper ? (entrez le numéro) :");
                                            for (int i = 0; i < joueur.getEquipements().size(); i++) {
                                                Equipement eq = joueur.getEquipements().get(i);
                                                System.out.println(i + " - " + eq.getNom());
                                            }

                                            int choix = -1;
                                            try {
                                                choix = Integer.parseInt(scanner.nextLine());
                                            } catch (NumberFormatException e) {
                                                System.out.println("Entrée invalide.");
                                            }

                                            if (choix >= 0 && choix < joueur.getEquipements().size()) {
                                                Equipement equipementChoisi = joueur.getEquipements().remove(choix);
                                                joueur.equiper(equipementChoisi);

                                                System.out.println("Commentaire ? ");
                                                System.out.println(scanner.nextLine());

                                                actionsRestantes--;
                                            } else {
                                                System.out.println("Numéro invalide.");
                                            }
                                        } else {
                                            System.out.println("Vous n'avez aucun équipement dans votre inventaire.");
                                        }
                                    }
                                    case "3" -> {
                                        System.out.println("Rammasser un équipement");
                                        joueur.ramasserEquipement(map);
                                        actionsRestantes--;
                                    }
                                    case "4" -> {
                                        System.out.println("Vous avez choisi de vous déplacer !");
                                        System.out.println("Vers quelle direction souhaitez vous vous déplacez ? (haut/bas/droite/gauche)");
                                        String direction = scanner.nextLine().trim().toLowerCase();
                                        System.out.println("Le nombre de cases? (" + joueur.getVitesse()/3 + "cases max)");
                                        int cases = Integer.parseInt(scanner.nextLine());
                                        joueur.seDeplacer(direction, map, cases);
                                        actionsRestantes--;
                                    }
                                    case "5" -> {
                                        Archetype classe = joueur.getClasse();
                                        String type = classe.getNom();

                                        if (type.equals("Clerc") || type.equals("Magicien")) {
                                            System.out.println("Sorts disponibles :");
                                            System.out.println("1. Guérison");
                                            if (type.equals("Magicien")) {
                                                System.out.println("2. Bougie-Woogie");
                                                System.out.println("3. Enchanter une arme");
                                            }

                                            System.out.print("Choisissez un sort : ");
                                            String choixSort = scanner.nextLine();

                                            switch (choixSort) {
                                                case "1" -> {
                                                    System.out.println("Cibles disponibles pour Guérison :");
                                                    ArrayList<Entite> ciblesPossibles = new ArrayList<>();
                                                    for (Entite e : participants) {
                                                        if (!e.estMonstre()) {
                                                            ciblesPossibles.add(e);
                                                        }
                                                    }
                                                    for (int i = 0; i < ciblesPossibles.size(); i++) {
                                                        Entite e = ciblesPossibles.get(i);
                                                        System.out.println((i + 1) + " - " + e.getNom());
                                                    }
                                                    System.out.print("Entrez le numéro de la cible à soigner : ");
                                                    try {
                                                        int choix = Integer.parseInt(scanner.nextLine()) - 1;
                                                        if (choix >= 0 && choix < ciblesPossibles.size()) {
                                                            Joueur cible = (Joueur) ciblesPossibles.get(choix);
                                                            Guerison sort = new Guerison();
                                                            sort.utiliser(cible, cible);
                                                            System.out.println(cible.getNom() + " a été soigné !");
                                                            actionsRestantes--;
                                                        } else {
                                                            System.out.println("Numéro invalide.");
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Entrée invalide.");
                                                    }
                                                }
                                                case "2" -> {
                                                    if (type.equals("Magicien")) {
                                                        System.out.println("Cibles disponibles pour Bougie-Woogie :");
                                                        List<Entite> ciblesPossibles = participants;
                                                        for (int i = 0; i < ciblesPossibles.size(); i++) {
                                                            Entite e = ciblesPossibles.get(i);
                                                            System.out.println((i + 1) + " - " + e.getNom());
                                                        }
                                                        System.out.print("Entrez le numéro de la premiere cible à echanger : ");
                                                        try {
                                                            int choix = Integer.parseInt(scanner.nextLine()) - 1;
                                                            if (choix >= 0 && choix < ciblesPossibles.size()) {
                                                                Entite cible1 = ciblesPossibles.get(choix);
                                                                BougieWoogie sort = new BougieWoogie();
                                                                System.out.println("Cibles disponibles pour Bougie-Woogie :");
                                                                for (int i = 0; i < ciblesPossibles.size(); i++) {
                                                                    Entite e = ciblesPossibles.get(i);
                                                                    System.out.println((i + 1) + " - " + e.getNom());
                                                                }
                                                                System.out.print("Entrez le numéro de la deuxième cible à échanger : ");
                                                                try {
                                                                    int choix2 = Integer.parseInt(scanner.nextLine()) - 1;
                                                                    if (choix2 >= 0 && choix2 < ciblesPossibles.size()) {
                                                                        Entite cible2 = ciblesPossibles.get(choix2);
                                                                        sort.utilisermap(cible1, cible2);
                                                                        actionsRestantes--;
                                                                    } else {
                                                                        System.out.println("Numéro invalide.");
                                                                    }
                                                                } catch (NumberFormatException e) {
                                                                    System.out.println("Entrée invalide.");
                                                                }
                                                            } else {
                                                                System.out.println("Numéro invalide.");
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Entrée invalide.");
                                                        }
                                                    } else {
                                                        System.out.println("Ce sort est réservé aux magiciens.");
                                                    }
                                                }
                                                case "3" -> {
                                                    if (type.equals("Magicien")) {
                                                        System.out.println("Cibles disponibles pour l'enchantement :");
                                                        ArrayList<Entite> ciblesPossibles = new ArrayList<>();
                                                        for (Entite e : participants) {
                                                            if (!e.estMonstre()) {
                                                                ciblesPossibles.add(e);
                                                            }
                                                        }
                                                        for (int i = 0; i < ciblesPossibles.size(); i++) {
                                                            Entite e = ciblesPossibles.get(i);
                                                            System.out.println((i + 1) + " - " + e.getNom());
                                                        }
                                                        System.out.print("Entrez le numéro de la cible à enchanter : ");
                                                        try {
                                                            int choix = Integer.parseInt(scanner.nextLine()) - 1;
                                                            if (choix >= 0 && choix < ciblesPossibles.size()) {
                                                                Joueur joueurCible = (Joueur) ciblesPossibles.get(choix);

                                                                ArmeMagique sort = new ArmeMagique();
                                                                ArrayList<Equipement> armes = sort.getArmesDisponibles(joueurCible);

                                                                if (armes.isEmpty()) {
                                                                    System.out.println(joueurCible.getNom() + " ne possède aucune arme.");
                                                                } else {
                                                                    for (int i = 0; i < armes.size(); i++) {
                                                                        System.out.println((i + 1) + " - " + armes.get(i).getNom());
                                                                    }

                                                                    System.out.print("Choisissez le numéro de l'arme à améliorer : ");
                                                                    Scanner scanner3 = new Scanner(System.in);
                                                                    int choix2 = scanner3.nextInt();

                                                                    if (choix2 >= 1 && choix2 <= armes.size()) {
                                                                        Equipement armeChoisie = armes.get(choix2 - 1);
                                                                        sort.utiliser(joueurCible, joueurCible, armeChoisie);
                                                                        System.out.println("L'arme '" + armeChoisie.getNom() + "' a été améliorée !");
                                                                    } else {
                                                                        System.out.println("Choix invalide.");
                                                                    }
                                                                }
                                                                actionsRestantes--;


                                                            } else {
                                                                System.out.println("Numéro invalide.");
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Entrée invalide.");
                                                        }
                                                    } else {
                                                        System.out.println("Ce sort est réservé aux magiciens.");
                                                    }
                                                }
                                                default -> System.out.println("Sort inconnu.");
                                            }
                                        } else {
                                            System.out.println("Vous ne possédez aucun sort, cette action est donc impossible pour vous.");
                                        }
                                    }
                                    case "6" -> actionsRestantes = 0;
                                    default -> System.out.println("Action invalide.");
                                }
                                numeroTour++;
                                barre.Affichage(joueurs.get(0), numeroDonjon, participants, numeroTour);
                                map.Print(participants);
                            }
                        } else if (!p.estMort()) {
                            Monstre monstre = (Monstre) p;
                            int actionmonstre = 3;
                            System.out.println("\nTour de " + monstre.getEspece());
                            System.out.println("Actions restantes : " + actionmonstre);
                            while (actionmonstre > 0 && !monstre.estMort()) {
                                System.out.println("\n=== Contrôle de " + monstre.getNom() + " ===");
                                System.out.println("1. Attaquer");
                                System.out.println("2. Se déplacer");
                                System.out.println("3. Arrêter le contrôle");

                                int action = mj.saisirEntierMinMax("Votre choix : ", 1, 3);

                                switch (action) {
                                    case 1:
                                        mj.attaquerAvecMonstre(monstre, participants);
                                        actionmonstre--;
                                        break;
                                    case 2:
                                        mj.deplacerMonstre(monstre, map);
                                        actionmonstre--;
                                        break;
                                    case 3:
                                        actionmonstre = 0;
                                        System.out.println("Vous arrêtez de contrôler " + monstre.getNom());
                                        break;
                                }
                            }
                        }

                        boolean joueurMort = joueurs.stream().anyMatch(Joueur::estMort);

                        if (joueurMort) {
                            System.out.println("\n=== Un joueur est mort... Défaite ! === \n Un mot pour conclure Maître ?");
                            commentaire = scanner.nextLine();
                            mj.ajouterLignes("MJ : " + commentaire);
                            mj.afficherLignes();
                            partieEnCours = false;
                            partiegagné = false;
                            break;
                        }

                        // Vérification globale à la fin de chaque tour - si tous les monstres sont morts, fin de la partie
                        boolean tousLesMonstresMortsFinal = participants.stream()
                                .filter(o -> o.estMonstre())
                                .map(o -> (Monstre) o)
                                .allMatch(Monstre::estMort);
                        if (tousLesMonstresMortsFinal) {
                            System.out.println("\n=== Tous les monstres sont vaincus ! Victoire ! ===");
                            for (Joueur j : joueurs) {
                                j.soignerComplet();
                            }
                            System.out.println("Préparation du donjon suivant...\n");
                            numeroDonjon++;
                            numeroTour++;
                            partieEnCours = false;
                            partiegagné = true;
                            partie(scanner, participants, joueurs, monstres ,mj, numeroDonjon, map, numeroTour);
                            break;
                        }

                        index = (index + 1) % participants.size();
                    }
                }
            }
            else {
                System.out.println("Bravo vous avez fini\n Un mot pour conclure maître ?");
                String commentaireFin = scanner.nextLine();
                scanner.close();
                mj.ajouterLignes("MJ : " + commentaireFin);
                mj.afficherLignes();
                System.exit(0);
            }

        }
    }


    private MapMilieu choisirNouvelleCarte(Scanner scanner) {
        MapMilieu map = null;
        boolean verif = false;
        while (!verif) {
            System.out.println("\nChoisir la nouvelle map :");
            System.out.println("1. Forêt D'Yvann");
            System.out.println("2. Labyrinthe D'Yvann");
            System.out.println("3. Les 3 voies de l'Homme Gazées");
            String reponse = scanner.nextLine().trim().toLowerCase();
            switch (reponse) {
                case "1":
                    map = MapMilieu.map1();
                    verif = true;
                    break;
                case "2":
                    map = MapMilieu.map2();
                    verif = true;
                    break;
                case "3":
                    map = MapMilieu.map3();
                    verif = true;
                    break;
                default:
                    System.out.println("Réponse invalide. Veuillez entrer 1, 2 ou 3.");
            }
        }
        map.PrintVide();
        return map;
    }

    public static void VraiJeux() {
        Jeu jeu = new Jeu();
        Scanner scanner = new Scanner(System.in);

        List<Entite> participants = new ArrayList<>();
        List<Joueur> joueurs = new ArrayList<>();
        List<Monstre> monstres = new ArrayList<>();
        Maitredujeux mj = new Maitredujeux();
        int numeroDonjon = 1;
        int numeroTour = 1;
        MapMilieu map = null;

        // Démarrer le jeu
        jeu.demarrer(scanner, participants, joueurs, monstres, mj, numeroDonjon, map);

        // Lancer la partie
        jeu.partie(scanner, participants, joueurs, monstres, mj, numeroDonjon, map, numeroTour);

        scanner.close();
    }
}




