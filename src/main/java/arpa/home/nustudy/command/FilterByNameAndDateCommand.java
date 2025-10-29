package arpa.home.nustudy.command;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.DateParser;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyException;

/**
 * Command to filter study sessions by exact course name (case-insensitive) and date.
 */
public class FilterByNameAndDateCommand implements Command {
    private static final Logger logger = Logger.getLogger(FilterByNameAndDateCommand.class.getName());

    private final String courseKeyword;
    private final String dateString;
    private final Iterable<?> sessionSource; // optional injection for tests

    /**
     * Constructs the command without injected session source.
     *
     * @param courseKeyword course keyword (exact match required, case-insensitive)
     * @param dateString    date string in supported formats
     *
     * @throws NUStudyCommandException if either argument is null/blank
     */
    public FilterByNameAndDateCommand(final String courseKeyword, final String dateString)
            throws NUStudyCommandException {
        this(courseKeyword, dateString, null);
    }

    /**
     * Constructs the command with optional injected session source (for testing).
     *
     * @param courseKeyword course keyword (exact match required, case-insensitive)
     * @param dateString    date string in supported formats
     * @param sessionSource optional iterable source of session-like objects
     *
     * @throws NUStudyCommandException if either argument is null/blank
     */
    public FilterByNameAndDateCommand(final String courseKeyword, final String dateString,
            final Iterable<?> sessionSource)
            throws NUStudyCommandException {
        if (courseKeyword == null || courseKeyword.trim().isEmpty()) {
            throw new NUStudyCommandException("Filter course name cannot be empty. Usage: filter <course> <date>");
        }
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new NUStudyCommandException("Filter date cannot be empty. Usage: filter <course> <date>");
        }
        this.courseKeyword = courseKeyword.trim();
        this.dateString = dateString.trim();
        this.sessionSource = sessionSource;
    }

    /**
     * Executes the filter command, printing sessions that match the course name and date.
     *
     * @param courses  the CourseManager containing courses
     * @param sessions the SessionManager containing sessions
     *
     * @throws NUStudyException if an error occurs during execution
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.fine(
                () -> "FilterByNameAndDateCommand.execute() start: course=" + courseKeyword + " date=" + dateString);
        assert courses != null : "CourseManager cannot be null";
        assert sessions != null : "SessionManager cannot be null";

        final LocalDate date;
        try {
            date = DateParser.parseDate(this.dateString);
        } catch (WrongDateFormatException e) {
            UserInterface.printInvalidDateFormat(this.dateString);
            return;
        }

        final String keyLower = courseKeyword.toLowerCase();

        final Iterable<?> sessionIterable = this.sessionSource != null ? this.sessionSource : sessions;

        // For each course, require exact case-insensitive match of either course name or toString
        for (final Course course : courses.getCourses()) {
            final String nameRep = (course.getCourseName() == null ? course.toString() : course.getCourseName())
                    .toLowerCase();
            final String toStringRep = course.toString().toLowerCase();

            if (!(nameRep.equals(keyLower) || toStringRep.equals(keyLower))) {
                continue;
            }

            // For the matched course, iterate all sessions and compute per-course session indices
            final ArrayList<Session> matchedSessions = new ArrayList<>();
            final ArrayList<Integer> originalSessionIndices = new ArrayList<>();

            int sessionIdxForCourse = 0;
            for (final Object sObj : sessionIterable) {
                try {
                    Method getCourse = sObj.getClass().getMethod("getCourse");
                    Object sCourse = getCourse.invoke(sObj);

                    if (!course.equals(sCourse)) {
                        // not a session for this course; skip
                        continue;
                    }

                    // this is a session for the course -> increment per-course index
                    sessionIdxForCourse++;

                    Method getDate = sObj.getClass().getMethod("getDate");
                    Object sDate = getDate.invoke(sObj);

                    if (!(sDate instanceof LocalDate)) {
                        continue;
                    }

                    // If date matches, attempt to obtain logged hours and create a real Session
                    if (date.equals((LocalDate) sDate)) {
                        int hours = 0;
                        try {
                            Method getLoggedHours = sObj.getClass().getMethod("getLoggedHours");
                            Object hoursObj = getLoggedHours.invoke(sObj);
                            if (hoursObj instanceof Integer) {
                                hours = ((Integer) hoursObj).intValue();
                            } else {
                                hours = Integer.parseInt(hoursObj.toString());
                            }
                        } catch (NoSuchMethodException nsme) {
                            // If test POJO doesn't expose logged hours, default to 0
                            hours = 0;
                        }

                        // Build a real Session instance so UI can treat it as a Session
                        Session session = new Session((Course) sCourse, hours, (LocalDate) sDate);
                        matchedSessions.add(session);
                        originalSessionIndices.add(sessionIdxForCourse);
                    }
                } catch (NoSuchMethodException nsme) {
                    // session-like contract not met; skip this object
                    continue;
                } catch (Exception ex) {
                    // any reflection error -> skip this session object
                    continue;
                }
            }

            // Delegate printing for this course
            UserInterface.printSessionsForCourseOnDate(course, matchedSessions, originalSessionIndices, date);
        }

        logger.fine("FilterByNameAndDateCommand.execute() end");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
