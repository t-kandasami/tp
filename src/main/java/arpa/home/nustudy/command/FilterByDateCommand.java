package arpa.home.nustudy.command;

import java.time.LocalDate;
import java.util.ArrayList;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.DateParser;
import arpa.home.nustudy.exceptions.WrongDateFormatException;
import arpa.home.nustudy.exceptions.NUStudyException;

/**
 * Command to filter and display courses that have sessions on a specified date.
 * <p>
 * Supported date formats are handled by DateParser.
 */
public class FilterByDateCommand implements Command {
    private final String dateString;

    /**
     * Constructs a FilterByDateCommand with the raw date argument.
     *
     * @param arguments the date string provided by the user (may be in supported formats)
     */
    public FilterByDateCommand(final String arguments) {
        this.dateString = arguments == null ? "" : arguments.trim();
    }

    /** Executes the command to filter and display courses with sessions on the specified date.
     *
     * @param courses  the CourseManager containing all courses
     * @param sessions the SessionManager containing all sessions
     * @throws NUStudyException if an error occurs during execution
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        LocalDate date;
        try {
            date = DateParser.parseDate(this.dateString);
        } catch (WrongDateFormatException e) {
            UserInterface.printInvalidDateFormat(this.dateString);
            return;
        }

        final ArrayList<Course> matched = new ArrayList<>();
        final ArrayList<Integer> originalIndices = new ArrayList<>();

        int idx = 1;
        for (final Course course : courses.getCourses()) {
            boolean hasSessionOnDate = false;

            // iterate sessions via SessionManager (it implements Iterable<Session>)
            for (final Session s : sessions) {
                if (s.getCourse().equals(course) && date.equals(s.getDate())) {
                    hasSessionOnDate = true;
                    break;
                }
            }

            if (hasSessionOnDate) {
                matched.add(course);
                originalIndices.add(idx);
            }
            idx++;
        }

        UserInterface.printCoursesWithSessionsOnDate(matched, originalIndices, date);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
