package seedu.nustudy.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import seedu.nustudy.course.CourseManager;

class ListCourseCommandTest {

    @Test
    void execute() {
        CourseManager manager = new CourseManager();
        ListCourseCommand cmd = new ListCourseCommand("");
        assertDoesNotThrow(() -> cmd.execute(manager));
    }

    @Test
    void isExit() {
        ListCourseCommand cmd = new ListCourseCommand("");
        assertFalse(cmd.isExit());
    }
}