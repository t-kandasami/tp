package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

public class AddSessionCommand implements Command {
    private final String input;

    /**
     * Creates a new AddSessionCommand with the user's input
     *
     * @param input The user input to add new course
     */
    public AddSessionCommand(final String input) {
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
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        final String[] arguments = this.input.split("\\s+");
        final String courseName = arguments[0];
        final Course course = courses.findCourse(courseName);
        if (course == null) {
            throw new NUStudyNoSuchCourseException("Course with name " + courseName + " does not exist");
        }
        final int hours;
        try {
            hours = Integer.parseInt(arguments[1]);
        } catch (final NumberFormatException e) {
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
