package com.lming.jdk.hashmap1_7;

import com.alibaba.fastjson.JSON;
import com.lming.jdk.MyMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：仿写HashMap
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 13:53
 **/
public class MyArrayListHashMap<K, V> implements MyMap<K, V> {

    transient List<Entry> entryData = new ArrayList<>();

    @Override
    public int size() {
        return entryData.size();
    }

    @Override
    public V get(Object key) {
        for (Entry entry : entryData) {
            if (key.equals(entry.getKey())) {
                return (V) entry.getValue();
            }
        }
        return null;
    }

    public Entry getEntry(Object key) {
        for (Entry entry : entryData) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Entry entry = getEntry(key);
        if (entry != null) {
            entry.setValue(value);
            return value;
        }
        entryData.add(new Entry(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        Entry entry = getEntry(key);
        entryData.remove(entry);
        return (V) entry.getValue();
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
        return "MyArrayListHashMap{" +
                "entryData=" + JSON.toJSONString(entryData) +
                '}';
    }
}
