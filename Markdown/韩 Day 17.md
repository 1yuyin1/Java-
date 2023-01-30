# 常用类



## StringBuffer结构剖析

1. StringBuffer的直接父类是AbstractStringBuilder

2. StringBuffer实现了Serializable，即StringBuffer的对象可以串行化

3. 在父类中 AbstractStringBuilder有属性 char[] value，不是final

   该 value数组 存放字符串内容，引出存放在堆中的

4. StringBuffer是一个 final类，不能被继承 



- String VS StringBuffer
  1. String保存的是字符串常量，里面的值不能更改，每次String类的更新实际上是更改地址，效率较低
  2. StringBuffer保存的是字符串变量，里面的值可以更改，每次StringBuffer的更新实际上可以更新内容，不用每次更新地址，效率较高

## StringBuffer转换

- 构造器

1. 构造一个其中不带字符的字符串缓冲区，其初始容量为16个字符
2. 构造一个字符串缓冲区，它包含与指定的CharSequence相同的字符
3. 构造一个不带字符，但具有指定初始容量的字符串缓冲区
4. 构造一个字符串缓冲区去，并将其内容初始化为指定的字符串内容

- String和StringBuffer的转换

  ``` java
  //String--->StringBuffer
  String str = "hello";
  StringBuffer stringbuffer1 = new StringBuffer(str);
  
  StringBuffer stringbuffer2 = new StringBuffer();
  stringbuffer2 = stringbuffer2.append(str);
  //StringBuffer--->String
  StringBuffer  stringbuffer3 = new StringBuffer("韩顺平教育");
  String s = stringbuffer3.toString();
  String s1 = new String(stringbuffer3);
  ```

  

- StringBuffer常见方法
  1. 增 append
  2. 删 delete(start,end)
  3. 改 replace(start,end,string)
  4. 查 indexOf
  5. 插 insert
  6. 获取长度 length

## StringBuilder类

1. 一个可变的字符序列。此类提供一个与StringBuffer兼容的API，但不保证同步（StringBuilder不是线程安全）。此类被设计用作StringBuffer的一个简易替换，用在字符串缓冲区被单个线程使用的时候。如果可能，建议优先采用该类，因为在大多数实现中，它比StringBuffer要快
2. 在StringBuilder上的主要操作是append和insert方法，可重载这些方法，以接收任意类型的数据



老韩解读：

1. StringBuilder继承AbstractStringBuilder类
2. 实现了Serializable，说明StringBuilder对象是可以串行化
3. StringBuilder是final类，不能被继承
4. StringBuilder对象字符序列仍然是存放在其父类 AbstractStringBuilder的 char[] value;
5. StringBuilder的方法，没有做互斥的处理，即没有synchronized关键字，因此在单线程的情况下使用



- String、StringBuffer、StringBuilder比较

  1. StringBuilder和StringBuffer非常相似，均代表可变的字符序列，而且方法也一样

  2. String：不可变字符序列，效率低，但是复用率高

  3. StringBuffer：可变字符序列，效率较高（增删），线程安全

  4. StringBuilder：可变字符序列，效率最高，线程不安全

  5. String使用注意事项：

     String s = "a";//创建了一个字符串

     s +="b";//实际上原来的"a"字符串对象已经丢弃了，现在又产生了一个字符串 s+"b"（也就是"ab"）。如果多次执行这些改变字符串内容的操作，会导致大量副本字符串对象存留在内存中，降低效率。如果这样的操作放到循环中 ，会极大影响程序的性能=>结论：如果我们对String做大量修改，不要使用Sring

- String、StringBuffer、StringBuilder的选择

  使用的原则：

  1. 如果字符串存在大量的修改操作，一般使用StringBuffer或StringBuilder
  2. 如果字符串存在大量的修改操作，并在单线程的情况，使用StringBuilder
  3. 如果字符串存在大量的修改操作，并在多线程的情况，使用StringBuffer
  4. 如果我们字符串很少修改，被多个对象引用，使用String，比如配置信息

## Math类

1. abs绝对值
2. pow求幂
3. ceil 向上取整
4. floor 向下取整
5. round 四舍五入
6. sqrt 求开方
7. random 求随机数
8. max 求两个数的最大值
9. min 求两个数的最小值

``` java
//求a~b的随机数
//(int)(a)<= x <= (int)(a + Math.random() * (b - a + 1))
System.out.println((int)(2 + Math.random() * (7 - 2 +1)));//输出2~7的整数
```

## Arrays类

1. toString
2. sort
   1. 因为数组是引用类型，所以通过sort排序后，会直接影响到实参arr
   2. sort重载的，也可以通过传入一个接口 Comparator实现定制排序
   3. 调用定制排序时，传入两个参数（1）排序的数组 arr（2）实现了Comparator接口的匿名内部类，要求实现 compare方法

3. binarySearch  通过二分搜索法进行查找，要求必须排好序

4. copyOf  数组元素的赋值

5. fill  数组元素的填充

6. equals  比较两个数组元素内容是否完全一致

7. aList 将一组值，转换成list

   ``` java
   List<Integer> aslist = Arrays.asList(2,3,4,5,6,1);
   System.out.println("asList="+ asList);
   ```

   