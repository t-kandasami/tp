package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;

class DeleteSessionByIndexCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return outputStream.toString().trim();
    }

    @Test
    void constructor_createsCommandSuccessfully() {
        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "1");
        assertFalse(command.isExit());
    }

    @Test
    void isExit_returnsFalse() {
        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "1");
        assertFalse(command.isExit());
    }

    @Test
    void execute_nonExistentCourse_throwsException() {
        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "1");

        NUStudyNoSuchCourseException exception = assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Course with the name CS2100 does not exist", exception.getMessage());
    }

    @Test
    void execute_invalidIndexFormat_throwsException() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 5, LocalDate.now());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "invalidIndexFormat");
        NUStudyException exception = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Please input a valid index to edit", exception.getMessage());
    }

    @Test
    void execute_indexZero_throwsException() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 5, LocalDate.now());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "0");
        NUStudyException exception = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Invalid index entered.", exception.getMessage());
        assertTrue(getOutput().contains("You have 1 session(s) for CS2100"));
    }

    @Test
    void execute_negativeIndex_throwsException() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 5, LocalDate.now());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "-1");
        NUStudyException exception = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Invalid index entered.", exception.getMessage());
        assertTrue(getOutput().contains("You have 1 session(s) for CS2100"));
    }

    @Test
    void execute_indexOutOfBounds_throwsException() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 5, LocalDate.now());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "3");
        NUStudyException exception = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Invalid index entered.", exception.getMessage());
        assertTrue(getOutput().contains("You have 1 session(s) for CS2100"));
    }

    @Test
    void execute_validIndex_deletesSession() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        LocalDate testDate = LocalDate.of(2025, 10, 26);
        sessionManager.add(course, 5, testDate);

        assertEquals(1, sessionManager.getSessionCount());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "1");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getSessionCount());

        Session testSession = new Session(course, 5, testDate);
        String expectedDateFormat = testSession.getDateString();
        assertTrue(getOutput().contains("Session 1 was successfully deleted for CS2100"));
        assertTrue(getOutput().contains("Deleted session: CS2100 - 5 hours on " + expectedDateFormat));
    }

    @Test
    void execute_multipleSessions_deletesCorrectSession() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 5, LocalDate.of(2025, 10, 26));
        sessionManager.add(course, 6, LocalDate.of(2025, 10, 27));
        sessionManager.add(course, 7, LocalDate.of(2025, 10, 28));

        assertEquals(3, sessionManager.getSessionCount());

        final DeleteSessionByIndexCommand command = new DeleteSessionByIndexCommand("CS2100", "2");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(5, sessionManager.getAllSessionsForCourse(course).get(0).getLoggedHours());
        assertEquals(7, sessionManager.getAllSessionsForCourse(course).get(1).getLoggedHours());

        assertTrue(getOutput().contains("Session 2 was successfully deleted for CS2100"));
        assertTrue(getOutput().contains("Deleted session: CS2100 - 6 hours on 27 Oct 2025"));
    }
}
