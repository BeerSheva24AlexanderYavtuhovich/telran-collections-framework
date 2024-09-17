package telran.util;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class SortedSetTest extends SetTest {
    SortedSet<Integer> sortedSet;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        sortedSet = (SortedSet<Integer>) collection;
    }

    @Test
    void floorTest() {
        assertEquals(10, sortedSet.floor(10));
        assertNull(sortedSet.floor(-11));
        assertEquals(10, sortedSet.floor(11));
        assertEquals(100, sortedSet.floor(101));
    }

    @Test
    void ceilingTest() {
        assertEquals(10, sortedSet.ceiling(10));
        assertNull(sortedSet.ceiling(101));
        assertEquals(17, sortedSet.ceiling(11));
        assertEquals(-10, sortedSet.ceiling(-11));
    }

    @Test
    void firstTest() {
        assertEquals(-10, sortedSet.first());
    }

    @Test
    void lastTest() {
        assertEquals(100, sortedSet.last());
    }

    @Test
    void subSetTest() {
        Integer[] expected = { 10, 17 };
        Integer[] actual = sortedSet.subSet(10, 20).stream().toArray(Integer[]::new);
        assertArrayEquals(expected, actual);
    }
    
    @Override
    protected void fillBigCollection() {
        Integer[] array = getBigArrayCW();
        Arrays.stream(array).forEach(collection::add);
    }

    protected Integer[] getBigArrayCW() {
        return new Random().ints().distinct().limit(N_ELEMENTS).boxed().toArray(Integer[]::new );
    }
    protected Integer[] getBigArrayHW() {
       //TODO
       //return array that have already balanced order
       return null;
    }


    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }
}
