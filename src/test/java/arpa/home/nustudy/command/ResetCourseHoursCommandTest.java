package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

class ResetCourseHoursCommandTest {
    @Test
    void execute() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("");
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        assertDoesNotThrow(() -> command.execute(manager, sessionManager));
    }

    @Test
    void isExit() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("");

        assertFalse(command.isExit());
    }

    @Test
    void checkNonExistentCourse() {
        final ResetCourseHoursCommand command = new ResetCourseHoursCommand("NonExistentCourse");
        final CourseManager manager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        assertThrows(NUStudyNoSuchCourseException.class, () -> command.execute(manager, sessionManager));
    }
}
