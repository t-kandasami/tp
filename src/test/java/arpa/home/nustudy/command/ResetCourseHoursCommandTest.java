package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
