package org.aau.homework.assignment_01.tobi;

import java.util.concurrent.CountDownLatch;

public class MyTask2Runnable implements Runnable{

    long number_to_process;
    CountDownLatch latch; // makes sure the main thread waits till all the tasks are finished
    StringBuilder stringBuilder;

    public MyTask2Runnable(long number, CountDownLatch latch, StringBuilder s){
        number_to_process = number;
        this.latch = latch;
        this.stringBuilder = s;
    }
    @Override
    public void run() {
        if(isPrimeNumber(number_to_process)){
            stringBuilder.append(number_to_process);
            stringBuilder.append(" is a Prime Number\n");
            //System.out.printf("%d is a Prime Number\n", number_to_process);
        }
        latch.countDown(); // mark the task as done
    }

    /**
     * @param number_to_process number to evaluate
     * @return returns weather the number is a prime number or not
     */
    public boolean isPrimeNumber(long number_to_process){
        for(long c = 2; c < Math.sqrt(number_to_process); c++){
            if (number_to_process % c == 0){
                return false;
            }
        }
        return true;
    }

}
