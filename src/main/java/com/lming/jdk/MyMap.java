package com.lming.jdk;

/**
 * 功能描述：仿写Map接口
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 13:53
 **/
public interface MyMap<K, V> {
    /**
     * 获取真实容量
     *
     * @return
     */
    int size();

    /**
     * 根据Key 获取Value
     *
     * @param key
     * @return
     */
    V get(Object key);


    /**
     * 新增
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * 移除
     *
     * @param key
     * @return
     */
    V remove(Object key);

    interface Entry<K, V> {
        /**
         * 获取Key
         *
         * @return
         */
        K getKey();

        /**
         * 获取Value
         *
         * @return
         */
        V getValue();

        /**
         * 设置Value
         *
         * @param value
         * @return
         */
        V setValue(V value);
    }
}
