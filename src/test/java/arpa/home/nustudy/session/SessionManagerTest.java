package arpa.home.nustudy.session;

import arpa.home.nustudy.course.Course;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

class SessionManagerTest {
    private SessionManager sessionManager;
    private Course c1;
    private Course c2;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager();
        c1 = new Course("CS2040C");
        c2 = new Course("CS1010");
    }
    @Test
    void testAdd() {
        sessionManager.add(c1, 27, LocalDate.parse("2025-10-25"));
        ArrayList<Double> hours1 = sessionManager.getAllLoggedHoursForCourse(c1);
        ArrayList<Double> hours2 = sessionManager.getAllLoggedHoursForCourse(c2);
        Assertions.assertEquals(1, hours1.size());
        Assertions.assertEquals(27, hours1.get(0));
        sessionManager.add(c1, 60, LocalDate.parse("2025-10-25"));
        sessionManager.add(c2, 70, LocalDate.parse("2025-10-25"));
        hours1 = sessionManager.getAllLoggedHoursForCourse(c1);
        hours2 = sessionManager.getAllLoggedHoursForCourse(c2);
        Assertions.assertEquals(2, hours1.size());
        Assertions.assertEquals(70, hours2.get(0));
    }

    @Test
    void testGetAllLoggedHoursForCourse() {
        sessionManager.add(c1, 42, LocalDate.parse("2025-10-25"));
        sessionManager.add(c1, 69, LocalDate.parse("2025-10-25"));
        ArrayList<Double> hours = sessionManager.getAllLoggedHoursForCourse(c1);
        Assertions.assertEquals(2, hours.size());
        Assertions.assertEquals(42, hours.get(0));
        Assertions.assertEquals(69, hours.get(1));
    }

    @Test
    void testClearAllSessions() {
        ArrayList<Double> hours = sessionManager.getAllLoggedHoursForCourse(c1);
        Assertions.assertTrue(hours.isEmpty());

        sessionManager.add(c1, 59, LocalDate.parse("2025-10-25"));
        sessionManager.add(c1, 34, LocalDate.parse("2025-10-25"));
        sessionManager.add(c2, 29, LocalDate.parse("2025-10-25"));
        sessionManager.add(c2, 60, LocalDate.parse("2025-10-25"));
        sessionManager.clearAllSessions();
        Assertions.assertTrue(sessionManager.getAllLoggedHoursForCourse(c1).isEmpty());
        Assertions.assertTrue(sessionManager.getAllLoggedHoursForCourse(c2).isEmpty());
    }

    @Test
    void testRemoveAllSessionsForCourse() {
        sessionManager.add(c1, 98, LocalDate.parse("2025-10-25"));
        sessionManager.add(c1, 13, LocalDate.parse("2025-10-25"));
        sessionManager.add(c2, 23, LocalDate.parse("2025-10-25"));
        sessionManager.removeAllSessionsForCourse(c1);
        Assertions.assertTrue(sessionManager.getAllLoggedHoursForCourse(c1).isEmpty());
        Assertions.assertEquals(1, sessionManager.getAllLoggedHoursForCourse(c2).size());
    }

    @Test
    void iterator() {
        sessionManager.add(c1, 99, LocalDate.parse("2025-10-25"));
        sessionManager.add(c1, 63, LocalDate.parse("2025-10-25"));
        int count = 0;
        for (Session session : sessionManager) {
            count += 1;
        }
        Assertions.assertEquals(2, count);
    }
}
