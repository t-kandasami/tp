package seedu.nustudy.command;

import org.junit.jupiter.api.Test;
import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteCourseCommandTest {

    @Test
    void execute_validCourse_deletesSuccessfully() {
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        Course course = new Course("CS2113");
        courseManager.add(course);
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand("CS2113");
        assertDoesNotThrow(() -> deleteCourseCommand.execute(courseManager, sessionManager));
    }

    @Test
    void execute_emptyInput_throwsException() {
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        DeleteCourseCommand command = new DeleteCourseCommand("");
        NUStudyException ex = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Please enter a course name that you want to delete", ex.getMessage());
    }

    @Test
    void execute_courseNotFound_throwsNoSuchCourseException() {
        CourseManager courseManager = new CourseManager();
        SessionManager sessionManager = new SessionManager();
        DeleteCourseCommand command = new DeleteCourseCommand("CS2040");
        NUStudyNoSuchCourseException ex = assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Course does not exist", ex.getMessage());
    }

    @Test
    void isExit_returnsFalse() {
        DeleteCourseCommand cmd = new DeleteCourseCommand("");
        assertFalse(cmd.isExit());
    }
}
