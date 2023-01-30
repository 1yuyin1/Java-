#  常用类

## 包装类

- 包装类的分类 Wrapper   

  1. 针对八种基本数据类型相应的引用类型-包装类

  2. 有了类的特点，就可以调用类中的方法

     | 基本数据类型 | 包装类    |
     | ------------ | --------- |
     | boolean      | Boolean   |
     | char         | Character |
     | byte         | Byte      |
     | short        | Short     |
     | int          | Integer   |
     | long         | Long      |
     | float        | Float     |
     | double       | Double    |

     

![image-20221226104234142](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20221226104234142.png)

- 包装类和基本数据类型的转换
  1. jdk5前手动装箱和拆箱
  2. jdk5后自动装箱和拆箱
  3. 自动装箱底层调用的是valueOf方法

``` java
//手动    
	int n1 = 1;
    Integer integer = new Integer(n1);
	//Integer integer = Integer.valueOf(n1);
    int n2 = integer.intValue();
//自动    
    Integer integer1 = n1;
    int n3 = integer1;
```

- 包装类测试

  ``` .
  Double d = 100d;
  Float f = 1.5f;
  Object obj1 = true?new Integer(1):new Double(2.0);//三元运算符【是一个整体】
  System.out.println(obj1);//1.0
  
  Object obj2;
  if(true)
   obj2 = new Integer(1);
  else
   obj2 = new Double(2.0);
  System.out.println(obj2);//1
  ```

## 包装类方法

- 包装类型和String类型的相互转换

  ``` java
  //包装类型-->String
  Integer i = 10;
  //方式1
  String s1 = i.toString();
  //方式2
  String s2 = String.valueOf(i);
  //方式3
  String s3 = i +"";
  System.out.println(s3);
  //String-->包装类型
  //方式1
  Integer j = new Integer(s1);
  //方式2
  Integer j2 = Integer.valueof(s2);
  ```

- Integer类和Character类的常用方法

  ``` java 
  Integer.MIN_VALUE;//返回最大值
  Integer.MAX_VALUE;//返回最小值
  
  Character.isDigit('a');//判断是不是数字
  Character.isLetter('a');//判断是不是字母
  Character.isUpperCase('a');//判断是不是大写
  Character.isLowerCase('a');//判断是不是小写
  Character.isWhitespace('a');//判断是不是空格
  Character.toUpperCase('a');//转换成大写
  Character.toLowerCase('a');//转换成小写
  ```

- Integer创建机制

  ``` java
          Integer i = new Integer(1);
          Integer j = new Integer(1);
          System.out.println(i == j);//这里是两个对象不同，则输出false
  
          Integer n1 = 1;
          Integer n2 = 1;
          System.out.println(n1 == n2);//由于valueof方法把-128~127的值列为静态属性，所以对象相同，输出true
  
          Integer m1 = 128;
          Integer m2 = 128;
          System.out.println(m1 == m2);//由于超出范围，所以分别new对象，输出false
  ```

## String 结构剖析

1. String 对象用于保存字符串，也就是一组字符序列

2. 字符串常量对象是用双引号括起来的字符序列

3. 字符串的字符使用Unicode字符编码，一个字符占两个字节

4. String类较常用的构造器

   ``` java
   String s1 = new String();
   String s2 = new String(String original);
   String s3 = new String(char[] a);
   String s4 = new String(char[] a,int startIndex,int count);
   ```

5. String类实现了接口 Serializable [String 可以串行化：可以在网络传输]

   ​                         接口 Comparable[String 对象可以比较大小]

6. String 是final类，不能被其它的类继承

7. String 有属性 private final char value【】;用于存放字符串内容

8. 一定要注意：value是一个 final类型，不可以修改（指向的地址） 



- 两种创建String对象的区别

  方法一：直接赋值 String s = "hsp";

  方法二：调用构造器 String s2 = new String("hsp");

  1. 方式一：先从常量池查看是否有"hsp"数据空间，如果有，则直接指向；如果没有则重新创建，然后指向。s最终指向的是常量池的空间地址
  2. 方式二：先在堆中创建空间，里面维护了value属性，指向常量池的hsp空间。如果常量池没有“hsp”，重新创建，如果有，直接通过value指向。最终指向的是堆中的空间地址。

测试题

``` java
String a = "abc";
String b = "abc";
System.out.println(a.equals(b));//T
System.out.println(a==b);//T
```

``` java
String a = new String("abc");
String b = new String("abc");
System.out.println(a.equals(b));//T
System.out.println(a==b);//F
```

``` JAVA
        String a = "hsp";
        String b = new String("hsp");
        System.out.println(a.equals(b));
        System.out.println(a == b);
        System.out.println(a == b.intern());
        System.out.println(b == b.intern());
```

调用intern方法时，如果池已经包含一个等于此String对象的字符串（用equals(Object)方法确定），则返回池中的字符串。否则，将此String对象添加到池中，并返回此String对象的引用。   b.intern()方法最终返回的是常量池的地址（对象）。



``` java
        Person p1 = new Person();
        p1.name = "hsp";
        Person p2 = new Person();
        p2.name = "hsp";
        System.out.println(p1.name.equals(p2.name));//T
        System.out.println(p1.name == p2.name);//T
        System.out.println(p1.name == "hsp");//T
        
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1 == s2);//F
```

## String 对象特性

1. String是一个final类，代表不可变的字符序列
2. 字符串是不可变的。一个字符串对象一旦被分配，其内容是不可变的

``` java
//以下语句创建了几个对象？
String s1 = "hello";
s1 = "haha";
//创建了两个对象

//以下语句创建了几个对象？
String a = "hello"+"abc";//=>String a = "helloabc";
//创建了一个对象

//以下语句创建了几个对象？
String a = "hello";
String b = "abc";
String c = a+b;
//先创建一个 StringBuilder sb = StringBuilder();
//执行 sb.append("hello");
//sb.append("abc");
//String c = sb.toString
//最后其实是c 指向堆中的对象（String） value[] ->池中 "helloabc"
//创建了三个对象
```

总结：底层是 StringBuilder sb = new StringBuilder(); sb.append(a); sb.append(b);  sb是在堆中，并且append是在原来字符串的基础上追加的。

重要规则：String c1 = "ab" + "cd";常量相加，看的是池。 String c1 = a + b；变量相加，是在堆中 



``` java
public class temp {
    String str = new String("hsp");
    final char[] ch = {'j','a','v','a'};
    public void change(String str,char ch[]) {
        str = "java";
        ch[0] = 'h';
    }
    public static void main(String[] args) {
        temp ex = new temp();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str+" and ");//输出为hsp and hava
        System.out.println(ex.ch);
    }
}
```

​					

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20221226203130970.png" alt="image-20221226203130970" style="zoom:67%;" />

## String常用方法

String类是保存字符串常量的。每次更新都需要重新开辟空间，效率较低，因此Java设计者还提供了StringBuilder和StringBuffer来增强String的功能并提高效率。、

``` java
String s = new String("");
for(int i = 0;i<80000;i++) {
    s +="hello";
}
```

- String类的常见方法一览

  - equals

  - equalsIgnoreCase

  - length

  - indexOf

  - lastIndexOf

  - substring

  - trim

  - charAt获取某索引处的字符

  - toUpperCase

  - toLowerCase

  - concat

  - compareTo

  - toCharAarray

  - format

    ``` java
    String formatStr = "我的名字是%s 年龄是%d 成绩是%.2f 性别是%c";
    String info = String.format(formatStr,name,age,score,gender);
    ```

    