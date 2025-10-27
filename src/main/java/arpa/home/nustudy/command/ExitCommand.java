package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;

//@@author Wrooi
public class ExitCommand implements Command {
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        System.out.println("Exiting App. Goodbye!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
//@@author
