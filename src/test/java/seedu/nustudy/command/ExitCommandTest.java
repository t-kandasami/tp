package seedu.nustudy.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;

class ExitCommandTest {

    @Test
    void execute() {
        ExitCommand cmd = new ExitCommand();
        CourseManager manager = new CourseManager();
        assertDoesNotThrow(() -> cmd.execute(manager));

        NUStudyException ex = new NUStudyException("Test exception");
        assertEquals("Test exception", ex.getMessage());
    }

    @Test
    void isExit() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }
}