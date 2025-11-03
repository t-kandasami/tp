package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

//@@author t-kandasami
class DeleteCourseCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;
    private InputStream originalSystemIn;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
        originalSystemIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        UserInterface.reinitialiseScanner();
    }

    @Test
    void execute_emptyInput_throwsException() {
        final Command cmd = new DeleteCourseCommand("");
        final NUStudyException ex = assertThrows(NUStudyException.class,
                () -> cmd.execute(courseManager, sessionManager));
        assertEquals("Please enter a course name to delete", ex.getMessage());
    }

    @Test
    void execute_courseNotFound_throwsNoSuchCourseException() {
        final Command command = new DeleteCourseCommand("CS2040");
        final NUStudyNoSuchCourseException ex = assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));

        assertEquals("Course does not exist", ex.getMessage());
    }

    @Test
    void isExit_returnsFalse() {
        final Command cmd = new DeleteCourseCommand("");
        assertFalse(cmd.isExit(), "");
    }

    @Test
    void execute_nullCourseManager_throwsAssertionError() {
        final Command cmd = new DeleteCourseCommand("CS2113");
        assertThrows(AssertionError.class, () -> cmd.execute(null, sessionManager));
    }

    @Test
    void execute_userCancelsDeletion_courseNotDeleted() throws NUStudyException {
        final Course course = new Course("CS1010");
        courseManager.add(course);
        provideInput("n\n"); // simulate user declining deletion
        final Command cmd = new DeleteCourseCommand("CS1010");
        NUStudyException ex = assertThrows(NUStudyException.class,
                () -> cmd.execute(courseManager, sessionManager));
        assertEquals("CS1010 has not been deleted as per your request", ex.getMessage(), "");
        assertNotNull(courseManager.findCourse("CS1010"), ""); // course still exists
    }

    @Test
    void execute_successfulDeletion_courseDeleted() throws NUStudyException {
        final Course course = new Course("CS2030");
        courseManager.add(course);
        provideInput("y\n"); // simulate user confirming deletion
        final Command cmd = new DeleteCourseCommand("CS2030");
        cmd.execute(courseManager, sessionManager);
        assertNull(courseManager.findCourse("CS2030"), ""); // course deleted
    }

    @Test
    void execute_courseWithSessions_sessionsAlsoDeleted() throws NUStudyException {
        final Course course = new Course("CS1010");
        courseManager.add(course);
        final Command addSessionCommand = new AddSessionCommand("CS1010 3");
        addSessionCommand.execute(courseManager, sessionManager);
        provideInput("y\n"); // user confirms deletion
        final Command deleteCourseCommand = new DeleteCourseCommand("CS1010");
        deleteCourseCommand.execute(courseManager, sessionManager);
        assertNull(courseManager.findCourse("CS1010"), ""); // course deleted
        assertTrue(sessionManager.getAllSessionsForCourse(course).isEmpty(), ""); // sessions removed
    }
}
//@@author
