package interfacejeu;
import personnages.entite.Entite;

import java.util.List;


public class BarreHaut {
    private static final String m_barre = "********************************************************************************"; // 1



    /**
     * Classe d'affichage pour la barre supérieure de l'interface de jeu.
     */
    public void Affichage(Entite joueurActif, int donj, List<Entite> participants, int tour) {
        System.out.println(m_barre);
        System.out.println("Donjon " + donj + ":");
        sautLigne(2);

        System.out.println(joueurActif.getAffichageLong());

        sautLigne(2);
        System.out.println(m_barre);
        System.out.println("Tour " + tour + ":");

        for (Entite obj : participants) {
            String prefixe = (obj == joueurActif) ? "-> " : "   ";
            System.out.println(prefixe + obj.getAffichageCourt() + "   " + obj.getAffichageLong());
        }
    }
    /**
     * Affiche un nombre donné de sauts de ligne.
     *
     * @param nb nombre de lignes à sauter
     */
    public void sautLigne(int nb) {
        for (int n = 0; n < nb; n++) {
            System.out.println();
        }
    }
}

