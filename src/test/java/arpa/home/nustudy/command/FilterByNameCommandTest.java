package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.session.SessionManager;

/**
 * Unit tests for FilterByNameCommand.
 */
public class FilterByNameCommandTest {

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

    @Test
    void filter_matchesCoursesAndPrintsHeader() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        // populate course list (assumes getCourses() returns mutable list)
        courses.getCourses().add(new Course("CS2113"));
        courses.getCourses().add(new Course("MA1511"));

        FilterByNameCommand cmd = new FilterByNameCommand("CS");
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.contains("Filtered courses matching \"CS\""), "Header missing in output: " + out);
        assertTrue(out.contains("1. CS2113"), "Filtered course entry missing: " + out);
        assertFalse(out.contains("MA1511") && !out.contains("CS2113"));
    }

    @Test
    void filter_noMatches_printsEmptyMessage() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        courses.getCourses().add(new Course("CS2113"));
        courses.getCourses().add(new Course("MA1511"));

        FilterByNameCommand cmd = new FilterByNameCommand("XYZ");
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        assertTrue(out.contains("No courses matched \"XYZ\""), "Expected empty-match message: " + out);
    }

    @Test
    void filter_preservesOriginalIndices() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();

        // add in a specific order so MA1511 has index 2
        courses.getCourses().add(new Course("CS2113"));
        courses.getCourses().add(new Course("MA1511"));
        courses.getCourses().add(new Course("CS1231"));

        FilterByNameCommand cmd = new FilterByNameCommand("MA1511");
        cmd.execute(courses, sessions);

        String out = outContent.toString();
        // should show MA1511 with original index 2
        assertTrue(out.contains("2. MA1511"), "Expected original index preserved for MA1511: " + out);
    }

    @Test
    void constructor_rejectsEmptyKeyword() {
        try {
            new FilterByNameCommand("");
        } catch (NUStudyCommandException e) {
            assertTrue(e.getMessage().toLowerCase().contains("filter keyword cannot be empty"));
            return;
        }
        // If no exception thrown, fail
        throw new AssertionError("Expected NUStudyCommandException for empty keyword");
    }

    @Test
    void constructor_acceptsValidKeyword() throws Exception {
        FilterByNameCommand cmd = new FilterByNameCommand("MA");
        assertNotNull(cmd, "Constructor should create command instance for valid keyword");
    }

    @Test
    void constructor_rejectsNullKeyword() {
        assertThrows(NUStudyCommandException.class, () -> new FilterByNameCommand(null),
                "Constructor should throw NUStudyCommandException for null keyword");
    }

    @Test
    void isExit_returnsFalse() throws Exception {
        FilterByNameCommand cmd = new FilterByNameCommand("CS");
        assertFalse(cmd.isExit(), "isExit should return false for FilterByNameCommand");
    }

    @Test
    void isExit_stillFalseAfterExecute() throws Exception {
        CourseManager courses = new CourseManager();
        SessionManager sessions = new SessionManager();
        courses.getCourses().add(new Course("CS2113"));

        FilterByNameCommand cmd = new FilterByNameCommand("CS");
        cmd.execute(courses, sessions);
        assertFalse(cmd.isExit(), "isExit should remain false after execute");
    }
}
