import java.util.*;

public class RoundRobin extends Scheduler {
   public double timeSlice = .01;
    boolean preempt = false;
    int  processcompleted = 0;


    @Override
    public void Run() {

            checkNewProcesses();
            while (!readyqueue.isEmpty() && currentTime < simtime){
                contextSwitch();
                if (cpuqueue.get(0).iobound == false)//quantam rans out
                {
                    if(cpuqueue.get(0).burstTime - timeSlice < 0.0){
                        currentTime = cpuqueue.get(0).burstTime + currentTime;
                        cpuqueue.get(0).burstTime = 0;
                        System.out.println("debug burst time remaining: Finished Process: " + cpuqueue.get(0).ID);
                         contextSwitchDone();
                    }
                    else{
                    cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - timeSlice;
                    System.out.println("debug burst time remaining: " + cpuqueue.get(0).burstTime);
                    currentTime = currentTime + timeSlice;
                    contextSwitchDone();
                    }
                }
                checkNewProcesses();
                 //  contextSwitchDone();
                //System.out.println("[time "+currentTime + "ms] Process " + cpuqueue.get(0).ID + "  ");

            }
        }


    public void checkNewProcesses() {
        while (!inactive.isEmpty() && inactive.get(0).startTime <= currentTime) {
            if(inactive.get(0).burstTime > 0) {
                readyqueue.add(inactive.get(0));
                System.out.println("[time " + inactive.get(0).startTime + " ms][EVENT-new process]" + inactive.get(0).ID + " created ");
                    inactive.remove(0);
            }


            }
        }




     public void contextSwitch(){
         Process t = readyqueue.get(0);
         cpuqueue.add(t);
         currentTime += .00005;
         System.out.println("[time "+currentTime+"] "+"[Ready --> CPU]" +"Process: " +t.ID);
         readyqueue.remove(0);
         //System.out.println("Process " + cpuqueue.get(0).ID+ " needs " + cpuqueue.get(0).burstTime +" more CPU Time.");


    }
    void contextSwitchDone() {

        Process p = cpuqueue.get(0);
        if(p.burstTime == 0.0) {

            cpuqueue.remove(0);
            currentTime+=.00005;
        }
       else{
        readyqueue.add(p);
        //System.out.println("[time " +currentTime+ " Context switch (swapped out process) " + p.ID + " for process " + readyqueue.get(0).ID + ")");
            System.out.println("[time "+currentTime+"] "+"[CPU --> Ready]" +"Process: " +p.ID);
        cpuqueue.remove(0);
        currentTime +=.00005;
        }
      }
    }

