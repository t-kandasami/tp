package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

//@@author t-kandasami
class AddSessionCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
    }

    @Test
    void execute_validCourseSession_successfully() {
        final Course course = new Course("CS1010");
        courseManager.add(course);
        final Command cmd = new AddSessionCommand("CS1010 3");
        assertDoesNotThrow(() -> cmd.execute(courseManager, sessionManager));

        // Verify session added with correct hours
        assertTrue(sessionManager.getAllLoggedHoursForCourse(course).contains(3.0),
                "Session with 3.0 hours should be recorded for course CS1010");
    }

    @Test
    void execute_courseNotFound_throwsException() {
        final Command cmd = new AddSessionCommand("MA1512 2");
        NUStudyException ex = assertThrows(NUStudyNoSuchCourseException.class,
                () -> cmd.execute(courseManager, sessionManager));
        assertEquals("Course with the name MA1512 does not exist", ex.getMessage());
    }

    @Test
    void execute_invalidHours_throwsNumberFormatException() {
        final Course course = new Course("CS2100");
        courseManager.add(course);
        final AddSessionCommand command = new AddSessionCommand("CS2100 two");

        NUStudyException ex = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Hours must be a double", ex.getMessage());
    }

    @Test
    void isExit_returnsFalse() {
        final AddSessionCommand cmd = new AddSessionCommand("CS2113 2");

        assertFalse(cmd.isExit());
    }
}
//@author
