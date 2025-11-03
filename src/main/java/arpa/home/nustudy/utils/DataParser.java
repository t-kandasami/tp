package arpa.home.nustudy.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.Session;

public class DataParser {

    /**
     * Converts a stored course entry in the format {@code "S\tCOURSE_NAME"} back into
     * a {@code Course} object for loading.
     *
     * @param storageString The stored course string from the data file.
     * @return A {@code Course} object which contains the course name.
     * @throws NUStudyException If required format is invalid.
     */
    public static Course parseCourse(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\t");

        if (!parts[0].equals("C")) {
            throw new NUStudyException("Invalid course type");
        }
        if (parts.length != 2) {
            throw new NUStudyException("Invalid course format");
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty");
        }

        return new Course(parts[1]);
    }

    /**
     * Converts a stored session in the format {@code "S\tCOURSE_NAME\tLOGGED_HOURS\tDATE"} back into
     * a {@code Session} object for loading.
     *
     * @param storageString The stored session string from the data file.
     * @return An {@code Session} object containing the course, logged hours and date.
     * @throws NUStudyException If required format is invalid.
     */
    public static Session parseSession(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\t");

        if (!parts[0].equals("S")) {
            throw new NUStudyException("Invalid session type");
        }
        if (parts.length != 4) {
            throw new NUStudyException("Invalid session format");
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty");
        }
        if (parts[2].isEmpty()) {
            throw new NUStudyException("Session hours cannot be empty");
        }
        if (parts[3].trim().isEmpty()) {
            throw new NUStudyException("Session date cannot be empty");
        }

        String courseName = parts[1];
        double hours;
        LocalDate sessionDate;

        try {
            hours = HourValidator.parseHours(parts[2].trim());
            if (hours < 0) {
                throw new NUStudyException("Session hours cannot be negative");
            }
        } catch (NumberFormatException e) {
            throw new NUStudyException("Failed to parse study hours");
        }

        try {
            sessionDate = LocalDate.parse(parts[3].trim());
        } catch (DateTimeParseException e) {
            throw new NUStudyException("Failed to parse study session date");
        }

        Course course = new Course(courseName);
        return new Session(course, hours, sessionDate);
    }
}
