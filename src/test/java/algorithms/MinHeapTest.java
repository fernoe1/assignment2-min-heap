package algorithms;

import org.example.algorithms.IMinHeap;
import org.example.algorithms.impl.MinHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    private IMinHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new MinHeap<>();
    }

    @Test
    void testInsertAndGetMin() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);

        assertEquals(1, heap.getMin());
    }

    @Test
    void testExtractMinMaintainsHeapProperty() {
        heap.insert(4);
        heap.insert(2);
        heap.insert(7);
        heap.insert(1);

        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.getMin());
        assertEquals(3, heap.size());
    }

    @Test
    void testDecreaseKeyByIndex() {
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);

        heap.decreaseKey(2, (Integer) 5);
        assertEquals(5, heap.getMin());
    }

    @Test
    void testDecreaseKeyByElement() {
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);

        heap.decreaseKey((Integer) 30, (Integer) 1);
        assertEquals(1, heap.getMin());
    }

    @Test
    void testDecreaseKeyThrowsWhenIncreasing() {
        heap.insert(5);
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey((Integer) 0, (Integer) 10));
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey((Integer) 5, (Integer) 10));
    }

    @Test
    void testExtractMinOnMultipleElements() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(2);
        heap.insert(10);

        assertEquals(2, heap.extractMin());
        assertEquals(3, heap.getMin());
    }

    @Test
    void testMergeHeapsWithSingleList() {
        List<Integer> anotherHeap = Arrays.asList(2, 4, 6);
        heap.insert(1);
        heap.insert(3);
        heap.mergeHeaps(anotherHeap);

        assertEquals(1, heap.getMin());
        assertTrue(heap.getHeap().containsAll(Arrays.asList(1, 2, 3, 4, 6)));
    }

    @Test
    void testMergeTwoListsConstructor() {
        List<Integer> first = Arrays.asList(5, 7, 9);
        List<Integer> second = Arrays.asList(1, 2, 3);

        MinHeap<Integer> mergedHeap = new MinHeap<>(first, second);
        assertEquals(1, mergedHeap.getMin());
    }

    @Test
    void testBuildHeapFromListConstructor() {
        List<Integer> data = new ArrayList<>(Arrays.asList(10, 5, 7, 2, 8));
        MinHeap<Integer> newHeap = new MinHeap<>(data);

        assertEquals(2, newHeap.getMin());
    }

    @Test
    void testExtractMinUntilEmpty() {
        heap.insert(3);
        heap.insert(1);
        heap.insert(2);

        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertThrows(IndexOutOfBoundsException.class, heap::getMin);
    }

    @Test
    void testDecreaseKeyNonExistentElement() {
        heap.insert(10);
        assertThrows(NoSuchElementException.class, () -> heap.decreaseKey((Integer) 5, (Integer) 2));
    }

    @Test
    void testComplexSequence() {
        heap.insert(8);
        heap.insert(5);
        heap.insert(3);
        heap.insert(10);
        heap.insert(1);

        assertEquals(1, heap.getMin());
        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.getMin());

        heap.decreaseKey((Integer) 10, (Integer) 2);
        assertEquals(2, heap.getMin());
    }
}
