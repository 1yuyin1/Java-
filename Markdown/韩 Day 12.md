# 接口

## 接口快速入门

``` java
public class Demo {
    public static void main(String[] args) {
        computer computer = new computer();
        phone phone = new phone();
        camera camera = new camera();
        computer.working(phone);
        System.out.println("===================");
        computer.working(camera);
    }
}
class computer {
    public void working(Usb usb) {
        usb.start();
        usb.end();
    }
}
class camera implements Usb {
    @Override
    public void start() {
        System.out.println("相机开始工作...");
    }

    @Override
    public void end() {
        System.out.println("相机结束工作...");
    }
}
class phone implements Usb {
    @Override
    public void start() {
        System.out.println("手机开始工作...");
    }

    @Override
    public void end() {
        System.out.println("手机结束工作...");
    }
}
```

## 接口基本介绍

- 基本介绍

  - 接口就是给出一些没有实现的方法，封装到一起，当某个类要使用的时候，在具体情况把这些方法写出来。

    interface 接口名{

    ​		//属性

       	//方法（1.抽象方法2.默认实现方法3.静态方法）

    }

  - class 类名 implements 接口{

    ​	自己属性;

    ​	自己方法;

    ​	必须实现的接口的抽象方法

    }

小结：

1. 在jdk7.0前，接口里的所有方法都没有方法体，即是抽象方法。
2. jdk8.0后接口可以有静态方法，默认方法（用default修饰)，也就是说接口中可以有方法的具体实现

## 接口应用场景

1. 说现在要制造战斗机，专家只需把飞机需要的功能/规格定下来即可，然后让别的人具体实现就行。
2. 现在有一个项目经理，管理三个程序员，开发一个软件，为了控制和管理软件，项目经理可以定义一些接口，然后由程序员具体实现。

更加规范化

## 接口细节

1. 接口不能被实例化
2. 接口中所有的方法是public方法，接口中抽象方法，可以不用abstract修饰
3. 一个普通类实现接口，就必须将该接口的所有方法实现
4. 抽象类实现接口，可以不用实现接口的方法
5. 一个类同时可以实现多个接口
6. 接口中的属性只能是final的，而且是public static final 修饰符。比如：int a = 1;实际上是 public static final int a = 1;(必须初始化)
7. 接口中属性的访问形式：接口名.属性名
8. 一个接口不能继承其它的类，但是可以继承多个别的接口
9. 接口的修饰符只能是public和默认，这点和类的修饰符是一样的

## 接口VS继承

- 实现接口 vs 继承类
  - 接口和继承解决的问题不同
    - 继承的价值主要在于：解决代码的复用性和可维护性
    - 接口的价值主要在于：设计，设计好各种规范（方法），让其它类去实现这些方法，即更加的灵活
  - 接口比继承更加灵活
    - 接口比继承更加灵活，继承是满足 is-a的关系，而接口只需满足 like - a的关系
  - 接口在一定程度上实现代码解耦【即：接口规范性+动态绑定机制】

## 接口多态特性

接口的多态特性

- 多态参数（前面案例体现）

  - 在前面的Usb接口案例，Usbinterface usb，既可以接收手机对象，又可以接收相机对象，就体现了 接口 多态（接口引用可以指向实现了接口的类的对象）

- 多态数组

  - 演示一个案例：给Usb数组中，存放Phone和相机对象，Phone类还有一个特有的方法call(),请遍历Usb数组，如果是Phone对象，除了调用Usb接口定义的方法外，还需要调用Phone特有方法call

    - ``` java
      public class Demo {
          public static void main(String[] args) {
              Usb[] usbs = new Usb[2];
              usbs[0] = new Phone();
              usbs[1] = new Camera();
              for (int i = 0; i < usbs.length; i++) {
                  usbs[i].work();
                  if(usbs[i] instanceof Phone) {
                      ((Phone) usbs[i]).call();
                  }
              }
          }
      }
      interface Usb {
          void work();
      }
      class Phone implements Usb{
      
          @Override
          public void work() {
              System.out.println("手机正在工作");
          }
          public void call() {
              System.out.println("拨通了电话");
          }
      }
      class Camera implements Usb{
          @Override
          public void work() {
              System.out.println("相机正在工作");
          }
      }
      ```

  - 接口存在多态传递现象

    - 通过接口的继承，实现接口的多态传递

    - ```java
      public class Demo {
          public static void main(String[] args) {
              IG ig = new gg();
              ig.hi();
              IH ih = new gg();
          }
      }
      
      interface IH {
          void hi();
      }
      interface IG extends IH{}
      class gg implements IG{
      
          @Override
          public void hi() {
              System.out.println("调用了IH的方法");
          }
      }
      ```

## 接口课堂练习

![image-20220823213758981](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220823213758981.png)

修改后：

``` java
interface A {
    int x = 0;
}

class B {
    int x = 1;
}

class C extends B implements A {
    public void Px() {
        System.out.println(super.x);
        System.out.println("===================");
        System.out.println(A.x);
//        System.out.println(x);
    }
    public static void main(String[] args) {
        new C().Px();
    }
}
```

