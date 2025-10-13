package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

public class DeleteCourseCommand implements Command {
    private final String input;

    public DeleteCourseCommand(final String input) {
        this.input = input;
    }

    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        if (this.input.isEmpty()) {
            throw new NUStudyException("Please enter a course name that you want to delete");
        }
        final Course courseToDelete = courses.findCourse(this.input);
        if (courseToDelete == null) {
            throw new NUStudyNoSuchCourseException("Course does not exist");
        }
        courses.delete(courseToDelete);
        UserInterface.printCourseDeleted(courseToDelete);
    }

    /**
     * Indicate whether this command should exit the application
     *
     * @return false, as adding course does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
