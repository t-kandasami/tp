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
}
