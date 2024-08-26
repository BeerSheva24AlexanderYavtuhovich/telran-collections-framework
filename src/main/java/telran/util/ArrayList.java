package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

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
        checkIndex(index, false);
        @SuppressWarnings("unchecked")
        T removed = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removed;
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        while (index < size && !Objects.equals(array[index], pattern)) {
            index++;
        }
        return index == size ? -1 : index;
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
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            private boolean flNext = false;

            @Override
            public boolean hasNext() {
                flNext = true;
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

            @Override
            public void remove() {
                if (!flNext) {
                    throw new IllegalStateException();
                }
                ArrayList.this.remove(--index);
                flNext = false;
            }

        };
    }

    @Override
    public boolean removeIf(Predicate<T> predicate) {
        boolean removed = false;
        int newIndex = 0;

        for (int currentIndex = 0; currentIndex < size; currentIndex++) {
            T element = (T) array[currentIndex];
            if (!predicate.test(element)) {
                array[newIndex++] = element;
            } else {
                removed = true;
            }
        }

        for (int i = newIndex; i < size; i++) {
            array[i] = null;
        }

        size = newIndex;
        return removed;
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
        int index = size - 1;
        while (index >= 0 && !Objects.equals(array[index], pattern)) {
            index--;
        }
        return index;
    }

}
