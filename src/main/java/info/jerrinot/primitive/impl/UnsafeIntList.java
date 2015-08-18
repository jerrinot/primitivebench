package info.jerrinot.primitive.impl;

import info.jerrinot.primitive.IntList;

import java.io.IOException;

import static info.jerrinot.primitive.utils.UnsafeUtils.UNSAFE;

public class UnsafeIntList implements IntList, AutoCloseable {

    private int capacity;
    private int size;

    private long baseAddress;

    UnsafeIntList(int initialCapacity) {
        this.capacity = initialCapacity;
        baseAddress = UNSAFE.allocateMemory(initialCapacity * 4);
    }

    @Override
    public void add(int item) {
        if (size == capacity) {
            resize();
        }
        int offset = size * 4;
        UNSAFE.putInt(baseAddress + offset, item);
        size++;
    }

    @Override
    public int elementAt(int position) {
        if (position > size) {
            throw new IndexOutOfBoundsException();
        }
        int offset = position * 4;
        return UNSAFE.getInt(baseAddress + offset);
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        long newAddress = UNSAFE.allocateMemory(newCapacity * 4);
        UNSAFE.copyMemory(baseAddress, newAddress, capacity * 4);
        UNSAFE.freeMemory(baseAddress);
        baseAddress = newAddress;
        capacity = newCapacity;
    }

    @Override
    public void close() throws IOException {
        UNSAFE.freeMemory(baseAddress);
    }
}
