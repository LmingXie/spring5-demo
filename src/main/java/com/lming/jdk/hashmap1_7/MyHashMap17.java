package com.lming.jdk.hashmap1_7;

import com.alibaba.fastjson.JSON;
import com.lming.jdk.MyMap;

import java.util.Map;

/**
 * 功能描述：仿写 jdk1.7的HashMap
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-14 14:05
 **/
public class MyHashMap17<K, V> implements MyMap<K, V> {

    /**
     * 默认容量 必须是2的n次方 TODO 为什么必须是2的n次方？
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 默认负载因子 TODO 为什么是0.75？
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 最大容量，如果指定的容量大于最大容量，则应用MAXIMUM_CAPACITY。
     * 1 << 30 = 2^30
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * 触发扩容的阈值
     */
    int threshold;

    /**
     * 存储数据的表。HashMap将之称为表，默认大小为16的数组。
     * 每个数组又存有由{@link Entry}结构钢组成的一个单向链表，也称为“bucket”，“桶”
     */
    transient Entry<K, V>[] table;

    transient int size;

    public MyHashMap17() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }


    private static class Holder {
        /**
         * 备用哈希阈值，要在其之上切换以使用备用哈希。
         * 通过VM Options 设置 -Djdk.map.althashing.threshold=100
         * 默认：Integer.MAX_VALUE
         */
        static final int ALTERNATIVE_HASHING_THRESHOLD;

        /**
         * 不安全的实用程序
         */
        static final sun.misc.Unsafe UNSAFE = null;

        /**
         * 我们必须在readObject（）方法中设置的“final” hashSeed字段的偏移量
         */
        static final long HASHSEED_OFFSET = 0;
        /**
         * 替代哈希阈值默认值
         */
        static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

        static {
            // doPrivileged() 表示如果允许当前代码行获取环境配置信息，请给我"jdk.map.althashing.threshold"配置信息，stackoverflow：https://stackoverflow.com/questions/4954924/getpropertyaction-vs-system-getproperty-in-obtaining-system-variables
            // 测试：VM Options 设置 -Djdk.map.althashing.threshold=100
            String altThreshold = java.security.AccessController.doPrivileged(
                    new sun.security.action.GetPropertyAction(
                            "jdk.map.althashing.threshold"));

            int threshold;
            try {
                // 优先级： altThreshold > threshold
                threshold = (null != altThreshold)
                        ? Integer.parseInt(altThreshold)
                        : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

                // 如果altThreshold设置为-1，使用 Integer.MAX_VALUE > MAXIMUM_CAPACITY
                if (threshold == -1) {
                    threshold = Integer.MAX_VALUE;
                }

                if (threshold < 0) {
                    throw new IllegalArgumentException("value must be positive integer.");
                }
            } catch (IllegalArgumentException failed) {
                throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
            }

            // 替代哈希阈值
            ALTERNATIVE_HASHING_THRESHOLD = threshold;
/*
            try {
                // 初始化 UNSAFE，通过Unsafe可以在任意内存位置读写数据
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                // objectFieldOffset 获取一个类的某个成员属性在内存中的地址 相对于 自身对象在内存中的地址的偏移量
                HASHSEED_OFFSET = UNSAFE.objectFieldOffset(MyHashMap17.class.getDeclaredField("hashSeed"));
            } catch (NoSuchFieldException | SecurityException e) {
                throw new Error("Failed to record hashSeed offset", e);
                // UNSAFE 权限过大，只允许JDK内部使用。
            }*/
        }
    }

    /**
     * 用户减少哈希碰撞概率的随机数，可以理解为盐。用于对Key进行hash操作，使哈希碰撞的概率更小
     */
    transient final int hashSeed = 6666;

    /**
     * 如果为{@code true}，则在进行key哈希计算时使用，使用hashSeed作为初始哈希种子，以提高由于哈希码计算能力较弱而导致的冲突发生率。
     * 如果为{@code false}，则使用0作为初始哈希种子，相对而言hash碰撞概率更高。
     */
    transient boolean useAltHashing;

    transient int modCount;

    public MyHashMap17(int initialCapacity, float loadFactor) {
        // 初始容量小于0  抛出异常
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        // 超过最大容量
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }

        // 校验loadFactor合法性
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        }

        // 找到比 initialCapacity 大的第一个2的n次方的整数
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }

        // 初始化负载因子
        this.loadFactor = loadFactor;

        // 扩容阈值 = Min[容量 * 负载因子，最大容量 + 1] TODO 为什么是 MAXIMUM_CAPACITY + 1 答：
        this.threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);

        // 初始化数组
        table = new Entry[capacity];

        // useAltHashing用于减少hash碰撞的概率，当容量大于Holder.ALTERNATIVE_HASHING_THRESHOLD时有较高的哈希碰撞概率。
        // 默认：capacity >= Integer.MAX开启
        useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(Object key) {
        if (null == key) {
            return getForNullKey();
        }
        final Entry<K, V> e = getEntry(key);
        return e == null ? null : e.value;
    }

    private V getForNullKey() {
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            // 如果表中存在hash相同的对象也相同的对象，则直接覆盖。==判断基本数据类型，equals判断对象类型
            if (e.key == null) {
                return e.value;
            }
        }
        return null;
    }

    final Entry<K, V> getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key);
        for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            Object k;
            if (hash == e.hash && (key == (k = e.key) || (k != null && key.equals(k)))) {
                return e;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (null == key) {
            return putForNullKey(value);
        }
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
            Object k;
            // 如果表中存在hash相同的对象也相同的对象，则直接覆盖。==判断基本数据类型，equals判断对象类型
            if (hash == e.hash && (key == (k = e.key) || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        // 新增
        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }

    /**
     * 向某个Bucket的最前面插入新的元素
     *
     * @param hash
     * @param key
     * @param value
     * @param bucketIndex 桶索引
     */
    void addEntry(int hash, K key, V value, int bucketIndex) {
        // 判断是否扩容 TODO 为什么判断 null != table[bucketIndex] ?
        if (size >= threshold && null != table[bucketIndex]) {
            resize(2 * table.length);
            // 重新计算hash
            hash = (key != null) ? hash(hash) : 0;
            // 重新获取桶索引
            bucketIndex = indexFor(hash, table.length);
        }

        // 新增元素
        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> next = table[bucketIndex];
        table[bucketIndex] = new Entry(key, value, hash, next);
        size++;
    }

    void resize(int newCapacity) {
        Entry<K, V>[] oldTable = this.table;
        int oldCapacity = oldTable.length;
        if (oldCapacity > MAXIMUM_CAPACITY) {
            // TODO ?
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry<K, V>[] newTable = new Entry[newCapacity];

        boolean oldAltHashing = useAltHashing;
        // |：位值同为0则为0，否则为1。前后都为false时 useAltHashing 为false，否则为true。
        useAltHashing |= sun.misc.VM.isBooted() &&
                (newCapacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        // ^：相同位值为0，否则为1。前后值相同为true，否则为false。 默认oldAltHashing ^ useAltHashing = true
        boolean rehash = oldAltHashing ^ useAltHashing;

        // 将旧table数据转移到新table，并重新计算hash值
        transfer(newTable, rehash);
        table = newTable;
        threshold = (int) Math.min(loadFactor * table.length, MAXIMUM_CAPACITY + 1);
    }

    void transfer(Entry[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        for (Entry e : table) {
            while (null != e) {
                Entry next = e.next;
                // 刷新计算hash值 TODO hash为什么需要重新计算？  调试
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                // 重新计算 桶哈希
                int i = indexFor(e.hash, newCapacity);
                // 将当前元素e添加到新表表头
                e.next = newTable[i];
                // 覆盖到新表
                newTable[i] = e;
                e = next;
            }
        }
    }

    @Override
    public V remove(Object key) {
        final Entry<K, V> e = removeEntryForKey(key);
        return e == null ? null : e.value;
    }

    final Entry<K, V> removeEntryForKey(Object key) {
        int hash = key == null ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        // 获取前一个entry，默认为第一个
        Entry<K, V> prev = table[i];

        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
            Object k;
            // hash相同，并且对象相同
            if (e.hash == hash && (key == (k = e.key) || (key != null && key.equals(k)))) {
                modCount++;
                size--;

                Entry<K, V> next = e.next;
                // (prev == e)说明移除的是第一个entry
                if (prev == e) {
                    // 将next赋给 table[i]，抛弃 e
                    table[i] = next;
                } else {
                    // 否则，说明移除的是之后的元素，则将prev.next 指向 next，抛弃 e
                    prev.next = next;
                }
                return e;
            }
            // 若entry不匹配，则将当前 e 指向prev，继续循环
            prev = e;
        }
        return null;
    }

    private V putForNullKey(V value) {
        // 发生hash碰撞时，进行值覆盖
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (null == e.key) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        addEntry(0, null, value, 0);
        return null;
    }

    /**
     * 扰动函数（对k进行哈希运算）
     *
     * @param k
     */
    final int hash(Object k) {
        int h = 0;
        // 当容量大于 Holder.ALTERNATIVE_HASHING_THRESHOLD（默认Integer.MAX）时，h值改变为hashSeed 目的是使哈希结果更加离散
        if (useAltHashing) {
//            if (k instanceof String) {
//                // 对于String类型则使用stringHash32进行32-bit的hash运算，此种运算对于所有k都会添加一个HASHING_SEED，降低哈希碰撞概率
//                return sun.misc.Hashing.stringHash32((String) k);
//            }
            h = hashSeed;
        }
        h ^= k.hashCode();

        // 对Hash进行扰动，扰动后的结果更加离散，减少Hash碰撞
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * 计算Hash在table中的索引位置
     *
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        // length - 1 的原因：
        // java int类型为32位，若一个数字a的二进制长度不足32位时，向前补0，此为无效位，而a的二进制表示则为最低有效位
        // 与运算：对位同为1结果为1，其他请款均为0.
        // 对于&运算，定义（a & b）且a > b时，结果中b的无效位全部为0。
        // length 永远是2的n次方，二进制表示则是1后面n个0，根据上面的定义，对于此结果进行与运算，将有极大概率使数据落入同一table中，怎么解决？
        // length-1用二进制表示则n+1个1，比如16 = 4+1个1 = 11111，相对于以上而言则会使与运算的结果更加离散。
        return h & (length - 1);
    }

    /**
     * Entry是单向链表的结构
     */
    static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        int hash;
        Entry<K, V> next;

        public Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;

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
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    @Override
    public String toString() {
        return "MyHashMap17{" +
                ", table=" + JSON.toJSONString(table) +
                ", size=" + size +
                ", hashSeed=" + hashSeed +
                ", modCount=" + modCount +
                '}';
    }
}
