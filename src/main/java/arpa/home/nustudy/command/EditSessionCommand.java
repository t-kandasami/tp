package arpa.home.nustudy.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.FutureDateException;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchSessionException;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.DateParser;

public class EditSessionCommand implements Command {
    private final Logger logger = Logger.getLogger(EditSessionCommand.class.getName());
    private String input;

    public EditSessionCommand(final String input) {
        this.input = input;
    }

    /**
     * Edits a study session for a specified course by updating its date or study hours.
     *
     * @param courses  The course list to work with
     * @param sessions The session manager to work with
     *
     * @throws NUStudyException              If the course name is invalid
     * @throws NUStudyCommandException       If the index or date format is invalid
     * @throws NUStudyNoSuchSessionException If the session index is out of bounds
     * @throws FutureDateException           If the provided date is in the future
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        final String[] argument = input.split("\\s+");

        Course course = courses.findCourse(argument[0].trim());

        if (course == null) {
            throw new NUStudyNoSuchCourseException("Invalid course name!");
        }

        int index;
        try {
            index = Integer.parseInt(argument[1].trim());
        } catch (final NumberFormatException e) {
            throw new NUStudyCommandException("Please input a valid index to edit");
        }

        ArrayList<Session> courseSessions = sessions.getAllSessionsForCourse(course);

        if (index < 1 || index > courseSessions.size()) {
            throw new NUStudyNoSuchSessionException();
        }

        Session session = courseSessions.get(index - 1);

        final String arg2 = argument[2].trim();
        LocalDate date;
        try {
            date = DateParser.parseDate(arg2);
            if (date.isAfter(LocalDate.now())) {
                throw new FutureDateException();
            }
            session.setDate(date);
            UserInterface.printEditSessionDateSuccess(date);
            return;
        } catch (final WrongDateFormatException e) {
            // Intentionally left empty to try for Integer
        }

        logger.log(Level.FINE, "Attempting to parse '" + arg2 + "' as integer");

        try {
            final int newHours = Integer.parseInt(arg2);

            logger.log(Level.FINE, "Parsed '" + arg2 + "' as integer 2 successfully");
            logger.log(Level.FINE, "Updating course session hours to " + newHours);

            session.setLoggedHours(newHours);

            assert session.getLoggedHours() == newHours : "Updating course session hours should work as expected";

            UserInterface.printEditSessionHoursSuccess(newHours);
            return;
        } catch (final NumberFormatException ignored) {
            // Intentionally left empty to fall through to error handling
        }

        logger.log(Level.WARNING, "Failed to parse '" + arg2 + "' as anything");

        throw new NUStudyCommandException("Please input a valid date or study hours");
    }

    /**
     * Indicate whether this command should exit the application
     *
     * @return false, as editing session does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
