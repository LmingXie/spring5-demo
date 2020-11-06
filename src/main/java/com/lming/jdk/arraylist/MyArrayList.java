package com.lming.jdk.arraylist;

import com.lming.jdk.MyList;

import java.util.Arrays;

/**
 * 功能描述：仿写的ArrayList
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 09:46
 **/
public class MyArrayList<E> implements MyList<E> {
    /**
     * 元素数据
     */
    transient Object[] elementData;
    /**
     * 定义一个默认空容量的元素数据
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 定义一个默认容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 模数 遵循了Fail-Fast原则，方系统出现异常时，程序应当快速失败，而不是异常的运行下去。
     */
    protected transient int modCount = 0;

    /**
     * 元素数据长度，代表了实际的元素数量
     */
    private int size;

    /**
     * 定义队列的最大元素数据长度
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        // 扩容检查，ArrayList在每次添加元素时进行扩容检查
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 确保容量足够
     *
     * @param minCapacity 最小容量
     */
    private void ensureCapacityInternal(int minCapacity) {
        // 通过calculateCapacity选取合适的最小容量，ensureExplicitCapacity检查是否需要扩容
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    /**
     * 计算容量
     *
     * @param elementData
     * @param minCapacity
     * @return
     */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // 元素数组为空，表示首次进行扩容
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 首次扩容时，选择默认容量与最小容量中的较大者作为本次扩容的长度，因此首次返回的minCapacity为 10
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        // 对于非首次进行扩容检查，直接使用最小容量最为扩容的标准
        return minCapacity;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // 10 - 0 = 10 > 0 故而进行扩容 ,实际情况是不允许出现 10 - 11的情况的，根据rangeCheckForAdd的检查得知。
        if (minCapacity - elementData.length > 0) {
            // 进行扩容
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        // 临时记录 旧数组的长度
        int oldCapacity = elementData.length;
        // 我们将扩容后的数组称为新数组，新数组的长度为旧数组的两倍，>> 表示二进制右移
        // 右移算法的结果与对原数据乘以 2的n次方是一致的，因此这里 (oldCapacity >> 1) = oldCapacity * 2^1
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        // 新数组的长度 小于最小容量，则设置新数组的长度为 最小容量
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }

        // 超过最大容量（溢出）时，进行溢出检查
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        // 这里直接使用Arrays.copyOf进行数组的复制，复制的过程创建一个新容量的数组，然后通过System.arraycopy() 实现数组元素的复制
        // System.arraycopy() 是Native方法，以为着真正的复制是通过C实现的
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 溢出检查
     *
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {

        // 溢出，java对于无法表示的巨大整数，采用负数的形式表达，因此这里直接抛出内存溢出异常
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        // 对于未出现类型溢出的情况下，验证最大队列容量，选择合理的容量作为新数组的最大容量
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    @Override
    public E get(int index) {
        // 索引范围检测
        rangeCheck(index);
        // 直接从元素数据数组中获取对应索引存放的值
        return elementData(index);
    }

    private void rangeCheck(int index) {
        // 当索引超过真实索引长度时，抛出索引越界异常
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public E remove(int index) {
        // 检查索引的范围
        rangeCheck(index);

        // 模数++，模数的作用在程序出现异常时，快速抛出异常，此处具体防止的是 多线程并发写入时的数据紊乱异常，当发生并发写入时，并不会立马抛出异常，而是在之后的读（forEach）操作时会进行modCount的检查。
        modCount++;

        // 获取到要删除的旧数据
        E oldValue = elementData(index);

        // 获取需要移动的数组元素数量 = 真实容量 - 待删除元素的索引 - 1，-1是因为 要删除一个元素，因此移动的数组元素数量 -1
        int numMoved = size - index - 1;
        // (numMoved > 0) 表示后面还有元素，若不大于0，则表示待删除元素后面无元素，自然无需进行数据的移动，直接将最后一个元素置空即可
        if (numMoved > 0) {
            // 同样通过System.arraycopy()方法进行数组的复制，注意这里的新数组和原数组为同一个


            /* @param      src      原数组
             * @param      srcPos   原数组中的起始位置
             * @param      dest     目标数组
             * @param      destPos  目标数组中的起始位置
             * @param      length   要复制的数组元素的数量
             */
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        // 将最后一个元素置空
        elementData[--size] = null;

        // 返回删除的元素信息
        return oldValue;
    }

    public void add(int index, E element) {
        // 检查索引是否越界
        rangeCheckForAdd(index);

        // 扩容检查
        ensureCapacityInternal(size + 1);
        // 将元素进行后移
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        // 覆盖原有元素值
        elementData[index] = element;
        // 容量++
        size++;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }


    @Override
    public String toString() {
        return "MyArrayList{" +
                "elementData=" + Arrays.toString(elementData) +
                ", modCount=" + modCount +
                ", size=" + size +
                '}';
    }
}
