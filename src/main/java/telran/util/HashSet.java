package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;

    private class HashSetIterator implements Iterator<T> {
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;
        int indexIterator;
        boolean hasNextElement;

        public HashSetIterator() {
            this.indexIterator = 0;
            this.currentIterator = getCurrentIterator();
            this.hasNextElement = (currentIterator != null && currentIterator.hasNext());
        }

        @Override
        public boolean hasNext() {
            return hasNextElement;
        }

        @Override
        public T next() {
            if (!hasNextElement) {
                throw new NoSuchElementException();
            }
            prevIterator = currentIterator;
            T nextElement = currentIterator.next();
            if (!currentIterator.hasNext()) {
                currentIterator = getCurrentIterator();
            }
            hasNextElement = (currentIterator != null && currentIterator.hasNext());
            return nextElement;
        }

        @Override
        public void remove() {
            if (prevIterator == null) {
                throw new IllegalStateException();
            }

            prevIterator.remove();
            prevIterator = null;
            size--;
        }

        private Iterator<T> getCurrentIterator() {
            Iterator<T> iterator = null;
            while (indexIterator < hashTable.length && iterator == null) {
                List<T> bucket = hashTable[indexIterator++];
                if (bucket != null && !bucket.isEmpty()) {
                    iterator = bucket.iterator();
                }
            }

            return iterator;
        }
    }

    public HashSet(int hashTableLength, float factor) {
        hashTable = new List[hashTableLength];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if (size >= hashTable.length * factor) {
                hashTableReallocation();
            }
            addObjInHashTable(obj, hashTable);
            size++;
        }
        return res;
    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }
        list.add(obj);
    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    private void hashTableReallocation() {
        List<T>[] tempTable = new List[hashTable.length * 2];
        for (List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
                list.clear();
            }
        }
        hashTable = tempTable;
    }

    @Override
    public boolean remove(T pattern) {
        boolean removed = false;
        if (contains(pattern)) {
            int index = getIndex(pattern, hashTable.length);
            List<T> list = hashTable[index];
            list.remove(pattern);
            size--;
            removed = true;
        }
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        int index = getIndex((T) pattern, hashTable.length);
        List<T> list = hashTable[index];

        if (list != null) {
            int i = 0;
            while (i < list.size() && res == null) {
                T element = list.get(i);
                if (element.equals(pattern)) {
                    res = element;
                }
                i++;
            }
        }

        return res;
    }

}
