package telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;

public class HashMapTest extends AbstractMapTest {

    @Override
    <T> void runTest(T[] expected, T[] actual) {
        T[] expectedSorted = Arrays.copyOf(expected, expected.length);
        T[] actualSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Arrays.sort(actualSorted);
        assertArrayEquals(expectedSorted, actualSorted);
    }

    @BeforeEach
    @Override
    void setUp() {
        map = new HashMap<>();
        super.setUp();
    }

}
