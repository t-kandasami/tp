package arpa.home.nustudy.session;

import java.util.ArrayList;

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
}
