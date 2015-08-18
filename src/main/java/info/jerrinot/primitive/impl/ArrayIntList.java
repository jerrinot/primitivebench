package info.jerrinot.primitive.impl;

import info.jerrinot.primitive.IntList;

class ArrayIntList implements IntList {
    private int size;
    private int capacity;
    private int[] array;

    ArrayIntList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        this.capacity = initialCapacity;
        this.array = new int[initialCapacity];
    }

    @Override
    public void add(int item) {
        if (size == capacity) {
            resize();
        }
        array[size] = item;
        size++;
    }

    public int size() {
        return size;
    }

    @Override
    public int elementAt(int pos) {
        //TODO: Validations?
        return array[pos];
    }

    private void resize() {
        int newCapacity = calculateNewCapacity();
        int[] newArray = new int[newCapacity];

        for (int i = 0; i < capacity; i ++) {
            newArray[i] = array[i];
        }
        array = newArray;
        capacity = newCapacity;
    }

    private int calculateNewCapacity() {
        if (capacity == Integer.MAX_VALUE) {
            throw new IllegalStateException("Cannot grow over " + Integer.MAX_VALUE + " items!");
        }
        int newCapacity = 2 * capacity;
        if (capacity * 2 < capacity) { //overflow
            newCapacity = Integer.MAX_VALUE;
        }
        return newCapacity;
    }

}
