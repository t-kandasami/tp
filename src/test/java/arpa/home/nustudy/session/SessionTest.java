package arpa.home.nustudy.session;

import arpa.home.nustudy.course.Course;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    void testStorageStringConversion() {
        Course course = new Course("CS2113");
        Session session = new Session(course, 5);
        assertEquals("S|CS2113|5", session.toStorageString());
    }
}
