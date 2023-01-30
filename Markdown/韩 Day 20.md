# 集合

## Vector注意事项

1. Vector类的定义说明

   ``` java
   public class Vector<E>
       extends AbstractList<E>
       implements List<E>, RandomAccess, Cloneable, java.io.Serializable
   ```

2. Vector底层也是一个对象数组，protected Object[] elementData;
3. Vector 是线程同步的，即线程安全，Vector的操作方法带有synchronized
4. 在开发中，需要线程同步安全时，考虑使用Vector



Vector扩容机制：如果是无参，默认10，满后，就按2倍扩容  如果指定大小，则每次直接按2倍扩容

## 双向链表模拟

- LinkedList说明
  1. LinkList底层实现了双向链表和双端队列特点
  2. 可以添加任意元素（元素可以重复）包括null
  3. 线程不安全，没有实现同步

- LinkedList
  1. LinkedList底层维护了一个双向链表
  2. LinkedList中维护了两个属性first和last分别指向首节点和尾节点
  3. 每个节点（Node对象），里面又维护了prev、next、item三个属性，其中通过prev指向前一个，通过next指向后一个节点，最终实现双向链表。
  4. 所以LinkedList的元素的添加和删除不是通过数组来完成的，相对来说效率较高

## LinkedList源码图解

``` java
import java.util.LinkedList;

@SuppressWarnings({"all"})
public class temp {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        for (int i = 1; i <= 2; i++) {
            linkedList.add(i);
        }
        linkedList.add(100);
        linkedList.add(100);
        for (Object object : linkedList) {
            System.out.println(object);
        }
        //linkedList.remove(0);
        //linkedList.remove(kk);
        linkedList.set(0, "韩顺平教育");
        System.out.println("===");
        for (Object object : linkedList) {
            System.out.println(object);
        }
        Object object = linkedList.get(0);
        System.out.println("object=" + object);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
    }
}
```

## List集合选择

- ArrayList和LinkedList的比较

  |            | 底层结构 | 增删的效率         | 改查的效率 |
  | ---------- | -------- | ------------------ | ---------- |
  | ArrayList  | 可变数组 | 较低，数组扩容     | 较高       |
  | LinkedList | 双向链表 | 较高，通过链表追加 | 较低       |

如何选择ArrayList和LinkedList

1. 如果我们改查的操作多，选择ArrayList
2. 如果我们增删的操作多，选择LinkedList
3. 一般来说，在程序中80%-90%都是查询，因此大部分情况下会选择ArrayList
4. 在一个项目中，根据业务灵活选择，也可能这样，一个模块使用的是ArrayList，另外一个模块时LinkedList

## Set接口

- Set接口基本介绍
  1. 无序（添加和取出的顺序不一致），没有索引
  2. 不允许重复元素，所以最多一个null

- Set接口的常用方法

  和List接口一样，Set接口也是Collection的子接口，因此，常用方法和Collection接口一样

- Set接口的遍历方式

  同Collection的遍历方式一样，因为Set接口是Collection接口的子接口

  1. 可以使用迭代器
  2. 增强for
  3. 不能使用索引的方式来获取 

## HashSet说明

1. HashSet实现了Set接口

2. HashSet实际上是HashMap

   ``` java
       public HashSet() {
           map = new HashMap<>();
       }
   ```

3. 可以存放null值，但只能有一个null

4. HashSet不保证元素是有序的，取决于hash后，再确定索引的结果

5. 不能有重复元素/对象，在前面Set接口使用已经讲过



- HashSet全面说明

``` java
        HashSet set = new HashSet();
        System.out.println(set.add("john"));//T
        System.out.println(set.add("lucy"));.//T
        System.out.println(set.add("john"));//F
        System.out.println(set.add("jack"));//T
        System.out.println(set.add("Rose"));//T
        set.remove("john");
        System.out.println("set=" + set);

```

``` java
        HashSet set = new HashSet();
        set.add("lucy");//T
        set.add("lucy");//F
        set.add(new Dog("tom"));//T
        set.add(new Dog("tom"));//T
        System.out.println("set=" + set);
```

``` java
        HashSet set = new HashSet();
        set.add(new String("hsp"));//T
        set.add(new String("hsp"));//F
        System.out.println("set=" + set);
```



- HashSet底层机制说明

  - 分析HashSet底层是HashMap，HashMap底层是（数组+链表+红黑树）

    ​    数据的存储与  hash()+equals()  两个方法有重要关系。
    
    ![image-20230101172650183](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230101172650183.png)

## HashSet扩容机制

- HashSet底层说明

  - 分析HashSet的添加元素底层是如何实现（hash()+equals()）

    先说结论，再debug源码+图解

  1. HashSet底层是HashMap
  2. 添加一个元素时，先得到hash值，会转换成索引值
  3. 找到存储数据表table，看这个索引位置是否已经存放的有元素
  4. 如果没有，直接加入
  5. 如果有，调用equals比较，如果相同，就放弃添加，如果不相同，则添加到最后
  6. 在Java8中，如果一条链表的元素个数到达TREEIFY_THRESHOLD（默认是8），并且table的大小>=MIN_TRREIFY_CAPACITY（默认是64），就会进行树化（红黑树）（将该位置的链表树化）

``` java
//源码
		HashSet hashSet = new HashSet();
        hashSet.add("java");
        hashSet.add("php");
        hashSet.add("java");
        System.out.println("set=" + hashSet);
```

``` java
@SuppressWarnings({"all"})
public class HashSetSource {
    public static void main(String[] args) {
 
        HashSet hashSet = new HashSet();
        hashSet.add("java");//到此位置，第1次add分析完毕.
        hashSet.add("php");//到此位置，第2次add分析完毕
        hashSet.add("java");
        System.out.println("set=" + hashSet);
 
        /*
        对HashSet 的源码解读
        1. 执行 HashSet()
            public HashSet() {
                map = new HashMap<>();
            }
        2. 执行 add()
           public boolean add(E e) {//e = "java"
                return map.put(e, PRESENT)==null;//(static) PRESENT = new Object();
           }
         3.执行 put() , 该方法会执行 hash(key) 得到key对应的hash值 算法h = key.hashCode()) ^ (h >>> 16)
             public V put(K key, V value) {//key = "java" value = PRESENT 共享
                return putVal(hash(key), key, value, false, true);
            }
         4.执行 putVal
         final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
                Node<K,V>[] tab; Node<K,V> p; int n, i; //定义了辅助变量
                //table 就是 HashMap 的一个数组，类型是 Node[]
                //if 语句表示如果当前table 是null, 或者 大小=0
                //就是第一次扩容，到16个空间.
                if ((tab = table) == null || (n = tab.length) == 0)
                    n = (tab = resize()).length;
                //(1)根据key，得到hash 去计算该key应该存放到table表的哪个索引位置
                //并把这个位置的对象，赋给 p
                //(2)判断p 是否为null
                //(2.1) 如果p 为null, 表示还没有存放元素, 就创建一个Node (key="java",value=PRESENT)
                //(2.2) 就放在该位置 tab[i] = newNode(hash, key, value, null)
                if ((p = tab[i = (n - 1) & hash]) == null)
                    tab[i] = newNode(hash, key, value, null);
                else {
                    //一个开发技巧提示： 在需要局部变量(辅助变量)时候，在创建
                    Node<K,V> e; K k; //
                    //如果当前索引位置对应的链表的第一个元素和准备添加的key的hash值一样
                    //并且满足 下面两个条件之一:
                    //(1) 准备加入的key 和 p 指向的Node 结点的 key 是同一个对象
                    //(2)  p 指向的Node 结点的 key 的equals() 和准备加入的key比较后相同
                    //就不能加入
                    if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                        e = p;
                    //再判断 p 是不是一颗红黑树,
                    //如果是一颗红黑树，就调用 putTreeVal , 来进行添加
                    else if (p instanceof TreeNode)
                        e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
                    else {//如果table对应索引位置，已经是一个链表, 就使用for循环比较
                          //(1) 依次和该链表的每一个元素比较后，都不相同, 则加入到该链表的最后
                          //    注意在把元素添加到链表后，立即判断 该链表是否已经达到8个结点
                          //    , 就调用 treeifyBin() 对当前这个链表进行树化(转成红黑树)
                          //    注意，在转成红黑树时，要进行判断, 判断条件
                          //    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY(64))
                          //            resize();
                          //    如果上面条件成立，先table扩容.
                          //    只有上面条件不成立时，才进行转成红黑树
                          //(2) 依次和该链表的每一个元素比较过程中，如果有相同情况,就直接break
                        for (int binCount = 0; ; ++binCount) {
                            if ((e = p.next) == null) {
                                p.next = newNode(hash, key, value, null);
                                if (binCount >= TREEIFY_THRESHOLD(8) - 1) // -1 for 1st
                                    treeifyBin(tab, hash);
                                break;
                            }
                            if (e.hash == hash &&
                                ((k = e.key) == key || (key != null && key.equals(k))))
                                break;
                            p = e;
                        }
                    }
                    if (e != null) { // existing mapping for key
                        V oldValue = e.value;
                        if (!onlyIfAbsent || oldValue == null)
                            e.value = value;
                        afterNodeAccess(e);
                        return oldValue;
                    }
                }
                ++modCount;
                //size 就是我们每加入一个结点Node(k,v,h,next), size++
                if (++size > threshold)
                    resize();//扩容
                afterNodeInsertion(evict);
                return null;
            }
         */
 
    }
}
```

 分析HashSet的扩容和转为红黑树机制。

1. HashSet底层是HashMap，第一次添加时，table数组扩容到16，临界值（threshold）是16*加载因子（loadFactor）是0.75 = 12

2. 如果table数组使用到了临界值12，就会扩容到16*2 = 32，新的临界值就是32*0.75 = 24，以此类推；

3. 在Java8中，如果一条链表的元素个数 >= TREEIFY_THRESHOLD(默认是8)，并且table的大小 >= MIN_TREEIFY_CAPACITY(默认64)，就会进行树化（红黑树），**否则仍然采用数组扩容机制**。

``` java
@SuppressWarnings({"all"})
public class temp {
    public static void main(String[] args) {
        /*
        HashSet底层是HashMap, 第一次添加时，table 数组扩容到 16，
        临界值(threshold)是 16*加载因子(loadFactor)是0.75 = 12
        如果table 数组使用到了临界值 12,就会扩容到 16 * 2 = 32,
        新的临界值就是 32*0.75 = 24, 依次类推
         */
        HashSet hashSet = new HashSet();
//        for(int i = 1; i <= 100; i++) {
//            hashSet.add(i);//1,2,3,4,5...100
//        }
        /*
        在Java8中, 如果一条链表的元素个数到达 TREEIFY_THRESHOLD(默认是 8 )，
        并且table的大小 >= MIN_TREEIFY_CAPACITY(默认64),就会进行树化(红黑树),
        否则仍然采用数组扩容机制
         */
 
//        for(int i = 1; i <= 12; i++) {
//            hashSet.add(new A(i));//
//        }
 
 
        /*
            当我们向hashset增加一个元素，-> Node -> 加入table , 就算是增加了一个size++
         */
 
        for(int i = 1; i <= 7; i++) {//在table的某一条链表上添加了 7个A对象
            hashSet.add(new A(i));//
        }
 
        for(int i = 1; i <= 7; i++) {//在table的另外一条链表上添加了 7个B对象
            hashSet.add(new B(i));//
        }
 
 
 
    }
}
 
class B {
    private int n;
 
    public B(int n) {
        this.n = n;
    }
    @Override
    public int hashCode() {
        return 200;
    }
}
 
class A {
    private int n;
 
    public A(int n) {
        this.n = n;
    }
    @Override
    public int hashCode() {
        return 100;
    }
}
```

## HashSet 课堂练习

- 定义一个Employee类，该类包含：private成员属性name，age 要求：

	1. 创建3个Employee放入 HashSet中
	2. 当name和age的值相同时，认为是相同员工，不能添加到HashSet集合中

``` java
import java.util.HashSet;
import java.util.Objects;

@SuppressWarnings({"all"})
public class temp {
    public static void main(String[] args) {
        HashSet hashset = new HashSet();
        hashset.add(new Employee("小红", 21));
        hashset.add(new Employee("小李", 22));
        hashset.add(new Employee("小红", 21));
        System.out.println(hashset);
    }
}
class Employee{
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", age=" + age ;
    }
}
```

- 定义一个Employee类，该类包含：private成员属性name，age ，birthday（MyDate类型，属性包括：years、month、day)要求：
  1. 创建3个Employee放入 HashSet中
  2. 当name和birthday的值相同时，认为是相同员工，不能添加到HashSet集合中

``` java
import java.util.HashSet;
import java.util.Objects;

@SuppressWarnings({"all"})
public class temp {
    public static void main(String[] args) {
        HashSet hashset = new HashSet();
        hashset.add(new Employee("小红", new MyDate(2003, 1, 1)));
        hashset.add(new Employee("小李", new MyDate(2001, 3, 21)));
        hashset.add(new Employee("小红", new MyDate(2003, 1, 1)));
        System.out.println(hashset);
    }
}

class Employee {
    private String name;
    private MyDate birthday;

    public Employee(String name, MyDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name) && birthday.equals(employee.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday);
    }
}

class MyDate {
    private int years;
    private int month;
    private int day;

    public MyDate(int years, int month, int day) {
        this.years = years;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "years=" + years +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDate myDate = (MyDate) o;
        return years == myDate.years && month == myDate.month && day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(years, month, day);
    }
}
```

因为涉及到两个类的equals和hashCode方法，所以需要对两个类都进行重写。
