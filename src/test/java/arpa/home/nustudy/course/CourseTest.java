package arpa.home.nustudy.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.utils.DataParser;

class CourseTest {

    @Test
    void testStorageStringConversion() {
        Course course = new Course("CS2113");
        assertEquals("C|CS2113", course.toStorageString());
    }

    @Test
    void testStringStorageConversion() {
        String storedCourseName = "C|CS2113";
        Course course = null;
        try {
            course = DataParser.parseCourse(storedCourseName);
        } catch (NUStudyException e) {
            throw new RuntimeException(e);
        }
        assertEquals("CS2113", course.getCourseName());
    }
}
