package des;

import java.io.IOException;
import java.util.Random;

public class Des {
    private static Random m_random = new Random();
    private static final String[] DICE_FACES = {
            " ----- \n|     |\n|  *  |\n|     |\n ----- ",  // 1
            " ----- \n| *   |\n|     |\n|   * |\n ----- ",  // 2
            " ----- \n| *   |\n|  *  |\n|   * |\n ----- ",  // 3
            " ----- \n| * * |\n|     |\n| * * |\n ----- ",  // 4
            " ----- \n| * * |\n|  *  |\n| * * |\n ----- ",  // 5
            " ----- \n| * * |\n| * * |\n| * * |\n ----- "   // 6
    };

    private static int lancerDes(int nombreFaces, int nombreDes) {
        int resultat = 0;
        for (int i = 0; i < nombreDes; i++) {
            int lance = m_random.nextInt(nombreFaces) + 1;
            resultat += lance;
        }
        return resultat;
    }

    public static int lancerDes(String notation) {
        String[] parts = notation.split("d");
        int nombreDes = Integer.parseInt(parts[0]);
        int nombreFaces = Integer.parseInt(parts[1]);
        return lancerDes(nombreFaces, nombreDes);
    }

    public static void lancerDesAvecAnimation(String notation) throws InterruptedException {
        System.out.print("Rolling");
        for (int i = 0; i < 3; i++) { // 10 itérations pour 2 secondes
            System.out.print(".");
            Thread.sleep(500);
        }
        System.out.println();

        int total = lancerDes(notation);
        System.out.println("\n🎲 Résultat final de " + notation + " : " + total);
    }

    private static void printDiceFace(int num, int faces) {
        if (faces == 6 && num >= 1 && num <= 6) {
            System.out.println(DICE_FACES[num - 1]);
        } else {
            System.out.println("[ " + num + " ]");
        }
    }

    @Override
    public String toString() {
        return "Classe Des: Générateur de dés";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        lancerDesAvecAnimation("3d6");
    }
}
