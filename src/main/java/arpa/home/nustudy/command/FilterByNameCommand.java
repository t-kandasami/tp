package arpa.home.nustudy.command;

import java.util.ArrayList;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

/**
 * Command to filter courses by name containing a specified keyword.
 */
public class FilterByNameCommand implements Command {
    private static final Logger logger = Logger.getLogger(FilterByNameCommand.class.getName());
    private final String keyword;

    /**
     * Constructs a FilterByNameCommand with the specified keyword.
     *
     * @param arguments The keyword to filter courses by.
     *
     * @throws NUStudyCommandException if the keyword is null/blank
     */
    public FilterByNameCommand(final String arguments) throws NUStudyCommandException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new NUStudyCommandException("Filter keyword cannot be empty. Usage: filter <course>");
        }
        keyword = arguments.trim();
    }

    /**
     * Executes the filter command on the provided CourseManager.
     *
     * @param courses  The CourseManager containing all courses.
     * @param sessions The SessionManager (not used in this command).
     *
     * @throws NUStudyException if an internal error occurs
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        logger.fine(() -> "FilterByNameCommand.execute() start, keyword=" + keyword);
        assert courses != null : "CourseManager cannot be null";

        final ArrayList<Course> matched = new ArrayList<>();
        final ArrayList<Integer> originalIndices = new ArrayList<>();

        final String kwLower = keyword.toLowerCase();

        int idx = 1;
        for (final Course c : courses.getCourses()) {
            final String nameRep = (c.getCourseName() == null ? c.toString() : c.getCourseName()).toLowerCase();
            final String toStringRep = c.toString().toLowerCase();
            if (nameRep.contains(kwLower) || toStringRep.contains(kwLower)) {
                matched.add(c);
                originalIndices.add(idx);
            }
            idx++;
        }

        logger.fine(() -> "FilterByNameCommand matched count: " + matched.size());
        UserInterface.printFilteredCourseList(matched, originalIndices, keyword);
        logger.fine("FilterByNameCommand.execute() end");
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return {@code false} for FilterByNameCommand since it does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
