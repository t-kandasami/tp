package arpa.home.nustudy.session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import arpa.home.nustudy.course.Course;

public class SessionManager implements Iterable<Session> {
    private final ArrayList<Session> sessions = new ArrayList<>();

    /**
     * Adds a study session to the studySessions
     *
     * @param course      The course object want to add study session
     * @param loggedHours The amount of hours spent for a study session
     */
    public void add(final Course course, final int loggedHours, final LocalDate date) {
        sessions.add(new Session(course, loggedHours, date));
    }

    public boolean sessionExists (final Course course, final int loggedHours) {
        for (Session session : sessions) {
            if (session.getCourse().equals(course) && session.getLoggedHours() == loggedHours) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of all logged hours for a specific course
     *
     * @param course The course to get all logged hours of
     *
     * @return List of all logged hours
     */
    public ArrayList<Integer> getAllLoggedHoursForCourse(final Course course) {
        final ArrayList<Integer> res = new ArrayList<>();

        for (final Session session : sessions) {
            if (!session.getCourse().equals(course)) {
                continue;
            }

            res.add(session.getLoggedHours());
        }

        return res;
    }

    public ArrayList<String> getAllDateStringsForCourse(final Course course) {
        final ArrayList<String> res = new ArrayList<>();

        for (final Session session : sessions) {
            if (!session.getCourse().equals(course)) {
                continue;
            }
            res.add(session.getDateString());
        }
        return res;
    }

    /**
     * Clears all study sessions regardless of course from session list of type {@code ArrayList<Session>}.
     * Be careful when using this method, action cannot be undone.
     */
    public void clearAllSessions() {
        sessions.clear();
    }

    /**
     * Removes all study sessions related to a specific course named.
     * An iterator is used through the sessions list of type {@code ArrayList<Session>} and deletes
     * every session to the associated {@code Course} matched.
     *
     * @param course The {@code Course} to whose sessions are to be removed.
     */
    public void removeAllSessionsForCourse(final Course course) {
        final Iterator<Session> iterator = sessions.iterator();

        while (iterator.hasNext()) {
            Session nextSession = iterator.next();
            Course nextCourse = nextSession.getCourse();
            if (nextCourse.equals(course)) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns an iterator for the session in the list.
     *
     * @return An iterator over the sessions
     */
    @Override
    public Iterator<Session> iterator() {
        return sessions.iterator();
    }

    /**
     * Returns an {@code Integer} on the number of sessions in the {@code ArrayList<Session>}.
     *
     * @return An integer on the size of {@code sessions}.
     */
    public int getSessionCount() {
        return sessions.size();
    }
}
