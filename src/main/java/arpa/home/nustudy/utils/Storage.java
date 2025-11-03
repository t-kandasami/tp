package arpa.home.nustudy.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.Session;
import arpa.home.nustudy.session.SessionManager;

/**
 * Handles loading and saving tasks to a data file.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final File dataFile;

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param filepath The path to the data file
     */
    public Storage(final String filepath) {
        dataFile = new File(filepath);
    }

    /**
     * Checks and ensures that parent directory of queried data file exists.
     *
     * @return {@code true} if parent directory exists or was created successfully if not previously existing,
     *         {@code false} if creating directory fails.
     */
    private boolean ensureParentDirectoryExists() {
        final File parent = dataFile.getParentFile();

        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            logger.log(Level.SEVERE, "Unable to create directory: "
                    + parent.getAbsolutePath());
            return false;
        }

        assert parent == null || (parent.exists() && parent.isDirectory())
                : "Parent should exist and be a directory: " + parent;
        return true;
    }

    /**
     * Saves the course list and session list to the data file.
     *
     * @param courses  The course list to save
     * @param sessions The session list to save
     *
     * @throws IOException If writing to the file fails
     */
    private void saveData(CourseManager courses, SessionManager sessions)
            throws IOException {
        try (final FileWriter fw = new FileWriter(dataFile, false)) {

            logger.log(Level.INFO, "Saving courses");
            for (final Course course : courses) {
                fw.write(course.toStorageString());
                fw.write(System.lineSeparator());
            }

            logger.log(Level.INFO, "Saving sessions");
            for (final Session session : sessions) {
                fw.write(session.toStorageString());
                fw.write(System.lineSeparator());
            }
        }
    }

    /**
     * Saves the courses and sessions to the data file.
     *
     * @param courses  The course list to save
     * @param sessions The session list to save
     */
    public void save(CourseManager courses, SessionManager sessions) {
        assert courses != null;
        assert sessions != null;

        try {
            if (!ensureParentDirectoryExists()) {
                return;
            }

            logger.log(Level.INFO, "Saving courses to: "
                    + dataFile.getAbsolutePath());

            saveData(courses, sessions);

            logger.log(Level.INFO, "Saved courses to: "
                    + dataFile.getAbsolutePath());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to save courses: ");
        }
    }

    /**
     * Parses and loads the stored data from a buffered reader into the {@code CourseManager} and {@code
     * SessionManager} instances. Entries must be in TAB-separated format.
     *
     * <ul>
     *     <li>Course: {@code C\tCOURSE_NAME}</li>
     *     <li>Session: {@code S\tCOURSE_NAME\tHOURS\tDATE}</li>
     * </ul>
     * invalid or corrupted entires are ignored and reported to the user.
     *
     * @param courses The {@code CourseManager} instance to load courses into.
     * @param sessions The {@code SessionManager} instance to load sessions into.
     * @param buffer The {@code BufferedReader} for reading the storage file.
     * @throws IOException If an I/O error is raised while reading from the buffer.
     */
    private static void loadData(CourseManager courses, SessionManager sessions,
            final BufferedReader buffer) throws IOException {
        String line;
        ArrayList<String> ignoredEntries = new ArrayList<>();

        while ((line = buffer.readLine()) != null) {
            line = line.trim();

            try {
                // Checks if line is manually modified with spaces " " instead of tabs "\t"
                if ((line.startsWith("C ") || line.startsWith("S ") || !line.contains("\t"))) {
                    ignoredEntries.add("Entry ignored: " + line + "\nReason: manually injected data");
                    logger.log(Level.WARNING, "Manually injected data detected: " + line);
                    continue;
                }

                if (line.startsWith("C")) {
                    final Course course = DataParser.parseCourse(line);
                    if (courses.findCourse(course.getCourseName()) != null) {
                        ignoredEntries.add("Entry ignored: " + line + "\nReason: duplicate course");
                        logger.log(Level.WARNING, "Duplicate course ignored: " + line);
                        continue;
                    }
                    courses.add(course);
                } else if (line.startsWith("S")) {
                    final Session session = DataParser.parseSession(line);
                    Course sessionCourse = session.getCourse();

                    Course matchingCourse = null;
                    for (Course c : courses) {
                        if (c.getCourseName().equalsIgnoreCase(sessionCourse.getCourseName())) {
                            matchingCourse = c;
                            break;
                        }
                    }

                    if (matchingCourse != null) {
                        sessions.add(matchingCourse, session.getLoggedHours(), session.getDate());
                    } else {
                        ignoredEntries.add("Entry ignored: " + line + "\nReason: session course not found");
                        logger.log(Level.WARNING, "Session course is invalid: " + sessionCourse.getCourseName());
                    }
                }
            } catch (NUStudyException e) {
                ignoredEntries.add("Entry ignored: " + line + "\nReason: " + e.getMessage());
                logger.log(Level.WARNING, "Unable to parse on line: " + line + " - " + e.getMessage());
            }
        }

        if (!ignoredEntries.isEmpty()) {
            System.out.println("\n=== Entries with issues ===\n");
            for (String ignoredEntry : ignoredEntries) {
                System.out.println(ignoredEntry);
            }
            System.out.println("\n=== Entries with issues ===\n");
            System.out.println("Please do not modify the data file unnecessarily!\n");
        }
    }

    /**
     * Loads {@code Course} and {@code Session} data from the stored text file.
     * Creates a parent directory for data file if non-existent.
     * If file is non-existent, an empty dataset is initialised.
     * Handles corrupted or invalid entries gracefully by logging them and notifying the user.
     *
     * @param courses The {@code CourseManager} instance to load courses into.
     * @param sessions The {@code SessionManager} instance to load sessions into.
     */
    public void load(CourseManager courses, SessionManager sessions) {
        if (!ensureParentDirectoryExists()) {
            return;
        }

        if (!dataFile.exists()) {
            logger.log(Level.INFO, "No previous storage: " + dataFile.getAbsolutePath());
            logger.log(Level.INFO, "Starting with new dataset");
            return;
        }

        try (BufferedReader buffer = new BufferedReader(new FileReader(dataFile))) {
            loadData(courses, sessions, buffer);
            logger.log(Level.INFO, "Successfully loaded all data from: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to load data from: " + dataFile.getAbsolutePath());
            logger.log(Level.INFO, "Error message: " + e.getMessage());
        }
    }
}
