package lt.viko.eif.dalencinovic.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    @Test
    void studentShouldBecomeAbsentAfterThreeMisses(){
        AttendanceService service= new AttendanceService();

        service.markMissed();
        service.markMissed();
        service.markMissed();

        assertTrue(service.isAbsent());
    }

}