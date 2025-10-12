package seedu.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.session.SessionManager;

class ExitCommandTest {

    @Test
    void execute() {
        ExitCommand cmd = new ExitCommand();
        CourseManager manager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
        NUStudyException ex = new NUStudyException("Test exception");
        assertEquals("Test exception", ex.getMessage());
    }

    @Test
    void isExit() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }
}
