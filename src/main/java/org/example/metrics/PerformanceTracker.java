package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Tracks performance metrics for MinHeap operations. <br>
 * Tracks: <br>
 *  Decrease key <br>
 *  Merge heap <br>
 * Includes recursive depth tracking and CSV output.
 */
public class PerformanceTracker {
    private static long comparisons = 0;
    private static long swaps = 0;
    private static long arrayAccesses = 0;
    private static double executionTimeMs = 0.0;
    private static int maxRecursiveDepth = 0;

    private static String operationName = "";
    private static int heapSize = 0;

    private static long startTimeMillis = 0;

    private static boolean metricsInitialized = false;

    /**
     * Resets all metrics.
     */
    public static void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        executionTimeMs = 0.0;
        maxRecursiveDepth = 0;
        operationName = "";
        heapSize = 0;
        startTimeMillis = 0;
    }

    /**
     * Starts tracking a new operation, automatically resets previous metrics.
     */
    public static void start(String operation, int size) {
        reset();
        operationName = operation;
        heapSize = size;
        startTimeMillis = System.currentTimeMillis();
    }

    /**
     * Stops tracking and records elapsed time.
     */
    public static void stop() {
        executionTimeMs = System.currentTimeMillis() - startTimeMillis;
    }

    public static void incrementComparisons(long count) {
        comparisons += count;
    }

    public static void incrementSwaps(long count) {
        swaps += count;
    }

    public static void incrementArrayAccesses(long count) {
        arrayAccesses += count;
    }

    public static void updateRecursiveDepth(int currentDepth) {
        if (currentDepth > maxRecursiveDepth) {
            maxRecursiveDepth = currentDepth;
        }
    }

    /**
     * Writes metrics to a CSV file
     */
    public static void writeToCSV(String filePath) {

        if (!metricsInitialized) {
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write("Operation,HeapSize,ExecutionTime(ms),Comparisons,Swaps,ArrayAccesses,MaxRecursiveDepth\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            metricsInitialized = true;
        }

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(String.format("%s,%d,%.3f,%d,%d,%d,%d\n",
                    operationName,
                    heapSize,
                    executionTimeMs,
                    comparisons,
                    swaps,
                    arrayAccesses,
                    maxRecursiveDepth));
        } catch (IOException e) {
            System.err.println("CSV error: " + e.getMessage());
        }
    }
}
