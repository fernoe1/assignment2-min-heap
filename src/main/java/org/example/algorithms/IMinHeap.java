package org.example.algorithms;

import java.util.List;

/**
 * Interface for Min Heap Logical Data Structure.
 */
public interface IMinHeap<T extends Comparable<T>> {
    /**
     * Adds the element to the heap.
     * @param element the element to be added.
     */
    void insert(T element);

    /**
     * Extracts minimum from the heap.
     * @return the minimum.
     */
    T extractMin();

    /**
     * Gets the minimum from the heap.
     * @return the minimum.
     */
    T getMin();

    /**
     * Decreases specified element to the new element.
     * @param element the specified element.
     * @param newElement the element to decrease into.
     */
    void decreaseKey(T element, T newElement);

    /**
     * Decreases element at the specified index to the new element.
     * @param index the specified index.
     * @param newElement the element to decrease into.
     */
    void decreaseKey(int index, T newElement);

    /**
     * Merges specified min heap to current min heap.
     * @param minHeap the specified min heap.
     */
    void mergeHeaps(List<T> minHeap);

    /**
     * Clears the current heap and makes new one by combining specified two heaps.
     * @param firstHeap the first heap.
     * @param secondHeap the second heap.
     */
    void mergeHeaps(List<T> firstHeap, List<T> secondHeap);
}
