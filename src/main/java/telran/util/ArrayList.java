package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] array;
    private int size;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not correct");
        }
        @SuppressWarnings("unchecked")
        T removed = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removed;
    }

    @Override
    public boolean remove(T pattern) {
        int index = indexOf(pattern);
        boolean wasRemoved = index >= 0;
        if (wasRemoved) {
            remove(index);
        }
        return wasRemoved;
    }

    @Override
    public int indexOf(T pattern) {
        int i = 0;
        int result = -1;

        while (i < size && result == -1) {
            if (array[i].equals(pattern)) {
                result = i;
            }
            i++;
        }

        return result;
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
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) array[index++];
            }

        };
    }

    @Override
    public boolean add(T obj) {
        if (size == array.length) {
            reallocate();
        }
        array[size++] = obj;
        return true;
    }

    @Override
    public void add(int index, T obj) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not correct");
        }

        if (size == array.length) {
            reallocate();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not correct");
        }
        return (T) array[index];
    }

    @Override
    public int lastIndexOf(T pattern) {
        int i = size - 1;
        int result = -1;

        while (i >= 0 && result == -1) {
            if ((array[i] == null && pattern == null) || (array[i] != null && array[i].equals(pattern))) {
                result = i;
            }
            i--;
        }

        return result;
    }

}
