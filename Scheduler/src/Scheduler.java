import java.util.*;


public abstract class Scheduler {

    double currentTime = 0;



  //  Vector <Integer> waitTimes = new Vector <Integer>();
   // Vector <Integer> turnaroundTimes = new Vector <Integer>();

    Vector <Process> cpuqueue = new Vector <Process>();
    Vector <Process> readyqueue = new Vector <Process>();
    Vector <Process> inactive = new Vector <Process>();

    abstract void Run();
    public void addProcesses(Vector <Process> p) {
        inactive.addAll(p);
        Collections.sort(inactive, new startTimeComparator());

    }
    public abstract void contextSwitch();


}
