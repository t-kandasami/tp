package arpa.home.nustudy.session;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.exceptions.NUStudyException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    void testStringStorageConversion() {
        Course course = new Course("CS2113");
        Session session = new Session(course, 5);
        assertEquals("S|CS2113|5", session.toStorageString());
    }

    @Test
    void testStorageStringConversion() throws NUStudyException {
        String storedCourseName = "S|CS2113|5";
        Object[] expected = {"CS2113", 5};
        Object[] actual = Session.fromStorageString(storedCourseName);
        assertArrayEquals(expected, actual);
    }
}
