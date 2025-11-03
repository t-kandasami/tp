package arpa.home.nustudy.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.Session;

public class DataParser {

    /**
     * Converts a stored session in the format {@code "S|COURSE_NAME"} back into
     * a {@code Course} with the course name for loading.
     *
     * @param storageString The stored session string from the data file.
     *
     * @return A {@code Course} which contains the course name.
     *
     * @throws NUStudyException If required format (empty segments between "|") is wrong.
     */
    public static Course parseCourse(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\t");

        if (!parts[0].equals("C")) {
            throw new NUStudyException("Invalid course type: " + storageString);
        }
        if (parts.length != 2) {
            throw new NUStudyException("Invalid course format: " + storageString);
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty: " + storageString);
        }

        return new Course(parts[1]);
    }

    /**
     * Converts a stored session in the format {@code "S|COURSE_NAME|LOGGED_HOURS"} back into
     * an {@code Object[]} with the course name and logged session hours for loading.
     *
     * @param storageString The stored session string from the data file.
     * @return An {@code Object[]} where {@code Object[0]} is the course name and {@code Object[1]}
     *     is the logged session hours.
     * @throws NUStudyException If required format (empty segments between "|") is wrong.
     */
    public static Session parseSession(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\t");

        if (!parts[0].equals("S")) {
            throw new NUStudyException("Invalid course type: " + storageString
                    + "\nDiscarding data....");
        }
        if (parts.length != 4) {
            throw new NUStudyException("Invalid session hours format: " + storageString
                    + "\nDiscarding data....");
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty: " + storageString
                    + "\nDiscarding data....");
        }
        if (parts[2].isEmpty()) {
            throw new NUStudyException("Session hours cannot be empty: " + storageString
                    + "\nDiscarding data....");
        }
        if (parts[3].trim().isEmpty()) {
            throw new NUStudyException("Session date cannot be empty: " + storageString
                    + "\nDiscarding data....");
        }

        String courseName = parts[1];
        double hours;
        LocalDate sessionDate;

        try {
            hours = HourValidator.parseHours(parts[2].trim());
            if (hours < 0) {
                throw new NUStudyException("Invalid session hours format: " + storageString
                        + "\nDiscarding data...");
            }
            sessionDate = LocalDate.parse(parts[3].trim());
        } catch (NumberFormatException e) {
            throw new NUStudyException("Fail to parse Study Hours: " + storageString
                    + "\nDiscarding data....");
        } catch (DateTimeParseException e) {
            throw new NUStudyException("Fail to parse Study Hours: " + storageString
                    + "\nDiscarding data....");
        }
        Course course = new Course(courseName);

        return new Session(course, hours, sessionDate);
    }
}
