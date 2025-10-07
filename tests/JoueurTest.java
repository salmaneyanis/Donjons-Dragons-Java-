
import classes.Guerrier;
import personnages.Joueur;
import races.Elfe;

public class JoueurTest extends Joueur {
    public JoueurTest(String nom) {
        super(nom, new Guerrier(), new Elfe());
        setPV(100);
        setPosSansVerif(0, 0);
    }
}
