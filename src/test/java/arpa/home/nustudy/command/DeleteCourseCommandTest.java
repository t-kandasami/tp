package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

class DeleteCourseCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
    }

    @Test
    void execute_validCourse_deletesSuccessfully() {
        final Course course = new Course("CS2113");
        courseManager.add(course);
        final Command cmd = new DeleteCourseCommand("CS2113");

        assertDoesNotThrow(() -> cmd.execute(courseManager, sessionManager));
        assertNull(courseManager.findCourse("CS2113"), "Success: Cannot find course CS2113 which was deleted");
    }

    @Test
    void execute_emptyInput_throwsException() {
        final Command cmd = new DeleteCourseCommand("");
        final NUStudyException ex = assertThrows(NUStudyException.class,
                () -> cmd.execute(courseManager, sessionManager));
        assertEquals("Please enter a course name that you want to delete", ex.getMessage());
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

    /**
     * This test simulates failure without mock by manually overriding delete
     *
     * @throws NUStudyException
     */
    @Test
    void execute_deleteFails_throwsNUStudyException() throws NUStudyException {
        final CourseManager failingCourseManager = new CourseManager() {
            @Override
            public void delete(Course course) {
                // do nothing, simulate failure
            }

            @Override
            public Course findCourse(String courseName) {
                // always return course to simulate deletion didn't happen
                return new Course(courseName);
            }
        };
        Course course = new Course("CS2113");
        failingCourseManager.add(course);

        DeleteCourseCommand cmd = new DeleteCourseCommand("CS2113");
        NUStudyException ex = assertThrows(NUStudyException.class,
                () -> cmd.execute(failingCourseManager, sessionManager));
        assertEquals("Course deletion failed", ex.getMessage());
    }
}
