package com.github.jvmdemo.algorithm.linkedlsit;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTestTest {

    @Test
    public void reverseKGroup() {
        ListNode head = new ListNode(1);
        ListNode ListNode1 = new ListNode(2);
        ListNode ListNode2 = new ListNode(3);
        ListNode ListNode3 = new ListNode(4);
        ListNode ListNode4 = new ListNode(5);
        ListNode ListNode5 = new ListNode(6);

        head.next = ListNode1;
        ListNode1.next = ListNode2;
        ListNode2.next = ListNode3;
        ListNode3.next = ListNode4;
        ListNode4.next = ListNode5;


        // 翻转链表
        LinkedListTest linkedListTest = new LinkedListTest();
        ListNode reverseKGroup = linkedListTest.reverseKGroup(head, 2);
        System.out.println("reverseKGroup = " + reverseKGroup.value);

        while (reverseKGroup != null) {
            System.out.println(reverseKGroup.value);
            reverseKGroup = reverseKGroup.next;
        }
    }
}