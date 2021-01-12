package com.github.jvmdemo.algorithm.stack;

public class Stack {
    private StackNode head;

    public synchronized Character pop() {
        if (head == null) {
            return null;
        }
        StackNode nodeTop = head;
        head = head.next;
        nodeTop.next = null;
        return nodeTop.value;
    }


    public Character push(Character item) {
        StackNode newNode = new StackNode(item);
        newNode.next = head;
        head = newNode;
        return item;
    }

    public static class StackNode {
        public StackNode next;
        public Character value;

        public StackNode(Character value) {
            this.value = value;
        }
    }
}
