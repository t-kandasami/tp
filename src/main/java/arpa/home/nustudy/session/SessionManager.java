package arpa.home.nustudy.session;

import java.util.ArrayList;
import java.util.Iterator;

import arpa.home.nustudy.course.Course;

public class SessionManager {
    private final ArrayList<Session> sessions = new ArrayList<>();

    /**
     * Adds a study session to the studySessions
     *
     * @param course      The course object want to add study session
     * @param loggedHours The amount of hours spent for a study session
     */
    public void add(final Course course, final int loggedHours) {
        sessions.add(new Session(course, loggedHours));
    }

    /**
     * Get a list of all logged hours for a specific course
     * @param course The course to get all logged hours of
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

    /**
     * Clears all study sessions regardless of course from session list of type {@code ArrayList<Session>}.
     * Be careful when using this method, action cannot be undone.
     */
    public void clearAllSessions() {
        sessions.clear();
    }

    /**
     * Removes all study sessions related to a specific course named.
     * An iterator is used through the the sessions list of type {@code ArrayList<Session>} and deletes
     * every session to the associated {@code Course} matched.
     *
     * @param course The {@code Course} to whose sessions are to be removed.
     */
    public void removeAllSessionsForCourse(final Course course) {
        final Iterator<Session> iterator = sessions.iterator();

        while (iterator.hasNext()) {
            Session nextSession =  iterator.next();
            Course nextCourse = nextSession.getCourse();
            if (nextCourse.equals(course)) {
                iterator.remove();
            }
        }
    }
}
