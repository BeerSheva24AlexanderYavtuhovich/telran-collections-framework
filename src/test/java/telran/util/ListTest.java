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
    @Override
    void addTest() {
        Integer newEl = 300;
        list.add(newEl);
        assertTrue(list.contains(newEl));
    }

    @Test
    @Override
    void removeTest() {
        Integer newEl = 300;
        Integer elToRemove = 20;

        list.remove(elToRemove);
        assertFalse(list.contains(elToRemove));

        list.add(newEl);
        assertTrue(list.contains(newEl));
        list.remove(newEl);
        assertFalse(list.contains(newEl));

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
    void testIterator() {
        Iterator<Integer> iterator = list.iterator();
        for (Integer el : array) {
            assertTrue(iterator.hasNext());
            assertEquals(el, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
