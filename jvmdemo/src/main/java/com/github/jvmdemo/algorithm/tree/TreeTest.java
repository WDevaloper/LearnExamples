package com.github.jvmdemo.algorithm.tree;

public class TreeTest {
    /**
     * 方法：先访问根节点，然后访问左子树，最后访问右子树。在访问左、右子树的时候，同样，先访问子树的根节点，再访问子树根节点的左子树和右子树，这是一个不断递归的过程。
     * <p>
     * 应用场景：运用最多的场合包括在树里进行搜索以及创建一棵新的树。
     */
    public void preorderTraversal(TreeNode root) {
        if (root == null) return;
        System.out.println("root = " + root.value);
        if (root.left != null) preorderTraversal(root.left);
        if (root.right != null) preorderTraversal(root.right);
    }


    public TreeNode buildTree(int[] array, int index) {
        TreeNode treeNode = null;
        if (index < array.length) {
            treeNode = new TreeNode(array[index]);
            // 对于顺序存储的完全二叉树，如果某个节点的索引为index，其对应的左子树的索引为2*index+1，右子树为2*index+2
            treeNode.left = buildTree(array, 2 * index + 1);
            treeNode.right = buildTree(array, 2 * index + 2);
        }
        return treeNode;
    }

    public int kthSmallest(TreeNode root, int k) {
        return 0;
    }
}
