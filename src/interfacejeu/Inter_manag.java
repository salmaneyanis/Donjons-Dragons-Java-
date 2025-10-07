/*package interfacejeu;
import java.util.ArrayList;
import java.util.List;
import classes.Classe;
import classes.Guerrier;
import classes.Roublard;
import equipements.Armes.ArmeCourante;
import maitredujeux.Maitredujeux;
import monstres.Dragon;
import personnages.Entité.entite;
import personnages.Joueur;
import races.Elfe;
import races.Humain;
import races.Races;

public class Inter_manag {
    public static void main(String[] args) {
        BarreHaut barreHaut = new BarreHaut();
        Races humain = new Humain();
        Races elfe = new Elfe();
        Classe guerrier = new Guerrier();
        Classe roublard = new Roublard();
        Maitredujeux mj = new Maitredujeux();
        map_milieu map = new map_milieu(20,20); // création d'une instance

        // Création des joueurs
        Joueur joueur1 = new Joueur("Arthur", guerrier, humain);
        Joueur joueur2 = new Joueur("Luna", guerrier, elfe);
        Joueur joueur3 = new Joueur("Sonic", roublard, humain);
        Dragon raciste = new Dragon(1);


        ArrayList<entite> participants = new ArrayList<>();
        ArrayList<Object> p2 = new ArrayList<>();
        participants.add(joueur1);
        participants.add(joueur2);
        participants.add(joueur3);
        participants.add(raciste);
        p2.add(joueur1);
        p2.add(joueur2);
        p2.add(raciste);

        ArmeCourante d = new ArmeCourante("Bâton", "1d6");

        barreHaut.Affichage(joueur1, 1, participants,2);


        map.addObstacle(14, 11);
        map.addObstacle(14, 13);
        map.addEquipment(14, 9, d);

        //joueur1.setPosXY(13, 10);
        //joueur3.setPosXY(12, 6);
        //raciste.setPosXY(15, 9);

        //joueur1.seDeplacer("gauche",map,1);
        joueur1.seDeplacer("gauche",map,1);
        //joueur2.seDeplacer("droite",map, 1);
        //joueur3.seDeplacer("haut",map,1);

        mj.intervenir(participants, map);


        map.Print(p2);
        joueur1.ramasserEquipement(map);
        joueur1.seDeplacer("gauche",map,1);
        joueur1.seDeplacer("gauche",map,1);
        map.Print(p2);

    }

}
*/