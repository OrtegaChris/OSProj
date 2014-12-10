import java.util.*;

public class Process {

    public int ID;
    public double burstTime;
    public boolean iobound;
    public double startTime;
    public double turnaroundTime;//time from start to finish.
    public double waitTime;//time spent waiting
    public double lastTimeAccessed;
    /* total time spent so far in
        ready queue
        CPU
        I/O

        cpu time remaining

*/
    public Process(int ID, int startTime) {
        this.ID              = ID;
        this.startTime   = startTime;
        burstTime    = 1;
        turnaroundTime   = 0;
        waitTime         = 0;
        lastTimeAccessed = 0;
        iobound = false;

    }

}
