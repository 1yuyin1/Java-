# 重载
## 方法重载

- 基本介绍：Java中允许同一个类中，多个同名方法的存在，但要求形参列表不一致！比如：Syestm.out.println();

- 重载的好处：
  - 减轻了起名的麻烦
  - 减轻了记名的麻烦

## 重载快速入门

``` java
public class Demo {
    public static void main(String[] args) {
        MyCalculater myCalculater = new MyCalculater();
        System.out.println(myCalculater.calculater(10,2));
        System.out.println(myCalculater.calculater(2.1,3));
        System.out.println(myCalculater.calculater(2, 5.9));
    }
}

    class MyCalculater {
        public int calculater(int n1, int n2) {
            return n1 + n2;
        }

        public double calculater(double n1, int n2) {
            return n1 + n2;
        }

        public double calculater(int n1, double n2) {
            return n1 + n2;
        }
    }
```



## 重载使用细节

1. 方法名相同
2. 形参类型必须不同
3. 返回类型：无要求

# 可变参数
## 可变参数的使用

- 基本概念：Java允许将同 一个类中多个同名同功能但参数不同的方法，封装成一个方法

- 基本语法

  - 访问修饰符 返回类型 方法名（数据类型.. 形参名）

  ``` java
  public class Demo {
      public static void main(String[] args) {
          MyCalculater myCalculater = new MyCalculater();
          System.out.println(myCalculater.calculater(10,2));
          System.out.println("===========================");
          System.out.println(myCalculater.calculater(1,2,3,4,5,6));
      }
  }
  
      class MyCalculater {
  /*    老韩解读
          1.int... 表示可变参数，类型是int，可以接受多个int
          2.使用可变参数时可以当作数组使用，即nums为数组名*/
  
          public int calculater(int... nums) {
              int res = 0;
              System.out.println(nums.length);
              for (int i = 0; i < nums.length; i++) {
                  res += nums[i];
              }
              return res;
          }
      }
  ```

  ## 可变参数细节

  1. 可变参数的数量可以为0个或任意多个
  2. 可变参数的实参可以为数组
  3. 可变参数的本质就是数组
  4. 可变参数可以和普通类型的参数一起放在形参列表，但必须保证可变参数在最后
  5. 一个形参列表中只能出现一个可变参数

# 作用域
## 作用域基本使用

![image-20220808164036800](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808164036800.png)



![image-20220808173138589](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808173138589.png)



- 作用域范围不同
  - 全局变量：可以被本类使用，或其它类使用（通过对象调用）
  - 局部变量：只能在本类中对应的方法中使用
- 修饰符不同
  - 全局变量可以加修饰符
  - 局部变量不可以加修饰符

# 构造器
## 构造器基本介绍

![image-20220808174806825](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808174806825.png)

![image-20220808174907925](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808174907925.png)

## 构造器快速入门

``` java
public class Demo {
    public static void main(String[] args) {
        person person = new person("小白",20);
        System.out.println(person.name);
        System.out.println(person.age);
    }
}

class person {
    String name;
    int age;
    public person(String pname,int page) {
        System.out.println("构造器被调用");
        name = pname;
        age = page;
    }
}
```

## 构造器使用细节

1. 一个类可以定义多个不同的构造器，即构造器重载

​		比如：我们可以再给Person类定义一个构造器，用来创建对象的时候，只指定人名，不指定年龄

2. 构造器名和类名要相同
3. 构造器没有返回值
4. 构造器是完成对象的初始化，并不是创建对象
5. 在创建对象的过程中，系统自动调用该类的创建方法

6. 如果没有定义构造器，系统会自动给类生成一个默认无参构造器（也叫默认构造器），比如Person();   可以用Javap命令反编译
7. 一旦定义了自己的构造器，默认的构造器就覆盖了，就不能再使用默认的无参构造器了，除非显式的定义一下。

## 对象创建流程

案例

``` java
class Person {
    int age = 90;
    String name;
    Person(String n,int a) {
        name = n;
        age = a;
    }
}
Person p = new Person("小倩",20);
```

流程分析：

1. 加载Person类信息，只会加载一次
2. 在堆中分配空间（地址）
3. 完成对象初始化  【3.1默认初始化 age = 0 name = null 3.2显式初始化 age =90, name = null  3.3构造器的初始化 age = 20,name = 小倩
4. 把对象在堆中的地址返回给p(p是对象名，可以理解成对象的引用)

# this
## 引出this

``` java
public class Demo {
    public static void main(String[] args) {
        person person = new person("小白",20);
        System.out.println(person.name);
        System.out.println(person.age);
    }
}

class person {
    String name;
    int age;
    public person(String pname,int page) {
        System.out.println("构造器被调用");
        name = pname;
        age = page;
    }
}
```



构造方法的输入参数名不是非常的好，如果能够将dName 改成name就好了，但我们会发现按照变量的作用域原则，name的值就是null，怎么解决？

## this入门

- 什么是this

Java虚拟机会给每个对象分配this，代表当前对象。

``` java
public class Demo {
    public static void main(String[] args) {
        person person = new person("小白",20);
        System.out.println(person.name);
        System.out.println(person.age);
    }
}

class person {
    String name;
    int age;
    public person(String name,int age) {
        System.out.println("构造器被调用");
        //this.name指属性
        this.name = name;
        this.age = age;
    }
}
```

## this本质

![image-20220808213348244](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808213348244.png)

``` java
public class Demo {
    public static void main(String[] args) {
        person person = new person("小白",20);
        System.out.println("person的hashCode\t" +person.hashCode());
        System.out.println(person.name);
        System.out.println(person.age);
    }
}

class person {
    String name;
    int age;
    public person(String name,int age) {
        this.name = name;
        this.age = age;
        System.out.println("this的hashCode\t" +this.hashCode());
    }
}
```

## this小结

简单的说，哪个对象被调用，this就代表哪个对象



![image-20220808213932989](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220808213932989.png)



## this课堂练习

```java
/*
	定义person类，里面有name、age属性，并提供conpare比较方法，用于判断年龄是否相等。
*/
public class Demo {
    public static void main(String[] args) {
        person person1 = new person("小白",20);
        person person2 = new person("小黑",20);
        System.out.println(person1.compare(person2));
    }
}

class person {
    String name;
    int age;
    public person(String name,int age) {
        this.name = name;
        this.age = age;
    }
    public boolean compare (person p) {
        return this.age == p.age;
    }
}
```

