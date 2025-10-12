package seedu.nustudy.command;

import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.session.SessionManager;

public class ExitCommand implements Command {
    @Override
    public void execute(CourseManager courses, SessionManager sessions) throws NUStudyException {
        System.out.println("Exiting NUStudy. Goodbye!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

