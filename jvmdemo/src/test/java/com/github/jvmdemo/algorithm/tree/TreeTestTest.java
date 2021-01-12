package com.github.jvmdemo.algorithm.tree;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTestTest {

    @Test
    public void preorderTraversal() {
        TreeTest treeTest = new TreeTest();
        int[] array = new int[]{2, 6, 9, 7, 3, 5, 6, 1, 6, 5, 8, 2, 6, 7, 6};
        TreeNode root = treeTest.buildTree(array, 0);
        treeTest.preorderTraversal(root);
    }
}