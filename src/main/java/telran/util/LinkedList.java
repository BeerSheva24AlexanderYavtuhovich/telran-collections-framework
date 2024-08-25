package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements List<T> {

    private static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T obj = current.obj;
            current = current.next;

            return obj;
        }
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.prev;
        nodeToInsert.next = nodeBefore;
        nodeToInsert.prev = nodeAfter;
        nodeBefore.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;
    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
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
        return new LinkedListIterator();
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        Node<T> removedNode;
        checkIndex(index, true);
        if (index == 0) {
            removedNode = removeHead();
        } else if (index == size) {
            removedNode = removeTail();
        } else {
            removedNode = removeMiddle(index);
        }
        size--;
        return (T) removedNode.obj;
    }

    private Node<T> removeMiddle(int index) {
        Node<T> removedNode = getNode(index);
        Node<T> nodeBefore = removedNode.prev;
        Node<T> nodeAfter = removedNode.next;

        nodeBefore.next = nodeAfter;
        nodeAfter.prev = nodeBefore;

        return removedNode;
    }

    private Node<T> removeTail() {
        Node<T> removedNode = tail;
        tail = tail.prev;
        return removedNode;
    }

    private Node<T> removeHead() {
        Node<T> removedNode = head;
        head = head.next;

        if (Objects.equals(head, null)) {
            tail = null;
        } else {
            head.prev = null;
        }
        return removedNode;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        Node<T> current = head;
        int index = 0;
        while (index < size && !Objects.equals(current.obj, pattern)) {
            current = current.next;
            index++;
        }
        return index == size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        Node<T> current = tail;
        int index = size - 1;
        while (index >= 0 && !Objects.equals(current.obj, pattern)) {
            current = current.prev;
            index--;
        }
        return index >= 0 ? index : -1;
    }

}
