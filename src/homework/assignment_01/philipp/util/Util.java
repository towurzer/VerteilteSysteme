package homework.assignment_01.philipp.util;

import java.math.BigInteger;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Util {

    /**
     * Get the amount of divisors for a given number
     */
    public static long getDivisorCount(long num){
        long divisorsCount = 0;
        //Convert long to BigInteger and get the sqrt of the value
        var big = new BigInteger(String.valueOf(num));
        var sqrt = big.sqrt().longValue();
        for(long j = 2; j <= sqrt; j++){
            if(num % j == 0){
                divisorsCount++;
            }
        }

        //Multiply by two since if m is a divisor of N, then also N/m is a divisor
        return divisorsCount * 2;
    }

    /**
     * Print the elapsed time to the console
     * @param start Start time in nanoseconds
     * @param end End time in nanoseconds
     */
    public static Duration printElapsedTime(long start, long end){
        var elapsedTime = Duration.of(end - start, ChronoUnit.NANOS);
        System.out.printf("Elapsed time: %s.%s seconds\n", elapsedTime.getSeconds(), elapsedTime.getNano());
        return elapsedTime;
    }

    /**
     * Checks if a given number is prime
     */
    public static boolean isPrime(long num){
        //Convert long to BigInteger and get the sqrt of the value
        var big = new BigInteger(String.valueOf(num));
        var sqrt = big.sqrt().longValue();
        for(long j = 2; j <= sqrt; j++){
            if(num % j == 0){
               return false;
            }
        }

        //Multiply by two since if m is a divisor of N, then also N/m is a divisor
        return true;
    }
}
