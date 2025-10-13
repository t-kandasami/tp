package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;

class ListCourseCommandTest {
    @Test
    void execute() {
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final ListCourseCommand cmd = new ListCourseCommand();

        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
    }

    @Test
    void isExit() {
        final ListCourseCommand cmd = new ListCourseCommand();

        assertFalse(cmd.isExit());
    }
}
