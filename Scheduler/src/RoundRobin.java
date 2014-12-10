import java.util.*;

public class RoundRobin extends Scheduler {
    public double quantum = .01;
    public double iointerrupt = .02;
    double simtime = 1000;
    double contxt = .00005;

    //int  processid = 1;
    int processcompleted = 0;


    public void Run() {

        checkNewProcesses();
        while (!readyqueue.isEmpty() && currentTime < simtime || !inactive.isEmpty()){
            checkNewProcesses();
            contextSwitch();
            //===========================================
            //This is for cpu bound processes
            //===========================================
            if(cpuqueue.isEmpty()){
                System.out.printf("[time %.5fs][IDLE] Waiting. . .\n",currentTime);
                currentTime = inactive.get(0).startTime;
                System.out.printf("[time %.5fs][IDLE] Waiting. . . DONE! \n",currentTime);
                contextSwitch();

            }
           else if (!cpuqueue.get(0).iobound)
            {
                if(cpuqueue.get(0).burstTime - quantum < 0.0){
                    currentTime = cpuqueue.get(0).burstTime + currentTime;
                    cpuqueue.get(0).burstTime = 0;
                    System.out.printf("[time %.5fs][Finished Process] Process " + cpuqueue.get(0).ID+"\n",currentTime);
                    contextSwitchDone();
                }
                else{
                    cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - quantum;
                    currentTime = currentTime + quantum;
                    System.out.printf("[time %.5fs][Quantum Expired] Process "+cpuqueue.get(0).ID +"- CPU time remaining: %.6fs\n",currentTime,cpuqueue.get(0).burstTime);
                    contextSwitchDone();
                }
            }
            else {
                //===========================================
                //This is for io bound processes
                //===========================================

                if(cpuqueue.get(0).burstTime - quantum < 0.0){
                    System.out.printf("[time %.5fs][IO-Interrupt] Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime += iointerrupt;
                    System.out.printf("[time %.5fs][IO-Complete] Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime = cpuqueue.get(0).burstTime + currentTime;
                    cpuqueue.get(0).burstTime = 0;
                    System.out.printf("[time %.5fs][Finished Process] Process " + cpuqueue.get(0).ID+"\n",currentTime);
                    contextSwitchDone();
                }
                else {
                    System.out.printf("[time %.5fs][IO-Interrupt] Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    currentTime += iointerrupt;
                    System.out.printf("[time %.5fs][IO-Complete] Process "+ cpuqueue.get(0).ID+"\n",currentTime);
                    cpuqueue.get(0).burstTime = cpuqueue.get(0).burstTime - quantum;
                    currentTime = currentTime + quantum;
                    System.out.printf("[time %.5fs][Quantum Expired] Process "+cpuqueue.get(0).ID +"- CPU time remaining: %.6fs\n",currentTime,cpuqueue.get(0).burstTime);
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

                System.out.println("[time "+inactive.get(0).startTime+"s][New Process] " + inactive.get(0).ID + " created.");
                inactive.remove(0);
            }


        }
    }


    //===========================================
    //This loads process onto cpu.
    //===========================================
    public void contextSwitch(){
        if(readyqueue.isEmpty()){
             currentTime += contxt;
        }
        else {
            Process t = readyqueue.get(0);
            cpuqueue.add(t);
            currentTime += contxt;
            System.out.printf("[time %.5fs][Ready --> CPU] Process " + t.ID + "\n", currentTime);
            readyqueue.remove(0);

        }

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
            System.out.printf("[time %.5fs][CPU --> Ready]" +" Process: " +p.ID+"\n",currentTime);
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

