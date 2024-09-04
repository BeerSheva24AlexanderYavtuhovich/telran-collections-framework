package telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Map.Entry;

public abstract class AbstractMapTest {
    protected Map<Integer, Integer> map;
    protected Integer[] testKeys = { 10, -2, 12, -3 };

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        for (Integer key : testKeys) {
            map.put(key, key * key);
        }
    }

    @Test
    void getTest() {
        for (Integer key : testKeys) {
            Integer expectedValue = key * key;
            Integer actualValue = map.get(key);
            assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    void putTest() {
        map.put(-3, generateValueFromKey(-3));
        map.put(20, generateValueFromKey(20));
        map.put(-15, generateValueFromKey(-15));

        Integer[] expectedKeys = { 10, -2, 12, -3, 20, -15 };
        Integer[] expectedValues = generateValuesFromKeys(expectedKeys);

        assertEquals(generateValueFromKey(10), map.get(10));
        assertEquals(generateValueFromKey(20), map.get(20));
        assertEquals(generateValueFromKey(-15), map.get(-15));

        Set<Integer> actualKeysSet = map.keySet();
        Integer[] actualKeys = new Integer[actualKeysSet.size()];
        Integer[] actualValues = new Integer[map.size()];

        int index = 0;
        for (Integer key : actualKeysSet) {
            actualKeys[index] = key;
            actualValues[index] = map.get(key);
            index++;
        }

        Arrays.sort(actualKeys); 
        Arrays.sort(actualValues); 

        runTest(expectedKeys, actualKeys);
        runTest(expectedValues, actualValues);
    }

    @Test
    void containsKeyTest() {
        for (var key : testKeys) {
            assertTrue(map.containsKey(key));
        }
        assertFalse(map.containsKey(99));
    }

    @Test
    void keySetTest() {
        Set<Integer> actualKeySet = map.keySet();
        Integer[] actual = actualKeySet.stream().toArray(Integer[]::new);
        Arrays.sort(actual);       
        runTest(testKeys, actual);
    }

    @Test
    void entrySetTest() {
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        assertEquals(4, entries.size());
        assertTrue(entries.contains(new Entry<>(10, 100)));
        assertTrue(entries.contains(new Entry<>(-2, 4)));
        assertTrue(entries.contains(new Entry<>(12, 144)));
        assertTrue(entries.contains(new Entry<>(-3, 9)));
        assertFalse(entries.contains(new Entry<>(-99, 99)));
    }

    @Test
    void valuesTest() {
        Collection<Integer> values = map.values();
        assertEquals(4, values.size());
        assertTrue(values.contains(100));
        assertTrue(values.contains(4));
        assertFalse(values.contains(99));
    }

    @Test
    void sizeTest() {
        assertEquals(4, map.size());
        map.put(4, 1);
        assertEquals(5, map.size());
    }

    @Test
    void isEmptyTest() {
        assertFalse(map.isEmpty());
        map = new HashMap<>();
        assertTrue(map.isEmpty());
    }

    abstract <T> void runTest(T[] expected, T[] actual);

    public static Integer[] generateValuesFromKeys(Integer[] keys) {
        Integer[] values = new Integer[keys.length];
        for (int i = 0; i < keys.length; i++) {
            values[i] = keys[i] * keys[i];
        }
        return values;
    }

    public static Integer generateValueFromKey(Integer key) {
        return key * key;
    }

}
