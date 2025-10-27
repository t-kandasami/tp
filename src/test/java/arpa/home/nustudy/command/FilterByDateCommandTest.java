package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
 * Unit tests for FilterByDateCommand.
 * <p>
 * Tests use an injected sessionSource consisting of simple POJO sessions that expose getCourse() and getDate().
 */
public class FilterByDateCommandTest {

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

    // simple test POJO representing a session-like object used by reflection in the command
    private static class TestSession {
        private final Course course;
        private final LocalDate date;

        TestSession(Course course, LocalDate date) {
            this.course = course;
            this.date = date;
        }

        public Course getCourse() {
            return course;
        }

        public LocalDate getDate() {
            return date;
        }
    }

    @Test
    void constructor_rejectsEmptyDateArgument() {
        assertThrows(NUStudyCommandException.class, () -> new FilterByDateCommand(""),
                "Constructor should reject empty date argument");
    }

    @Test
    void execute_matchesCoursesAndPrintsHeader_andPreservesIndices() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c1 = new Course("CS2113");
        Course c2 = new Course("MA1511");
        courses.getCourses().add(c1);
        courses.getCourses().add(c2);

        ArrayList<Object> sessionList = new ArrayList<>();
        // create a session for MA1511 on 23 May 2025
        sessionList.add(new TestSession(c2, LocalDate.of(2025, 5, 23)));

        FilterByDateCommand cmd = new FilterByDateCommand("23/05/2025", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.contains("Courses with sessions on"), "Header should be printed: " + out);
        assertTrue(out.contains("MA1511"), "Matching course should be printed: " + out);
        assertTrue(out.contains("2."), "Original index (2) should be preserved in output: " + out);
    }

    @Test
    void execute_noMatches_printsEmptyMessage() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        Course c1 = new Course("CS2113");
        courses.getCourses().add(c1);

        ArrayList<Object> sessionList = new ArrayList<>();
        // session on different date
        sessionList.add(new TestSession(c1, LocalDate.of(2025, 1, 1)));

        FilterByDateCommand cmd = new FilterByDateCommand("23/05/2025", sessionList);
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.contains("No sessions found on"), "Should print empty message when no matches: " + out);
    }

    @Test
    void execute_invalidDate_printsInvalidDateMessage() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        FilterByDateCommand cmd = new FilterByDateCommand("not-a-date", new ArrayList<>());
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.toLowerCase().contains("invalid date format"), "Should print invalid date message: " + out);
    }

    @Test
    void isExit_returnsFalse() throws Exception {
        FilterByDateCommand cmd = new FilterByDateCommand("23/05/2025", new ArrayList<>());
        assertFalse(cmd.isExit(), "isExit should return false for FilterByDateCommand");
    }
}

