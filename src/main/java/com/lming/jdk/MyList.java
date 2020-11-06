package com.lming.jdk;

/**
 * 功能描述：仿写List接口
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 09:48
 **/
public interface MyList<E> {

    /**
     * 获取size
     *
     * @return size
     */
    int size();

    /**
     * 新增
     *
     * @param e 元素
     * @return
     */
    boolean add(E e);

    /**
     * 根据下标获取元素
     *
     * @param index 下标
     * @return
     */
    E get(int index);

    /**
     * 删除元素
     *
     * @param index 下标
     * @return
     */
    E remove(int index);

}
