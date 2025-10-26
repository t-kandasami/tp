package arpa.home.nustudy.command;

import java.util.ArrayList;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyCommandException;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;

public class DeleteSessionByIndexCommand implements Command {
    private final String courseName;
    private final String indexString;

    public DeleteSessionByIndexCommand(final String courseName, final String indexString) {
        this.courseName = courseName;
        this.indexString = indexString;
    }

    /**
     * Deletes study session according to a specified index for a specified course.
     *
     * @param courses The course list to find the specified course in.
     * @param sessions The {@SessionManager} instance to delete the specified session from.
     * @throws NUStudyException If no sessions are present for the specified course.
     * @throws NUStudyCommandException If specified index for the specified course is invalid.
     * @throws NUStudyNoSuchCourseException If specified course is non-existent.
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        Course course = courses.findCourse(courseName);
        if (course == null) {
            throw new NUStudyNoSuchCourseException("Course with the name " + courseName + " does not exist");
        }

        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new NUStudyCommandException("Please input a valid index to edit");
        }

        ArrayList<Session> courseSessions = sessions.getAllSessionsForCourse(course);

        if (courseSessions == null) {
            throw new NUStudyException("No sessions found for course: " + courseName);
        }

        if (index < 1 || index > courseSessions.size()) {
            System.out.println("You have " + courseSessions.size() + " session(s) for " + courseName);
            throw new NUStudyException("Invalid index entered.");
        }

        try {
            Session sessionToDelete = courseSessions.get(index - 1);
            sessions.removeSession(sessionToDelete);
            System.out.println("Session " + index + " was successfully deleted for " + courseName);
            System.out.println("Deleted session: " + courseName + " - " +
                    sessionToDelete.getLoggedHours() + " hours on " + sessionToDelete.getDateString());
        } catch (final IndexOutOfBoundsException ex) {
            throw new NUStudyException("Invalid index entered.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
