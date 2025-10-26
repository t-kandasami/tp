package arpa.home.nustudy.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.ConfirmationHandler;

//@@author t-kandasami

/**
 * Handles the deletion of a course from the CourseManager. Prompts user for confirmation before deleting the specified
 * course. Provides feedback through the UserInterface and logs actions.
 */
public class DeleteCourseCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteCourseCommand.class.getName());
    private final String input;

    /**
     * Constructs a DeleteCourseCommand with the specified course name.
     *
     * @param input Name of the course to be deleted.
     */
    public DeleteCourseCommand(final String input) {
        this.input = input;
        logger.log(Level.FINE, "DeleteCourseCommand created with input: {0}", input);
    }

    /**
     * Executes the deletion of the course from the CourseManager after confirmation. Throws NUStudyException if the
     * course cannot be deleted or does not exist.
     *
     * @param courses  The CourseManager containing all courses.
     * @param sessions The SessionManager used for session-related actions (not used here).
     *
     * @throws NUStudyException If the course cannot be deleted or is not found.
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.log(Level.INFO, "Executing DeleteCourseCommand with input: {0}", input);
        assert courses != null : "List of Courses cannot be null";
        assert input != null : "Input cannot be null";

        if (input.isEmpty()) {
            logger.log(Level.WARNING, "DeleteCourseCommand failed: Input Course Name is empty");
            throw new NUStudyException("Please enter a course name to delete");
        }

        logger.log(Level.FINE, "DeleteCourseCommand starts a search for course: {0}", input);
        final Course courseToDelete = courses.findCourse(input);

        if (courseToDelete == null) {
            logger.log(Level.WARNING, "Course not found: {0}", input);
            throw new NUStudyNoSuchCourseException("Course does not exist");
        }

        final boolean confirmation =
                ConfirmationHandler.firstLevelConfirmation(
                        "Are you sure you want to delete " + courseToDelete + "?");

        if (!confirmation) {
            throw new NUStudyException(courseToDelete + " has not been deleted as per your request");
        }
        assert (courseToDelete.getCourseName().equals(input)) : "Returned course name does "
                + "match input";

        courses.delete(courseToDelete);

        if (courses.findCourse(input) == null) {
            logger.log(Level.INFO, "Successfully deleted course: {0}", courseToDelete);
        } else {
            logger.log(Level.SEVERE, "Course deletion failed: {0}", courseToDelete);
            throw new NUStudyException("Course deletion failed");
        }

        assert courses.findCourse(input) == null : "Course was successfully deleted";

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
//@@author
