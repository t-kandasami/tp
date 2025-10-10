package seedu.nustudy.command;

import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.ui.UserInterface;

public class ListCourseCommand implements Command {
    /**
     * Create a new ListCourseCommand with the user's input in the provided course list
     *
     * @param courses The course list to work with
     * @throws NUStudyException If listing fails (not expected here)
     */
    @Override
    public void execute(CourseManager courses) throws NUStudyException {
        UserInterface.printCourseList(courses);
    }

    /**
     * Indicate whether this command should exit the application
     *
     * @return false, as listing courses does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}

