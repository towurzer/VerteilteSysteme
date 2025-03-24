package homework.assignment_01.philipp.ex2;


import homework.assignment_01.philipp.util.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class PrimeSingleThreaded {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter L: ");
        long lower = scanner.nextLong();
        System.out.println("Enter R: ");
        long upper = scanner.nextLong();
        long startTime = System.nanoTime();
        var primeNumbers = new ArrayList<Long>();

        //Iterate over each number in the given range and output
        //every prime number
        for (long i = lower; i <= lower + upper; i++) {
            if (Util.isPrime(i)) {
                //i is prime
                primeNumbers.add(i);
            }
        }

        //Use the end time of calculation and do the output afterwards to
        //make Single vs. Multi-Threaded more compareable
        long endTime = System.nanoTime();
        primeNumbers.forEach(System.out::println);
        Util.printElapsedTime(startTime, endTime);
    }
}
