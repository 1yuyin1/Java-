# 类与对象

##  类与对象引出



张老太养了两只猫：一只叫小白，三岁，白色。另一只叫小花，100岁，花色。请编写一个程序，当用户输入小猫的名字时，就显示该猫的名字，年龄，颜色。如果用户输入的小猫名字错误，则显示张老太没有这只猫。

使用现有技术解决的缺点分析：

1. 不利于数据管理
2. 效率低 ==>引出 类与对象（oop）

## 类与对象概述

![image-20220803143135961](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803143135961.png)

![image-20220803143212157](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803143212157.png)

## 面向对象快速入门

张老太养猫问题

``` java
public class Demo {
    public static void main(String[] args) {
            Cat cat1 = new Cat();
            cat1.Name = "小白";
            cat1.Age = 3;
            cat1.Color = "白色";
            Cat cat2 = new Cat();
        cat2.Name = "小花";
        cat2.Age = 5;
        cat2.Color = "花色";
        System.out.println("第一只猫的信息 "+ cat1.Name+ " "+ cat1.Color+ " "+ cat1.Age);
        System.out.println("第二只猫的信息 "+ cat2.Name+ " "+ cat2.Color+ " "+ cat2.Age);
    }
}

class Cat {           //创建Cat类
    String Name;
    String Color;
    int Age;
}
```



![image-20220803144720543](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803144720543.png)

## 对象内存布局

![image-20220803153806767](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803153806767.png)

由于 String 是引用类型，放在方法区

## 属性概念

1. 从概念或叫法上看：成员变量=属性=field （即成员变量是用来表示属性的）
2. 属性是类的一个组成部分，一般是基本数据类型，也可以是引用类型（对象，数组）。

## 属性注意细节

![image-20220803160428858](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803160428858.png)

## 创建对象访问属性

· 如何创建对象

![image-20220803160518569](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803160518569.png)

## 对象分配机制

![image-20220803162558227](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803162558227.png)

## 对象创建过程

![image-20220803163238957](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803163238957.png)

## Java方法快速入门

先在类中创建方法，然后创建对象，再调用

```java
public class Demo {
    public static void main(String[] args) {
            Cat cat1 = new Cat();
            cat1.speak();
    }
}

class Cat {
    String Name;
    String Color;
    int Age;
    public void speak() {
        System.out.println("叫了一声");
    }
}
```

public void speak()    public表示公开，void表示无返回值，（）中可以添加参数

## 方法调用机制

1. 当程序执行到方法时，就会开辟一个独立的空间（栈空间）
2. 当方法执行完毕，或者执行到return语句时，就会返回
3. 返回到调用方法的地方，并释放空间
4. 返回后，继续执行后面的代码
5. 当main方法执行完毕后，整个程序退出

成员方法的好处：

1. 提高代码复用性
2. 将实现的细节封装起来，然后供其他用户来调用即可

## 成员方法的定义

![image-20220803182424054](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803182424054.png)

## 方法使用细节

![image-20220803183702591](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803183702591.png)



![image-20220803183809789](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803183809789.png)



![image-20220803184916909](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220803184916909.png)
