# 类变量

## 引出

要求：有一群小孩在玩堆雪人，不时有新的小孩加入，请问如何知道现在共有多少人在玩？



思路：

1. 在main方法中定义一个变量 count
2. 当一个小航加入游戏后 count++，最后count 就记录有多少小孩玩游戏

问题分析：

1. count是一个独立对象，很尴尬
2. 以后我们访问 count很麻烦，没有使用到OOP
3. 因此，我们引出 类变量，静态变量

## 类变量快速入门

```Java
public class Demo {
    public static void main(String[] args) {
        child child = new child("大华");
        child.count++;
        child child1 = new child("大白");
        child1.count++;
        System.out.println(child.count);
        System.out.println(child1.count);
    }
}

class child {
    String name;
    //定义一个变量 count，是一个类变量（静态变量）
    //该变量最大的特点就是会被child 类的所有对象实例共享
    public static int count;
    public child(String name) {
        this.name = name;
        System.out.println(name +"加入游戏...");
    }
}
```

## 类变量内存剖析

jdk8以前类变量存放在方法区中，jdk8以后类变量存放在堆中

不管类变量存放在哪里，有两点不变：1.类变量是一个类所有对象共享 2.类变量在类加载的时候就生成

## 类变量使用细节

1. 什么时候使用类变量

​		当我们需要让某个类的所有对象都共享一个变量时，就可以考虑使用类变量（静态变量）比如：定义学生类，统计所有学生共交多少钱时。

2. 类变量与实例变量（普通属性）区别

​		类变量是该类的所有对象共享的，而实例变量是每个对象独享的

3. 加上static称为类变量或静态变量，否则称为实例变量/普通变量/非静态变量
4. 类变量可以通过 类名.类变量名 或者 对象名.类变量名来访问，但Java设计者推荐我们使用 类名.类变量名方式访问

5. 实例变量不能通过 类名.类变量名方式访问
6. 类变量在类加载时就初始化了，也就是说，即使你没有创建对象，只要类加载了。就可以使用类变量了。
7. 类变量的生命周期是随类的加载开始，随类消亡而销毁。

## 类方法快速入门

![image-20220819162016562](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220819162016562.png)

## 类方法最佳实践

使用场景：当方法中不涉及任何和对象相关的成员，则可以将方法设计成静态方法，提高开发效率

比如：工具类中的方法 utils Math类、Arrays类

在程序员实际开发，往往会将一些通用的方法设计成静态方法，这样我们不需要创建对象就可以使用了，比如打印一维数组，冒泡排序，完成某个计算任务 等..

## 类方法注意事项

1. 类方法和普通方法都是随着类的加载而加载，将结构信息存储在方法区

​		类方法中无this的参数

​		普通方法中隐含着this的参数

2. 类方法可以通过类名引用，也可以通过对象名调用
3. 普通方法和对象有关，需要通过对象名调用，比如对象.方法名(参数)。不能通过类名调用

4. 类方法中不允许使用和对象有关的关键字，比如this和super。普通方法可以。
5. 类方法中只能访问静态变量或静态方法
6. 普通成员方法，既可以访问普通成员，也可以访问静态成员

小结：静态方法只能访问静态的成员，非静态的方法可以访问静态成员和非静态成员（必须遵守访问权限）

## main语法说明

解释main方法的形式：public static void main（String[] args){}

1. main方法是虚拟机调用
2. Java虚拟机需要调用main方法，所以该方法的访问权限必须是public

3. Java虚拟机在执行main方法时，不必创建对象，所以该方法必须是static
4. 该方法接受String类型的数组参数，该数组中保存，执行Java命令时传递给所运行的类的参数
5. Java 执行的程序 参数1 参数2 参数3 

![image-20220819182602029](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220819182602029.png)





**main动态传值**




![image-20220819184225967](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220819184225967.png)



``` java
public class Demo {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) +"个参数" +args[i]);
        }
    }
}
```

# 代码块

- 基本介绍

​		代码块又称为初始化块，属于类中的成员【即 是类的一部分】，类似于方法，将逻辑语句封装在方法体中，通过{}包围起来

​		但和方法不同，没有方法名，没有返回，没有参数，只有方法体，而且不用通过对象或类显式调用，而是加载类时，或创建对象时隐式调用

- 基本语法

【修饰符】{

​		代码

};

注意：

1. 修饰符可选，要写的话，也只能写 static
2. 代码块分为两类，使用static修饰的叫静态代码块，没有static修饰的叫普通代码块
3. 逻辑语句可以为任何逻辑语句（输入、输出、方法调用、循环、判断等）
4. ;号可以写上，也可以忽略

- 代码块的好处和案例演示

1. 相当于另外一种形式的构造器（对构造器的补充机制），可以做初始化的操作
2. 如果多个构造器中都有重复的语句，可以抽取到初始化块中，提高代码的重用性

## 代码块快速入门

``` java
public class Demo {
    public static void main(String[] args) {
        Movie movie1 = new Movie("新杨戬", 45, "×××");
        System.out.println();
    }
}
class Movie{
    private String name;
    private int price;
    private String director;
    {
        System.out.println("电影屏幕打开...");
        System.out.println("广告开始了...");
        System.out.println("电影正式开始...");
    }
    //下面的三个构造器有相同的语句
    //这样看起来比较冗余
    //我们把相同的语句放在代码块中即可
    //这样不管我们调用哪个构造器，都会先调用代码块
    //代码块调 用的顺序优先于构造器
    public Movie(String name) {
        this.name = name;
//        System.out.println("电影屏幕打开...");
//        System.out.println("广告开始了...");
//        System.out.println("电影正式开始...");
        System.out.println("Movie(String name)被调用...");
    }

    public Movie(String name, int price) {
        this.name = name;
        this.price = price;
//        System.out.println("电影屏幕打开...");
//        System.out.println("广告开始了...");
//        System.out.println("电影正式开始...");
        System.out.println("Movie(String name, int price)被调用...");
    }

    public Movie(String name, int price, String director) {
        this.name = name;
        this.price = price;
        this.director = director;
//        System.out.println("电影屏幕打开...");
//        System.out.println("广告开始了...");
//        System.out.println("电影正式开始...");
        System.out.println("Movie(String name, int price, String director)被调用...");
    }
}
```

## 代码块使用细节

1. static代码块也叫静态代码块，作用就是对类进行初始化，而且它随着类的加载只会执行一次，如果是普通代码块，每创建一个对象，就执行

2. 类什么时候被加载

   1. 创建对象实例时
   2. 创建子类对象实例，父类也会被加载
   3. 使用类的静态成员时

3. 普通的代码块，在创建对象时，会被隐式地使用

   被创建一次就会调用一次

   如果知识使用类的静态成员，普通代码块并不会执行

**小结**：1.static代码块是类加载时执行，只会执行一次    2.普通代码块是在创建对象时调用，创建一次，调用一次



4. 创建一个对象时，在一个类 调用顺序是

   1. 调用静态代码块和静态属性初始化（注意：静态代码块和静态属性初始化调用的优先级一样，如果有多个静态代码块和多个静态变量初始化，则按他们定义的顺序调用）
   2. 调用普通代码块和普通属性的初始化（注意：普通代码块和普通属性初始化调用的优先级一样，如果有多个普通代码块和多个普通属性初始化，则按定义顺序调用）
   3. 调用构造方法

   即    静态代码块和属性初始化>普通代码块和属性初始化>构造器

5. 构造器的最前面其实隐含了super()和调用普通代码块。静态相关的代码块和属性初始化，在类加载时就执行完毕，因此时优先于构造器和普通代码块完成的。

``` java
class A {
    public A() {//构造器
        //这里有隐藏的执行要求
        //1.super();//这个知识点在讲解继承时说过
        //2.调用普通代码块
        System.out.println("OK");
    }
}
```

6. 创建一个子类对象时（继承关系），他们的静态代码块，静态属性初始化，普通代码块，普通属性初始化，构造方法的调用顺序如下：

   1. 父类的静态代码块和静态属性（优先级一样，按定义顺序执行）
   2. 子类的静态代码块和静态属性（优先级一样，按定义顺序执行）
   3. 父类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
   4. 父类的构造方法
   5. 子类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
   6. 子类的构造方法
   
   
``` java
class A02 { //父类
    private static int n1 = getVal01();
    static {
        System.out.println("A02的一个静态代码块..");//(2)
    }
    {
        System.out.println("A02的第一个普通代码块..");//(5)
    }
    public int n3 = getVal02();//普通属性的初始化
    public static int getVal01() {
        System.out.println("getVal01");//(1)
        return 10;
    }
 
    public int getVal02() {
        System.out.println("getVal02");//(6)
        return 10;
    }
 
    public A02() {//构造器
        //隐藏
        //super()
        //普通代码和普通属性的初始化......
        System.out.println("A02的构造器");//(7)
    }
}
 
class B02 extends A02 { //
 
    private static int n3 = getVal03();
 
    static {
        System.out.println("B02的一个静态代码块..");//(4)
    }
    public int n5 = getVal04();
    {
        System.out.println("B02的第一个普通代码块..");//(9)
    }
 
    public static int getVal03() {
        System.out.println("getVal03");//(3)
        return 10;
    }
 
    public int getVal04() {
        System.out.println("getVal04");//(8)
        return 10;
    }
    //一定要慢慢的去品..
    public B02() {//构造器
        //隐藏了
        //super()
        //普通代码块和普通属性的初始化...
        System.out.println("B02的构造器");//(10)
    }
}
 
 
new B02();
```



7. 静态代码块只能调用静态成员，普通代码块可以调用任意成员

## 代码块课堂练习

1.

``` java
public class Demo {
    public static void main(String[] args) {
        System.out.println("total = +" +Person.total);
        System.out.println("total = +" +Person.total);
    }
}
class Person {
    public static int total;
    static {
        total = 100;
        System.out.println("in static block!");
    }
}
```

输出：in static block!																//在加载类时，给total赋值，调用静态代码块
			total = +100
			total = +100

**2.**

![image-20220819222349004](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220819222349004.png)