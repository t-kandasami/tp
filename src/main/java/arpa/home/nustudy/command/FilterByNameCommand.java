package arpa.home.nustudy.command;

import java.util.ArrayList;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

/**
 * Filters courses by a name keyword. Command format: filter <keyword>
 *
 * Behaviour:
 * - Case-insensitive substring match against course name / code representation.
 * - Prints matched courses keeping their original indices from the full course list.
 */
public class FilterByNameCommand implements Command {
    private final String keyword;

    public FilterByNameCommand(final String arguments) {
        this.keyword = arguments == null ? "" : arguments.trim();
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}
