package seedu.nustudy.session;

import seedu.nustudy.course.Course;

import java.util.ArrayList;

public class SessionManager {
    private final ArrayList<Session> sessions = new ArrayList<>();

    /**
     * Adds a study session to the studySessions
     *
     * @param course      The course object want to add study session
     * @param loggedHours The amount of hours spent for a study session
     */
    public void add(Course course, int loggedHours) {
        sessions.add(new Session(course, loggedHours));
    }
}
