package arpa.home.nustudy.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
     * Parses then loads the stored data from a buffered reader into the {@code CourseManager} and {@code
     * SessionManager} instances.
     *
     * @param courses The {@code CourseManager} instance to load in the loaded courses.
     * @param sessions The {@code SessionManager} instance to load in the loaded sessions.
     * @param buffer The duplicate copy of type {@code BufferedReader} of the storage file.
     * @throws IOException If an I/O error is raised while reading from the buffer.
     */
    private static void loadData(CourseManager courses, SessionManager sessions,
            final BufferedReader buffer) throws IOException {
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();

            try {
                if (line.startsWith("C|")) {
                    final Course course = DataParser.parseCourse(line);
                    courses.add(course);
                } else if (line.startsWith("S|")) {
                    final Session session = DataParser.parseSession(line);
                    Course sessionCourse = session.getCourse();

                    Course matchingCourse = null;
                    for (Course c : courses) {
                        if (c.getCourseName().equals(sessionCourse.getCourseName())) {
                            matchingCourse = c;
                            break;
                        }
                    }

                    if (matchingCourse != null) {
                        sessions.add(matchingCourse, session.getLoggedHours(), session.getDate());
                    } else {
                        logger.log(Level.WARNING, "Session course is invalid: " + sessionCourse.getCourseName());
                    }
                }
            } catch (NUStudyException e) {
                logger.log(Level.WARNING, "Unable to parse line: ");
                logger.log(Level.INFO, "Error message: " + e.getMessage());
            }
        }
    }

    /**
     * Loads data of types {@code Course} and {@code Session} from the stored text file.
     *
     * @param courses The {@code CourseManager} instance to load in the loaded courses with.
     * @param sessions The {@code SessionManager} instance to load in the loaded sessions with.
     * @throws FileNotFoundException
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
            logger.log(Level.INFO, "Loading data from: " + dataFile.getAbsolutePath());
            loadData(courses, sessions, buffer);
            logger.log(Level.INFO, "Successfully loaded all data from: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to load data from: " + dataFile.getAbsolutePath());
            logger.log(Level.INFO, "Error message: " + e.getMessage());
        }
    }
}
