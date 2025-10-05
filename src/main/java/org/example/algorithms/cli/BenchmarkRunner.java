package org.example.algorithms.cli;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Scanner;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter heap size: ");
        String heapSize = scanner.nextLine();

        System.out.println("Select benchmark to run:");
        System.out.println("1 = Insert");
        System.out.println("2 = Extract Min");
        System.out.println("3 = Decrease Key");
        System.out.println("4 = Merge Heaps");
        System.out.println("5 = All Benchmarks");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        ChainedOptionsBuilder builder = new OptionsBuilder()
                .warmupIterations(2)
                .measurementIterations(3)
                .forks(1)
                .param("heapSize", heapSize);

        switch (choice) {
            case 1 -> builder.include(".*testInsert.*");
            case 2 -> builder.include(".*testExtractMin.*");
            case 3 -> builder.include(".*testDecreaseKey.*");
            case 4 -> builder.include(".*testMergeHeaps.*");
            case 5 -> builder.include(".*MinHeapBenchmark.*");
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        Options opt = builder.build();
        new Runner(opt).run();
    }
}
