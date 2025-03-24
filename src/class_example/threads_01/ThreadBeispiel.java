package class_example.threads_01;

class Michi implements Runnable{
    int threadID;
    public Michi(int threadID){
        this.threadID = threadID;
    }

    @Override
    public void run() {
        System.out.printf("Class_Example.Michi %02d!\n",threadID);
    }
}

public class ThreadBeispiel{
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < cores; i++){
            Thread t = new Thread(new Michi(i+1));
            t.start();
        }
    }
}
