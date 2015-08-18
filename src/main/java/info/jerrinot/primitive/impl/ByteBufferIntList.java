package info.jerrinot.primitive.impl;

import info.jerrinot.primitive.IntList;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ByteBufferIntList implements IntList {
    private IntBuffer buffer;

    public ByteBufferIntList(int initialCapacity) {
        buffer = ByteBuffer.allocateDirect(initialCapacity * 4).asIntBuffer();
    }

    @Override
    public void add(int item) {
        if (buffer.remaining() == 0) {
            resize();
        }
        buffer.put(item);
    }

    @Override
    public int elementAt(int position) {
        return buffer.get(position);
    }

    @Override
    public int size() {
        return buffer.position();
    }

    private void resize() {
        int newCapacity = buffer.capacity() * 2;
        IntBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity * 4).asIntBuffer();
        buffer.flip();
        newBuffer.put(buffer);
        buffer = newBuffer;

    }
}
