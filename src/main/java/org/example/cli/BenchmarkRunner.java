package org.example.cli;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.Scanner;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Run mode:");
        System.out.println("1 = Custom (choose heap size and data type)");
        System.out.println("2 = Full suite (run all sizes and data types)");
        System.out.print("Choice: ");
        int mode = Integer.parseInt(scanner.nextLine());

        System.out.println("\nSelect benchmark to run:");
        System.out.println("1 = Decrease Key");
        System.out.println("2 = Merge Heaps");
        System.out.println("3 = Both");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        String includePattern;
        switch (choice) {
            case 1 -> includePattern = ".*testDecreaseKey.*";
            case 2 -> includePattern = ".*testMergeHeaps.*";
            case 3 -> includePattern = ".*MinHeapBenchmark.*";
            default -> {
                System.out.println("Invalid benchmark choice");
                return;
            }
        }

        if (mode == 1) {
            System.out.print("\nEnter heap size: ");
            String heapSize = scanner.nextLine();

            System.out.println("\nSelect data type to generate:");
            System.out.println("1 = Random");
            System.out.println("2 = Sorted");
            System.out.println("3 = Reversed");
            System.out.println("4 = Nearly Sorted");
            System.out.print("Choice: ");
            int dataChoice = Integer.parseInt(scanner.nextLine());

            String dataType;
            switch (dataChoice) {
                case 1 -> dataType = "random";
                case 2 -> dataType = "sorted";
                case 3 -> dataType = "reversed";
                case 4 -> dataType = "nearly_sorted";
                default -> {
                    System.out.println("Invalid data type choice");
                    return;
                }
            };

            ChainedOptionsBuilder builder = new OptionsBuilder()
                    .warmupIterations(2)
                    .measurementIterations(3)
                    .forks(1)
                    .include(includePattern)
                    .param("heapSize", heapSize)
                    .param("dataType", dataType);

            new Runner(builder.build()).run();

        } else if (mode == 2) {
            List<String> heapSizes = List.of("100", "1000", "10000", "100000");
            List<String> dataTypes = List.of("random", "sorted", "reversed", "nearly_sorted");

            System.out.println("\nRunning full benchmark en suite...");
            for (String dataType : dataTypes) {
                for (String heapSize : heapSizes) {
                    System.out.printf("\nRunning %s heapSize=%s ...%n", dataType, heapSize);

                    ChainedOptionsBuilder builder = new OptionsBuilder()
                            .warmupIterations(2)
                            .measurementIterations(3)
                            .forks(1)
                            .include(includePattern)
                            .param("heapSize", heapSize)
                            .param("dataType", dataType);

                    Options opt = builder.build();
                    new Runner(opt).run();
                }
            }

            System.out.println("\n Full benchmark en suite completed");
        } else {
            System.out.println("Invalid mode selected");
        }
    }
}
