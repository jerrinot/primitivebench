package info.jerrinot.primitive;

import info.jerrinot.primitive.utils.Sorcery;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SorceryTest {

    @Test
    public void testReturnZeroIfNotEqualsOtherwiseReturnOne() {
        int count = 100000;
        Random r = new Random();

        for (int i = 0; i < count; i++) {
            int i1 = r.nextInt();
            int i2 = r.nextInt();

            boolean reallyEquals = (i1 == i2);
            boolean fastEquals = fastEquals(i1, i2);
            assertEquals(reallyEquals, fastEquals);

        }
    }

    private boolean fastEquals(int x, int y) {
        int i = Sorcery.returnOneIfEqualsZeroOtherwise(x, y);
        switch (i) {
            case 0 : return false;
            case 1 : return true;
            default: throw new IllegalStateException();

        }
    }

}
