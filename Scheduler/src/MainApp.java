import java.util.*;

public class MainApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Vector<Process> queue1 = new Vector<Process>();
        int numProcesses = 25;

        for (int i = 1; i <= 23; i++) {
            queue1.add(new Process(i, 0));
        }
        queue1.add(new Process(24,100));
        queue1.add(new Process(25,200));


        Simulate(new RoundRobin(),queue1);
        System.out.println("Simulation Ended");

    }
    static void Simulate(Scheduler s, Vector<Process> v) {
        s.addProcesses(v);
        s.Run();
        System.out.println("Starting....");
    }




}
