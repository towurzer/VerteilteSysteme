package homework.assignment_04.ex1.server.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkerPool {

    private final List<UUID> workers = new ArrayList<>();

    private static WorkerPool INSTANCE;

    private WorkerPool() {}

    public static WorkerPool getInstance() {
        if(INSTANCE == null) {
            //Lazily initialize the WorkerPoolInstance
            INSTANCE = new WorkerPool();
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
}
