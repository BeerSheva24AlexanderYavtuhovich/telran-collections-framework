package telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        assertEquals(array.length + 2, collection.size());
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }

    // TODO
    // all collection tests
}
