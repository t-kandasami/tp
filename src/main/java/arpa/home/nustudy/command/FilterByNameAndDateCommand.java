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

/**
 * Command to filter sessions for courses matching a name/keyword on a given date.
 *
 * Command format: {@code filter <course> <date>}
 *
 * Behaviour:
 * - Finds all courses matching the provided keyword (case-insensitive substring).
 * - For each matched course, finds sessions on the parsed date.
 * - Prints sessions grouped by course. Session indices printed are the original 1-based indices
 *   within that course's session list (so they can be used with session-index commands).
 */
public class FilterByNameAndDateCommand implements Command {
    private final String courseKeyword;
    private final String dateString;

    public FilterByNameAndDateCommand(final String courseKeyword, final String dateString) {
        this.courseKeyword = courseKeyword == null ? "" : courseKeyword.trim();
        this.dateString = dateString == null ? "" : dateString.trim();
    }

    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) {
        final LocalDate date;
        try {
            date = DateParser.parseDate(this.dateString);
        } catch (WrongDateFormatException e) {
            UserInterface.printInvalidDateFormat(this.dateString);
            return;
        }

        final String kwLower = courseKeyword.toLowerCase();

        // Find all courses that match the keyword (exact match required for combined filter)
        for (final Course course : courses.getCourses()) {
            final String nameRep = (course.getCourseName() == null ? course.toString() : course.getCourseName())
                    .toLowerCase();
            final String toStringRep = course.toString().toLowerCase();

            // Require exact case-insensitive equality for combined filter,
            // so "ma1511" will not match "ma1511x".
            if (!(nameRep.equals(kwLower) || toStringRep.equals(kwLower))) {
                continue;
            }

            // For the matched course, find sessions on the date and preserve their original indices
            final ArrayList<Session> matchedSessions = new ArrayList<>();
            final ArrayList<Integer> originalSessionIndices = new ArrayList<>();

            final ArrayList<Session> allSessionsForCourse = sessions.getAllSessionsForCourse(course);
            int sessionIdx = 1;
            for (final Session s : allSessionsForCourse) {
                if (date.equals(s.getDate())) {
                    matchedSessions.add(s);
                    originalSessionIndices.add(sessionIdx);
                }
                sessionIdx++;
            }

            // Delegate printing for this course
            UserInterface.printSessionsForCourseOnDate(course, matchedSessions, originalSessionIndices, date);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
