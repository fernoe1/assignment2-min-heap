package org.example.cli;

import org.example.algorithms.impl.MinHeap;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class MinHeapBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int heapSize;

    @Param({"random", "sorted", "reversed", "nearly_sorted"})
    private String dataType;

    private MinHeap<Integer> heap;
    private List<Integer> otherHeap;
    private Random random;

    @Setup(Level.Invocation)
    public void setup() {
        heap = new MinHeap<>();
        random = new Random();

        int[] data = generateData(heapSize, dataType);
        for (int value : data) {
            heap.insert(value);
        }

        otherHeap = new ArrayList<>();
        int[] otherData = generateData(heapSize / 2, "random");
        for (int value : otherData) {
            otherHeap.add(value);
        }
    }

    @Benchmark
    public void testDecreaseKey() {
        int index = random.nextInt(heap.size());
        Integer current = heap.getHeap().get(index);
        Integer decreased = current - random.nextInt(100);
        heap.decreaseKey(index, decreased);
    }

    @Benchmark
    public void testMergeHeaps() {
        heap.mergeHeaps(otherHeap);
    }

    private static int[] generateData(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random();

        switch (type) {
            case "random":
                for (int i = 0; i < size; i++) {
                    arr[i] = rand.nextInt(size);
                }
                break;
            case "sorted":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;
            case "reversed":
                for (int i = 0; i < size; i++) {
                    arr[i] = size - 1 - i;
                }
                break;
            case "nearly_sorted":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                int swaps = (int) (size * 0.1);
                for (int i = 0; i < swaps; i++) {
                    int idx1 = rand.nextInt(size);
                    int idx2 = rand.nextInt(size);
                    int temp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = temp;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong data type: " + type);
        }
        return arr;
    }
}
