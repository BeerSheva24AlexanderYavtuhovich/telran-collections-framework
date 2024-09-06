package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private final LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;
        }
        return res;
    }

    @Override
    public T get(Object pattern) {
        return map.get(pattern) == null ? null : map.get(pattern).obj;
    }

    @Override
    public boolean remove(T pattern) {
        boolean removed = false;
        if (map.containsKey(pattern)) {
            list.remove(pattern);
            map.remove(pattern);
            removed = true;
        }
        return removed;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map != null && map.containsKey(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }

    private class LinkedHashSetIterator implements Iterator<T> {
        private final Iterator<T> iterator = list.iterator();
        private T current = null;
        private boolean wasNext = false;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = iterator.next();
            wasNext = true;
            return current;
        }

        @Override
        public void remove() {
            if (!wasNext) {
                throw new IllegalStateException();
            }
            iterator.remove();
            map.remove(current);
            wasNext = false;
        }
    }

}
