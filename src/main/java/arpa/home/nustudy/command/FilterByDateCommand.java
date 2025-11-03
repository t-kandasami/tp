package arpa.home.nustudy.command;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.DateParser;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.utils.LoggerHandler;

/**
 * Command to filter and display courses that have sessions on a specified date.
 * <p>
 * The command accepts a date argument in various supported formats (e.g., "2025-10-26", "26/10/2025"). It parses the
 * date and iterates through all courses and their sessions to find matches. Matched courses are displayed along with
 * their original indices in the course list.
 */
public class FilterByDateCommand implements Command {
    private static final Logger logger = LoggerHandler.getLogger(FilterByDateCommand.class);

    private final String dateString;
    private final Iterable<?> sessionSource; // optional test injection; if null, use the provided SessionManager

    /**
     * Constructs a FilterByDateCommand.
     *
     * @param arguments the date string provided by the user
     *
     * @throws NUStudyCommandException if arguments is null or blank
     */
    public FilterByDateCommand(final String arguments) throws NUStudyCommandException {
        this(arguments, null);
    }

    /**
     * Constructs a FilterByDateCommand with an optional session source for testing.
     *
     * @param arguments     the date string provided by the user
     * @param sessionSource an iterable source of session-like objects for testing; if null, the SessionManager will be
     *                      used
     *
     * @throws NUStudyCommandException if arguments is null or blank
     */
    public FilterByDateCommand(final String arguments, final Iterable<?> sessionSource)
            throws NUStudyCommandException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new NUStudyCommandException("Filter date cannot be empty. Usage: filter <date>");
        }
        this.dateString = arguments.trim();
        this.sessionSource = sessionSource;
    }

    /**
     * Executes the filter by date command. It parses the provided date string and iterates through all courses and
     * their sessions to find and display courses that have sessions on the specified date.
     *
     * @param courses  the CourseManager containing all courses
     * @param sessions the SessionManager containing all sessions
     *
     * @throws NUStudyException if any error occurs during execution
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.fine(() -> "FilterByDateCommand.execute() start with dateString=" + dateString);
        assert courses != null : "CourseManager cannot be null";
        assert sessions != null : "SessionManager cannot be null";

        final LocalDate date;
        try {
            date = DateParser.parseDate(this.dateString);
        } catch (WrongDateFormatException e) {
            UserInterface.printInvalidDateFormat(this.dateString);
            return;
        }

        final ArrayList<Course> matched = new ArrayList<>();
        final ArrayList<Integer> originalIndices = new ArrayList<>();

        final Iterable<?> sessionIterable = this.sessionSource != null ? this.sessionSource : sessions;

        int idx = 1;
        for (final Course course : courses.getCourses()) {
            boolean hasSessionOnDate = false;

            for (final Object sObj : sessionIterable) {
                try {
                    // reflectively obtain course and date to avoid tight coupling in tests
                    Method getCourse = sObj.getClass().getMethod("getCourse");
                    Object sCourse = getCourse.invoke(sObj);

                    Method getDate = sObj.getClass().getMethod("getDate");
                    Object sDate = getDate.invoke(sObj);

                    if (!(sCourse instanceof Course) || !(sDate instanceof LocalDate)) {
                        continue;
                    }

                    if (((Course) sCourse).equals(course) && date.equals((LocalDate) sDate)) {
                        hasSessionOnDate = true;
                        break;
                    }
                } catch (NoSuchMethodException nsme) {
                    // object doesn't follow expected session-like contract; skip
                    continue;
                } catch (Exception ex) {
                    // any reflection/Invocation issues -> skip this session object
                    continue;
                }
            }

            if (hasSessionOnDate) {
                matched.add(course);
                originalIndices.add(idx);
            }
            idx++;
        }

        logger.fine(() -> "FilterByDateCommand matched count: " + matched.size());
        UserInterface.printCoursesWithSessionsOnDate(matched, originalIndices, date);
        logger.fine("FilterByDateCommand.execute() end");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
