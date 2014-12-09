import java.util.*;

public class RoundRobin extends Scheduler {
    int timeSlice = 100;
    boolean preempt = false;
    double processcompleted = 0;
    //int simTime = 10000;


    @Override
    public void Run() {

            checkNewProcesses();
            while (!readyqueue.isEmpty() && currentTime < simtime) {
                contextSwitch();
                if (cpuqueue.get(0).iobound == false) {
                    cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - timeSlice;
                    System.out.println("debug burst time remaining: " + cpuqueue.get(0).burstTime);
                    contextSwitchDone();
                    currentTime = currentTime + timeSlice;
                }
                checkNewProcesses();

                //System.out.println("[time "+currentTime + "ms] Process " + cpuqueue.get(0).ID + "  ");

            }
        }


    public void checkNewProcesses() {
        while (!inactive.isEmpty() && inactive.get(0).startTime <= currentTime) {
            if(inactive.get(0).burstTime > 0) {
                readyqueue.add(inactive.get(0));
                System.out.println("[time " + currentTime + "ms][EVENT-new process]" + inactive.get(0).ID + " created ");
                    inactive.remove(0);
            }


            }
        }




     public void contextSwitch(){
        Process p = readyqueue.get(0);
        cpuqueue.add(p);
         currentTime += 9;
        System.out.println("[time "+currentTime+"] "+"[Ready --> CPU]" +"Process: " +p.ID);
         readyqueue.remove(0);
         //System.out.println("Process " + cpuqueue.get(0).ID+ " needs " + cpuqueue.get(0).burstTime +" more CPU Time.");

    }
    void contextSwitchDone() {
        Process p = cpuqueue.get(0);
        if(p.burstTime <= 0) {

            cpuqueue.remove(0);
            currentTime+=9;
        }
       else{
        readyqueue.add(p);
        System.out.println("[timeContext switch (swapped out process) " + p.ID + " for process " + readyqueue.get(0).ID + ")");
        cpuqueue.remove(0);
        currentTime +=9;
      }
    }
}
