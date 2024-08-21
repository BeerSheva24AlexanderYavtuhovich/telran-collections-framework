package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void removeTest() {
        assertTrue(collection.remove(-10));
        assertFalse(collection.contains(-10));
        assertFalse(collection.remove(200));
    }

    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        clearCollection();
        assertTrue(collection.isEmpty());
    }

    private void clearCollection() {
        while (!collection.isEmpty()) {
            collection.remove(collection.iterator().next());
        }
    }

    @Test
    void containsTest() {
        assertTrue(collection.contains(3));
        assertFalse(collection.contains(200));
    }

    @Test
    void streamTest() {
        Stream<Integer> stream = collection.stream();
        assertEquals(array.length, stream.count());
    }

    @Test
    void parallelStreamTest() {
        Stream<Integer> parallelStream = collection.parallelStream();
        assertEquals(array.length, parallelStream.count());
    }

    @Test
    void iteratorHasNextTest() {
        Iterator<Integer> iterator = collection.iterator();
        for (Integer el : array) {
            assertTrue(iterator.hasNext());
            assertEquals(el, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorNextTest() {
        Iterator<Integer> iterator = collection.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            assertEquals(array[index++], iterator.next());
        }
    }

    @Test
    void iteratorEmptyTest() {
        ArrayList<Integer> emptyCollection = new ArrayList<>();
        Iterator<Integer> iterator = emptyCollection.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

}
