package arpa.home.nustudy.command;

import java.util.ArrayList;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

/**
 * Command to filter courses by a keyword in their name or string representation.
 */
public class FilterByNameCommand implements Command {
    private final String keyword;

    /**
     * Constructs a FilterByNameCommand with the specified keyword.
     *
     * @param arguments The keyword to filter courses by.
     */
    public FilterByNameCommand(final String arguments) {
        this.keyword = arguments == null ? "" : arguments.trim();
    }

    /**
     * Executes the filter command on the provided CourseManager.
     *
     * @param courses  The CourseManager containing all courses.
     * @param sessions The SessionManager (not used in this command).
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) {
        final ArrayList<Course> matched = new ArrayList<>();
        final ArrayList<Integer> originalIndices = new ArrayList<>();

        final String kwLower = keyword.toLowerCase();

        int idx = 1;
        for (final Course c : courses.getCourses()) {
            // match against course's name or toString() representation (case-insensitive)
            final String nameRep = (c.getCourseName() == null ? c.toString() : c.getCourseName()).toLowerCase();
            final String toStringRep = c.toString().toLowerCase();
            if (nameRep.contains(kwLower) || toStringRep.contains(kwLower)) {
                matched.add(c);
                originalIndices.add(idx);
            }
            idx++;
        }

        UserInterface.printFilteredCourseList(matched, originalIndices, keyword);
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
