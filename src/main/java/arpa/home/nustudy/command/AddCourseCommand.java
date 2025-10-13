package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyCourseAlreadyExistException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

public class AddCourseCommand implements Command {
    private final String input;

    /**
     * Creates a new AddCourseCommand with the user's input
     *
     * @param input The user input to add new course
     */
    public AddCourseCommand(final String input) {
        this.input = input;
    }

    /**
     * Adds the new course into the provided course list
     *
     * @param courses The course list to work with
     *
     * @throws NUStudyCommandException            If user's input is empty
     * @throws NUStudyCourseAlreadyExistException If user's input is already in the course list
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        if (input.isEmpty()) {
            throw new NUStudyCommandException("Input a course name");
        }

        if (courses.findCourse(input) != null) {
            throw new NUStudyCourseAlreadyExistException("Course already exists");
        }

        final Course course = new Course(input);

        courses.add(course);
        UserInterface.printCourseAdded(course);
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
