package com.lming.jdk.linkedlist;

import java.util.Vector;

/**
 * 功能描述：LinkedList仿写测试
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 11:10
 **/
public class LinkedListTest {

    public static void main(String[] args) {
        MyLinkedList<String> linkedList = new MyLinkedList<>();
        linkedList.add("zhangsan");
        linkedList.add("lisi");
        linkedList.add("wangwu");
        System.out.println(linkedList.get(0) + ",当前队列长度：" + linkedList.size());
        System.out.println(linkedList.get(1) + ",当前队列长度：" + linkedList.size());
        System.out.println(linkedList.get(2) + ",当前队列长度：" + linkedList.size());

        System.out.println("删除前元素内容：" + linkedList);
        // 删除是节点指针方向的改变
        linkedList.remove(0);
        System.out.println("删除后元素内容：" + linkedList);

        Vector<String> vector = new Vector<>();
        vector.add("zhangsan");
        vector.add("lisi");
        vector.add("wangwu");
        System.out.println(vector.get(0) + ",当前队列长度：" + vector.size());
        System.out.println(vector.get(1) + ",当前队列长度：" + vector.size());
        System.out.println(vector.get(2) + ",当前队列长度：" + vector.size());

        System.out.println("删除前元素内容：" + vector);
        // 删除是节点指针方向的改变
        vector.remove(0);
        System.out.println("删除后元素内容：" + vector);
        

    }
}
