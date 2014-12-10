import java.util.*;

class startTimeComparator implements Comparator {

    public int compare(Object p1, Object p2) {
        int t1 = ((Process)p1).startTime;
        int t2 = ((Process)p2).startTime;

        if (t1 < t2){
            return -1;
        }
        else{
            return 1;
        }
    }

}