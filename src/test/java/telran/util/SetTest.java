package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class SetTest extends CollectionTest {
    Set<Integer> set;

    @BeforeEach
    @Override
    void setUp() {
        super.setUp();
        set = (Set<Integer>) collection;
    }

    @Test
    @Override
    void addExistingTest() {
        assertFalse(collection.add(17));
        assertEquals(array.length + 1, collection.size());
    }

    @Test
    void getPatternTest() {
        assertEquals(-10, set.get(-10));
        assertNull(set.get(1000000));
    }
}
