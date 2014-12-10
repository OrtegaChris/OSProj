import java.util.*;

public class MainApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Vector<Process> queue1 = new Vector<Process>();
        int numProcesses = 120;

        for (int i = 1; i <= 60; i++) {
            queue1.add(new Process(i, 0,true));
        }/*
        for(int c = 61; c<= 120;c++){
            queue1.add(new Process(c,0,true));
        }
*/
        System.out.println("Starting....");
        Simulate(new RoundRobin(),queue1);


    }
    static void Simulate(Scheduler s, Vector<Process> v) {
        s.addProcesses(v);
        s.Run();

    }




}
