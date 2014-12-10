import java.util.*;

public class RoundRobin extends Scheduler {
   public double timeSlice = .01;
    public double iointerrupt = .02;
    double simtime = 100;

    int  processcompleted = 0;


    @Override
    public void Run() {

            checkNewProcesses();
            while (!readyqueue.isEmpty() && currentTime < simtime){
                checkNewProcesses();
                //checkioProcesses();
                contextSwitch();
                if (cpuqueue.get(0).iobound == false)//quantam runs out
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
                else {
                       // Process p = readyqueue.get(0);
                    if(cpuqueue.get(0).burstTime - timeSlice < 0.0){
                        System.out.println("[time "+currentTime+ " ms][IO-Inteerupt]");
                        currentTime += iointerrupt;
                        System.out.println("[time "+currentTime+ " ms][IO-Complete]");
                        currentTime = cpuqueue.get(0).burstTime + currentTime;
                        cpuqueue.get(0).burstTime = 0;
                        System.out.println("debug burst time remaining: Finished Process: " + cpuqueue.get(0).ID);
                        contextSwitchDone();
                    }
                    else {
                        System.out.println("[time "+currentTime+ " ms][IO-Inteerupt]");
                        currentTime += iointerrupt;
                        System.out.println("[time "+currentTime+ " ms][IO-Complete]");
                        cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - timeSlice;
                        currentTime = currentTime + timeSlice;
                        System.out.println("debug burst time remaining: " + cpuqueue.get(0).burstTime);
                        contextSwitchDone();
                    }


                }


                 //  contextSwitchDone();
                //System.out.println("[time "+currentTime + "ms] Process " + cpuqueue.get(0).ID + "  ");

            }
        endofRun();
    }


    public void checkNewProcesses() {
        while (!inactive.isEmpty() && inactive.get(0).startTime <= currentTime) {
            if(inactive.get(0).burstTime > 0) {
                readyqueue.add(inactive.get(0));
                System.out.println("[time " + inactive.get(0).startTime + " ms][New Process]" + inactive.get(0).ID + " created.");
                inactive.remove(0);
            }


            }
        }
  /*  public void checkioProcesses(){
        while(!ioqueue.isEmpty()){
            if(inactive.get(0).iotime < 0){
                ioqueue.get(0).iobound = false;
                readyqueue.add(ioqueue.get(0));
                System.out.println("[time "+ currentTime + " ms] [IO FINISHED] Process: "+ ioqueue.get(0).ID);
                ioqueue.remove(0);

            }
        }
    }

*/


     public void contextSwitch(){
         Process t = readyqueue.get(0);
         cpuqueue.add(t);
         currentTime += .00005;
         System.out.println("[time "+currentTime+"] "+"[Ready --> CPU]" +"Process: " +t.ID);
         readyqueue.remove(0);
         //System.out.println("Process " + cpuqueue.get(0).ID+ " needs " + cpuqueue.get(0).burstTime +" more CPU Time.");


    }
   public void contextSwitchDone() {

        Process p = cpuqueue.get(0);
        if(p.burstTime == 0.0) {

            cpuqueue.remove(0);
            currentTime+=.00005;
            processcompleted++;
        }
       else{
        readyqueue.add(p);
        //System.out.println("[time " +currentTime+ " Context switch (swapped out process) " + p.ID + " for process " + readyqueue.get(0).ID + ")");
            System.out.println("[time "+currentTime+"] "+"[CPU --> Ready]" +"Process: " +p.ID);
        cpuqueue.remove(0);
        currentTime +=.00005;
        }
      }

   public void endofRun(){
        System.out.println("======== SIMULATION OVER =========");
        System.out.println("Processes Finished: "+ processcompleted);
       //Other Stats
        }
    }

