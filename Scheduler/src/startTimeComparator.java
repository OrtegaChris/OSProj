import java.util.*;

class startTimeComparator implements Comparator {

    public int compare(Object p1, Object p2) {
        double t1 = ((Process)p1).startTime;
        double t2 = ((Process)p2).startTime;

        if (t1 < t2){
            return -1;
        }
        else{
            return 1;
        }
    }

}