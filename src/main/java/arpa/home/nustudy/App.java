package arpa.home.nustudy;

import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.Parser;

public class App {
    private static final UserInterface ui = new UserInterface();
    private static final CourseManager courseManager = new CourseManager();
    private static final SessionManager sessionManager = new SessionManager();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(final String[] args) {
        System.out.println("Hello from NUStudy");

        boolean isExit = false;

        do {
            final String userInput = UserInterface.readInput();

            if (userInput == null) {
                break;
            }

            if (userInput.isEmpty()) {
                continue;
            }

            try {
                final Command c = Parser.parseCommand(userInput);
                c.execute(courseManager, sessionManager);

                if (c.isExit()) {
                    isExit = true;
                }
            } catch (final NUStudyException e) {
                System.out.println(e.getMessage());
            }
        } while (!isExit);
    }
}
