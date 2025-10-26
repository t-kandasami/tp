package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

class ResetCourseHoursCommandTest {
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
    void isExit_returnsFalse() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("");
        assertFalse(command.isExit());
    }

    @Test
    void execute_emptyInput_doesNotThrow() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_whiteSpaceInput_doesNotThrow() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("   ");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_nonExistentCourse_throwsException() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("NonExistentCourse");
        assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_courseWithNoSessions_executesSuccessfully() {
        Course course = new Course("CS2100");
        courseManager.add(course);

        assertEquals(0, sessionManager.getSessionCount());

        provideInput("y\nRESET\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("CS2100");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getSessionCount());
    }

    @Test
    void constructor_trimsWhiteSpace() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 15, LocalDate.now());

        assertEquals(1, sessionManager.getSessionCount());

        provideInput("y\nRESET\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("   CS2100   ");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getSessionCount());
    }

    @Test
    void execute_validCourseFirstConfirmationNo_cancelsReset() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 15, LocalDate.now());

        assertEquals(1, sessionManager.getSessionCount());

        provideInput("n\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("CS2100");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(1, sessionManager.getSessionCount());
    }

    @Test
    void execute_validCourseSecondConfirmationMismatch_cancelsReset() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 15, LocalDate.now());

        assertEquals(1, sessionManager.getSessionCount());

        provideInput("y\nNOTSAFEWORD\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("CS2100");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(1, sessionManager.getSessionCount());
    }

    @Test
    void execute_validCourseBothConfirmationYes_resetsSessions() {
        Course course = new Course("CS2100");
        courseManager.add(course);
        sessionManager.add(course, 15, LocalDate.now());
        sessionManager.add(course, 10, LocalDate.now().minusDays(1));

        assertEquals(2, sessionManager.getSessionCount());

        provideInput("y\nRESET\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("CS2100");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getAllSessionsForCourse(course).size());
    }

    @Test
    void execute_resetAllFirstConfirmationNo_cancelsReset() {
        Course course1 = new Course("CS2100");
        Course course2 = new Course("CS1010");
        courseManager.add(course1);
        courseManager.add(course2);
        sessionManager.add(course1, 5, LocalDate.now());
        sessionManager.add(course2, 10, LocalDate.now().minusDays(1));

        assertEquals(2, sessionManager.getSessionCount());

        provideInput("n\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("all");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(2, sessionManager.getSessionCount());
    }

    @Test
    void execute_resetAllSecondConfirmationWrong_cancelsReset() {
        Course course1 = new Course("CS2100");
        Course course2 = new Course("CS1010");
        courseManager.add(course1);
        courseManager.add(course2);
        sessionManager.add(course1, 5, LocalDate.now());
        sessionManager.add(course2, 10, LocalDate.now().minusDays(1));

        assertEquals(2, sessionManager.getSessionCount());

        provideInput("y\nNOTSAFEWORD\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("all");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(2, sessionManager.getSessionCount());
    }

    @Test
    void execute_resetAllSecondConfirmationYes_resetsAllSessions() {
        Course course1 = new Course("CS2100");
        Course course2 = new Course("CS1010");
        courseManager.add(course1);
        courseManager.add(course2);
        sessionManager.add(course1, 5, LocalDate.now());
        sessionManager.add(course2, 10, LocalDate.now().minusDays(1));

        assertEquals(2, sessionManager.getSessionCount());

        provideInput("y\nRESET ALL\n");
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("all");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));

        assertEquals(0, sessionManager.getSessionCount());
    }

}
