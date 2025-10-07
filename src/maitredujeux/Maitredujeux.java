package maitredujeux;

import java.util.*;

import des.Des;
import interfacejeu.MapMilieu;
import monstres.*;
import personnages.entite.Entite;
import personnages.Joueur;

import static des.Des.lancerDes;

public class Maitredujeux {
    private ArrayList<String> m_lignes;
    private Map<String, Integer> m_compteurMonstres;
    private ArrayList<String> m_especesPerso;
    private final Scanner m_scanner;
    private Map<String, Monstreperso> m_monstresPersonnalises = new HashMap<>();


    public Maitredujeux() {
        m_lignes = new ArrayList<>();
        m_compteurMonstres = new HashMap<>();
        m_especesPerso = new ArrayList<>();
        m_scanner = new Scanner(System.in);
    }

    public void ajouterLignes(String ligne) {
        m_lignes.add(ligne);
    }

    public void afficherLignes() {
        for (String ligne : m_lignes) {
            System.out.println(ligne);
        }
    }

    public Monstre creerMonstre() {
        System.out.println("=== Création d'un monstre ===");
        System.out.println("1. Gobelin");
        System.out.println("2. Dragon");
        System.out.println("3. Squelette");
        System.out.println("4. Monstre personnalisé");

        int index = 5;
        for (String espece : m_especesPerso) {
            System.out.println(index + ". " + espece);
            index++;
        }

        int choix = -1;
        do {
            System.out.print("Choisissez le type de monstre : ");
            if(m_scanner.hasNextInt()) {
                choix = m_scanner.nextInt();
                m_scanner.nextLine();
            } else {
                m_scanner.nextLine(); // vider entrée non-int
            }
        } while (choix < 1 || choix >= index);

        if (choix == 4) {
            Monstreperso monstre = creerMonstrePerso();
            if(!m_especesPerso.contains(monstre.getEspece())) {
                m_especesPerso.add(monstre.getEspece());
            }
            return monstre;
        } else if (choix >= 5 && choix < index) {
            String especePerso = m_especesPerso.get(choix - 5);
            return creerMonstreEspece(especePerso);
        }

        switch (choix) {
            case 1: return creerMonstreEspece("gobelin");
            case 2: return creerMonstreEspece("dragon");
            case 3: return creerMonstreEspece("squelette");
            default: throw new IllegalArgumentException("Choix invalide.");
        }
    }

    public Monstre creerMonstreEspece(String espece) {
        m_compteurMonstres.putIfAbsent(espece, 0);
        int numero = m_compteurMonstres.get(espece) + 1;
        m_compteurMonstres.put(espece, numero);

        switch (espece) {
            case "gobelin": return new Gobelin(numero);
            case "dragon": return new Dragon(numero);
            case "squelette": return new Squelette(numero);
            default:
                Monstreperso modele = m_monstresPersonnalises.get(espece);
                if (modele != null) {
                    return new Monstreperso(
                            modele.getEspece(),
                            numero,
                            modele.getPointDeVie(),
                            modele.getForce(),
                            modele.getDexterite(),
                            modele.getInitiative(),
                            modele.getClasseArmure(),
                            modele.getTypeAttaque(),
                            modele.getPortee(),
                            modele.getDegats(),
                            modele.getIcone(),
                            modele.getVitesse()
                    );
                }
                return new Monstreperso(espece, numero, 50, 10, 10, 10, 10, "attaque basique", 1, "1d6","X&" ,3);
        }
    }

    public Monstreperso creerMonstrePerso() {
        System.out.println("=== Création d'un monstre personnalisé ===");

        String espece;
        do {
            System.out.print("Espèce du monstre : ");
            espece = m_scanner.nextLine().trim();
        } while (espece.isEmpty());

        m_compteurMonstres.putIfAbsent(espece, 0);
        int numero = m_compteurMonstres.get(espece) + 1;
        m_compteurMonstres.put(espece, numero);

        int pointDeVie = saisirEntierPositif("Points de vie : ");
        int portee = saisirEntierMin("Portée de l'attaque (>=1) : ", 1);
        int force;
        int dexterite;

        if (portee == 1) {
            System.out.println("Ce monstre attaque au corps à corps donc sa dextérité est de 0 !");
            force = saisirEntierPositif("Force : ");
            dexterite = 0;
        } else {
            System.out.println("Ce monstre attaque à distance donc sa force est de 0 !");
            dexterite = saisirEntierPositif("Dextérité : ");
            force = 0;
        }

        int initiative = saisirEntierPositif("Initiative : ");
        int classeArmure = saisirEntierPositif("Classe d'armure : ");
        int vitesse = saisirEntierPositif("Vitesse : ");

        System.out.print("Type d'attaque : ");
        String typeAttaque = m_scanner.nextLine();

        String degats;
        do {
            System.out.print("Dégâts (ex: 1d6) : ");
            degats = m_scanner.nextLine().trim();

            if (!degats.matches("\\d+d\\d+")) {
                System.out.println("Format invalide. Utilisez le format NdM, ex: 1d6 ou 2d8.");
            }
        } while (!degats.matches("\\d+d\\d+"));

        System.out.print("Icône du monstre (ex: X^, X(, etc.) : ");
        String icone = m_scanner.nextLine();
        if (icone.isEmpty()) {
            icone = "X&";
        }
        else {
            while(icone.length() > 3) {
                System.out.print("Icône du monstre (ex: X^, X(, etc., doit avoir max 3 caractères) : ");
                icone = m_scanner.nextLine();
            }
        }

        Monstreperso m = new Monstreperso(espece, numero, pointDeVie, force, dexterite,
                initiative, classeArmure, typeAttaque, portee,
                degats, icone, vitesse);
        m_monstresPersonnalises.put(espece, m);

        return new Monstreperso(espece, numero, pointDeVie, force, dexterite,
                initiative, classeArmure, typeAttaque, portee,
                degats, icone, vitesse);
    }

    private int saisirEntierPositif(String message) {
        int valeur;
        do {
            System.out.print(message);
            while (!m_scanner.hasNextInt()) {
                m_scanner.nextLine();
                System.out.print(message);
            }
            valeur = m_scanner.nextInt();
            m_scanner.nextLine();
        } while (valeur < 0);
        return valeur;
    }

    private int saisirEntierMin(String message, int min) {
        int valeur;
        do {
            System.out.print(message);
            while (!m_scanner.hasNextInt()) {
                m_scanner.nextLine();
                System.out.print(message);
            }
            valeur = m_scanner.nextInt();
            m_scanner.nextLine();
        } while (valeur < min);
        return valeur;
    }

    public void intervenir(List<Entite> participants, MapMilieu map) {
        System.out.println("=== Intervention du Maître du Jeu ===");
        System.out.println("1. Commenter l'action");
        System.out.println("2. Déplacer un monstre ou un joueur");
        System.out.println("3. Attaquer un monstre ou un joueur");
        System.out.println("4. Contrôler un monstre");
        System.out.println("5. Ne rien faire");

        int choix = saisirEntierMin("Votre choix : ", 1);

        switch (choix) {
            case 1:
                System.out.print("Commentaire : ");
                String commentaire = m_scanner.nextLine();
                ajouterLignes("MJ : " + commentaire);
                break;
            case 2:
                deplacerEntite(participants, map);
                break;
            case 3:
                attaquerEntite(participants);
                break;
            case 4:
                controlerMonstre(participants, map);
                break;
            case 5:
                System.out.println("Aucune intervention.");
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    public void deplacerEntite(List<Entite> entites, MapMilieu map) {
        System.out.println("=== Déplacement d'une entité ===");
        for (int i = 0; i < entites.size(); i++) {
            Entite e = entites.get(i);
            System.out.println((i + 1) + " . " + e.getNom() + " - Position (" + e.getPosX() + ", " + e.getPosY() + ")");
        }

        int choix = saisirEntierMin("Choisissez une entité : ", 1) - 1;
        if (choix < 0 || choix >= entites.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Entite entite = entites.get(choix);
        int x = saisirEntierPositif("Nouvelle position X : ");
        int y = saisirEntierPositif("Nouvelle position Y : ");

        if (!map.isValidPositionAndFree(x, y)) {
            System.out.println("Position invalide sur la carte.");
            return;
        }

        entite.setPosXY(x, y,map);
        System.out.println(entite.getNom() + " a été déplacé en (" + x + ", " + y + ").");
    }

    public void attaquerEntite(List<Entite> entites) {
        System.out.println("=== Attaquer une entité ===");


        for (int i = 0; i < entites.size(); i++) {
            Entite e = entites.get(i);
            System.out.println((i + 1) + " . " + e.getNom() + " - Points de vie : " + e.afficherPVDB());
        }

        // Choix de la cible
        int choix = saisirEntierMin("Choisissez une entité : ", 1) - 1;
        if (choix < 0 || choix >= entites.size()) {
            System.out.println("Choix invalide.");
            return;
        }


        Scanner scanner = new Scanner(System.in);
        System.out.print("Choisir un dés à lancer (exemple: 2d13) : ");
        String des = scanner.nextLine();

        int jetAttaque;
        try {
            jetAttaque = lancerDes(des);
        } catch (Exception e) {
            System.out.println("Erreur lors du lancer de dés : " + e.getMessage());
            return;
        }

        int degats = jetAttaque;

        Entite cible = entites.get(choix);
        cible.setPV(-degats);
        System.out.println("Vous attaquez " + cible.getNom() + " pour " + degats + " points de dégâts !");
    }


    private void controlerMonstre(List<Entite> participants, MapMilieu map) {
        // Filtrer les monstres vivants
        List<Monstre> monstresDisponibles = participants.stream()
                .filter(e -> e.estMonstre() && !e.estMort())
                .map(e -> (Monstre)e)
                .toList();

        if (monstresDisponibles.isEmpty()) {
            System.out.println("Aucun monstre disponible à contrôler.");
            return;
        }

        // Afficher la liste des monstres
        System.out.println("\n=== Monstres disponibles ===");
        for (int i = 0; i < monstresDisponibles.size(); i++) {
            Monstre m = monstresDisponibles.get(i);
            System.out.printf("%d. %s - PV: %d/%d - Position: (%d,%d)\n",
                    i+1, m.getNom(), m.getPointDeVie(), m.getPvDeBase(),
                    m.getPosX(), m.getPosY());
        }

        // Sélection du monstre
        int choixMonstre = saisirEntierMinMax("Choisissez un monstre à contrôler (1-" + monstresDisponibles.size() + ") : ",
                1, monstresDisponibles.size()) - 1;
        Monstre monstre = monstresDisponibles.get(choixMonstre);
        System.out.println("Vous contrôlez maintenant " + monstre.getNom());

        // Menu de contrôle
        boolean continuer = true;
        while (continuer && !monstre.estMort()) {
            System.out.println("\n=== Contrôle de " + monstre.getNom() + " ===");
            System.out.println("1. Attaquer");
            System.out.println("2. Se déplacer");
            System.out.println("3. Arrêter le contrôle");

            int action = saisirEntierMinMax("Votre choix : ", 1, 3);

            switch (action) {
                case 1:
                    attaquerAvecMonstre(monstre, participants);
                    break;
                case 2:
                    deplacerMonstre(monstre, map);
                    break;
                case 3:
                    continuer = false;
                    System.out.println("Vous arrêtez de contrôler " + monstre.getNom());
                    break;
            }
        }
    }

    public void attaquerAvecMonstre(Monstre monstre, List<Entite> participants) {
        // Filtrer les joueurs proches (dans la portée du monstre)
        List<Joueur> cibles = participants.stream()
                .filter(e -> !e.estMonstre() &&
                        distance(monstre, e) <= monstre.getPortee())
                .map(e -> (Joueur)e)
                .toList();

        if (cibles.isEmpty()) {
            System.out.println("Aucun joueur à portée d'attaque.");
            return;
        }

        // Afficher les cibles disponibles
        System.out.println("\n=== Cibles disponibles ===");
        for (int i = 0; i < cibles.size(); i++) {
            Joueur j = cibles.get(i);
            System.out.printf("%d. %s - PV: %d/%d - Distance: %d\n",
                    i+1, j.getNom(), j.getPointDeVie(), j.getPVdebase(),
                    distance(monstre, j));
        }

        // Sélection de la cible
        int choixCible = saisirEntierMinMax("Choisissez une cible (1-" + cibles.size() + ") : ",
                1, cibles.size()) - 1;
        Joueur cible = cibles.get(choixCible);

        // Effectuer l'attaque
        int jetAttaque = Des.lancerDes("1d20");
        int modificateur = monstre.getPortee() > 1 ? monstre.getDexterite() : monstre.getForce();
        int totalAttaque = jetAttaque + modificateur;

        System.out.printf("%s attaque %s (Jet: %d + Mod: %d = %d vs CA: %d)\n",
                monstre.getNom(), cible.getNom(),
                jetAttaque, modificateur, totalAttaque,
                cible.getClasseArmureActuelle());

        if (totalAttaque > cible.getClasseArmureActuelle()) {
            int degats = Des.lancerDes(monstre.getDegats());
            System.out.println("Attaque réussie ! Dégâts: " + degats);
            cible.setPV(-degats);
            System.out.printf("%s PV: %d/%d\n",
                    cible.getNom(), cible.getPointDeVie(), cible.getPVdebase());
        } else {
            System.out.println("Attaque échouée !");
        }
    }

    private int distance(Entite e1, Entite e2) {
        return Math.abs(e1.getPosX() - e2.getPosX()) + Math.abs(e1.getPosY() - e2.getPosY());
    }

    public void deplacerMonstre(Monstre monstre, MapMilieu map) {
        System.out.println("Directions: haut, bas, gauche, droite");
        System.out.print("Direction: ");
        String direction = m_scanner.nextLine().toLowerCase();

        int vitesse = monstre.getVitesse();
        int maxCases = vitesse / 3;
        System.out.print("Nombre de cases (1-" + maxCases + "): ");
        int nbCases = saisirEntierMinMax("", 1, maxCases);

        int newX = monstre.getPosX();
        int newY = monstre.getPosY();

        switch (direction) {
            case "haut": newX -= nbCases; break;
            case "bas": newX += nbCases; break;
            case "gauche": newY -= nbCases; break;
            case "droite": newY += nbCases; break;
            default:
                System.out.println("Direction invalide.");
                return;
        }

        if (map.isValidPositionAndFree(newX, newY)) {
            map.videCase(monstre.getPosX(), monstre.getPosY());
            monstre.setPosSansVerif(newX, newY);
            map.UpdateCase(newX, newY, monstre);
            System.out.printf("%s s'est déplacé en (%d,%d)\n",
                    monstre.getNom(), newX, newY);
        } else {
            System.out.println("Déplacement impossible !");
        }
    }

    public int saisirEntierMinMax(String message, int min, int max) {
        int valeur;
        do {
            System.out.print(message);
            while (!m_scanner.hasNextInt()) {
                m_scanner.nextLine();
                System.out.print(message);
            }
            valeur = m_scanner.nextInt();
            m_scanner.nextLine();
        } while (valeur < min || valeur > max);
        return valeur;
    }


}










