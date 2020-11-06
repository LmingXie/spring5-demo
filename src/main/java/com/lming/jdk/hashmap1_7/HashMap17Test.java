package com.lming.jdk.hashmap1_7;

import java.util.HashMap;

/**
 * 功能描述：HashMap1.7仿写测试
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 11:55
 **/
public class HashMap17Test {
    public static void main(String[] args) {
        //  ArrayList 实现
//        testByMyArrayListHashMap();
        //  LinkedList + 数组 实现链表结构 没有解决hash碰撞的问题
//        testByMyLinkedListHashMap();
        // JDK1.7 实现
        testByMyJdk1_7HashMap();
        // JDK1.8 红黑树实现

        HashMap<Object, Object> hashMap = new HashMap<>();

    }


    public static void testByMyJdk1_7HashMap(){
        MyHashMap17<Object, Object> hashMap = new MyHashMap17<>();
        hashMap.put("10001", "zhangsan");
        hashMap.put("10002", "lisi");
        hashMap.put("10003", "wangwu");
        hashMap.put("10001", "zhangsan");
        hashMap.put("10002", "lisi");
        hashMap.put("10003", "wangwu");
        hashMap.put("1", "1");
        hashMap.put("2", "2");
        hashMap.put("3", "3");
        hashMap.put("4", "4");
        hashMap.put("5", "5");
        hashMap.put("6", "6");
        hashMap.put("7", "7");
        hashMap.put("8", "8");
        hashMap.put("9", "9");
        hashMap.put("10", "10");
        hashMap.put("11", "11");
        System.out.println(hashMap.get("10001") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("10002") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("10003") + ",当前集合长度：" + hashMap.size());

        System.out.println("删除前元素内容：" + hashMap);
        // 可以发现，若数组非常大时，将会产生非常多的空元素位。
        hashMap.remove("10002");
        System.out.println("删除10002后元素内容：" + hashMap);
        hashMap.remove("10003");
        System.out.println("删除10003后元素内容：" + hashMap);

        hashMap.put("10001", "XLM");
        System.out.println("修改后元素内容：" + hashMap);

        // hash碰撞模拟
        hashMap.put("a", "a");
        hashMap.put(97, 97);
        System.out.println("碰撞后a：" + hashMap.get("a"));
        System.out.println("碰撞后97：" + hashMap.get(97));
    }

    public static void testByMyLinkedListHashMap() {
        MyLinkedListHashMap<Object, Object> hashMap = new MyLinkedListHashMap<>();
        // 数组 + ArrayList 实现
        hashMap.put("10001", "zhangsan");
        hashMap.put("10002", "lisi");
        hashMap.put("10003", "wangwu");
        hashMap.put("1", "1");
        hashMap.put("2", "2");
        hashMap.put("3", "3");
        hashMap.put("4", "4");
        hashMap.put("5", "5");
        hashMap.put("6", "6");
        hashMap.put("7", "7");
        hashMap.put("8", "8");
        hashMap.put("9", "9");
        hashMap.put("10", "10");
        hashMap.put("11", "11");

        System.out.println(hashMap.get("10001") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("10002") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("4") + ",当前集合长度：" + hashMap.size());

        System.out.println("删除前元素内容：" + hashMap);
        // 可以发现，若数组非常大时，将会产生非常多的空元素位。
        hashMap.remove("10002");
        System.out.println("删除后元素内容：" + hashMap);

        hashMap.put("10001", "XLM");
        System.out.println("修改后元素内容：" + hashMap);

        // hash碰撞模拟
        hashMap.put("a", "a");
        hashMap.put(97, 97);
        System.out.println("碰撞后a：" + hashMap.get("a"));
        System.out.println("碰撞后97：" + hashMap.get(97));
    }

    /**
     * 测试 基于ArrayList实现的HashMap集合
     */
    public static void testByMyArrayListHashMap() {
        MyArrayListHashMap<String, String> hashMap = new MyArrayListHashMap<>();
        // 数组 + ArrayList 实现
        hashMap.put("10001", "zhangsan");
        hashMap.put("10002", "lisi");
        hashMap.put("10003", "wangwu");

        System.out.println(hashMap.get("10001") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("10002") + ",当前集合长度：" + hashMap.size());
        System.out.println(hashMap.get("10003") + ",当前集合长度：" + hashMap.size());

        System.out.println("删除前元素内容：" + hashMap);
        // 可以发现，若数组非常大时，将会产生非常多的空元素位。
        hashMap.remove("10002");
        System.out.println("删除后元素内容：" + hashMap);

        hashMap.put("10001", "XLM");
        System.out.println("修改后元素内容：" + hashMap);
    }
}
