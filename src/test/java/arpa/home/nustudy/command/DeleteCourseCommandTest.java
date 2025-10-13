package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteCourseCommandTest {

    @Test
    void execute_validCourse_deletesSuccessfully() {
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final Course course = new Course("CS2113");
        courseManager.add(course);
        final DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand("CS2113");
        assertDoesNotThrow(() -> deleteCourseCommand.execute(courseManager, sessionManager));
    }

    @Test
    void execute_emptyInput_throwsException() {
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final DeleteCourseCommand command = new DeleteCourseCommand("");
        final NUStudyException ex = assertThrows(NUStudyException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Please enter a course name that you want to delete", ex.getMessage());
    }

    @Test
    void execute_courseNotFound_throwsNoSuchCourseException() {
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();
        final DeleteCourseCommand command = new DeleteCourseCommand("CS2040");
        final NUStudyNoSuchCourseException ex = assertThrows(NUStudyNoSuchCourseException.class,
                () -> command.execute(courseManager, sessionManager));
        assertEquals("Course does not exist", ex.getMessage());
    }

    @Test
    void isExit_returnsFalse() {
        final DeleteCourseCommand cmd = new DeleteCourseCommand("");
        assertFalse(cmd.isExit());
    }
}
