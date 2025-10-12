package seedu.nustudy.session;

import seedu.nustudy.course.Course;

public class Session {
    private final int loggedHours;
    private final Course course;

    public Session(Course course, int loggedHours) {
        this.loggedHours = loggedHours;
        this.course = course;
    }

    @Override
    public String toString() {
        return "You have studied for " + loggedHours + "hours" + "for " + course.getCourseName();
    }
}
