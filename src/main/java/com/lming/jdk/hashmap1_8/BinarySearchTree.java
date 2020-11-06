package com.lming.jdk.hashmap1_8;

/**
 * 功能描述：二叉搜索树算法
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-19 13:55
 **/
public class BinarySearchTree<V> {
    /**
     * 第一个叶子节点
     */
    private static volatile Node rootLeaf;
    private static volatile int size;

    static class Node<V> {
        Node<V> left;
        int index;
        V value;
        Node<V> right;

        public Node(int index, V value) {
            this.index = index;
            this.value = value;
        }
    }

    public synchronized int add(int index, V value) {
        if (index < 0) {
            new RuntimeException("非法的索引值");
        }
        if (rootLeaf == null) {
            rootLeaf = new Node(index, value);
            return index;
        }
        return add(index, value, rootLeaf);
    }

    private int add(int index, V value, Node<V> rootLeaf) {
        if (index > rootLeaf.index) {
            if (rootLeaf.right != null) {
                return add(index, value, rootLeaf.right);
            } else {
                rootLeaf.right = new Node<>(index, value);
            }
        } else {
            if (rootLeaf.left != null) {
                return add(index, value, rootLeaf.left);
            } else {
                rootLeaf.left = new Node<>(index, value);
            }
        }
        return index;
    }

    public static void main(String[] args) {
        BinarySearchTree<Object> binarySearchTree = new BinarySearchTree<>();

        int data[] = {4, 7, 2, 5, 1};
        for (int i : data) {
            System.out.println(binarySearchTree.add(i, "第" + i + "号数据"));
        }
    }
}
