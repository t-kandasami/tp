package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ListCourseCommandTest {

    @Test
    void execute() {
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final ListCourseCommand cmd = new ListCourseCommand("");
        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
    }

    @Test
    void isExit() {
        final ListCourseCommand cmd = new ListCourseCommand("");
        assertFalse(cmd.isExit());
    }
}
