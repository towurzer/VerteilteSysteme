package homework.assignment_04.ex1.api;

import java.io.Serializable;
import java.util.UUID;

public record PrimeSearcherTask(UUID taskQueueId, long number) implements Serializable {
}
