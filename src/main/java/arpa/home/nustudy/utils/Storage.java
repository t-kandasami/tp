package arpa.home.nustudy.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
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
            for (Course course : courses) {
                fw.write(course.toStorageString());
                fw.write(System.lineSeparator());
            }

            logger.log(Level.INFO, "Saving sessions");
            for (Session session : sessions) {
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
        try {
            final File parent = dataFile.getParentFile();

            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                logger.log(Level.SEVERE, "Unable to create directory: "
                        + parent.getAbsolutePath());
                return;
            }

            assert parent == null || (parent.exists() && parent.isDirectory())
                    : "Parent should exist and be a directory: " + parent;

            logger.log(Level.INFO, "Saving courses to: "
                    + dataFile.getAbsolutePath());

            saveData(courses, sessions);

            logger.log(Level.INFO, "Saved courses to: "
                    + dataFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}
