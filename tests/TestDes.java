
import des.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDes {

    @Test
    void testLancerDesNotation3d6() {
        int result = Des.lancerDes("3d6");
        assertTrue(result >= 3 && result <= 18, "3d6 devrait donner un résultat entre 3 et 18");
    }

    @Test
    void testLancerDesNotation1d20() {
        int result = Des.lancerDes("1d20");
        assertTrue(result >= 1 && result <= 20, "1d20 devrait donner un résultat entre 1 et 20");
    }

    @Test
    void testToString() {
        Des des = new Des();
        assertEquals("Classe Des: Générateur de dés", des.toString());
    }

    @Test
    void testLancerDesMultipleAppels() {
        for (int i = 0; i < 10; i++) {
            int result = Des.lancerDes("2d10");
            assertTrue(result >= 2 && result <= 20);
        }
    }

    @Test
    void testLancerDesNotationInvalidThrowsException() {
        assertThrows(NumberFormatException.class, () -> Des.lancerDes("xd6"));
        assertThrows(NumberFormatException.class, () -> Des.lancerDes("2dx"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Des.lancerDes("2"));
    }
}
