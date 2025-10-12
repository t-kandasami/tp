package seedu.nustudy.command;

import org.junit.jupiter.api.Test;
import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddSessionCommandTest {

    @Test
    void execute() {

    }

    @Test
    void execute_validCourse_successful() {
        Course course = new Course("CS1010");
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        courseManager.add(course);
        AddSessionCommand command = new AddSessionCommand("CS1010 3");
        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_courseNotFound_throwsException() {
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        AddSessionCommand command = new AddSessionCommand("MA1521 2");
        assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_invalidHours_throwsNumberFormatException() {
        Course course = new Course("CS2100");
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        courseManager.add(course);
        AddSessionCommand command = new AddSessionCommand("CS2100 two");
        assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void isExit_returnsFalse() {
        AddSessionCommand cmd = new AddSessionCommand("CS2113 2");
        assertFalse(cmd.isExit());
    }
}
