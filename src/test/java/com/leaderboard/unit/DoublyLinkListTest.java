package com.leaderboard.unit;

import com.leaderboard.dataStructure.DoublyLinkList;
import org.junit.Test;

/**
 * Created by ankit.chaudhury on 16/08/17.
 */
public class DoublyLinkListTest {
    @Test
    public void test() {
        DoublyLinkList<Integer> list = new DoublyLinkList<Integer>();

        list.addFirst(5);

        assert list.head() == list.find(5);
        assert list.tail() == list.find(5);

        list.remove(5);

        assert list.head() == null;
        assert list.tail() == null;

        list.addLast(10);

        assert list.head() == list.find(10);
        assert list.tail() == list.find(10);
        list.remove(10);

        for(int i=1; i<=10; i++) {
            list.addLast(i);
        }

        list.remove(1);
        assert list.head() == list.find(2);

        list.remove(10);
        assert list.tail() == list.find(9);

        list.remove(5);
        assert list.head() == list.find(2);
        assert list.size() == 7;
    }
}
