package arpa.home.nustudy.command;

import java.util.ArrayList;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

/**
 * Command handler to list course hours per session
 */
public class ListCourseHoursPerSessionCommand implements Command {
    /// Command input after the "list" command
    final String input;

    /**
     * List course hours per session
     *
     * @param input Command input after the "list" command
     */
    public ListCourseHoursPerSessionCommand(final String input) {
        this.input = input;
    }

    /**
     * Execute the command to list course hours per session
     *
     * @param courses  The course list to work with
     * @param sessions The session manager to work with
     *
     * @throws NUStudyException If the command input after the "list" command is invalid
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        final String[] arguments = input.split("\\s+");
        final String courseName = arguments[0];
        final Course course = courses.findCourse(courseName);

        if (course == null) {
            throw new NUStudyNoSuchCourseException("Course with name " + courseName + " does not exist");
        }

        final ArrayList<Integer> courseHours = sessions.getAllLoggedHoursForCourse(course);
        final ArrayList<String> courseDate = sessions.getAllDateStringsForCourse(course);

        UserInterface.printCourseHoursPerSession(course, courseHours, courseDate);
    }

    /**
     * Get if the command should cause the program to terminate
     *
     * @return If the command should cause the program to terminate
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
