package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.FutureDateException;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchSessionException;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.utils.DateParser;

class EditSessionCommandTest {
    private final DateTimeFormatter dayMonthYear = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private CourseManager courseManager;
    private SessionManager sessionManager;
    private Course course;

    @BeforeEach
    void setUp() throws WrongDateFormatException {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
        course = new Course("CS2113");
        courseManager.add(course);
        sessionManager.add(course, 5, DateParser.parseDate("20/10/2025"));
        sessionManager.add(course, 3, DateParser.parseDate("21/10/2025"));
    }

    @Test
    void execute_validDateEdit_success() throws NUStudyException {
        EditSessionCommand command = new EditSessionCommand("CS2113 1 22/10/2025");
        command.execute(courseManager, sessionManager);

        Session editedSession = sessionManager.getAllSessionsForCourse(course).get(0);
        assertEquals(LocalDate.of(2025, 10, 22), editedSession.getDate(),
                "Session date should be updated to 22/10/2025");
    }

    @Test
    void execute_invalidCourseName_throwsException() {
        EditSessionCommand command = new EditSessionCommand("CS9999 1 22/10/2025");

        assertThrows(NUStudyNoSuchCourseException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception for non-existent course");
    }

    @Test
    void execute_invalidIndexNotNumber_throwsException() {
        EditSessionCommand command = new EditSessionCommand("CS2113 abc 22/10/2025");

        assertThrows(NUStudyCommandException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception when index is not a number");
    }

    @Test
    void execute_indexTooLow_throwsException() {
        EditSessionCommand command = new EditSessionCommand("CS2113 0 22/10/2025");

        assertThrows(NUStudyNoSuchSessionException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception when index is less than 1");
    }

    @Test
    void execute_indexTooHigh_throwsException() {
        EditSessionCommand command = new EditSessionCommand("CS2113 5 22/10/2025");

        assertThrows(NUStudyNoSuchSessionException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception when index exceeds session count");
    }

    @Test
    void execute_futureDate_throwsException() {
        LocalDate future = LocalDate.now().plusDays(3);
        String dateStr = future.format(dayMonthYear);
        EditSessionCommand command = new EditSessionCommand("CS2113 1 " + dateStr);

        assertThrows(FutureDateException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception when date is in the future");
    }

    @Test
    void execute_invalidDate_throwsException() {
        EditSessionCommand command = new EditSessionCommand("CS2113 1 26//2025");

        assertThrows(NUStudyCommandException.class, () -> {
            command.execute(courseManager, sessionManager);
        }, "Should throw exception when date is invalid format");
    }

    @Test
    void isExit_returnsFalse() {
        EditSessionCommand command = new EditSessionCommand("CS2113 1 26/10/2025");

        assertFalse(command.isExit());
    }
}
