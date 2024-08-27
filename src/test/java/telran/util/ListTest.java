package telran.util;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    // { 3, -10, 20, 1, 10, 8, 100, 17 };

    @Test
    void addTest() {
        Integer newEl = 300;
        list.add(newEl);
        assertTrue(list.contains(newEl));
    }

    @Test
    void addToBeginTest() {
        list.add(0, 999);
        assertEquals(999, list.get(0));
        assertEquals(array.length + 1, list.size());
    }

    @Test
    void addToEndTest() {
        list.add(list.size(), 999);
        assertEquals(999, list.get(list.size() - 1));
        assertEquals(array.length + 1, list.size());
    }

    @Test
    void addInMiddleTest() {
        list.add(4, 999);
        assertEquals(999, list.get(4));
        assertEquals(array.length + 1, list.size());
        assertEquals(10, list.get(5));
    }

    @Test
    void removeHeadTest() {
        list.remove(0);
        assertFalse(list.contains(3));
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void removeMiddleTest() {
        list.remove(4);
        assertFalse(list.contains(10));
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void removeTailTest() {
        list.remove(7);
        assertFalse(list.contains(17));
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void testRemoveAtIndex() {
        assertEquals(20, list.remove(2));
        assertEquals(7, list.size());
        assertFalse(list.contains(20));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(10);
        });

    }

    @Test
    void testSize() {
        assertEquals(array.length, list.size());
        list.remove(1);
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void testIsEmpty() {
        clearList();
        assertTrue(list.isEmpty());
    }

    private void clearList() {
        while (!list.isEmpty()) {
            list.remove(list.iterator().next());
        }
    }

    @Test
    void testContains() {
        assertTrue(list.contains(1));
        assertFalse(list.contains(12));
    }

    @Test
    void testIndexOf() {
        assertEquals(3, list.indexOf(1));
        assertEquals(-1, list.indexOf(12));
    }

    @Test
    void testLastIndexOf() {
        list.add(null);
        assertEquals(8, list.lastIndexOf(null));
    }

    @Test
    void testIterator() {
        Iterator<Integer> iterator = list.iterator();
        for (Integer el : array) {
            assertTrue(iterator.hasNext());
            assertEquals(el, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
