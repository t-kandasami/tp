package arpa.home.nustudy.command;

import java.time.LocalDate;

import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.utils.DateParser;

public class DeleteByDateCommand implements Command {
    private final String dateString;

    public DeleteByDateCommand(final String dateString) {
        this.dateString = dateString;
    }

    /**
     * Deletes all study sessions according to a specified date.
     *
     * @param sessions The {@code SessionManager} instance to delete sessions from.
     *
     * @throws NUStudyException If the date format is invalid.
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        final LocalDate date = DateParser.parseDate(dateString);
        int deletedSessions = sessions.removeAllSessionsByDate(date);

        if (deletedSessions > 0) {
            System.out.println("Successfully deleted " + deletedSessions +
                    " session(s) on " + DateParser.formatDate(date) + ".");
        } else {
            System.out.println("No sessions found to delete on " + DateParser.formatDate(date) + ".");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
