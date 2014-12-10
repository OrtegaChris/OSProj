import java.util.*;
import java.text.NumberFormat;

public abstract class Scheduler {
    boolean stillRunning = true;
    double currentTime = 0;
    double simtime = 200.5;

    Vector <Integer> waitTimes = new Vector <Integer>();
    Vector <Integer> turnaroundTimes = new Vector <Integer>();

    Vector <Process> cpuqueue = new Vector <Process>(); // renames cpuqueue readyqueue
    Vector <Process> readyqueue = new Vector <Process>();
    Vector <Process> ioqueue = new Vector <Process>();
    Vector <Process> inactive = new Vector <Process>();



    abstract void Run();
    public void addProcesses(Vector <Process> p) {
        inactive.addAll(p);
        Collections.sort(inactive, new startTimeComparator());
    }
    public abstract void contextSwitch();

    //this function finds the min, max, and ave values for the wait and turnaround times to correct precision
 /*   public void printMMMstats(){
        float wtmin = 1000000, wtmax = 0, wttotal = 0;
        float ttmin = 1000000, ttmax = 0, tttotal = 0;
        for (int i = 0; i < waitTimes.size(); i++){
            if(waitTimes.get(i) < wtmin) wtmin = waitTimes.get(i);
            if(waitTimes.get(i) > wtmax) wtmax = waitTimes.get(i);
            if(turnaroundTimes.get(i) < ttmin) ttmin = turnaroundTimes.get(i);
            if(turnaroundTimes.get(i) > ttmax) ttmax = turnaroundTimes.get(i);
            wttotal += waitTimes.get(i);
            tttotal += turnaroundTimes.get(i);
        }
        wttotal = wttotal/waitTimes.size();
        tttotal = tttotal/waitTimes.size();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        nf.setMinimumFractionDigits(3);
        System.out.println("Wait Times:");
        System.out.print("       Minimum: " + nf.format(wtmin));
        System.out.print("       Maximum: " + nf.format(wtmax));
        System.out.print("       Average: " + nf.format(wttotal) + "\n");
        System.out.println("Turnaround Times:");
        System.out.print("       Minimum: " + nf.format(ttmin));
        System.out.print("       Maximum: " + nf.format(ttmax));
        System.out.print("       Average: " + nf.format(tttotal) + "\n");*/
    //}
}
