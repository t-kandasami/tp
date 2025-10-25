package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.session.SessionManager;

class StorageTest {

    @TempDir
    Path tempDir;

    private Storage storage;
    private CourseManager courseManager;
    private SessionManager sessionManager;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("data/test.txt").toString();
        storage = new Storage(testFilePath);
        courseManager = new CourseManager();
        sessionManager = new SessionManager();
    }

    @Test
    void save_coursesAndSessions_success() throws IOException {
        courseManager.add(new Course("CS2113"));
        courseManager.add(new Course("MA1508E"));
        courseManager.add(new Course("CG2111A"));

        sessionManager.add(courseManager.getCourses().get(0), 5, LocalDate.parse("2025-10-25"));
        sessionManager.add(courseManager.getCourses().get(0), 10, LocalDate.parse("2025-10-25"));
        sessionManager.add(courseManager.getCourses().get(1), 3, LocalDate.parse("2025-10-25"));
        sessionManager.add(courseManager.getCourses().get(1), 5, LocalDate.parse("2025-10-25"));
        sessionManager.add(courseManager.getCourses().get(2), 3, LocalDate.parse("2025-10-25"));
        sessionManager.add(courseManager.getCourses().get(2), 1, LocalDate.parse("2025-10-25"));

        storage.save(courseManager, sessionManager);

        List<String> lines = Files.readAllLines(Paths.get(testFilePath));

        assertEquals(9, lines.size(), "Expect: 9\n Actual: " + lines.size());
        assertEquals("C|CS2113", lines.get(0), "Expect: C|CS2113\n Actual:" + lines.get(0));
        assertEquals("C|MA1508E", lines.get(1), "Expect: C|MA1508E\n Actual:" + lines.get(1));
        assertEquals("C|CG2111A", lines.get(2), "Expect: C|CG2111A\n Actual:" + lines.get(2));

        assertEquals("S|CS2113|5|2025-10-25", lines.get(3), "Expect: S|CS2113|5|2025-10-25\n Actual:"
                + lines.get(3));
        assertEquals("S|CS2113|10|2025-10-25", lines.get(4), "Expect: S|CS2113|10|2025-10-25\n Actual:"
                + lines.get(4));
        assertEquals("S|MA1508E|3|2025-10-25", lines.get(5), "Expect: S|MA1508E|3|2025-10-25\n Actual:"
                + lines.get(5));
        assertEquals("S|MA1508E|5|2025-10-25", lines.get(6), "Expect: S|MA1508E|5|2025-10-25\n Actual:"
                + lines.get(6));
        assertEquals("S|CG2111A|3|2025-10-25", lines.get(7), "Expect: S|CG2111A|3|2025-10-25\n Actual:"
                + lines.get(7));
        assertEquals("S|CG2111A|1|2025-10-25", lines.get(8), "Expect: S|CG2111A|1|2025-10-25\n Actual:"
                + lines.get(8));

    }

    @Test
    void save_emptyCourseAndSession_createsEmptyFile() throws IOException {
        storage.save(courseManager, sessionManager);

        final File file = new File(testFilePath);
        assertTrue(file.exists(), "File should be created");
        assertEquals(0, file.length(), "File length should be zero");
    }

    @Test
    void save_fileAlreadyExists_overwritesFile() throws IOException {
        courseManager.add(new Course("CS2113"));
        sessionManager.add(courseManager.getCourses().get(0), 5, LocalDate.parse("2025-10-25"));
        storage.save(courseManager, sessionManager);

        final List<String> firstSave = Files.readAllLines(Paths.get(testFilePath));
        assertEquals(2, firstSave.size(), "Expect: 2");
        assertEquals("C|CS2113", firstSave.get(0), "Expect: C|CS2113\n Actual:" + firstSave.get(0));
        assertEquals("S|CS2113|5|2025-10-25", firstSave.get(1),
                "Expect: S|CS2113|5|2025-10-25\n Actual:" + firstSave.get(1));

        courseManager.add(new Course("MA1508E"));
        storage.save(courseManager, sessionManager);
        final List<String> secondSave = Files.readAllLines(Paths.get(testFilePath));
        assertEquals(3, secondSave.size(), "Expect: 3");
        assertEquals("C|CS2113", secondSave.get(0), "Expect: C|CS2113\n Actual:" + secondSave.get(0));
        assertEquals("C|MA1508E", secondSave.get(1), "Expect: C|MA1508E\n Actual:" + secondSave.get(0));
        assertEquals("S|CS2113|5|2025-10-25", secondSave.get(2),
                "Expect: S|CS2113|5|2025-10-25\n Actual:" + secondSave.get(1));
    }
}
