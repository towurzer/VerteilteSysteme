package homework.assignment_01.tobi;

public class MyTask1Runnable implements Runnable{
    long[] numbers;
    MyTask1Runnable(long[] numbers){
        this.numbers = numbers;
    }
    @Override
    public void run() {
        // evaluate each task in the given array
        for(long number: this.numbers){
            System.out.printf("%d has %d real dividers\n", number, calculate_real_divider_amount(number));
        }
    }

    /**
     * @param number number to evaluate for dividers
     * @return returns the amount of dividers the number has
     */
    public int calculate_real_divider_amount(long number){
        int div_counter = 0;
        for(long c = 2; c < Math.sqrt(number); c++){
            if (number % c == 0){
                div_counter++;
            }
        }

        return 2 * div_counter;
    }
}
