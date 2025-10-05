package org.example.algorithms.cli;

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

    private MinHeap<Integer> heap;
    private List<Integer> otherHeap;
    private Random random;

    @Setup(Level.Invocation)
    public void setup() {
        heap = new MinHeap<>();
        random = new Random();

        for (int i = 0; i < heapSize; i++) {
            heap.insert(random.nextInt());
        }

        otherHeap = new ArrayList<>();
        for (int i = 0; i < heapSize / 2; i++) {
            otherHeap.add(random.nextInt());
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
}
