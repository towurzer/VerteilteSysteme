package homework.assignment_01.philipp.ex2;

import homework.assignment_01.philipp.util.Util;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PrimeMultiThreaded {

    public static void main(String[] args) {

        //Read variable values from console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter L: ");
        long lower = scanner.nextLong();
        System.out.println("Enter R: ");
        long upper = scanner.nextLong();

        /*

        System.out.println("Enter number of threads: ");
        int threadCount = scanner.nextInt();
        startPrimeNumberTask(lower, upper, threadCount);

         */

        var testThreadCount = List.of(1, 2, 4, 6, 8, 12, 16);
        System.out.printf("Starting test for following thread counts: %s... \n",
                testThreadCount.stream().map(i -> "" + i).collect(Collectors.joining(", ")));
        for (int i : testThreadCount) {
            startPrimeNumberTask(lower, upper, i);
        }
    }

    private static void startPrimeNumberTask(long lower, long upper, int threadCount) {
        Duration elapsedTime;
        long startTime = 0;
        //ConcurrentLinkedQueue is a thread-safe, non-blocking queue
        //which allows multiple threads to add and remove tasks concurrently
        var primeNumbers = new ConcurrentLinkedQueue<Long>();

        //Since the ExecutorService implements AutoCloseable, we can use it in
        // a try-with block and do not have to explicitly call service.shutdown() anymore
        try (ExecutorService service = Executors.newFixedThreadPool(threadCount)) {
            //This list holds all Long values in the range [lower, upper+lower]
            var queue = new ConcurrentLinkedQueue<>(LongStream.range(lower, upper + lower).boxed().toList());

            //The task which should be carried out
            Runnable task = () -> {
                //Each task polls a number from the queue as long as it is
                //not empty and checks if it is a prime number
                while (!queue.isEmpty()) {
                    Long num = queue.poll();
                    if (num != null && Util.isPrime(num)) {
                        //Store the prime numbers in a list and print them all at the end
                        //to mitigate performance penalty of synchronized System.out
                        primeNumbers.add(num);
                    }
                }
            };

            startTime = System.nanoTime();

            //Execute the requested number of tasks
            for (int i = 0; i < threadCount; i++) {
                service.execute(task);
            }
        } finally {
            //Use the end time of calculation and do the output afterwards to
            //make Single vs. Multi-Threaded more compareable
            long endTime = System.nanoTime();
            //Print the calculated prime numbers and the elapsed time
            //primeNumbers.forEach(System.out::println);
            System.out.printf("%s threads -> ", threadCount);
            Util.printElapsedTime(startTime, endTime);
        }
    }

}
