package personnages;

import classes.Archetype;
import equipements.Equipement;
import equipements.armes.ArmeCourante;
import equipements.armures.ArmureLegere;
import interfacejeu.ContenuCase;
import interfacejeu.MapMilieu;
import monstres.*;
import personnages.entite.Entite;
import races.Races;

import des.*;
import java.util.ArrayList;
import java.util.Optional;

public class Joueur extends Personnage implements ContenuCase, Entite {
    private Archetype m_classe;
    private Races m_race;
    private ArrayList<Equipement> m_inventaire;
    private int m_posX;
    private int m_posY;


    public Joueur(String nom, Archetype classe, Races race) {
        super(nom, classe.getPvDeBase(), 0, 0, 0, 0);
        this.m_classe = classe;
        this.m_race = race;
        this.m_inventaire = new ArrayList<>();


        race.appliquerBonus(this);
        classe.genererEquipementDeBase(this);
        this.equiper((new ArmeCourante("Sans Arme", "1d1")));
        this.equiper(new ArmureLegere("Sans Armure", 1));

    }

    public void attaquer(Monstre cible) {
        afficherAttaque(getNom(), cible.getEspece(), getNomArme());

        Equipement arme = m_equipementEquipe[0];
        int portee = (arme != null) ? arme.getPortee() : 1; // portée par défaut = 1

        // 1. Calcul de la distance entre joueur et monstre
        int distance = Math.abs(getPosX() - cible.getPosX()) + Math.abs(getPosY() - cible.getPosY());

        // 2. Vérification de la portée
        if (distance > portee) {
            afficherAttaqueEchouee();
            return;
        }

        int jetAttaque = Des.lancerDes("1d20");
        int modificateur = 0;
        int degats = 0;

        if (arme != null) {
            if (arme.getType().contains("Arme à distance")) {
                modificateur = getDexterite();
            } else if (arme.getType().contains("Arme de guerre") || arme.getType().contains("Arme courante")) {
                modificateur = getForce();
            }
        }

        jetAttaque += modificateur + (arme != null ? arme.getEnchante() : 0);
        afficherJetAttaque(jetAttaque);

        if (jetAttaque > cible.getClasseArmure()) {
            afficherAttaqueReussie();
            if (arme != null) {
                degats = Des.lancerDes(arme.getDegats()) + arme.getEnchante();
            }
            afficherDegats(degats);
            cible.subirDegats(degats);
            afficherPvRestants(cible.getEspece(), cible.getPointDeVie());
        } else {
            afficherAttaqueEchouee();
        }
    }


    public void equiper(Equipement equipement) {
        int forceAvant = getForce();
        int vitesseAvant = getVitesse();

        // Vérifier si l'équipement est déjà équipé et le retirer si nécessaire
        if (equipement.estArme()) {
            if (m_equipementEquipe[0] != null) {
                setForce(getForce() - m_equipementEquipe[0].getModificateurForce());
                setVitesse(getVitesse() - m_equipementEquipe[0].getModificateurVitesse());
                getEquipements().add(m_equipementEquipe[0]);
            }
            m_equipementEquipe[0] = equipement;
            getEquipements().remove(equipement);

        } else if (equipement.estArmure()) {
            if (m_equipementEquipe[1] != null) {
                setVitesse(getVitesse() - m_equipementEquipe[1].getModificateurVitesse());
                getEquipements().add(m_equipementEquipe[1]);
            }
            m_equipementEquipe[1] = equipement;
            getEquipements().remove(equipement);
        }

        setForce(getForce() + equipement.getModificateurForce());
        setVitesse(getVitesse() + equipement.getModificateurVitesse());

        if (equipement.estArme()) {
            afficherEquipement(getNom(), equipement.getNom(), forceAvant, getForce(), vitesseAvant, getVitesse(), Optional.of(equipement.getEnchante()));
        }
        else {
            afficherEquipement(getNom(), equipement.getNom(), forceAvant, getForce(), vitesseAvant, getVitesse(), null);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nom : ").append(getNom()).append("\n")
                .append("Race : ").append(getRace().getNom()).append("\n")
                .append("Classe : ").append(getClasse().getNom()).append("\n")
                .append("PV : ").append(getPointDeVie()).append("\n")
                .append("Force : ").append(getForce()).append("\n")
                .append("Dextérité : ").append(getDexterite()).append("\n")
                .append("Vitesse : ").append(getVitesse()).append("\n")
                .append("Initiative : ").append(getInitiative()).append("\n")
                .append("Équipements : \n");

        for (Equipement equipement : getEquipements()) {
            sb.append(" - ").append(equipement).append("\n");
        }

        return sb.toString();
    }

    public Archetype getClasse() {
        return m_classe;
    }

    public Races getRace() {
        return m_race;
    }

    private Equipement getDernierEquipement() {
        if(getEquipements().getLast() == null) {
            return(new ArmureLegere("Rien", 1));
        }
        else {
            return getEquiper().getLast();
        }
    }

    public int getClasseArmureActuelle() {
        Equipement equipement = getDernierEquipement();
        return equipement != null ? equipement.getClasseArmure() : 0;
    }

    private void afficherAttaque(String nomAttaquant, String especeCible, String nomArme) {
        System.out.println(nomAttaquant + " attaque " + especeCible + " avec " + nomArme + " !");
    }

    private void afficherJetAttaque(int jet) {
        System.out.println("Jet d'attaque : " + jet);
    }

    private void afficherAttaqueReussie() {
        System.out.println("Attaque réussie!");
    }

    private void afficherDegats(int degats) {
        System.out.println("Dégâts infligés : " + degats);
    }

    private void afficherPortee(int portee) {
        System.out.println("Portée de : " + portee);
    }

    private void afficherPvRestants(String cible, int pv) {
        System.out.println(cible + " PV restants : " + pv);
    }

    private void afficherAttaqueEchouee() {
        System.out.println("Attaque échouée!");
    }

    private void afficherEquipement(String nom, String equipement, int forceAvant, int forceApres, int vitesseAvant, int vitesseApres, Optional<Integer> enchante) {
        System.out.println("[ÉQUIPEMENT] " + nom + " équipe : " + equipement);
        System.out.println("Force avant : " + forceAvant + ", Force après : " + forceApres);
        System.out.println("Vitesse avant : " + vitesseAvant + ", Vitesse après : " + vitesseApres);
        if (enchante != null) {
            System.out.println("Niveau de l'enchantement de l'arme : " + enchante);
        }
    }

    public void ajouterEquipement(Equipement equipement) {
        if (equipement != null) {
            m_inventaire.add(equipement);
            System.out.println(getNom() + " a ajouté : " + equipement.getNom() + " à son inventaire.");
        } else {
            System.out.println("Équipement invalide.");
        }
    }

    public void afficherInventaire() {
        System.out.println("Inventaire de " + getNom() + " :");
        for (Equipement equipement : m_inventaire) {
            System.out.println(" - " + equipement.getNom());
        }
    }


    public ArrayList<Equipement> getEquipements() {
        return m_inventaire;
    }



    public void seDeplacer(String direction, MapMilieu map, int nbCase) {
        int newX = m_posX;
        int newY = m_posY;

        if (nbCase <= (this.getVitesse() / 3)) {

            switch (direction.toLowerCase()) {
                case "haut":
                    newX -= nbCase;
                    break;
                case "bas":
                    newX += nbCase;
                    break;
                case "gauche":
                    newY -= nbCase;
                    break;
                case "droite":
                    newY += nbCase;
                    break;
                default:
                    System.out.println("Direction invalide.");
                    return;
            }

            if(map.isValidPositionAndFree(newX, newY)) {
                map.videCase(m_posX, m_posY);
                m_posX = newX;
                m_posY = newY;
                map.UpdateCase(m_posX, m_posY, this);
                System.out.println(getNom() + " se déplace vers " + direction + ".");
            }
             else {
                System.out.println("Déplacement impossible vers " + direction + ".");
            }
        } else {
            System.out.println("Nb Case Trop Grand");
        }
    }




    public void ramasserEquipement(MapMilieu map) {
        Equipement equip = map.recupererEquipement(m_posX, m_posY);
        if (equip != null) {
            ajouterEquipement(equip);
            System.out.println(getNom() + " a ramassé : " + equip.getNom() + " en (" + m_posX + ", " + m_posY + ").");
        } else {
            System.out.println("Pas d'équipement à récupérer à cette position.");
        }
    }


    public int getPosX() {return this.m_posX;
    }

    public int getPosY() {return this.m_posY;
    }

    public boolean setPosXY(int x, int y, MapMilieu map) {
        if (map.isValidPositionAndFree(x,y)) {
            this.m_posX = x;
            this.m_posY = y;
            return true;
        }
        else  {return false;}
    }

    public int getPVdebase()
    {
        return this.m_classe.getPvDeBase();
    }

    @Override
    public String afficherPVDB() {
        System.out.print(getPointDeVie() + "/" + getPVdebase());
        return "";
    }

    @Override
    public String getTypeContenu() {
        return "Joueur";
    }

    @Override
    public String afficher() {
        if (this.getNom() == "Yanis" || this.getNom() == "yanis") { return "👑 ";}
        else if (this.getNom() == "Sonic" || this.getNom() == "sonic") { return "\uD83E\uDD94 ";}
        else if (this.getNom() == "Yvann" || this.getNom() == "yvann") { return "\uD83D\uDCA9 ";}
        else {
            return this.getNom().length() > 3 ? this.getNom().substring(0, 3) : String.format("%-3s", this.getNom());
        }
    }

    @Override
    public String affichageClass(){
        return getClasse().toString();
    }

    @Override
    public String getAffichageCourt() {
        if (getNom() == "Yanis" || getNom() == "yanis") { return "👑 ";}
        else if (this.getNom() == "Sonic" || this.getNom() == "sonic") { return "\uD83E\uDD94 ";}
        else if (this.getNom() == "Yvann" || this.getNom() == "yvann") { return "\uD83D\uDCA9 ";}
        else {

            return getNom().substring(0, 3);
        }
    }

    @Override
    public String getAffichageLong() {
        return getNom() + " (" + getClasse().getNom() + " " + getRace().getNom() + ", " + getPointDeVie() + "/" + getPVdebase() + " HP)";
    }

    @Override
    public void setPV(int degats){
        this.addPdV(degats);
    }

    @Override
    public void setPosSansVerif(int x, int y) {
        this.m_posX = x;
        this.m_posY = y;
    }

    public String getNomArme() {
        return getEquiper().getFirst().getNom();
    }

    @Override
    public void soignerComplet() {
        setPointDeVie(this.getClasse().getPvDeBase());
    }

    @Override
    public boolean estMonstre(){return false;}

    @Override
    public boolean estParticipant() {
        return true;
    }
}
