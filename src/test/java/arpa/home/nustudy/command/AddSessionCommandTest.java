package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddSessionCommandTest {

    @Test
    void execute() {

    }

    @Test
    void execute_validCourse_successful() {
        final Course course = new Course("CS1010");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        courseManager.add(course);
        final AddSessionCommand command = new AddSessionCommand("CS1010 3");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_courseNotFound_throwsException() {
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final AddSessionCommand command = new AddSessionCommand("MA1521 2");
        assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_invalidHours_throwsNumberFormatException() {
        final Course course = new Course("CS2100");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        courseManager.add(course);
        final AddSessionCommand command = new AddSessionCommand("CS2100 two");
        assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void isExit_returnsFalse() {
        final AddSessionCommand cmd = new AddSessionCommand("CS2113 2");
        assertFalse(cmd.isExit());
    }
}
