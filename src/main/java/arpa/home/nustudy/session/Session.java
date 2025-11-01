package arpa.home.nustudy.session;

import static arpa.home.nustudy.utils.DateParser.formatDate;

import java.time.LocalDate;


import arpa.home.nustudy.course.Course;

/**
 * Represents a course identified by its name
 */
public class Session {
    /**
     * The object of the course
     * <p>
     * The loggedHours for each session
     */
    private int loggedHours;
    private final Course course;
    private LocalDate date;

    /**
     * Creates a new Course with the specific name
     *
     * @param course      The object of the Course
     * @param loggedHours the amount of hours spent
     * @param date        The date of Study Session
     */
    public Session(final Course course, final int loggedHours, final LocalDate date) {
        this.loggedHours = loggedHours;
        this.course = course;
        this.date = date;
    }

    /**
     * Returns a string representation of the course
     *
     * @return The course name
     */
    @Override
    public String toString() {
        return "You have studied for " + loggedHours + "hours" + "for " + course.getCourseName();
    }

    /**
     * Get logged hours for this session
     *
     * @return Logged hours for this session
     */
    public int getLoggedHours() {
        return loggedHours;
    }

    /**
     * Set logged hours for this session
     *
     * @param loggedHours Logged hours for this session
     */
    public void setLoggedHours(final int loggedHours) {
        this.loggedHours = loggedHours;
    }

    /**
     * Get course for this session
     *
     * @return Course for this session
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Returns a string representation be stored in data file. The format is "S|Course_Name|Logged_Hours|Date"
     *
     * @return A string format in the formate "S|Course_Name|Logged_Hours|Date"
     */
    public String toStorageString() {
        return "S\t" + course.getCourseName() + "\t" + loggedHours + "\t" + date;
    }

    /**
     * Return the date of the session.
     *
     * @return The LocalDate object representing the date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a formatted string representation of the date.
     *
     * @return The date formatted as a string.
     */
    public String getDateString() {
        return formatDate(this.date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
