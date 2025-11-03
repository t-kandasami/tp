package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.session.SessionManager;

/**
 * Unit tests for FilterByNameAndDateCommand.
 * <p>
 * Tests inject a simple TestSession POJO list so tests don't rely on concrete Session API.
 */
public class FilterByNameAndDateCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    // lightweight test POJO representing a session-like object used by reflection in the command
    private static class TestSession {
        private final Course course;
        private final LocalDate date;
        private final int hours;

        TestSession(Course course, LocalDate date, int hours) {
            this.course = course;
            this.date = date;
            this.hours = hours;
        }

        public Course getCourse() {
            return course;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getLoggedHours() {
            return hours;
        }
    }

    @Test
    void constructor_rejectsEmptyCourseOrDate() {
        assertThrows(NUStudyCommandException.class, () -> new FilterByNameAndDateCommand("", "23/05/2025"));
        assertThrows(NUStudyCommandException.class, () -> new FilterByNameAndDateCommand("MA1511", ""));
    }

    // constructor accepts valid args
    @Test
    void constructor_acceptsValidArgs() throws Exception {
        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "23/05/2025");
        assertNotNull(cmd);
    }

    // constructor rejects nulls
    @Test
    void constructor_rejectsNulls() {
        assertThrows(NUStudyCommandException.class, () -> new FilterByNameAndDateCommand(null, "23/05/2025"));
        assertThrows(NUStudyCommandException.class, () -> new FilterByNameAndDateCommand("MA1511", null));
    }

    @Test
    void execute_exactCourseMatch_printsSessionsAndPreservesIndices() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c1 = new Course("CS2113");
        Course c2 = new Course("MA1511");
        Course c2x = new Course("MA1511X");
        courses.getCourses().add(c1);
        courses.getCourses().add(c2);
        courses.getCourses().add(c2x);

        // Prepare session list with two sessions for MA1511; one on target date, one different date
        ArrayList<Object> sessionList = new ArrayList<>();
        sessionList.add(new TestSession(c2, LocalDate.of(2025, 5, 23), 3)); // course MA1511 index 1 for that course
        sessionList.add(new TestSession(c2, LocalDate.of(2025, 5, 23), 1)); // course MA1511 index 2 for that course
        sessionList.add(new TestSession(c2x, LocalDate.of(2025, 5, 23), 2)); // different course
        sessionList.add(new TestSession(c1, LocalDate.of(2025, 5, 23), 4)); // different course

        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "23/05/2025", sessionList);
        assertNotNull(cmd);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        // header should include Sessions for MA1511 on ...
        assertTrue(out.toLowerCase().contains("sessions for"), "Header should be printed: " + out);
        // should include both matching sessions with original per-course indices "1." and "2."
        assertTrue(out.contains("1."), "Should print original session index 1 for MA1511: " + out);
        assertTrue(out.contains("2."), "Should print original session index 2 for MA1511: " + out);
        // should not print MA1511X since name must be exact for combined filter
        assertFalse(out.toLowerCase().contains("ma1511x"));
    }

    // case-insensitive exact match test
    @Test
    void execute_caseInsensitiveExactMatch() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c2 = new Course("Ma1511"); // mixed case
        courses.getCourses().add(c2);

        ArrayList<Object> sessionList = new ArrayList<>();
        sessionList.add(new TestSession(c2, LocalDate.of(2025, 5, 23), 2));

        // use lower-case query; should match exactly ignoring case
        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("ma1511", "23/05/2025", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.toLowerCase().contains("sessions for"),
                "Header should be printed for case-insensitive match: " + out);
        assertTrue(out.toLowerCase().contains("ma1511"), "Matched course should be printed: " + out);
    }

    // ensure per-course session indexing works when sessions are interleaved across courses
    @Test
    void execute_perCourseIndexingWithInterleavedSessions() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course cA = new Course("MA1511");
        Course cB = new Course("CS2113");
        courses.getCourses().add(cA);
        courses.getCourses().add(cB);

        ArrayList<Object> sessionList = new ArrayList<>();
        // interleaved sessions: two sessions for MA1511 separated by a CS2113 session
        sessionList.add(new TestSession(cA, LocalDate.of(2025, 5, 23), 3)); // MA idx 1
        sessionList.add(new TestSession(cB, LocalDate.of(2025, 5, 23), 2)); // other course
        sessionList.add(new TestSession(cA, LocalDate.of(2025, 5, 23), 1)); // MA idx 2

        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "23/05/2025", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        // should show both per-course indices 1 and 2 for MA1511
        assertTrue(out.contains("1."), "Should print per-course index 1 for MA1511: " + out);
        assertTrue(out.contains("2."), "Should print per-course index 2 for MA1511: " + out);
    }

    @Test
    void execute_noMatch_printsNoSessionsMessage() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c1 = new Course("CS2113");
        courses.getCourses().add(c1);

        ArrayList<Object> sessionList = new ArrayList<>();
        sessionList.add(new TestSession(c1, LocalDate.of(2025, 1, 1), 2));

        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("CS2113", "23/05/2025", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.toLowerCase().contains("no sessions found"),
                "Should print empty message when no matches: " + out);
    }

    @Test
    void execute_invalidDate_printsInvalidDateMessage() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        ArrayList<Object> sessionList = new ArrayList<>();
        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "not-a-date", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.toLowerCase().contains("invalid date or date format"),
                "Should print invalid date message: " + out);
    }

    @Test
    void isExit_returnsFalse() throws Exception {
        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "23/05/2025", new ArrayList<>());
        assertFalse(cmd.isExit(), "isExit should return false for FilterByNameAndDateCommand");
    }

    // isExit still false after execute
    @Test
    void isExit_stillFalseAfterExecute() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c = new Course("MA1511");
        courses.getCourses().add(c);

        ArrayList<Object> sessionList = new ArrayList<>();
        sessionList.add(new TestSession(c, LocalDate.of(2025, 5, 23), 2));

        FilterByNameAndDateCommand cmd = new FilterByNameAndDateCommand("MA1511", "23/05/2025", sessionList);
        cmd.execute(courses, sessions);
        assertFalse(cmd.isExit(), "isExit should remain false after execute");
    }
}
