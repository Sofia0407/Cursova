package comparators;

import entities.disks.Disk;
import entities.tracks.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiskComparatorTest {
    Disk a;
    Disk b;

    @BeforeEach
    void setUp() {
        a = new Disk("Aaa", 2.4, new ArrayList<Track>());
        b = new Disk("Bbb", 54.4, new ArrayList<Track>());
    }

    @Test
    void compareNamesAsc() {
        assertEquals(-1, new DiskComparator("name", true).compare(a, b));
    }
    @Test
    void compareNamesDesc() {
        assertEquals(1, new DiskComparator("name", false).compare(a, b));
    }

    @Test
    void comparePriceAsc() {
        assertEquals(-1, new DiskComparator("price", true).compare(a, b));
    }
    @Test
    void comparePriceDesc() {
        assertEquals(1, new DiskComparator("price", false).compare(a, b));
    }
}