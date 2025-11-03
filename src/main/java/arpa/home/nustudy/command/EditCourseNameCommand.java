package arpa.home.nustudy.command;

import java.util.regex.Pattern;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyCourseAlreadyExistException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.LoggerHandler;

/**
 * Edits (renames) an existing course. Command format: edit oldCourseName newCourseName
 */
public class EditCourseNameCommand implements Command {
    private static final Logger logger = LoggerHandler.getLogger(EditCourseNameCommand.class);

    private static final Pattern PATTERN = Pattern.compile("\\s+");
    private String oldCourseName;
    private String newCourseName;

    /**
     * Constructs an EditCourseCommand with the given arguments.
     *
     * @param arguments User input string after "edit"
     *
     * @throws NUStudyCommandException if the input format is invalid
     */
    public EditCourseNameCommand(final String arguments) throws NUStudyCommandException {
        final String[] parts = PATTERN.split(arguments);
        if (parts.length != 2) {
            throw new NUStudyCommandException("Usage: edit <oldCourseName> <newCourseName>");
        }
        oldCourseName = parts[0];
        newCourseName = parts[1];
    }

    /**
     * Edits a specified course by changing its coursename.
     *
     * @param courses  The course list to work with
     * @param sessions The session manager to work with
     *
     * @throws NUStudyCommandException       If the course names are empty
     * @throws NUStudyNoSuchCourseException If the oldCourse does not exist
     * @throws NUStudyCourseAlreadyExistException If the newCourse already exist
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.info(() -> "Executing EditCourseCommand: " + oldCourseName + " -> " + newCourseName);
        assert courses != null : "CourseManager cannot be null";

        if (oldCourseName.isEmpty() || newCourseName.isEmpty()) {
            throw new NUStudyCommandException("Please enter current course name and new course names.");
        }
        final Course courseToEdit = courses.findCourse(oldCourseName);
        if (courseToEdit == null) {
            throw new NUStudyNoSuchCourseException("Course doesn't exist");
        }
        if (courses.findCourse(newCourseName) != null) {
            throw new NUStudyCourseAlreadyExistException("A course with the new name already exists");
        }

        courseToEdit.setCourseName(newCourseName);

        if (courses.findCourse(newCourseName) == null) {
            throw new NUStudyNoSuchCourseException("Course renaming failed due to internal error.");
        }
        UserInterface.printCourseNameEdited(oldCourseName, newCourseName);
        logger.info("Course successfully renamed.");
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
