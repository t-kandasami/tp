package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;

//@@author Wrooi
class ExitCommandTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_printsGoodbyeMessage() {
        final ExitCommand cmd = new ExitCommand();
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        assertDoesNotThrow(() -> cmd.execute(manager, sessionManager));
        String out = outContent.toString();
        assertTrue(out.contains("Exiting App. Goodbye!"), "Exit message should be printed");
    }

    @Test
    void isExit() {
        final ExitCommand cmd = new ExitCommand();

        assertTrue(cmd.isExit());
    }

    @Test
    void execute_nullArguments_triggersAssertionWhenEnabled() {
        final ExitCommand cmd = new ExitCommand();

        // Detect whether assertions are enabled in this JVM.
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
//@@author
