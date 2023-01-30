## HMap阶段小结

1. Map接口的常用实现类：HashMap、Hashtable和Properties
2. HashMap是Map接口使用频率最高的实现类
3. HashMap是以key-val对的方式来存储数据
4. key不能重复，但是值可以重复，【允许使用null键和null值】
5. 如果添加相同的key，则会覆盖原来的key-val，等同于修改（key不会替换，val会替换）
6. 与HashSet一样，不保证映射的顺序，因为底层是以hash表的方式来存储的
7. HashMap没有实现同步，因此是线程不安全的

## HMap底层机制

1. （k，v）是一个Node实现了Map.Entry<k,v>，查看HashMap的源码可以看到
2. jdk7.0的hashmap的层实现了【数组+链表】，jdk8.0底层【数组+链表+红黑树】

``` java
@SuppressWarnings({"all"})
public class HashMapSource1 {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("java", 10);//ok
        map.put("php", 10);//ok
        map.put("java", 20);//替换value
 
        System.out.println("map=" + map);//
 
        /*解读HashMap的源码+图解
        1. 执行构造器 new HashMap()
           初始化加载因子 loadfactor = 0.75
           HashMap$Node[] table = null
        2. 执行put 调用 hash方法，计算 key的 hash值 (h = key.hashCode()) ^ (h >>> 16)
            public V put(K key, V value) {//K = "java" value = 10
                return putVal(hash(key), key, value, false, true);
            }
         3. 执行 putVal
         final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
                Node<K,V>[] tab; Node<K,V> p; int n, i;//辅助变量
                //如果底层的table 数组为null, 或者 length =0 , 就扩容到16
                if ((tab = table) == null || (n = tab.length) == 0)
                    n = (tab = resize()).length;
                //取出hash值对应的table的索引位置的Node, 如果为null, 就直接把加入的k-v
                //, 创建成一个 Node ,加入该位置即可
                if ((p = tab[i = (n - 1) & hash]) == null)
                    tab[i] = newNode(hash, key, value, null);
                else {
                    Node<K,V> e; K k;//辅助变量
                // 如果table的索引位置的key的hash相同和新的key的hash值相同，
                 // 并 满足(table现有的结点的key和准备添加的key是同一个对象  || equals返回真)
                 // 就认为不能加入新的k-v
                    if (p.hash == hash &&
                        ((k = p.key) == key || (key != null && key.equals(k))))
                        e = p;
                    else if (p instanceof TreeNode)//如果当前的table的已有的Node 是红黑树，就按照红黑树的方式处理
                        e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
                    else {
                        //如果找到的结点，后面是链表，就循环比较
                        for (int binCount = 0; ; ++binCount) {//死循环
                            if ((e = p.next) == null) {//如果整个链表，没有和他相同,就加到该链表的最后
                                p.next = newNode(hash, key, value, null);
                                //加入后，判断当前链表的个数，是否已经到8个，到8个，后
                                //就调用 treeifyBin 方法进行红黑树的转换
                                if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                                    treeifyBin(tab, hash);
                                break;
                            }
                            if (e.hash == hash && //如果在循环比较过程中，发现有相同,就break,就只是替换value
                                ((k = e.key) == key || (key != null && key.equals(k))))
                                break;
                            p = e;
                        }
                    }
                    if (e != null) { // existing mapping for key
                        V oldValue = e.value;
                        if (!onlyIfAbsent || oldValue == null)
                            e.value = value; //替换，key对应value
                        afterNodeAccess(e);
                        return oldValue;
                    }
                }
                ++modCount;//每增加一个Node ,就size++
                if (++size > threshold[12-24-48])//如size > 临界值，就扩容
                    resize();
                afterNodeInsertion(evict);
                return null;
            }
              5. 关于树化(转成红黑树)
              //如果table 为null ,或者大小还没有到 64，暂时不树化，而是进行扩容.
              //否则才会真正的树化 -> 剪枝
              final void treeifyBin(Node<K,V>[] tab, int hash) {
                int n, index; Node<K,V> e;
                if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
                    resize();
            }
         */
    }
}
```



## 模拟HashMap触发扩容、树化情况

``` java
@SuppressWarnings({"all"})
public class HashMapSource2 {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        for(int i = 1; i <= 12; i++) {
            hashMap.put(i, "hello");
        }
        hashMap.put("aaa", "bbb");
        System.out.println("hashMap=" + hashMap);//12个 k-v
        //布置一个任务，自己设计代码去验证，table 的扩容
        //0 -> 16(12) -> 32(24) -> 64(64*0.75=48)-> 128 (96) ->
        //自己设计程序，验证-》 增强自己阅读源码能力. 看别人代码.
    }
}
 
class A  {
    private int num;
 
    public A(int num) {
        this.num = num;
    }
 
    //所有的A对象的hashCode都是100
//    @Override
//    public int hashCode() {
//        return 100;
//    }
 
    @Override
    public String toString() {
        return "\nA{" +
                "num=" + num +
                '}';
    }
}
```

## HashTable使用

1. 存放的元素是键值对，即K-V

2. hashtable的键和值【都不能为null】

3. hashtable使用方法基本上和hashmap一样

4. hashtable是线程安全的，hashmap是线程不安全的

5. HashTble的应用案例(下面的代码是否正确，如果错误，为什么？)

   ``` java
   @SuppressWarnings({"all"})
   public class HashTableExercise {
       public static void main(String[] args) {
           Hashtable table = new Hashtable();//ok
           table.put("john", 100); //ok
           //table.put(null, 100); //异常 NullPointerException
           //table.put("john", null);//异常 NullPointerException
           table.put("lucy", 100);//ok
           table.put("lic", 100);//ok
           table.put("lic", 88);//替换
           table.put("hello1", 1);
           table.put("hello2", 1);
           table.put("hello3", 1);
           table.put("hello4", 1);
           table.put("hello5", 1);
           table.put("hello6", 1);
           System.out.println(table);
           //简单说明一下Hashtable的底层
           //1. 底层有数组 Hashtable$Entry[] 初始化大小为 11
           //2. 临界值 threshold 8 = 11 * 0.75
           //3. 扩容: 按照自己的扩容机制来进行即可.
           //4. 执行 方法 addEntry(hash, key, value, index); 添加K-V 封装到Entry
           //5. 当 if (count >= threshold) 满足时，就进行扩容
           //5. 按照 int newCapacity = (oldCapacity << 1) + 1; 的大小扩容.
       }
   }
   ```


##  Properties

- 基本介绍
  1. Properties类继承自Hashtable并且实现了Map接口，也是一种键值对的形式来保存数据
  2. 它的使用特点和Hashtable类似
  3. properties还可以用于 从×××.properties 文件中，加载数据到Properties类对象，并进行读取和修改
  4. 说明：工作后×××.properties 文件通常作为配置文件，这个知识点在IO流举例

``` java
@SuppressWarnings({"all"})
public class Properties_ {
    public static void main(String[] args) {
        //解读
        //1. Properties 继承  Hashtable
        //2. 可以通过 k-v 存放数据，当然key 和 value 不能为 null
        //增加
        Properties properties = new Properties();
        //properties.put(null, "abc");//抛出 空指针异常
        //properties.put("abc", null); //抛出 空指针异常
        properties.put("john", 100);//k-v
        properties.put("lucy", 100);
        properties.put("lic", 100);
        properties.put("lic", 88);//如果有相同的key ， value被替换
 
        System.out.println("properties=" + properties);
 
        //查找，通过k 获取对应值
        System.out.println(properties.get("lic"));//88
        System.out.println(properties.getProperty("lic"));//88
	
        //删除
        properties.remove("lic");
        System.out.println("properties=" + properties);
 
        //修改
        properties.put("john", "约翰");
        System.out.println("properties=" + properties);
    }
}
```

## 集合选型规则

 总结-开发中如何选择集合实现类

在开发中，选择什么集合实现类，主要取决于业务操作特点，然后根据集合类实现特性进行选择，分析如下：

1. 先判断存储的类型（一组对象【单列】或一组键值对【双列】）

2. 一组对象【单列】：Collection接口

   ​						允许重复：List

   ​										增删多：LinkedList【底层维护了一个双向链表】

   ​										改查多：ArrayList【底层维护了Object类型的可变数组】

   ​						不允许重复：Set

   ​										无序：HashSet【底层是HashMap，维护了一个哈希表即（数组+链表+红黑树）】

   ​										排序：TreeSet【底层是TreeMap】

   ​										插入和取出顺序一致：LinkedHashSet【底层是LinkedHashMap维护了数组+双向链表】

3. 一组键值对：Map

​								键无序：HashMap【底层是哈希表 jdk7：数组+链表  jdk8：数组+链表+红黑树】

​								键排序：TreeMap

​								键插入和取出顺序一致：LinkedHashMap【底层是HashMap】

​								读取文件：Properties

## TreeSet源码解读

``` java
@SuppressWarnings({"all"})
public class TreeSet_ {
    public static void main(String[] args) {
        //解读
        //1. 当我们使用无参构造器，创建TreeSet时，仍然是无序的
        //2. 老师希望添加的元素，按照字符串大小来排序
        //3. 使用TreeSet 提供的一个构造器，可以传入一个比较器(匿名内部类)
        //   并指定排序规则
        //4. 简单看看源码
        //老韩解读
        /*
        1. 构造器把传入的比较器对象，赋给了 TreeSet的底层的 TreeMap的属性this.comparator
         public TreeMap(Comparator<? super K> comparator) {
                this.comparator = comparator;
            }
         2. 在 调用 treeSet.add("tom"), 在底层会执行到
             if (cpr != null) {//cpr 就是我们的匿名内部类(对象)
                do {
                    parent = t;
                    //动态绑定到我们的匿名内部类(对象)compare
                    cmp = cpr.compare(key, t.key);
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else //如果相等，即返回0,这个Key就没有加入
                        return t.setValue(value);
                } while (t != null);
            }
         */
 
//        TreeSet treeSet = new TreeSet();
        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //下面 调用String的 compareTo方法进行字符串大小比较，若字符串内容完全相同，则不再添加
                //如果老韩要求加入的元素，按照长度大小排序，若长度相同则不再添加
                //return ((String) o2).compareTo((String) o1);
                return ((String) o1).length() - ((String) o2).length();
            }
        });
        //添加数据.
        treeSet.add("jack");
        treeSet.add("tom");//3
        treeSet.add("sp");
        treeSet.add("a");
        treeSet.add("abc");//3
 
        System.out.println("treeSet=" + treeSet); 
    }
}
```

## TreeMap源码解读

``` java
@SuppressWarnings({"all"})
public class TreeMap_ {
    public static void main(String[] args) {
        //使用默认的构造器，创建TreeMap, 是无序的(也没有排序)
        /*
            要求：按照传入的 k(String) 的大小进行排序
         */
//        TreeMap treeMap = new TreeMap();
        TreeMap treeMap = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //按照传入的 k(String) 的大小进行排序
                //按照K(String) 的长度大小排序
                //return ((String) o2).compareTo((String) o1);
                return ((String) o2).length() - ((String) o1).length();
            }
        });
        treeMap.put("jack", "杰克");
        treeMap.put("tom", "汤姆");
        treeMap.put("kristina", "克瑞斯提诺");
        treeMap.put("smith", "斯密斯");
        treeMap.put("hsp", "韩顺平");//加入不了
        System.out.println("treemap=" + treeMap);
 
        /*
            老韩解读源码：
            1. 构造器. 把传入的实现了 Comparator接口的匿名内部类(对象)，传给给TreeMap的comparator
             public TreeMap(Comparator<? super K> comparator) {
                this.comparator = comparator;
            }
            2. 调用put方法
            2.1 第一次添加, 把k-v 封装到 Entry对象，放入root
            Entry<K,V> t = root;
            if (t == null) {
                compare(key, key); // type (and possibly null) check
                root = new Entry<>(key, value, null);
                size = 1;
                modCount++;
                return null;
            }
            2.2 以后添加
            Comparator<? super K> cpr = comparator;
            if (cpr != null) {
                do { //遍历所有的key , 给当前key找到适当位置
                    parent = t;
                    cmp = cpr.compare(key, t.key);//动态绑定到我们的匿名内部类的compare
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else  //如果遍历过程中，发现准备添加Key 和当前已有的Key 相等，就不添加
                        return t.setValue(value);
                } while (t != null);
            }
         */
    }
}
```

## Collection工具类

- Collections工具类介绍

  1. Collections是一个操作Set、List和Map等集合的工具类
  2. Collections中提供了一系列静态的方法对集合元素进行排序、查询和修改等操作

- 排序操作：（均为static方法）

  1. reverse（List）：反转List中元素的顺序

  2. shuffle（List）：对List集合元素进行随机排序

  3. sort（List）：根据元素的自然顺序对指定List集合元素按升序排序

  4. sort（List，Comparator）：根据指定的Comparator产生的顺序对List集合元素进行排序

  5. swap（List，int，int）：将指定list集合中的i处元素和j处元素进行交换

  6. Object max（Collection）：根据元素的自然顺序，返回给定集合中的最大元素

  7. Object max（Collection，Comparator）：根据Comparator指定的顺序，返回给定集合中的最大元素

  8. Object min（Collection）

  9. Object min（Collection，Comparator）

  10. int frequency（Collection，Object）：返回集合中指定元素的出现次数

  11. void copy（List dest，List src）：将src中的内容复制到dest中

  12. boolean replaceAll（List list，Object oldVal，Object newVal）：使用新值替换List对象的所有旧值

  13. 应用案例演示

      ``` java
      @SuppressWarnings({"all"})
      public class Collections_ {
          public static void main(String[] args) {
              //创建ArrayList 集合，用于测试.
              List list = new ArrayList();
              list.add("tom");
              list.add("smith");
              list.add("king");
              list.add("milan");
              list.add("tom");
      //        reverse(List)：反转 List 中元素的顺序
              Collections.reverse(list);
              System.out.println("list=" + list);
      //        shuffle(List)：对 List 集合元素进行随机排序
      //        for (int i = 0; i < 5; i++) {
      //            Collections.shuffle(list);
      //            System.out.println("list=" + list);
      //        }
       
      //        sort(List)：根据元素的自然顺序对指定 List 集合元素按升序排序
              Collections.sort(list);
              System.out.println("自然排序后");
              System.out.println("list=" + list);
      //        sort(List，Comparator)：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
              //我们希望按照 字符串的长度大小排序
              Collections.sort(list, new Comparator() {
                  @Override
                  public int compare(Object o1, Object o2) {
                      //可以加入校验代码.
                      return ((String) o2).length() - ((String) o1).length();
                  }
              });
              System.out.println("字符串长度大小排序=" + list);
      //        swap(List，int， int)：将指定 list 集合中的 i 处元素和 j 处元素进行交换
       
              //比如
              Collections.swap(list, 0, 1);
              System.out.println("交换后的情况");
              System.out.println("list=" + list);
       
              //Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
              System.out.println("自然顺序最大元素=" + Collections.max(list));
              //Object max(Collection，Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
              //比如，我们要返回长度最大的元素
              Object maxObject = Collections.max(list, new Comparator() {
                  @Override
                  public int compare(Object o1, Object o2) {
                      return ((String)o1).length() - ((String)o2).length();
                  }
              });
              System.out.println("长度最大的元素=" + maxObject);
       
       
              //Object min(Collection)
              //Object min(Collection，Comparator)
              //上面的两个方法，参考max即可
       
              //int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
              System.out.println("tom出现的次数=" + Collections.frequency(list, "tom"));
       
              //void copy(List dest,List src)：将src中的内容复制到dest中
       
              ArrayList dest = new ArrayList();
              //为了完成一个完整拷贝，我们需要先给dest 赋值，大小和list.size()一样
              for(int i = 0; i < list.size(); i++) {
                  dest.add("");
              }
              //拷贝
              Collections.copy(dest, list);
              System.out.println("dest=" + dest);
       
              //boolean replaceAll(List list，Object oldVal，Object newVal)：使用新值替换 List 对象的所有旧值
              //如果list中，有tom 就替换成 汤姆
              Collections.replaceAll(list, "tom", "汤姆");
              System.out.println("list替换后=" + list);   
          }
      }
      ```

      

## 集合作业

1. HashSet和TreeSet去重机制的描述
   1. Hashset的去重机制：hashCode() + equals() ，底层先通过存入对象，进行运算得到一个hash值，通过hash值得到对应的索引，如果发现table索引所在的位置，没有数据，就直接存放。如果有数据，就进行equals比较【遍历比较】，如果比较后，不相同，就加入，否则就不加入
   2. TreeSet的去重机制：如果你传入了一个Comparator匿名对象，就用实现的compare去重，如果compare方法返回0，就认为是相同得的元素/数据，就不添加。如果你没有传入一个Comparator匿名对象，则以你添加的对象实现的Comparable接口的compareTo去重

2. 下面代码会不会抛出异常，并从源码层面说明原因.【考察 读源码+接口编程+动态绑定】

   ``` java
   @SuppressWarnings({"all"})
   public class temp {
       public static void main(String[] args) {
           TreeSet treeSet = new TreeSet();
           treeSet.add(new Person());//无论第几次添加都会调用compare方法
       }
   }
   class Person{}
   ```

   会抛出ClassCastException异常，因为Person类没有实现Comparable接口，无法调用该接口下的compare方法。

3. 下面的代码输出什么？

   注意已知：Person类按照id和name重写了hashCode和equals方法

   ``` java
   @SuppressWarnings({"all"})
   public class Homework06 {
       public static void main(String[] args) {
           HashSet set = new HashSet();//ok
           Person p1 = new Person(1001,"AA");//ok
           Person p2 = new Person(1002,"BB");//ok
           set.add(p1);//ok
           set.add(p2);//ok
           p1.name = "CC";
           set.remove(p1);
           System.out.println(set);//2
           set.add(new Person(1001,"CC"));
           System.out.println(set);//3
           set.add(new Person(1001,"AA"));
           System.out.println(set);//4
       }
   }
    
   class Person {
       public String name;
       public int id;
    
       public Person(int id, String name) {
           this.name = name;
           this.id = id;
       }
    
       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (o == null || getClass() != o.getClass()) return false;
           Person person = (Person) o;
           return id == person.id &&
                   Objects.equals(name, person.name);
       }
    
       @Override
       public int hashCode() {
           return Objects.hash(name, id);
       }
    
       @Override
       public String toString() {
           return "Person{" +
                   "name='" + name + '\'' +
                   ", id=" + id +
                   '}';
       }
   }
   ```