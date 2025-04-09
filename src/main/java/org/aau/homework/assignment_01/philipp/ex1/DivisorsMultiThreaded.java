package org.aau.homework.assignment_01.philipp.ex1;


import org.aau.homework.assignment_01.philipp.util.Util;
import org.aau.homework.assignment_01.philipp.util.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class DivisorsMultiThreaded {

    public static void main(String[] args) throws InterruptedException {

        //Read variable values from console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter L: ");
        long lower = scanner.nextLong();
        System.out.println("Enter R: ");
        long upper = scanner.nextLong();
        System.out.println("Enter number of threads: ");
        int threadCount = scanner.nextInt();

        //Divide work (more or less) equally on the threads
        long range = upper / threadCount;

        //Spawn given number of threads, each thread gets a range of numbers
        //to find the divisors for.
        long startTime = System.nanoTime();
        var threads = new ArrayList<Thread>();
        long max = upper + lower;
        for (int i = 0; i < threadCount; i++) {
            var t = new Thread(new MyRunnable(i, lower, Math.min(lower + range, max)));
            t.start();
            threads.add(t);
            lower += (range + 1);
        }

        //Wait for the completion of all threads by joining them
        for (Thread thread : threads) {
            thread.join();
        }

        Util.printElapsedTime(startTime, System.nanoTime());
    }
}

class MyRunnable implements Runnable {

    private int threadId;
    private long lower;
    private long upper;

    public MyRunnable(int threadId, long lower, long upper) {
        this.threadId = threadId;
        this.lower = lower;
        this.upper = upper;
        System.out.printf("Thread %02d -> Range: %d - %d \n", threadId, lower, upper);
    }

    @Override
    public void run() {
        //Using string builder to mitigate the performance penalty of synchronized System.out
        StringBuilder sb = new StringBuilder();
        //Iterates over the given range of numbers, counts the divisor for each and add
        //a message to the string builder
        for (long i = lower; i <= upper; i++) {
            sb.append("Thread %02d -> Number of Divisors of %d: %s \n".formatted(threadId, i, Util.getDivisorCount(i)));
        }
        //Output to console
        System.out.println(sb);
    }
}
