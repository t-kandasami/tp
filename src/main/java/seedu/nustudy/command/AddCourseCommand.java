package seedu.nustudy.command;

import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.ui.UserInterface;

public class AddCourseCommand implements Command {
    private final String input;

    /**
     * Create a new AddCourseCommand with the user's input
     *
     * @param input The user input to add new course
     */
    public AddCourseCommand(String input) {
        this.input = input;
    }

    /**
     * Adds the new course into the provided course list
     *
     * @param courses The course list to work with
     * @throws NUStudyException If user's input is invalid
     */
    public void execute(CourseManager courses)
            throws NUStudyException {
        if (input.isEmpty()) {
            throw new NUStudyException("Input a course name");
        }
        Course course = new Course(input);
        courses.add(course);
        UserInterface.printCourseAdded(course);
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
