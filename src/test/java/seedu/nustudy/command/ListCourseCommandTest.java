package seedu.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.nustudy.course.CourseManager;
import seedu.nustudy.session.SessionManager;

class ListCourseCommandTest {

    @Test
    void execute() {
        CourseManager manager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        ListCourseCommand cmd = new ListCourseCommand("");
        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
    }

    @Test
    void isExit() {
        ListCourseCommand cmd = new ListCourseCommand("");
        assertFalse(cmd.isExit());
    }
}
