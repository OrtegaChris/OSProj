import java.util.*;

public class RoundRobin extends Scheduler {
    public double timeSlice = .01;
    public double iointerrupt = .02;
    double simtime = 100;
    double contxt = .00005;

    int  processcompleted = 0;


    @Override
    public void Run() {

        checkNewProcesses();
        while (!readyqueue.isEmpty() && currentTime < simtime){
            checkNewProcesses();
            contextSwitch();
            //===========================================
            //This is for cpu bound processes
            //===========================================
            if (!cpuqueue.get(0).iobound)
            {
                if(cpuqueue.get(0).burstTime - timeSlice < 0.0){
                    currentTime = cpuqueue.get(0).burstTime + currentTime;
                    cpuqueue.get(0).burstTime = 0;
                    System.out.printf("[time %.5f s][Finished Process] Process " + cpuqueue.get(0).ID,currentTime);
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
                //===========================================
                //This is for io bound processes
                //===========================================

                if(cpuqueue.get(0).burstTime - timeSlice < 0.0){
                    System.out.printf("[time %.5f s][IO-Interrupt] Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime += iointerrupt;
                    System.out.printf("[time %.5f s][IO-Complete]Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime = cpuqueue.get(0).burstTime + currentTime;
                    cpuqueue.get(0).burstTime = 0;
                    System.out.printf("[time %.5f s][Finished Process] Process " + cpuqueue.get(0).ID+"\n",currentTime);
                    contextSwitchDone();
                }
                else {
                    System.out.printf("[time %.5f s][IO-Interrupt]Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime += iointerrupt;
                    System.out.printf("[time %.5f s][IO-Complete]Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - timeSlice;
                    currentTime = currentTime + timeSlice;
                    System.out.println("debug burst time remaining: " + cpuqueue.get(0).burstTime);
                    contextSwitchDone();
                }


            }



        }
        endofRun();
    }
    //===================================================================
    //Check to see if any processes on inactive can move to readyqueue
    //===================================================================

    public void checkNewProcesses() {
        while (!inactive.isEmpty() && inactive.get(0).startTime <= currentTime) {
            if(inactive.get(0).burstTime > 0) {
                readyqueue.add(inactive.get(0));
                System.out.printf("[time %.5f s][New Process]" + inactive.get(0).ID + " created.\n",inactive.get(0).startTime);
                inactive.remove(0);
            }


        }
    }


    //===========================================
    //This loads process onto cpu.
    //===========================================
    public void contextSwitch(){
        Process t = readyqueue.get(0);
        cpuqueue.add(t);
        currentTime += .00005;
        System.out.printf("[time %.5f s][Ready --> CPU] Process " +t.ID+"\n",currentTime);
        readyqueue.remove(0);



    }
    //===========================================================================================================
    //This removes from cpu and either adds it back to readyqueue or removes completly if processes if finished
    //===========================================================================================================
    public void contextSwitchDone() {

        Process p = cpuqueue.get(0);
        if(p.burstTime == 0.0) {

            cpuqueue.remove(0);
            currentTime+=contxt;
            processcompleted++;
        }
        else{
            readyqueue.add(p);
            System.out.printf("[time %.5f s][CPU --> Ready]" +"Process: " +p.ID+"\n",currentTime);
            cpuqueue.remove(0);
            currentTime +=contxt;
        }
    }

    public void endofRun(){
        System.out.println("======== SIMULATION OVER =========");
        System.out.println("Processes Finished: "+ processcompleted);
        //Other Stats
    }
}

