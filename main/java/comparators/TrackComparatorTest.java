package comparators;

import entities.artists.Artist;
import entities.disks.Disk;
import entities.tracks.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.Genre;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrackComparatorTest {
    Track a;
    Track b;

    @BeforeEach
    void setUp() {
        a = new Track("AAA", new Artist("A", " "), 222, Genre.Unknown);
        b = new Track("BBB", new Artist("B", " "), 333, Genre.Ambient);
    }

    @Test
    void compareNamesAsc() {
        assertEquals(-1, new TrackComparator("name", true).compare(a, b));
    }
    @Test
    void compareNamesDesc() {
        assertEquals(1, new TrackComparator("name", false).compare(a, b));
    }

    @Test
    void compareLengthAsc() {
        assertEquals(-1, new TrackComparator("length", true).compare(a, b));
    }
    @Test
    void compareLengthDesc() {
        assertEquals(1, new TrackComparator("length", false).compare(a, b));
    }
}