package info.jerrinot.primitive;

import info.jerrinot.primitive.impl.IntListFactory;
import info.jerrinot.primitive.impl.UnsafeIntList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnsafeIntListTest {

    private UnsafeIntList intList;

    @Before
    public void setUp() {
        intList = (UnsafeIntList) IntListFactory.newList(IntListFactory.Type.UNSAFE, 10);
    }

    @After
    public void tearDown() throws Exception {
        if (intList instanceof AutoCloseable) {
            ((AutoCloseable) intList).close();
        }
    }

    @Test
    public void testGetAsLong() {
        int itemCount = Integer.MAX_VALUE / 8 + 1;
        for (int i = 0; i < itemCount; i++) {
            intList.add(i);
        }

        for (int i = 0; i < itemCount; i += 2) {
            long l = intList.elementAsLong(i);
            int first = (int) (l & 0xffffffff);
            int second = (int)(l >> 32);

            assertEquals(i, first);
            assertEquals(i +1, second);
        }
    }

}
