package info.jerrinot.primitive;

import info.jerrinot.primitive.impl.IntListFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestIntList {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {IntListFactory.Type.ARRAY},
                {IntListFactory.Type.DIRECT_BB},
                {IntListFactory.Type.HEAP_BB},
                {IntListFactory.Type.UNSAFE}
        });
    }

    @Parameter
    public IntListFactory.Type type;

    private IntList intList;

    @Before
    public void setUp() {
        intList = IntListFactory.newList(type, 1);
    }

    @After
    public void tearDown() throws Exception {
        if (intList instanceof AutoCloseable) {
            ((AutoCloseable) intList).close();
        }
    }

    @Test
    public void canGrow() {
        int itemCount = 100;

        for (int i = 0; i < itemCount; i++) {
            intList.add(i);
        }

        assertEquals(itemCount, intList.size());
        for (int i = 0; i < itemCount; i++) {
            int actualItem = intList.elementAt(i);
            assertEquals(i, actualItem);
        }
    }
}
