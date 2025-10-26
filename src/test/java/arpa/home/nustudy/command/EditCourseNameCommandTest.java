package arpa.home.nustudy.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyCourseAlreadyExistException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;

class EditCourseNameCommandTest {
    private CourseManager courseManager;
    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
        courseManager.add(new Course("MA1511"));
        courseManager.add(new Course("CS2113"));
    }

    @Test
    void execute_validRename_successfully() throws NUStudyException {
        EditCourseNameCommand cmd = new EditCourseNameCommand("MA1511 PC1201");
        cmd.execute(courseManager, sessionManager);
        assertNotNull(courseManager.findCourse("PC1201"));
        assertNull(courseManager.findCourse("MA1511"));
    }

    @Test
    void execute_oldCourseNotExist_throwsException() {
        assertThrows(NUStudyNoSuchCourseException.class, () -> {
            EditCourseNameCommand cmd = new EditCourseNameCommand("CS1231 PC1202");
            cmd.execute(courseManager, sessionManager);
        });
    }

    @Test
    void execute_newCourseAlreadyExists_throwsException() {
        assertThrows(NUStudyCourseAlreadyExistException.class, () -> {
            EditCourseNameCommand cmd = new EditCourseNameCommand("CS2113 MA1511");
            cmd.execute(courseManager, sessionManager);
        });
    }

    @Test
    void constructor_missingArguments_throwsCommandException() {
        assertThrows(NUStudyCommandException.class, () -> new EditCourseNameCommand(""));
        assertThrows(NUStudyCommandException.class, () -> new EditCourseNameCommand("MA1511"));
    }

    @Test
    void execute_emptyCourseNames_throwsCommandException() {
        assertThrows(NUStudyCommandException.class, () -> {
            EditCourseNameCommand cmd = new EditCourseNameCommand("  ");
            cmd.execute(courseManager, sessionManager);
        });
    }

    @Test
    void execute_sameName_throwsAlreadyExistException() {
        assertThrows(NUStudyCourseAlreadyExistException.class, () -> {
            EditCourseNameCommand cmd = new EditCourseNameCommand("MA1511 MA1511");
            cmd.execute(courseManager, sessionManager);
        });
    }

    @Test
    void execute_afterRename_courseUpdatedCorrectly() throws NUStudyException {
        EditCourseNameCommand cmd = new EditCourseNameCommand("CS2113 CS1231");
        cmd.execute(courseManager, sessionManager);
        assertNotNull(courseManager.findCourse("CS1231"));
        assertNull(courseManager.findCourse("CS2113"));
    }

    @Test
    void isExit_returnFalse() throws NUStudyCommandException {
        final Command cmd = new EditCourseNameCommand("CS2113 CS1231");
        assertFalse(cmd.isExit());
    }
}