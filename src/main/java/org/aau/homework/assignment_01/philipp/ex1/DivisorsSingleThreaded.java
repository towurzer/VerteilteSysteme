package org.aau.homework.assignment_01.philipp.ex1;


import org.aau.homework.assignment_01.philipp.util.Util;
import org.aau.homework.assignment_01.philipp.util.Util;

import java.util.Scanner;

public class DivisorsSingleThreaded {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter L: ");
        long lower = scanner.nextLong();
        System.out.println("Enter R: ");
        long upper = scanner.nextLong();
        long startTime = System.nanoTime();
        for (long i = lower; i <= lower + upper; i++) {
            System.out.printf("Number of Divisors of %d: %s \n", i, Util.getDivisorCount(i));
        }

        //Calculate and output elapsed time
        Util.printElapsedTime(startTime, System.nanoTime());
    }

}
