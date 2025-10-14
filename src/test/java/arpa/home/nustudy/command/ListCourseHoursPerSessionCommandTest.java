package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

class ListCourseHoursPerSessionCommandTest {
    @Test
    void execute_validCourseWithHours_successful() {
        final Course course = new Course("CS2113");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        courseManager.add(course);
        sessionManager.add(course, 1);
        sessionManager.add(course, 2);
        sessionManager.add(course, 3);

        final ListCourseHoursPerSessionCommand command = new ListCourseHoursPerSessionCommand("CS2113");

        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_validCourseNoHours_successful() {
        final Course course = new Course("CS2113");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        courseManager.add(course);

        final ListCourseHoursPerSessionCommand command = new ListCourseHoursPerSessionCommand("CS2113");

        assertDoesNotThrow(() -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_invalidCourseWithHours_throwsException() {
        final Course course = new Course("CS2113");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        courseManager.add(course);
        sessionManager.add(course, 1);
        sessionManager.add(course, 2);
        sessionManager.add(course, 3);

        final ListCourseHoursPerSessionCommand command = new ListCourseHoursPerSessionCommand("CS2040C");

        assertThrows(NUStudyNoSuchCourseException.class, () -> command.execute(courseManager, sessionManager));
    }

    @Test
    void execute_invalidCourseNoHours_throwsException() {
        final Course course = new Course("CS2113");
        final CourseManager courseManager = new CourseManager();
        final SessionManager sessionManager = new SessionManager();

        courseManager.add(course);

        final ListCourseHoursPerSessionCommand command = new ListCourseHoursPerSessionCommand("CS2040C");

        assertThrows(NUStudyNoSuchCourseException.class, () -> command.execute(courseManager, sessionManager));
    }
}
