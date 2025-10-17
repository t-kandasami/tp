package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

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

    @Test
    void execute_nullArguments_triggersAssertionWhenEnabled() {
        final ListCourseCommand cmd = new ListCourseCommand();

        // Robust detection of whether assertions are enabled in this JVM.
        boolean assertionsEnabled = false;
        try {
            assert false;
        } catch (final AssertionError e) {
            assertionsEnabled = true;
        }
        Assumptions.assumeTrue(assertionsEnabled, "Assertions are not enabled; skipping assertion-related test");

        final SessionManager sessionManager = new SessionManager();
        final CourseManager courseManager = new CourseManager();

        assertThrows(AssertionError.class, () -> cmd.execute(null, sessionManager));
        assertThrows(AssertionError.class, () -> cmd.execute(courseManager, null));
    }
}
