package homework.assignment_04.ex1.server.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkerService {

    private final List<UUID> workers = new ArrayList<>();
    private boolean terminated = false;

    private static WorkerService INSTANCE;

    private WorkerService() {}

    public static WorkerService getInstance() {
        if(INSTANCE == null) {
            //Lazily initialize the WorkerPoolInstance
            INSTANCE = new WorkerService();
        }
        return INSTANCE;
    }

    public UUID addWorker(){
        var workerId = UUID.randomUUID();
        workers.add(workerId);
        return workerId;
    }

    public List<UUID> getWorkers() {
        return workers;
    }

    public void terminateWorkers(){
        terminated = true;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void activateWorkers(){
        terminated = false;
    }
}
