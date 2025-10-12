package seedu.nustudy.command;

import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;
import seedu.nustudy.ui.UserInterface;

public class DeleteCourseCommand implements Command {
    private final String input;

    public DeleteCourseCommand(String input) {
        this.input = input;
    }

    public void execute(CourseManager courses, SessionManager sessions) throws NUStudyException {
        if(input.isEmpty()) {
            throw new NUStudyException("Please enter a course name that you want to delete");
        }
        Course courseToDelete = courses.findCourse(input);
        if (courseToDelete == null) {
            throw new NUStudyNoSuchCourseException("Course does not exist");
        }
        courses.delete(courseToDelete);
        UserInterface.printCourseDeleted(courseToDelete);
    }

    /**
     * Indicate whether this command should exit the application
     *
     * @return false, as adding course does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
