package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCourseCommand implements Command {
    // Add a logger for this command
    private static final Logger logger = Logger.getLogger(ListCourseCommand.class.getName());

    // Configure a lightweight console handler for this logger so logs are visible in tests/runs
    static {
        logger.setLevel(Level.INFO);
        // Add a ConsoleHandler only if no handlers are present to avoid duplicates
        if (logger.getHandlers().length == 0) {
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);
            logger.setUseParentHandlers(false);
        }
    }

    /**
     * Create a new ListCourseCommand with the user's input in the provided course list
     *
     * @param courses The course list to work with
     *
     * @throws NUStudyException If listing fails (not expected here)
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        // Basic sanity checks using assertions (can be enabled during test/run with -ea)
        assert courses != null : "courses must not be null";
        assert sessions != null : "sessions must not be null";

        logger.log(Level.INFO, "Executing ListCourseCommand");
        UserInterface.printCourseList(courses);
        logger.log(Level.FINE, "Finished ListCourseCommand");
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
