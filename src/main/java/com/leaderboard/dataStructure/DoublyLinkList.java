package com.leaderboard.dataStructure;

import com.leaderboard.utils.PreCondition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
public class DoublyLinkList<E> implements LinkList<E> {
    private Node<E> head;
    private Node<E> tail;
    private Map<E, Node<E>> map = new HashMap<E, Node<E>>();
    private int size = 0;

    @Override
    public synchronized void addFirst(E e) {
        PreCondition.notNull(e, "Object must be non null");
        Node<E> node = new Node(e, null, head);
        if(head != null)
            head.prev = node;
        head = node;
        if(tail == null) {
            tail = node;
        }
        map.put(e, node);
        size++;
    }

    @Override
    public synchronized void addLast(E e) {
        PreCondition.notNull(e, "Object must be non null");
        Node<E> node = new Node(e, tail, null);
        if(tail!=null)
            tail.next = node;
        tail = node;
        if(head == null) {
            head = node;
        }
        map.put(e, node);
        size++;
    }

    @Override
    public synchronized void remove(E e) {
        Node node = map.get(e);
        if(node != null) {
            if(node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = head.next;
            }

            if(node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = tail.prev;
            }

            node.prev = node.next = null;
            map.remove(e);
            size--;
        }
    }

    @Override
    public Node<E> find(E e) {
        return map.get(e);
    }

    @Override
    public Node<E> head() {
        return head;
    }

    @Override
    public Node<E> tail() {
        return tail;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> cur = head;
        if(cur!=null) {
            while (cur.getNext() != null) {
                sb.append(cur.get().toString()).append(", ");
                cur = cur.getNext();
            }
            sb.append(cur.get().toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public static class Node<E> {
        private E e;
        private Node prev;
        private Node next;

        private Node(E e) {
            this(e, null, null);
        }

        private Node(E e, Node prev, Node next) {
            this.e = e;
            this.prev = prev;
            this.next = next;
        }

        public E get() {
            return e;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            if (e != null ? !e.equals(node.e) : node.e != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return e != null ? e.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "e=" + e +
                    '}';
        }
    }
}
