package homework.assignment_04.ex1.api;

import java.io.Serializable;
import java.util.UUID;

public record PrimeSearcherTask(UUID taskId, UUID taskQueueId, long number) implements Serializable {
    public PrimeSearcherTask(UUID taskQueueId, long number) {
        this(UUID.randomUUID(), taskQueueId, number);
    }
}
