package seedu.nustudy.command;

import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;
import seedu.nustudy.ui.UserInterface;

public class AddSessionCommand implements Command {
    private final String input;

    /**
     * Creates a new AddSessionCommand with the user's input
     *
     * @param input The user input to add new course
     */
    public AddSessionCommand(String input) {
        this.input = input;
    }

    /**
     * Adds the new session into the provided session list
     *
     * @param courses  The course list to work with
     * @param sessions The session list to work with
     * @throws NUStudyException If user's input is invalid
     */
    @Override
    public void execute(CourseManager courses, SessionManager sessions) throws NUStudyException {
        String[] arguments = input.split("\\s+");
        String courseName = arguments[0];
        Course course = courses.findCourse(courseName);
        if (course == null) {
            throw new NUStudyNoSuchCourseException("Course with name " + courseName + " does not exist");
        }
        int hours;
        try {
            hours = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e) {
            throw new NUStudyException("Hours must be an integer");
        }
        UserInterface.printStudySessionAdded(course, hours);
    }

    /**
     * Indicates whether this command should exit the application
     *
     * @return false, as adding course does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
