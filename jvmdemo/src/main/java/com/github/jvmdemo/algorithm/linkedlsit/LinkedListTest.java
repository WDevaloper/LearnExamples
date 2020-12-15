package com.github.jvmdemo.algorithm.linkedlsit;

public class LinkedListTest {

    /**
     * 将 curr 指向的下一节点保存到 next 指针；
     * <p>
     * curr 指向 prev，一起前进一步；
     * <p>
     * 重复之前步骤，直到 k 个元素翻转完毕；
     * <p>
     * 当完成了局部的翻转后，prev 就是最终的新的链表头，curr 指向了下一个要被处理的局部，而原来的头指针 head 成为了链表的尾巴。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        // 找到这轮翻转的链表尾巴
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            // 剩余数量小于k的话，则不需要反转。
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }

        // 返回的翻转后head
        //ListNode newHead = reverse(head, k);
        ListNode newHead = reverse(head, tail);

        //而原来的头指针 head 成为了这一轮翻转链表的尾巴(tail)，即这轮的尾巴只向下一轮的head
        head.next = reverseKGroup(tail, k);

        return newHead;
    }


    public ListNode reverse(ListNode head, int k) {
        ListNode prev = null, curr = head, next = head;

        int n = k;

        while (curr != null && n-- > 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // if (head != null) head.next = reverse(curr, k);

        return prev;
    }

    /*左闭右开区间*/
    public ListNode reverse(ListNode head, ListNode tail) {
        ListNode pre = null;
        ListNode curr = head;
        ListNode next;

        while (curr != tail) {
            next = curr.next;
            curr.next = pre;//断开
            pre = curr;
            curr = next;
        }
        return pre;

    }
}
