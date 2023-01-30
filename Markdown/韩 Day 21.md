## LinkedHashSet介绍

1. LinkedHashSet是HashSet的子类

2. LinkedHashSet底层是一个LinkedHashMap，底层维护了一个数组+双向链表
3. LinkedHashSet根据元素的hashCode值来决定元素的存储位置，同时使用链表维护元素的次序（图），这使得元素看起来是以插入顺序保存的
4. LinkedHashSet不允许添加重复元素



1. LinkedHasSet中维护了一个hash表和双向链表（LinkedHashSet 有head和tail）
2. 每一个节点有pre和next属性，这样可以形成双向链表
3. 再添加一个元素时，先求hash值，再求索引，确定该元素在hashtable的位置，然后添加的元素加入到双向链表（如果已经存在，不添加【原则和hashset一样】）
4. 这样的话，我们遍历LinkedHashSet也能确保插入顺序和遍历顺序一致

## LinkedHashSet源码解读

``` java
@SuppressWarnings({"all"})
public class LinkedHashSetSource {
    public static void main(String[] args) {
        //分析一下LinkedHashSet的底层机制
        Set set = new LinkedHashSet();
        set.add(new String("AA"));
        set.add(456);
        set.add(456);
        set.add(new Customer("刘", 1001));
        set.add(123);
        set.add("HSP");
 
        System.out.println("set=" + set);
        //解读
        //1. LinkedHashSet 加入顺序和取出元素/数据的顺序一致
        //2. LinkedHashSet 底层维护的是一个LinkedHashMap(是HashMap的子类)
        //3. LinkedHashSet 底层结构 (数组table+双向链表)
        //4. 添加第一次时，直接将 数组table 扩容到 16 ,存放的结点类型是 LinkedHashMap$Entry
        //5. 数组是 HashMap$Node[] 存放的元素/数据是 LinkedHashMap$Entry类型
        /*
                //继承关系是在内部类完成.
                static class Entry<K,V> extends HashMap.Node<K,V> {
                    Entry<K,V> before, after;
                    Entry(int hash, K key, V value, Node<K,V> next) {
                        super(hash, key, value, next);
                    }
                }
         */
 
    }
}
class Customer {
    private String name;
    private int no;
 
    public Customer(String name, int no) {
        this.name = name;
        this.no = no;
    }
}
```

## Map接口特点

- Map接口实现类的特点【很实用】

  注意：这里讲的是JDK8的Map接口特点

  1. Map和Collection并列存在。用于保存具有映射关系的数据：Key-Value
  2. Map中的Key和value可以是任何引用类型的数据，会封装到HashMap$Node对象中
  3. Map中的key不允许重复，原因和HashSet一样，前面分析过源码，重复时，value会覆盖
  4. Map中的value可以重复
  5. Map中的key可以为null，value也可以为null，注意key为null，只能有一个
  6. 常用String类作为Map的key
  7. key和value之间存在单项一对一关系，即通过指定的key总能找到对应的value
  8. Map存放数据的key-value示意图，一对k-v是放在一个HashMap$Node中的，又因为Node实现了Entry接口，有些书上也说一对k-v就是一个Entry(如图)

 ![image-20230102223546779](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230102223546779.png)

## Map接口方法

- Map接口常用方法
  1. put：添加
  2. remove：根据键值删除映射关系
  3. get：根据键值获取值
  4. size：获取元素个数
  5. isEmpty：判断个数是否为0
  6. clear：清除
  7. containsKey：查找键值是否存在

## Map六大遍历方式

1. keySet：获取所有的键值
2. entrySet：获取所有关系的k-v
3. values：获取所有的值

``` java
@SuppressWarnings({"all"})
public class MapFor {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("邓超", "孙俪");
        map.put("王宝强", "马蓉");
        map.put("宋喆", "马蓉");
        map.put("刘令博", null);
        map.put(null, "刘亦菲");
        map.put("鹿晗", "关晓彤");
 
        
        //第一组: 先取出 所有的Key , 通过Key 取出对应的Value
        Set keyset = map.keySet();
        //(1) 增强for
        System.out.println("-----第一种方式-------");
        for (Object key : keyset) {
            System.out.println(key + "-" + map.get(key));
        }
        //(2) 迭代器
        System.out.println("----第二种方式--------");
        Iterator iterator = keyset.iterator();
        while (iterator.hasNext()) {
            Object key =  iterator.next();
            System.out.println(key + "-" + map.get(key));
        }
 
        //第二组: 把所有的values取出
        Collection values = map.values();
        //这里可以使用所有的Collections使用的遍历方法
        //(1) 增强for
        System.out.println("---取出所有的value 增强for----");
        for (Object value : values) {
            System.out.println(value);
        }
        //(2) 迭代器
        System.out.println("---取出所有的value 迭代器----");
        Iterator iterator2 = values.iterator();
        while (iterator2.hasNext()) {
            Object value =  iterator2.next();
            System.out.println(value);
 
        }
 
        //第三组: 通过EntrySet 来获取 k-v
        Set entrySet = map.entrySet();// EntrySet<Map.Entry<K,V>>
        //(1) 增强for
        System.out.println("----使用EntrySet 的 for增强(第3种)----");
        for (Object entry : entrySet) {
            //将entry 转成 Map.Entry
            Map.Entry m = (Map.Entry) entry;
            System.out.println(m.getKey() + "-" + m.getValue());
        }
        //(2) 迭代器
        System.out.println("----使用EntrySet 的 迭代器(第4种)----");
        Iterator iterator3 = entrySet.iterator();
        while (iterator3.hasNext()) {
            Object entry =  iterator3.next();
            //System.out.println(next.getClass());//HashMap$Node -实现-> Map.Entry (getKey,getValue)
            //向下转型 Map.Entry
            Map.Entry m = (Map.Entry) entry;
            System.out.println(m.getKey() + "-" + m.getValue());
        }
    }
}
```

## 课堂练习

使用HashMap添加3个员工对象，要求

键：员工id

值：员工对象

并遍历显示工资>18000的员工（遍历方式最少两种）

员工类：姓名、工资、员工id

``` java
@SuppressWarnings({"all"})
public class MapForPractice {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(1,new Employee(1,"小时",3000));
//        map.put(1,new Employee(1,"小时",3000));
        map.put(2,new Employee(2,"小留",20000));
        map.put(3,new Employee(3,"小杨",19000));
        map.put(4,new Employee(4,"小照",18400));
        System.out.println(map);
 
        // 使用KeySet
        Set set = map.keySet();
        System.out.println("======使用增强for======");
        for (Object key : set) {
            if (((Employee)map.get(key)).getSal() > 18000){
                System.out.println(key + "-" + map.get(key));
            }
        }
        System.out.println("=====使用迭代器=====");
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object key =  iterator.next();
            if (((Employee)map.get(key)).getSal() > 18000){
                System.out.println(key + "-" + map.get(key));
            }
        }
        //使用entrySet
        Set entrySet = map.entrySet();
        System.out.println("=====使用增强for=====");
        for (Object obj :entrySet) {
            Map.Entry entry = (Map.Entry) obj;
            if (((Employee)entry.getValue()).getSal() > 18000){
                System.out.println(entry.getKey() + "-" + entry.getValue());
            }
        }
        System.out.println("=====使用迭代器=====");
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()) {
            Object next =  iterator1.next();
            Map.Entry entry = (Map.Entry) next;
            if (((Employee)entry.getValue()).getSal() > 18000){
                System.out.println(entry.getKey() + "-" + entry.getValue());
            }
        }
 
    }
}
@SuppressWarnings({"all"})
class Employee{
    private int id;
    private String name;
    private double sal;
 
    public Employee(int id, String name, double sal) {
        this.id = id;
        this.name = name;
        this.sal = sal;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getSal() {
        return sal;
    }
 
    public void setSal(double sal) {
        this.sal = sal;
    }
 
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sal=" + sal +
                '}';
    }
}
```

