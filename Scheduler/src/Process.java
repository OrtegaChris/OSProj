import java.util.*;

public class Process {

    public int ID;
    public int burstTime;
    public boolean iobound;
    public int startTime;
    public int turnaroundTime;//time from start to finish.
    public int waitTime;//time spent waiting
    public int lastTimeAccessed;
    /* total time spent so far in
        ready queue
        CPU
        I/O

        cpu time remaining

*/
    public Process(int ID, int startTime) {
        this.ID              = ID;
        this.startTime   = startTime;
        burstTime    = 1000;
        turnaroundTime   = 0;
        waitTime         = 0;
        lastTimeAccessed = 0;
        iobound = false;

    }

}
