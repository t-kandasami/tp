package arpa.home.nustudy.command;//@@author t-kandasami

import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;

/**
 * Displays a detailed help message for all available commands in NUStudy.
 */
public class HelpCommand implements Command {
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());

    public HelpCommand() {
    }

    @Override
    public void execute(CourseManager courses, SessionManager sessions) {
        logger.log(Level.INFO, "Executing HelpCommand");

        String helpMessage = """
                        ====================== NUStudy Help ======================
                 Type                     Format                              Example
                 -----------------------------------------------------------------------
                 Help                     help                                help
                 Add a course             add <course code>                   add CS2113
                 Add a study session      add <course code> <hours> [date]    add CS2113 5
                 List all courses         list                                list
                 List study sessions      list <course code>                  list CS2113
                 Edit course code         edit <old course code> <new code>   edit CS2113 MA1511
                 Edit study session hours edit <course code> <index> <hours>  edit CS2113 1 2
                 Edit study session date  edit <course code> <index> <date>   edit CS2113 1 23/10/2025
                 Filter by course         filter <course>                     filter MA1511
                 Filter by date           filter <date>                       filter 23/10/2025
                 Filter code and date     filter <course> <date>              filter MA1511 23/10/2025
                 Reset                    reset <course> or reset all          reset CS2113
                 Delete a course          delete <course code>                delete CS2113
                 Delete session by index  delete <course code> <index>        delete CS2113 2
                 Delete session by date   delete <date>                       delete 26/10/2025
                 Exit NUStudy             exit                                exit
                        =============================================================
                """;

        System.out.println(helpMessage);
    }

    @Override
    public boolean isExit() {
        return false; // Help command does not exit the application
    }
}
