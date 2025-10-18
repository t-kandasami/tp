package arpa.home.nustudy;

import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.CommandParser;
import arpa.home.nustudy.utils.Storage;

public class App {
    private static final UserInterface ui = new UserInterface();
    private static final CourseManager courseManager = new CourseManager();
    private static final SessionManager sessionManager = new SessionManager();
    private static final String FILEPATH = "./data/NUStudy.txt";
    private static final Storage storage = new Storage(FILEPATH);


    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(final String[] args) {
        System.out.println("Hello from NUStudy");

        boolean isExit = false;

        storage.load(courseManager, sessionManager);


        do {
            final String userInput = UserInterface.readInput();

            if (userInput == null) {
                break;
            }

            if (userInput.isEmpty()) {
                continue;
            }

            try {
                final Command c = CommandParser.parseCommand(userInput);
                c.execute(courseManager, sessionManager);

                if (c.isExit()) {
                    isExit = true;
                    storage.save(courseManager, sessionManager);
                }
            } catch (final NUStudyException e) {
                System.out.println(e.getMessage());
            }
        } while (!isExit);
    }
}
