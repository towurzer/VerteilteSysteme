package homework.assignment_04.dragi_solution;

import java.io.Serializable;

public class PrimeSearcherTask implements Serializable {
    /** Task queue ID */
    public int queueID;

    /** Number N to be checked for being prime */
    public long N;

    public PrimeSearcherTask(int queueID, long N) {
        this.queueID = queueID;
        this.N = N;
    }
}
