package seedu.nustudy.command;

import org.junit.jupiter.api.Test;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ResetCourseHoursCommandTest {

    @Test
    void execute() {
        ResetCourseHoursCommand command = new ResetCourseHoursCommand("");
        CourseManager manager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        assertDoesNotThrow(() -> command.execute(manager, sessionManager));
    }

    @Test
    void isExit() {
        ResetCourseHoursCommand command = new ResetCourseHoursCommand("");
        assertFalse(command.isExit());
    }

    @Test
    void checkNonExistentCourse() {
        ResetCourseHoursCommand command = new ResetCourseHoursCommand("NonExistentCourse");
        CourseManager manager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        assertThrows(NUStudyNoSuchCourseException.class, () -> command.execute(manager, sessionManager));
    }


}
