package interfacejeu;

import equipements.armes.ArmeCourante;
import equipements.armures.ArmureLegere;
import equipements.Equipement;
import equipements.GestionEquipements;
import personnages.entite.Entite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MapMilieu {
    private Case[][] m_map;
    private int m_rows;
    private int m_cols;
    private String m_letters;

    public MapMilieu(int rows, int cols) {
        this.m_rows = rows;
        this.m_cols = cols;
        this.m_map = new Case[rows][cols];
        this.m_letters = generateLetters(cols);
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0; i < m_rows; i++) {
            for (int j = 0; j < m_cols; j++) {
                m_map[i][j] = new Case();
            }
        }
    }

    private String generateLetters(int cols) {
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < cols; i++) {
            if (i < 10) {
                sb.append(" ").append(i).append(" ");
            } else {
                sb.append(i).append(" ");
            }
        }
        return sb.toString();
    }

    public boolean isValidPositionAndFree(int row, int col) {
        if(row < 0 || row >= m_rows || col < 0 || col >= m_cols) {
            return false;
        }

        return m_map[row][col].estVide();
    }

    public void addObstacle(int row, int col) {
        if(row >= 0 && row < m_rows && col >= 0 && col < m_cols) {
            m_map[row][col].setM_contenu(new Obstacle());
        }
    }

    public void addEquipment(int row, int col, Equipement equipement) {
        if (isValidPositionAndFree(row - 1, col - 1)) {
            m_map[row - 1][col - 1].setM_contenu(equipement);
        }
    }

    public void UpdateCase(int row, int col, ContenuCase contenu) {
        if (row >= 0 && row < m_rows && col >= 0 && col < m_cols) {
            m_map[row][col].setM_contenu(contenu);
        }
    }

    public void videCase(int row, int col) {
        if (row >= 0 && row < m_rows && col >= 0 && col < m_cols) {
            m_map[row][col].setM_contenu(null);
        }
    }

    public List<Equipement> recupererEquipementsAdjacents(int row, int col) {
        List<Equipement> equipementsTrouves = new ArrayList<>();


        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < m_rows && newCol >= 0 && newCol < m_cols) {
                ContenuCase contenu = m_map[newRow][newCol].getM_contenu();
                if (contenu != null && "Equipement".equals(m_map[newRow][newCol].getM_contenu().getTypeContenu())) {
                    equipementsTrouves.add((Equipement) contenu);
                }
            }
        }

        return equipementsTrouves;
    }

    public Equipement recupererEquipement(int row, int col) {
        List<Equipement> equipements = recupererEquipementsAdjacents(row, col);

        if (equipements.isEmpty()) {
            System.out.println("Aucun équipement à proximité.");
            return null;
        }

        System.out.println("\nÉquipements disponibles autour de vous:");
        for (int i = 0; i < equipements.size(); i++) {
            System.out.println((i + 1) + ". " + equipements.get(i).getNom());
        }

        System.out.print("Choisissez un équipement à ramasser (0 pour annuler): ");
        int choix = new Scanner(System.in).nextInt();

        if (choix > 0 && choix <= equipements.size()) {
            Equipement equipementChoisi = equipements.get(choix - 1);
            for (int i = 0; i < m_rows; i++) {
                for (int j = 0; j < m_cols; j++) {
                    ContenuCase contenu = m_map[i][j].getM_contenu();
                    if (contenu == equipementChoisi) {
                        m_map[i][j].setM_contenu(null);
                        return equipementChoisi;
                    }
                }
            }
        }

        return null;
    }

    public void nettoyerParticipants() {
        for (int i = 0; i < m_rows; i++) {
            for (int j = 0; j < m_cols; j++) {
                ContenuCase contenu = m_map[i][j].getM_contenu();
                if (contenu != null && contenu.estParticipant()) {
                    m_map[i][j].setM_contenu(null);
                }
            }
        }
    }


    public void Print(List<Entite> participants) {
        nettoyerParticipants();
        for (Entite obj : participants) {
            if (!obj.estMonstre()) {
                int x = obj.getPosX();
                int y = obj.getPosY();

                if(x >= 0 && x < m_rows && y >= 0 && y < m_cols) {
                    m_map[x][y].setM_contenu((ContenuCase) obj);
                }
            } else if (obj.estMonstre()) {
                int x = obj.getPosX();
                int y = obj.getPosY();

                if(x >= 0 && x < m_rows && y >= 0 && y < m_cols) {
                    m_map[x][y].setM_contenu((ContenuCase) obj);
                }
            }
        }

        // Affichage de l'en-tête
        System.out.println(m_letters);

        // Ligne supérieure
        System.out.print("   *");
        for (int i = 0; i < m_cols * 3 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("*");

        // Affichage du corps de la carte
        for (int i = 0; i < m_rows; i++) {
            System.out.print((i < 10 ? " " : "") + i +" |");
            for (int j = 0; j < m_cols; j++) {
                System.out.print(m_map[i][j].afficher());
            }
            System.out.println("|");
        }

        // Ligne inférieure
        System.out.print("   *");
        for (int i = 0; i < m_cols * 3 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("*");

        // Légende
        System.out.println("    \uD83D\uDEE1\uFE0F/\uD83D\uDDE1\uFE0F Equipement   |   [ ] Obstacle");
    }

    public static MapMilieu map1() {

        MapMilieu map = new MapMilieu(15, 15);


        map.addObstacle(0, 1);
        map.addObstacle(2, 2);
        map.addObstacle(4, 0);
        map.addObstacle(3, 3);
        map.addObstacle(5, 6);
        map.addObstacle(12, 3);
        map.addObstacle(15, 14);
        map.addObstacle(13, 14);
        map.addObstacle(2, 15);
        map.addObstacle(3, 6);
        map.addObstacle(10, 11);
        map.addObstacle(12, 12);
        map.addObstacle(9, 3);
        map.addObstacle(12, 8);
        map.addObstacle(1, 14);
        map.addObstacle(2, 13);
        map.addObstacle(8, 11);
        map.addObstacle(8, 14);


        ArmeCourante a1 = new ArmeCourante("Massue Anti-Emeute", "1d6");
        ArmureLegere a2 = new ArmureLegere("Armure Yvann", 1);

        map.addEquipment(2, 3, a1);
        map.addEquipment(4, 2, a2);
        map.addRandomEquipment(11, 5);
        map.addRandomEquipment(5, 13);
        map.addRandomEquipment(14, 4);
        map.addRandomEquipment(12, 11);


        return map;
    }

    public static MapMilieu map2() {
        int taille = 20;
        MapMilieu map = new MapMilieu(taille, taille);


        for (int i = 0; i < taille; i++) {
            map.addObstacle(0, i);
            map.addObstacle(taille - 1, i);
            map.addObstacle(i, 0);
            map.addObstacle(i, taille - 1);
        }


        for (int i = 2; i < 18; i++) {
            map.addObstacle(i, 2);
            map.addObstacle(i, 17);
        }

        for (int j = 4; j < 16; j++) {
            map.addObstacle(2, j);
            map.addObstacle(17, j);
        }


        for (int i = 4; i < 16; i += 2) {
            for (int j = 4; j < 16; j++) {
                if (j % 3 != 0) {
                    map.addObstacle(i, j);
                }
            }
        }


        for (int i = 5; i < 15; i++) {
            if (i % 2 == 0) continue;
            map.addObstacle(i, 10);
        }


        ArmeCourante epee = new ArmeCourante("Épée du labyrinthe", "2d6");
        ArmureLegere armure = new ArmureLegere("Cotte de maille", 15);
        map.addEquipment(18, 17, epee);
        map.addEquipment(2, 2, armure);
        map.addRandomEquipment(6, 12);
        map.addRandomEquipment(14, 5);
        map.addRandomEquipment(5, 7);
        map.addRandomEquipment(11, 16);

        return map;
    }

    public static MapMilieu map3() {

        MapMilieu map = new MapMilieu(15, 25);


        for (int i = 0; i < 35; i++) {
            map.addObstacle(5, i);
            map.addObstacle(10, i);
        }

        map.addRandomEquipment(2, 11);
        map.addRandomEquipment(4, 23);
        map.addRandomEquipment(7, 9);
        map.addRandomEquipment(7, 24);
        map.addRandomEquipment(12, 1);
        map.addRandomEquipment(14, 6);

        return map;
    }

    public void PrintVide() {


        // Affichage de l'en-tête
        System.out.println(m_letters);

        // Ligne supérieure
        System.out.print("   *");
        for (int i = 0; i < m_cols * 3 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("*");

        // Affichage du corps de la carte
        for (int i = 0; i < m_rows; i++) {
            System.out.print((i < 10 ? " " : "") + i  + " |");
            for (int j = 0; j < m_cols; j++) {
                System.out.print(m_map[i][j].afficher());
            }
            System.out.println("|");
        }

        // Ligne inférieure
        System.out.print("   *");
        for (int i = 0; i < m_cols * 3 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("*");

        // Légende
        System.out.println("    \uD83D\uDEE1\uFE0F/\uD83D\uDDE1\uFE0F  Equipement   |   [ ] Obstacle");
    }

    public void addRandomEquipment(int row, int col) {
        List<Equipement> equipements = GestionEquipements.initialiserEquipements();
        if (equipements.isEmpty()) return;

        Random random = new Random();
        Equipement equipementAleatoire = equipements.get(random.nextInt(equipements.size()));

        if (isValidPositionAndFree(row - 1, col - 1)) {
            m_map[row - 1][col - 1].setM_contenu(equipementAleatoire);
        }
    }
    public Case getCase(int row, int col) {
        return this.m_map[row][col];
    }
}
