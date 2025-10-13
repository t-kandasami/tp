package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    @Test
    void execute() {
        final ExitCommand cmd = new ExitCommand();
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
        final NUStudyException ex = new NUStudyException("Test exception");
        assertEquals("Test exception", ex.getMessage());
    }

    @Test
    void isExit() {
        final ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }
}
