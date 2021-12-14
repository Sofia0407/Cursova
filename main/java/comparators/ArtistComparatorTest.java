package comparators;

import entities.artists.Artist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistComparatorTest {
    @Test
    void compareAscTrue() {
        assertEquals(-1, new ArtistComparator("name", true).compare(new Artist("Aaaa", " "), new Artist("Bbbb", " ")));
    }

    @Test
    void compareAscFalse() {
        assertEquals(1, new ArtistComparator("name", true).compare(new Artist("Bbb", " "), new Artist("Aaa", " ")));
    }

    @Test
    void compareDescTrue() {
        assertEquals(1, new ArtistComparator("name", false).compare(new Artist("Aaaa", " "), new Artist("Bbbb", " ")));
    }

    @Test
    void compareDescFalse() {
        assertEquals(-1, new ArtistComparator("name", false).compare(new Artist("Bbbb", " "), new Artist("Aaaa", " ")));
    }
}