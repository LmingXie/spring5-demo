package com.lming.jdk.hashmap1_8;

import java.util.TreeMap;

/**
 * 功能描述：红黑树实现
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-19 15:43
 **/
public class RedBlackTree<V> {

    private transient Node<V> root;

    transient int size;

    private static class Node<V> {
        private int index;
        private V value;
        private Node<V> right;
        private Node<V> left;
        private Node<V> parent;
        private NodeColour colour;

        public Node(int index, V value, Node<V> parent, NodeColour colour) {
            this.index = index;
            this.value = value;
            this.parent = parent;
            this.colour = colour;
        }
    }

    private enum NodeColour {
        RED, BLACK;
    }

    public V add(int index, V value) {
        if (root == null) {
            root = new Node(index, value, null, NodeColour.BLACK);
            return null;
        }

        // 存在根节点，进行
        Node<V> t = root;
        Node newNode = null;
        do {
            // 右子树
            if (t.index > index) {
                if (t.right == null) {
                    newNode = new Node(index, value, t, NodeColour.RED);
                    t.right = newNode;
                    break;
                } else {
                    t = t.right;
                }
            }
            // 左子树
            else {
                if (t.left == null) {
                    newNode = new Node(index, value, t, NodeColour.RED);
                    t.left = newNode;
                    break;
                } else {
                    t = t.left;
                }
            }
        } while (t != null);

        fixAfterInsertion(newNode);
        size++;
        return null;
    }

    private void fixAfterInsertion(Node node) {
        // 当父节点的颜色为红色，变更颜色
        if(node.parent.colour.equals(NodeColour.RED)){

        }
    }


    public static void main(String[] args) {
        TreeMap<Object, Object> treeMap = new TreeMap<>();
    }
}
