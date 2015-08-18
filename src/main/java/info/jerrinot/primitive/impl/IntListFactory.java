package info.jerrinot.primitive.impl;

import info.jerrinot.primitive.IntList;

public class IntListFactory {

    public enum Type {
        ARRAY,
        UNSAFE,
        HEAP_BB,
        DIRECT_BB
    }

    public static IntList newList(Type type, int initialCapacity) {
        switch (type) {
            case ARRAY:
                return new ArrayIntList(initialCapacity);
            case UNSAFE:
                return new UnsafeIntList(initialCapacity);
            case HEAP_BB:
                return new ByteBufferIntList(initialCapacity, false);
            case DIRECT_BB:
                return new ByteBufferIntList(initialCapacity, true);
            default:
                throw new IllegalArgumentException("Unknown IntList type: " + type);
        }
    }
}
