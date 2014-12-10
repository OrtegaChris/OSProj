import java.util.Random;
import java.util.*;
public class MainApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        int processid = 1;
        Vector<Process> queue1 = new Vector<Process>();
        int numProcesses = 2000;
        queue1.add(new Process(1,0,false));
        for (int i = 2; i <=1000 ; i++) {
            Random r = new Random();
            double a = r.nextDouble()*1000;
           // System.out.println(a);
            queue1.add(new Process(i,a,true));
        }

        for(int c = 1001; c<= numProcesses;c++){
            Random r = new Random();
            double a = r.nextDouble()*1000;
            //System.out.println(a);
            queue1.add(new Process (c,a,false));
        }





        System.out.println("Starting....");
        Simulate(new RoundRobin(),queue1);


    }
    static void Simulate(Scheduler s, Vector<Process> v) {
        s.addProcesses(v);
        s.Run();

    }




}
