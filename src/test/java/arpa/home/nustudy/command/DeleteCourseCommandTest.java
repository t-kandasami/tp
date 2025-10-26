package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

//@@author t-kandasami
class DeleteCourseCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
        boolean confirmation = true;
    }

    @Test
    void execute_emptyInput_throwsException() {
        final Command cmd = new DeleteCourseCommand("");
        final NUStudyException ex = assertThrows(NUStudyException.class,
                () -> cmd.execute(courseManager, sessionManager));
        assertEquals("Please enter a course name to delete", ex.getMessage());
    }

    @Test
    void execute_courseNotFound_throwsNoSuchCourseException() {
        final Command command = new DeleteCourseCommand("CS2040");
        final NUStudyNoSuchCourseException ex = assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));

        assertEquals("Course does not exist", ex.getMessage());
    }

    @Test
    void isExit_returnsFalse() {
        final Command cmd = new DeleteCourseCommand("");
        assertFalse(cmd.isExit());
    }

    @Test
    void execute_nullCourseManager_throwsAssertionError() {
        final Command cmd = new DeleteCourseCommand("CS2113");
        assertThrows(AssertionError.class, () -> cmd.execute(null, sessionManager));
    }
}
//@@author
