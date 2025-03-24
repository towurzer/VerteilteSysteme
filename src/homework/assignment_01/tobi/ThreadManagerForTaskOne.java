package homework.assignment_01.tobi;

import java.util.ArrayList;

/**
 * Class managing The threads for Task 1
 */
public class ThreadManagerForTaskOne {
    long lower_bound;
    int range;
    int thread_count;
    public ThreadManagerForTaskOne(long lower_bound, int range, int thread_count){
        this.lower_bound = lower_bound;
        this.range = range;
        this.thread_count = thread_count;
    }

    public void calculate_real_dividers(){
        ArrayList<Thread> threads = new ArrayList<>();
        int thread_workload = range / thread_count; // Amount of Work each thread is supposed to do
        int task_surplus = range % thread_count; // Amount of Workload Surplus that needs to be processed by threads
        long number = lower_bound; // current Number to process

        long start_time = System.nanoTime();


        try {
            // ########## Setup The Threads with their respective workload ########
            // Each Thread gets the standart workload plus one workload of surplus as long as there is a
            // workload surplus that needs to be processed
            for (int thread_counter = 0; thread_counter < thread_count; thread_counter++){
                // Each element in the array represents a task that needs to be processed by the thread
                long[] workload = new long[thread_workload + task_surplus];
                task_surplus = (task_surplus > 0) ? task_surplus-1 : 0;  // One surplus task less

                for(int i = 0; i < workload.length; i++){
                    workload[i] = number++;
                }


                // ####### create Threads ##########
                // create the runnable objects for each thread and the threads itself
                MyTask1Runnable runnable = new MyTask1Runnable(workload);
                Thread thread = new Thread(runnable);
                threads.add(thread);
                thread.start();
            }

            // ######## Wait till all the Threads are finished ##########
            for(Thread thread: threads){
                thread.join();
            }

        } catch (InterruptedException ignored){
            System.out.println("An exception as occurred");
        }

        // measure time needed to finish the task
        long end_time = System.nanoTime();
        System.out.printf("\nDas Programm benötigt %f Sekunden\n", (end_time-start_time)/Math.pow(10, 9));
    }
}
