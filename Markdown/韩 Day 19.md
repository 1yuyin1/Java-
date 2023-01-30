# 集合

 ## 集合介绍

前面我们保存多个数据使用的是数组，那么数组有不足的地方。

1. 长度开始时必须指定，而且一旦指定，不能更改

2. 保存的必须为同一类型的元素

3. 使用数组增加/删除元素的示意代码--比较麻烦

   ``` java
   Person[] pers = new Person[1];
   per[0] = new Person[];
   
   Person[] pers2 = new Person[pers.length+1];
   for(){}//拷贝数组元素
   pers2[pers2.length-1] = new Person[];
   ```

   

- 集合
  1. 可以动态保存任意多个对象， 使用比较方便
  2. 提供了一系列方便的操作对象的方法：add、remove、set、get等
  3. 使用集合添加删除新元素的示意代码--简洁明了

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20221230133239588.png" alt="image-20221230133239588" style="zoom:80%;" />

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20221230133248067.png" alt="image-20221230133248067"  />

## Collection方法

- Collection 接口实现类的特点
  1. collection实现子类可以存放多个元素，每个元素可以是Object
  2. 有些Collection的实现类，可以存存放重复的元素，有些不可以
  3. 有些Collection的实现类，有些是有序的（List），有些不是有序的（Set）
  4. Collection接口没有直接的实现子类，是通过它的子接口Set和List来实现的



- Collection接口和常用方法，以实现子类ArrayList来演示
  1. add：添加单个元素
  2. remove：删除指定元素
  3. contains：查找元素是否存在
  4. size：获取元素个数
  5. isEmpty：判断是否为空
  6. clear：清空
  7. addAll：添加多个元素
  8. containsAll：查找多个元素是否都存在
  9. removAll：删除多个元素
  10. 说明：以ArrayList实现类来演示


## 迭代器遍历

- Collection接口遍历元素方式1-使用Iterator（迭代器）
  1. Iterator对象称为迭代器，主要用于遍历Collection集合中的元素
  2. 所有实现了Collection接口的集合类都有一个iterator()方法，用以返回一个实现了Iterator接口的对象，即返回一个迭代器
  3. Iterator的结构
  4. Iterator仅用于遍历集合，本身并不存放对象

提示：在调用iterator.next()方法之前，必须要调用iterator.hasNext()进行检测。若不调用，且下一条记录无效，直接调用iterator.next()会抛出NoSuchElementException异常

``` java
        Iterator iterator = col.iterator();
		while (iterator.hasNext()) {//快捷键 itit
            Object next =  iterator.next();
            
        }
```

如果希望再次遍历，需要重置迭代器

``` java
iterator = col.iterator();
```



- Collection接口遍历对象方式2-for增强循环

  增强for，底层仍然是迭代器  可以理解成简化版本的迭代器遍历

  基本语法：

  for(元素类型 元素名:集合名或数组名)  {

  ​					访问元素

  }

``` java
for(Object book : col) {//快捷方式 i
    System.out.println("book=" + book);
}
```

- Collection接口遍历对象方式3-for循环

  ``` java
  for(int i = 0;i < list.size();i++) {
      System.out.println("对象=" + list.get(i));
  }
  ```

  

## List接口方法

- List接口基本介绍

  List接口是Collection接口的子接口

  1. List集合类中元素有序、且可重复

  2. List集合中的每个元素都有其对应的顺序索引，即支持索引

  3. List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素

  4. JDK API中List接口的实现类有：

     AbstractList , AbstractSequentialList , ArrayList , AttributeList , CopyOnWriteArrayList , LinkedList , 
     RoleList , RoleUnresolvedList , Stack, Vector



- List接口的常用方法
  1. void add(int index,Object ele);
  2. boolean addAll(Object obj);
  3. Object get(int index);
  4. int indexOf(Object obj);//返回首次出现的位置
  5. int lastIndexOf(Object obj);//返回末出现的位置
  6. Object remove(int index);//移除指定index位置的元素，并返回此元素
  7. Object set(int index,Object ele);//设置指定index位置的元素为ele，相当于替换
  8. List subList(int fromIndex,int toIndex)//返回fromIndex<=    <toIndex 的元素

## List排序练习

``` java
import java.util.ArrayList;
import java.util.List;

public class temp {
    public static void main(String[] args) {
        @SuppressWarnings({"all"})
        List list = new ArrayList();
        list.add(new Book("红楼梦", "曹雪芹", 100));
        list.add(new Book("西游记","吴承恩",80));
        list.add(new Book("水浒传","施耐庵",95));
        Book.sort(list);

        for (Object o : list) {
            System.out.println(o);
        }

    }
}

class Book {
    private String name;
    private String author;
    private int price;

    public Book(String name, String author, int price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "name=" + name + '\t' + "author=" + author + '\t' + "price=" + price;
    }

    public static void sort(List list) {
        int listsize = list.size();
        for(int i = 0;i<listsize - 1;i++) {
            for(int j = 0;j<listsize-i-1;j++) {
                Book book1 = (Book)list.get(j);
                Book book2 = (Book)list.get(j+1);
                if(book1.getPrice()> book2.getPrice()) {
                    list.set(j,book2);
                    list.set(j+1,book1);
                }
            }
        }
    }
}
```

## ArrayList 注意事项

1. permits all elements, including null, ArrayList 可以加入null，并且多个
2. ArrayList是由数组来实现数据存储的
3. ArrayList基本等同于Vector，除了ArrayList是线程不安全（执行效率高）看源码，在多线程情况下，不建议使用ArrayList   

## ArrayList底层结构和源码分析

1. ArrayList中维护了一个Object类型的数组elementData

   transient Object[] elementData;    //transient表示瞬间，短暂的，表示该属性不会被序列化

2. 当创建ArrayList对象时，如果使用的是无参构造器，element Data容量为0，第一次添加，则扩容elementData为10，如需再次扩容，则扩容elementData为1.5倍

3. 如果使用的是指定大小的构造器，则初始elementData容量为指定大小，如果需要扩容，则直接扩容elementData为1.5倍

建议自己去debug一把我们的ArrayList的创建和扩容的流程