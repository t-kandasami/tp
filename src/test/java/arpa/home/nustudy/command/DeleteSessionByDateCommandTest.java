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
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.session.SessionManager;

class DeleteSessionByDateCommandTest {
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
        return outputStream.toString();
    }

    @Test
    void isExit_returnFalse() {
        final DeleteSessionByDateCommand command = new DeleteSessionByDateCommand("2025-10-26");
        assertFalse(command.isExit());
    }

    @Test
    void execute_invalidDateFormat_throwsException() {
        final DeleteSessionByDateCommand command = new DeleteSessionByDateCommand("invalidDateFormat");
        assertThrows(WrongDateFormatException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_validDateNoSessions_printsMessage() {
        final DeleteSessionByDateCommand command = new DeleteSessionByDateCommand("2025-10-26");

        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertTrue(getOutput().contains("No sessions found to delete on 26 Oct 2025"));
    }

    @Test
    void execute_validDateWithSessions_deletesAndPrints() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        LocalDate testDate = LocalDate.of(2025, 10, 26);
        sessionManager.add(course, 5, testDate);
        sessionManager.add(course, 3, testDate);

        assertEquals(2, sessionManager.getSessionCount());

        final DeleteSessionByDateCommand command = new DeleteSessionByDateCommand("2025-10-26");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getSessionCount());
        assertTrue(getOutput().contains("Successfully deleted 2 session(s) on 26 Oct 2025"));
    }
}
