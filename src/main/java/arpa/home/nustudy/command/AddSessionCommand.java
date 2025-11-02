package arpa.home.nustudy.command;

import static arpa.home.nustudy.utils.DateParser.parseDate;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.FutureDateException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.DateParser;

public class AddSessionCommand implements Command {
    private static final Logger logger = Logger.getLogger(AddSessionCommand.class.getName());
    private final String input;

    /**
     * Creates a new AddSessionCommand with the user's input
     *
     * @param input The user input to add new course
     */
    public AddSessionCommand(final String input) {
        this.input = input;
        logger.log(Level.FINE, "AddSessionCommand created with input: {0}", input);
    }

    /**
     * Adds the new session into the provided session list
     *
     * @param courses  The course list to work with
     * @param sessions The session list to work with
     *
     * @throws NUStudyNoSuchCourseException If the specified course does not exist
     * @throws NUStudyException             If hours is not a valid integer
     * @throws WrongDateFormatException     If the date format is invalid
     * @throws FutureDateException          If the date is in the future
     */

    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.log(Level.INFO, "Executing AddSessionCommand with input: {0}", input);

        assert courses != null : "CourseManager cannot be null";
        assert sessions != null : "SessionManager cannot be null";
        assert input != null : "Input cannot be null";

        final String[] arguments = input.split("\\s+");
        logger.log(Level.FINE, "Split input arguments: {0}", (Object) arguments);

        assert arguments.length >= 2 : "Input must contain course name and hours separated by space";

        final String courseName = arguments[0];
        assert courseName != null && !courseName.isEmpty() : "Course name cannot be null or empty";

        final Course course = courses.findCourse(courseName);
        if (course == null) {
            logger.log(Level.WARNING, "Course not found: {0}", courseName);
            throw new NUStudyNoSuchCourseException("Course with the name " + courseName + " does not exist");
        }

        final int hours;
        try {
            hours = Integer.parseInt(arguments[1]);
            logger.log(Level.FINE, "Parsed hours: {0}", hours);
        } catch (final NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid hours format: {0}", arguments[1]);
            throw new NUStudyException("Hours must be an integer");
        }

        final LocalDate date;
        if (arguments.length == 3) {
            try {
                date = parseDate(arguments[2]);
            } catch (WrongDateFormatException e) {
                if (DateParser.isFutureDate(arguments[2])) {
                    // Surface a specific FutureDateException so callers/tests get the expected behavior
                    throw new FutureDateException(arguments[2].trim());
                }
                throw e;
            }
        } else {
            date = LocalDate.now();
            logger.log(Level.FINE, "No date provided; defaulting to today: {0}", date);
        }

        sessions.add(course, hours, date);
        assert sessions.sessionExists(course, hours) : "Session was created successfully";
        logger.log(Level.INFO, "AddSessionCommand executed with input: {0}", input);

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
