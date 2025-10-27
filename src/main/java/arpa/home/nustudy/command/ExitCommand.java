package arpa.home.nustudy.command;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author Wrooi
public class ExitCommand implements Command {
    private static final Logger logger = Logger.getLogger(ExitCommand.class.getName());

    static {
        logger.setLevel(Level.INFO);
        if (logger.getHandlers().length == 0) {
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);
            logger.setUseParentHandlers(false);
        }
    }

    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        // Sanity checks: can be enabled during tests with -ea
        assert courses != null : "courses must not be null";
        assert sessions != null : "sessions must not be null";

        logger.info("Executing ExitCommand");
        System.out.println("Exiting App. Goodbye!");
        logger.fine("Finished ExitCommand");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
//@@author
