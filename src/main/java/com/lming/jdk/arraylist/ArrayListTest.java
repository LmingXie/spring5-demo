package com.lming.jdk.arraylist;

/**
 * 功能描述：
 *
 * @program: spring5-demo
 * @author: LM.X
 * @create: 2020-10-12 09:44
 **/
public class ArrayListTest {

    public static void main(String[] args) {
        MyArrayList<String> arrayList = new MyArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");


        System.out.println(arrayList.get(0) + ",当前队列长度：" + arrayList.size());
        System.out.println(arrayList.get(1) + ",当前队列长度：" + arrayList.size());

        // remove() 方法的本质是进行数组元素的移动，删除某个元素，以为着其后面的所有元素都必须向前进行移位，覆盖前一个元素，最后，将末尾的元素置空
        System.out.println("删除前元素内容：" + arrayList);
        // 可以发现，若数组非常大时，将会产生非常多的空元素位。
        arrayList.remove(0);
        System.out.println("删除后元素内容：" + arrayList);

        // 对于此种新增，则直接对目标元素后的元素进行后移操作，然后覆盖即可
        arrayList.add(1, "zhangsan");
        System.out.println(arrayList.get(1) + ",当前队列长度：" + arrayList.size());
    }
}
