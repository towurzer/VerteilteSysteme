package Homework.Exercise_1_1;

public class Main {
    public static void main(String[] args) {

        // Setup for Task 1
        long lower_bound = (long) Math.pow(2, 57);
        int range = 10;
        int thread_count = 4;

        //new ThreadManagerForTaskOne(lower_bound, range, thread_count).calculate_real_dividers();


        // Setup for Task 2
        // verify https://www.numberempire.com/primenumbers.php
        //lower_bound = (long) Math.pow(2, 57);
        lower_bound = 10;
        range = 44444444;
        thread_count = 10;

        new ThreadManagerForTaskTwo(lower_bound, range, thread_count).getPrimeNumbersInRange();
    }
}
