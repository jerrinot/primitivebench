package info.jerrinot.primitive.impl;

import info.jerrinot.primitive.IntList;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

class ByteBufferIntList implements IntList {
    private IntBuffer buffer;

    ByteBufferIntList(int initialCapacity, boolean direct) {
        buffer = newIntBufferWithCapacity(initialCapacity, direct);
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
        boolean isDirect = buffer.isDirect();
        IntBuffer newBuffer = newIntBufferWithCapacity(newCapacity, isDirect);
        buffer.flip();
        newBuffer.put(buffer);
        buffer = newBuffer;
    }

    private IntBuffer newIntBufferWithCapacity(int capacity, boolean direct) {
        int sizeBytes = capacity * 4;
        ByteBuffer bb = direct ? ByteBuffer.allocateDirect(sizeBytes) : ByteBuffer.allocate(sizeBytes);
        return bb.asIntBuffer();
    }
}
