package seedu.nustudy.command;

import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.session.SessionManager;
import seedu.nustudy.ui.UserInterface;

public class AddSessionCommand implements Command {
    private final String input;

    public AddSessionCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(CourseManager courses, SessionManager sessions) throws NUStudyException {
        String[] arguments = input.split("\\s+");
        String courseName = arguments[0];
        Course course = courses.findCourse(courseName);
        if (course == null) {
            throw new NUStudyNoSuchCourseException("Course with name " + courseName + " does not exist");
        }
        int hours;
        try {
            hours = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e) {
            throw new NUStudyException("Hours must be an integer");
        }
        UserInterface.printStudySessionAdded(course, hours);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
