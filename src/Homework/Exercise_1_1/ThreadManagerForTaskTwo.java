package Homework.Exercise_1_1;

import java.util.concurrent.*;

/**
 * Class managing The threads for Task 2
 */
public class ThreadManagerForTaskTwo {
    long lower_bound;
    int range;
    int thread_count;

    StringBuilder myOutputString = new StringBuilder();
    public ThreadManagerForTaskTwo(long lower_bound, int range, int thread_count){
        this.lower_bound = lower_bound;
        this.range = range;
        this.thread_count = thread_count;
    }

    public void getPrimeNumbersInRange(){
        // list of tasks to be performed
        ConcurrentLinkedQueue<MyTask2Runnable> tasks = new ConcurrentLinkedQueue<>();
        CountDownLatch latch = new CountDownLatch(range); // latch to manage thread pool

        // setup tasks
        for(long number = lower_bound; number < lower_bound + range; number++){
            tasks.add(new MyTask2Runnable(number, latch, myOutputString));
        }


        long start_time = System.nanoTime();

        // create thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(thread_count);

        // while there are tasks left, work on them
        for (MyTask2Runnable task : tasks){
            executorService.execute(task);
        }

        // wait till all tasks are finished and shutdown the executorService to not accept any new tasks
        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            System.out.println("An exception Occurred");
        }

        // measure time needed and return
        System.out.println(myOutputString.toString());
        long end_time = System.nanoTime();
        System.out.printf("\nDas Programm benötigt %f Sekunden\n", (end_time-start_time)/Math.pow(10, 9));
    }
}
