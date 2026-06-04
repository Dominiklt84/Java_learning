package lt.viko.eif.dalencinovic.service;

public class AttendanceService {

    private int missedClasses=0;
    private static final int ABSENCE_LIMIT = 3;

    public void markMissed(){
        missedClasses++;
    }

    public boolean isAbsent(){
        return missedClasses>=ABSENCE_LIMIT;
    }
}
