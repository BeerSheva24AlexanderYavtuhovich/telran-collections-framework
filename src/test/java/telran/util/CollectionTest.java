package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public abstract class CollectionTest {
    protected static final int N_ELEMENTS = 1_048_576;
    protected Collection<Integer> collection;
    Random random = new Random();
    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void addNonExistingTest() {
        assertTrue(collection.add(200));
        assertEquals(array.length + 1, collection.size());
    }

    @Test
    void addExistingTest() {
        assertTrue(collection.add(17));
        assertEquals(array.length + 1, collection.size());
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
    void iteratorTest() {
        Iterator<Integer> iterator = collection.iterator();
        for (Integer el : array) {
            assertTrue(iterator.hasNext());
            assertEquals(el, iterator.next());
        }
        assertFalse(iterator.hasNext());
        assertThrowsExactly(NoSuchElementException.class, iterator::next);
    }

    @Test
    void removeInIteratorTest() {
        Iterator<Integer> iterator = collection.iterator();
        assertThrowsExactly(IllegalStateException.class, () -> iterator.remove());
        Integer n = iterator.next();
        iterator.remove();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertFalse(collection.contains(n));
        assertThrowsExactly(IllegalStateException.class, () -> iterator.remove());
    }

    @Test
    void iteratorEmptyTest() {
        ArrayList<Integer> emptyCollection = new ArrayList<>();
        Iterator<Integer> iterator = emptyCollection.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void removeIfTest() {
        assertTrue(collection.removeIf(n -> n % 2 == 0));
        assertFalse(collection.removeIf(n -> n % 2 == 0));
        assertTrue(collection.stream().anyMatch(n -> n % 2 != 0));

    }

    @Test
    void clearTest() {
        collection.clear();
        assertTrue(collection.isEmpty());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void performanceTest() {
        long start = System.nanoTime();

        fillBigCollection();
        collection.clear();
        fillBigCollection();
        collection.removeIf(n -> n % 2 == 0);
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
        collection.clear();
        assertTrue(collection.isEmpty());

        long end = System.nanoTime();
        System.out.println("Execution time: " + (end - start) / 1_000_000 + " ms");
    }

    protected void fillBigCollection() {
        IntStream.range(0, N_ELEMENTS).forEach(i -> collection.add(random.nextInt()));
    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }

}
