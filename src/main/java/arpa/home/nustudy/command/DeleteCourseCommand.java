package arpa.home.nustudy.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.Storage;

public class DeleteCourseCommand implements Command {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final String input;

    public DeleteCourseCommand(final String input) {
        this.input = input;
        logger.log(Level.FINE, "DeleteCourseCommand created with input: {0}", input);
    }

    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.log(Level.INFO, "Executing DeleteCourseCommand with input: {0}", input);
        assert courses != null : "List of Courses cannot be null";
        assert input != null : "Input cannot be null";

        if (input.isEmpty()) {
            logger.log(Level.WARNING, "DeleteCourseCommand failed: Input Course Name is empty");
            throw new NUStudyException("Please enter a course name that you want to delete");
        }

        logger.log(Level.FINE, "DeleteCourseCommand starts a search for course: {0}", input);
        final Course courseToDelete = courses.findCourse(input);

        if (courseToDelete == null) {
            logger.log(Level.WARNING, "Course not found: {0}", input);
            throw new NUStudyNoSuchCourseException("Course does not exist");
        }
        assert (courseToDelete.getCourseName().equals(input)) : "Returned course name does "
                + "match input";
        courses.delete(courseToDelete);

        assert courses.findCourse(input) == null : "Course was successfully deleted";
        if (courses.findCourse(input) == null) {
            logger.log(Level.WARNING, "Successfully deleted course: {0}", courseToDelete.getCourseName());
        } else {
            logger.log(Level.SEVERE, "Course deletion failed: {0}", courseToDelete.getCourseName());
            throw new NUStudyException("Course deletion failed");
        }

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
