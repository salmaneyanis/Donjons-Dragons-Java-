package personnages.entite;

import interfacejeu.MapMilieu;

/**
 * Représente une entité présente sur la carte (joueur, monstre...).
 * Fournit un contrat pour l'affichage, les points de vie et la position.
 */
public interface Entite {

    /**
     * Affichage ASCII ou unicode de l’entité (ex: "👾").
     */
    String affichageClass();

    /**
     * Affichage court (symbole ou lettre unique pour la grille).
     */
    String getAffichageCourt();

    /**
     * Affichage long (nom complet + infos).
     */
    String getAffichageLong();

    /**
     * Coordonnée X de l'entité sur la carte.
     */
    int getPosX();

    /**
     * Coordonnée Y de l'entité sur la carte.
     */
    int getPosY();

    /**
     * Tente de définir une nouvelle position si la case est libre.
     * @param x nouvelle position X
     * @param y nouvelle position Y
     * @param map carte du jeu
     * @return true si le déplacement est possible, false sinon
     */
    boolean setPosXY(int x, int y, MapMilieu map);

    /**
     * Nom de l’entité.
     */
    String getNom();

    /**
     * Initiative de l'entité (utilisé pour l'ordre de combat).
     */
    int getInitiative();

    /**
     * Affiche les points de vie de l’entité sous forme de barre.
     */
    String afficherPVDB();

    /**
     * Applique des dégâts à l'entité.
     * @param degats quantité de dégâts
     */
    void setPV(int degats);

    /**
     * Déplace l'entité sans vérifier les règles (utilisé en cas spéciaux).
     */
    void setPosSansVerif(int x, int y);

    /**
     * Indique si l'entité est un monstre.
     */
    boolean estMonstre();

    /**
     * Indique si l'entité est morte.
     */
    boolean estMort();
}
