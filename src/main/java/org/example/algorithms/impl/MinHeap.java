package org.example.algorithms.impl;

import org.example.algorithms.IMinHeap;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<T>> implements IMinHeap<T> {
    // Physical Data Structure
    private List<T> list;

    /**
     * Default constructor that initializes with Array List.
     */
    public MinHeap() {
        list = new ArrayList<>();
    }

    /**
     * Parametrized constructor that initializes Min Heap with the Physical Data Structure of the
     * user's choice.
     * @param list Any Physical Data Structure that implements the List interface.
     */
    public MinHeap(List<T> list) {
        this.list = new ArrayList<>(list);
        buildHeapFast();
    }

    /**
     * Parametrized constructor that initializes with both heaps.
     * @param firstHeap the first heap.
     * @param secondHeap the second heap.
     */
    public MinHeap(List<T> firstHeap, List<T> secondHeap) {
        this.list = new ArrayList<>();
        mergeHeaps(firstHeap, secondHeap);
    }

    /**
     * In-place heap construction using the McDiarmid–Reed algorithm.
     * Trickle an empty slot down, then bubble up the saved element.
     */
    private void buildHeapFast() {
        int n = list.size();
        for (int i = parentOf(n - 1); i >= 0; i--) {
            mergeAt(i, n);
        }
    }

    /**
     * Merge operation (min-heap version of McDiarmid & Reed "Merge").
     * Trickle an empty slot down the smaller-child path to a leaf,
     * then bubble-up the stored element.
     */
    private void mergeAt(int root, int n) {
        T x = list.get(root);
        int pos = root;

        // trickle empty slot down
        while (leftChildOf(pos) < n) {
            int left = leftChildOf(pos);
            int right = left + 1;
            int smaller = left;

            if (right < n && list.get(right).compareTo(list.get(left)) < 0) {
                smaller = right;
            }

            // move smaller child up into the empty slot
            list.set(pos, list.get(smaller));
            pos = smaller;
        }

        // place x at leaf
        list.set(pos, x);

        // bubble-up phase
        while (pos > 0) {
            int parent = parentOf(pos);
            if (list.get(pos).compareTo(list.get(parent)) < 0) {
                swap(pos, parent);
                pos = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Decreases element at the index to the new element.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @throws IllegalArgumentException if the new element is higher than the element at the specified index.
     * @param index the index of the element to change.
     * @param newElement the element to decrease into.
     */
    @Override
    public void decreaseKey(int index, T newElement) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("The " + index + " is out of bounds");
        }

        if (list.get(index).compareTo(newElement) < 0) {
            throw new IllegalArgumentException("You are increasing the key instead of decreasing it");
        }

        list.set(index, newElement);
        traverseUp(index);
    }

    /**
     * Merges specified heap into current heap.
     * @param minHeap the specified min heap.
     */
    @Override
    public void mergeHeaps(List<T> minHeap) {
        list.addAll(minHeap);
        minHeapBuilder();
    }

    /**
     * Clears current heap by changing the pointer to the first heap, and then merges them.
     * @param firstHeap the first heap.
     * @param secondHeap the second heap.
     */
    @Override
    public void mergeHeaps(List<T> firstHeap, List<T> secondHeap) {
        list.clear();
        list.addAll(firstHeap);
        list.addAll(secondHeap);
        minHeapBuilder();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<T> getHeap() {
        return list;
    }

    /**
     * Decreases element to specified new element.
     * @throws IllegalArgumentException If you are increasing the key instead of decreasing it.
     * @throws NoSuchElementException If the element you specified does not exist.
     * @param element element to change.
     * @param newElement element to decrease into.
     */
    @Override
    public void decreaseKey(T element, T newElement) {
        if (element.compareTo(newElement) < 0) {
            throw new IllegalArgumentException("You are increasing the key instead of decreasing it");
        }

        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).compareTo(element) == 0) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            list.set(index, newElement);
            traverseUp(index);
        } else {
            throw new NoSuchElementException(element + " is not found");
        }
    }

    /**
     * Inserts to the heap
     * @param element the element to be added.
     */
    @Override
    public void insert(T element) {
        list.add(element);
        traverseUp(list.size() - 1);
    }

    /**
     * Get root.
     * @return The root.
     */
    @Override
    public T getMin() {
        if (list.isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }

        return list.getFirst();
    }

    /**
     * Extract root.
     * @return The root.
     */
    @Override
    public T extractMin() {
        if (list.isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }

        T min = getMin();
        list.set(0, list.getLast());
        list.removeLast();
        heapify(0);

        return min;
    }

    /**
     * Builds min heap. <br>
     * This function should only be called when parametrized constructor is used.
     */
    private void minHeapBuilder() {
        for (int i = list.size()/2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Heapifies from specified index.
     * @param index index to start heapifying from.
     */
    private void heapify(int index) {
        // Checking if the index is a leaf
        int size = list.size();
        if (size / 2 <= index && index <= size - 1) {
            return;
        }

        // Finding index of smallest element
        int smallest = index;
        int leftChildIndex = leftChildOf(index);
        int rightChildIndex = rightChildOf(index);
        if (leftChildOf(index) < size && list.get(smallest).compareTo(list.get(leftChildIndex)) > 0) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < size && list.get(smallest).compareTo(list.get(rightChildIndex)) > 0) {
            smallest = rightChildIndex;
        }

        // If smallest is different from the index swap and continue to heapify
        if (smallest != index) {
            swap(smallest, index);
            heapify(smallest);
        }
    }

    /**
     * Sift-up operation.
     * @param index index of the element being inserted.
     */
    private void traverseUp(int index) {
        // If the element is the root stop
        if (index == 0) {
            return;
        }


        // If the element fits the requirement stop
        if (list.get(index).compareTo(list.get(parentOf(index))) > 0) {
            return;
        }

        // Else swap and continue traversing up
        swap(index, parentOf(index));
        traverseUp(parentOf(index));
    }

    /**
     * Returns parent index of a node.
     * @param index node's index.
     * @return Node's parent index.
     */
    private int parentOf(int index) {
        return (index - 1) / 2;
    }

    /**
     * Returns right child index of a node.
     * @param index node's index.
     * @return Node's right child index.
     */
    private int rightChildOf(int index) {
        return 2 * index + 2;
    }

    /**
     * Returns left child of a node.
     * @param index node's index.
     * @return Node's left child index.
     */
    private int leftChildOf(int index) {
        return 2 * index + 1;
    }

    /**
     * Swaps elements in specified two indexes.
     * @param indexOne Index of the first element.
     * @param indexTwo Index of the second element.
     */
    private void swap(int indexOne, int indexTwo) {
        T temp = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, temp);
    }
}
