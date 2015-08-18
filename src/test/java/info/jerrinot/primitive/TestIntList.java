package info.jerrinot.primitive;

import info.jerrinot.primitive.impl.ByteBufferIntList;
import info.jerrinot.primitive.impl.UnsafeIntList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIntList {

    @Test
    public void canGrow() {
        int itemCount = 100;

        IntList arrayIntList = new ByteBufferIntList(1, false);
        for (int i = 0; i < itemCount; i++) {
            arrayIntList.add(i);
        }

        assertEquals(itemCount, arrayIntList.size());
        for (int i = 0; i < itemCount; i++) {
            int actualItem = arrayIntList.elementAt(i);
            assertEquals(i, actualItem);
        }
    }
}
