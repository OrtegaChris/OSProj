import java.util.*;

public class MainApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Vector<Process> queue1 = new Vector<Process>();
        int numProcesses = 120;

        for (int i = 1; i <= numProcesses; i++) {
            queue1.add(new Process(i, 0));
        }


        System.out.println("Starting....");
        Simulate(new RoundRobin(),queue1);
        System.out.println("Simulation Ended");

    }
    static void Simulate(Scheduler s, Vector<Process> v) {
        s.addProcesses(v);
        s.Run();

    }




}
