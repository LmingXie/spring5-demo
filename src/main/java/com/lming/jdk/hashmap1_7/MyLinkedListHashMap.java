package com.lming.jdk.hashmap1_7;

import com.alibaba.fastjson.JSON;
import com.lming.jdk.MyMap;

import java.util.LinkedList;

/**
 * 功能描述：基于LinkedList实现的HashMap
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 14:33
 **/
public class MyLinkedListHashMap<K, V> implements MyMap<K, V> {

    private LinkedList<Entry>[] entryData = new LinkedList[10];

    transient int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(Object key) {
        int hashCode = hashCode(key);
        LinkedList<Entry> e = entryData[hashCode];
        if (e == null) {
            return null;
        }
        for (Entry entry : e) {
            if (entry.getKey().equals(key)) {
                return (V) entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(final K key, final V value) {
        int hashCode = hashCode(key);
        LinkedList<Entry> e = entryData[hashCode];
        // 哈希碰撞了
        if (e != null) {
            // 已存在相同Key TODO 碰撞后直接覆盖
            for (Entry entry : e) {
                if (entry.getKey().equals(key)) {
                    V oldV = (V) entry.getValue();
                    entry.setValue(value);
                    entryData[hashCode] = e;
                    return oldV;
                }
            }
            // 不存在相同Key
            e.add(new Entry(key, value));
        } else {
            e = new LinkedList() {{
                add(new Entry<>(key, value));
            }};
        }
        entryData[hashCode] = e;
        size++;
        return value;
    }

    @Override
    public V remove(Object key) {
        int hashCode = hashCode(key);
        LinkedList<Entry> e = entryData[hashCode];
        if (e == null) {
            return null;
        }
        if (e.size() == 1) {
            entryData[hashCode] = null;
            size--;
            return (V) e.get(0).getValue();
        }
        for (Entry entry : e) {
            if (entry.getKey().equals(key)) {
                e.remove(key);
                entryData[hashCode] = e;
                size--;
                return (V) entry.getValue();
            }
        }
        return null;
    }

    private int hashCode(Object key) {
        return key.hashCode() % entryData.length;
    }


    private static class Entry<K, V> implements MyMap.Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }

    @Override
    public String toString() {
        return "MyLinkedListHashMap{" +
                "entryData=" + JSON.toJSONString(entryData) +
                ", size=" + size +
                '}';
    }
}
