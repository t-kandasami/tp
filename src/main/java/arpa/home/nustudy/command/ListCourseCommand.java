package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

public class ListCourseCommand implements Command {
    public ListCourseCommand(final String arguments) {
    }

    /**
     * Create a new ListCourseCommand with the user's input in the provided course list
     *
     * @param courses The course list to work with
     * @throws NUStudyException If listing fails (not expected here)
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        UserInterface.printCourseList(courses);
    }

    /**
     * Indicate whether this command should exit the application
     *
     * @return false, as listing courses does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
