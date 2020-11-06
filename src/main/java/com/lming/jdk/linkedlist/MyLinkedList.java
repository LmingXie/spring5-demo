package com.lming.jdk.linkedlist;

import com.lming.jdk.MyList;

/**
 * 功能描述：仿写的LinkedList
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 11:12
 **/
public class MyLinkedList<E> implements MyList<E> {
    transient int size = 0;
    /**
     * 定义首个节点
     */
    transient MyLinkedList.Node<E> first;
    /**
     * 定义队列最后一个节点
     */
    transient MyLinkedList.Node<E> last;
    protected transient int modCount = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        // 将元素连接到最后
        linkLast(e);
        return true;
    }

    void linkLast(E e) {
        final MyLinkedList.Node<E> l = last;
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(l, e, null);
        last = newNode;

        if (first == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        // 检查节点索引
        checkElementIndex(index);
        // 折半查找
        return node(index).item;
    }

    MyLinkedList.Node<E> node(int index) {
        // 前半部分
        if (index < (index >> 1)) {
            MyLinkedList.Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;

        }
        // 后半部分
        else {
            MyLinkedList.Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        // node查询到对应的节点，unlink 进行指针变动
        return unlink(node(index));
    }

    E unlink(MyLinkedList.Node<E> x) {
        final E element = x.item;
        final MyLinkedList.Node<E> next = x.next;
        final MyLinkedList.Node<E> prev = x.prev;

        if (prev == null) {
            // 说明是第一个元素，因此first 变成下一个元素
            first = next;
        } else {
            // 不是第一个元素，当前元素的前一个元素的下一个元素，变成当前元素的下一个元素
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            // 说明是最后一个元素，则前一个元素的next置空
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    private static class Node<E> {
        Node<E> prev;
        E item;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    " item=" + item +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyLinkedList{" +
                "size=" + size +
                ", first=" + first +
                ", last=" + last +
                ", modCount=" + modCount +
                '}';
    }
}
