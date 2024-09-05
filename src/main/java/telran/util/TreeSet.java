package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {

    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        Node<T> current;
        Node<T> last;

        public TreeSetIterator() {
            this.current = getLeastFrom(root);
            this.last = null;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = current;
            current = getNextCurrent(current);
            return last.obj;
        }

        @Override
        public void remove() {
            if (last == null) {
                throw new IllegalStateException();
            }
            removeNode(last);
            last = null;
        }

        private Node<T> getLeastFrom(Node<T> node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        private Node<T> getNextCurrent(Node<T> node) {
            return node.right != null ? getLeastFrom(node.right) : getGreaterParent(node);
        }

        private Node<T> getGreaterParent(Node<T> node) {
            Node<T> parent = node.parent;
            while (parent != null && node == parent.right) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;
        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> node = getNode(pattern);
        if (node != null) {
            removeNode(node);
            res = true;
        }
        return res;
    }

    private void removeNode(Node<T> node) {
        if (node.left == null || node.right == null) {
            removeNonJunction(node);
        } else {
            removeJunction(node);
        }
        size--;
    }

    private void removeJunction(Node<T> node) {
        Node<T> greatest = getGreatestFrom(node.left);
        node.obj = greatest.obj;
        removeNonJunction(greatest);
    }

    private void removeNonJunction(Node<T> node) {
        Node<T> child = (node.left != null) ? node.left : node.right;

        if (node.parent == null) {
            root = child;
        } else {
            if (node == node.parent.left) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }
        if (child != null) {
            child.parent = node.parent;
        }
        setNulls(node);
    }

    private void setNulls(Node<T> node) {
        node.obj = null;
        node.parent = node.left = node.right = null;
    }

    private Node<T> getGreatestFrom(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
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
        Node<T> current = root;
        boolean res = false;
        while (current != null && !res) {
            int compRes = comparator.compare(pattern, current.obj);
            if (compRes == 0) {
                res = true;
            } else {
                current = compRes > 0 ? current.right : current.left;
            }
        }
        return res;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T result = (T) pattern;
        Node<T> node = getNode(result);
        return node != null ? node.obj : null;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current;
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if (res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }
        return res;
    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

    @Override
    public T first() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'first'");
    }

    @Override
    public T last() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'last'");
    }

    @Override
    public T floor(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'floor'");
    }

    @Override
    public T ceiling(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ceiling'");
    }

    @Override
    public SortedSet<T> subSet(T keyFrom, T keyTo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subSet'");
    }
}
