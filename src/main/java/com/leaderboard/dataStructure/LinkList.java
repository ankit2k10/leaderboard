package com.leaderboard.dataStructure;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */

import com.leaderboard.dataStructure.DoublyLinkList.Node;

public interface LinkList<E> {
    public void addFirst(E e);
    public void addLast(E e);
    public void remove(E e);
    public Node<E> find(E e);
    public Node<E> head();
    public Node<E> tail();
    public int size();
}
