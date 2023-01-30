# 单例设计模式

- 什么是设计模式

  1. 静态方法和属性的经典使用

  1. 设计模式是在大量的实践中总结和理论化之后优选的代码就够、编程风格以及解决问题的思考方式。设计模式就像是经典的棋谱



- 什么是单例模式
  1. 所谓的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例，并且该类只提供一个取得其对象实例的方法
  2. 单例模式有两种方式：1. 饿汉式 2. 懒汉式

## 饿汉式

步骤如下：

   			1. 构造器私有化  =》防止直接new
   			2. 类的内部创建对象
   			3. 向外暴露一个静态的公共方法

```java
public class Demo {
    public static void main(String[] args) {
        System.out.println(GirlFriend.GetGirlFriend());

        GirlFriend gf = GirlFriend.GetGirlFriend();
        System.out.println(gf);
    }
}
class GirlFriend {
    private String name;
    //为了能在静态方法中返回gf对象，修饰符为static
    private static GirlFriend girlFriend = new GirlFriend("小红");
    //1.将构造器私有化
    //2.在类的内部创建对象
    //3.提供一个公共的static方法，返回对象
    private GirlFriend(String name) {
        this.name = name;
    }
    public  static GirlFriend GetGirlFriend () {
        return girlFriend;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

因为只要加载类，就会创建对象，所以称为饿汉式

## 懒汉式

```java
public class Demo {
    public static void main(String[] args) {
        System.out.println(GirlFriend.n);
    }
}
class GirlFriend {
    public static int n = 999;
    private String name;
    //为了能在静态方法中返回gf对象，修饰符为static
    private static GirlFriend girlFriend ;
    //1.将构造器私有化
    //2.在类的内部创建对象
    //3.提供一个公共的static方法，返回对象
    private GirlFriend(String name) {
        System.out.println("构造器被调用");
        this.name = name;
    }
    public  static GirlFriend GetGirlFriend () {
        if(girlFriend == null) {
            girlFriend = new GirlFriend("小红");  //当实例为空时，创建对象
        }
        return girlFriend;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

不调用取得实例的方法，不会创建对象，因此称为懒汉式。

- 饿汉式 VS 懒汉式
    		1. 二者最主要的区别在于创建对象的时机不同：饿汉式是在类加载就创建了对象实例，而懒汉式是在使用时才创建。
        		2. 饿汉式不存在线程安全问题，懒汉式存在线程安全问题（后面学习线程后，会进行完善）
        		3. 饿汉式存在浪费资源的可能。因为程序员如果一个对象实例都没有使用，那么饿汉式创建的对象就浪费了，懒汉式是使用时才创建，就不存在这个问题
        		4. 在JavaSE标准类中，java.lang.Runtime就是经典的单例模式



小结：

1. 单例模式的两种实现方式:1.饿汉式2.懒汉式
2. 饿汉式的问题：在类加载时候就创建，可能存在资源浪费问题
3. 懒汉式的问题：线程安全问题，后面学习线程后，在进行完善

# final

final 可以修饰类、属性、方法和局部变量

在某些情况下，程序员可能有一下需求，就会使用到final

1. 当不希望类被继承时，可以用final修饰
2. 当不希望父类的某个方法被子类覆盖/重写时，可以用final关键字修饰
3. 当不希望类的某个属性的值被修改，就可以用final修饰
4. 当不希望某个局部变量被修改，可以使用final修饰

### final注意事项

1. final修饰的属性又叫常量，一般用XX_XX_XX来命名
2. final修饰的属性在定义时，必须赋初值，并且以后不能再修改，赋值可以在如下位置之一【选择一个位置赋初值即可】
   1. 定义时
   2. 在构造器中
   3. 在代码块中
3. 如果final修饰的属性是静态的，则初始化的位置只能是
   1. 定义时
   2. 在静态代码块，不能在构造器中赋值（因为静态的不需要调用构造器）
4. final类不能继承，但是可以实例化对象
5. 如果类不是final类，但是含有final方法，则该方法虽然不能重写，但是可以继承

6. 一般来说，如果一个类已经是final类了，就没有必要再将方法修饰词final方法
7. final不能修饰构造方法（即构造器）
8. final和static往往搭配使用，效率更高，不会导致类的加载。底层编译器做了优化处理
9. 包装类（Integer，Double，Float,Boolean等都是final），String也是final类

## final课堂练习

**题一：**

编写一个程序，能够计算圆形的面积，要求圆周率为3.14，赋值的位置三个位置都写一下。

``` java
public class Demo {
    public static void main(String[] args) {
        circle circle = new circle(2);
        System.out.println(circle.cal());
    }
}

class circle {
    private double radius;
    private final double PI; // =3.14
    {
        PI = 3.14;
    }
    public circle(double radius) {
        this.radius = radius;
        //PI = 3.14;
    }
    public double cal() {
        return PI*radius*radius;
    }
}
```

**题二：**

![image-20220821200848513](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220821200848513.png)

``` java
public class Something {
    public int addOne(final int x) {//下面的代码是否有误，为什么？
        ++x;//错误，原因是不能修改final x的值
        return x+1;//这里可以
    }
}
```

# 抽象类

## 抽象类引出

当父类的某些方法，需要声明，但是又不确定如何实现时，可以将其声明为抽象方法，那么这个类就是抽象类。

``` java
public class Demo {
    public static void main(String[] args) {
    }
}

abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }
    //所谓抽象方法就是没有实现的方法
    //所谓没有实现，就是没有方法体
    //当一个类中存在抽象方法，需把类声明为abstract类
    //一般来说，抽象类会被继承，由子类来实现
//        System.out.println(name +"不知道吃什么");
    public abstract void eat();
}
```

## 抽象类细节

- 抽象类的介绍

  - 用abstract关键字来修饰一个类时，这个类就叫抽象类

    访问修饰符 abstract 类名{

    }

  - 用abstract 关键字修饰一个方法时，这个方法就是抽象方法

    访问修饰符 abstract 返回类型 方法名(参数列表);//没有方法体

  - 抽象类的价值更多作用是在于设计，是设计者设计好后，让子类继承并实现抽象类

  - 抽象类在框架和设计模式使用较多

- 抽象类使用的注意事项
  - 抽象类不能被实例化
  - 抽象类不一定要包含abstract方法。也就是说，抽象类可以没有abstract方法
  - 一旦类包含了abstract方法，则这个类必须声明为abstract
  - abstract只能修饰类和方法，不能修饰属性和其它的
  - 抽象类可以有任意成员【因为抽象类还是类】，比如：非抽象方法、构造器、静态属性
  - 抽象方法不能有主体，即不能实现
  - 如果一个类继承了抽象类，则它必须实现抽象类的所有抽象方法，除非它自己也声明为abstract类
  - 抽象类不能使用private、final和static来修饰，因为这些关键字都是和重写相违背的

## 课堂练习

编写一个Employee 类，声明为抽象类，包含如下三个属性 : name，id，salary。 提供必要的构造器和抽象方法 : work()。对于Manager 类来说 ，他既是员工，还具有奖金的属性。请使用继承的思想，设计Woker类和Manager类，要求类中提供必要的方法进行属性访问，实现work{} 。



``` java
public class Demo {
    public static void main(String[] args) {
        Woker woker = new Woker("大白", 712, 4300);
        woker.work();
        Manager manager = new Manager("华强", 31, 6000);
        manager.setDonus(2000);
        manager.work();
    }
}

abstract class Employee {
    private String name;
    private int id;
    private float salary;

    public Employee(String name, int id, float salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
    public abstract void work();
}

class Woker extends Employee{
    public Woker(String name, int id, float salary) {
        super(name, id, salary);
    }

    @Override
    public void work() {
        System.out.println("员工" +getName() +" " +getId() +" 正在工作");
    }
}

class Manager extends Employee {
    private float donus;

    public Manager(String name, int id, float salary) {
        super(name, id, salary);
    }

    public float getDonus() {
        return donus;
    }

    public void setDonus(float donus) {
        this.donus = donus;
    }

    @Override
    public void work() {
        System.out.println("经理" +getName() +" " +getId() +" 正在工作");
    }
}
```

## 抽象类实践-模板设计模式



``` java
public class Demo {
    public static void main(String[] args) {
        BB bb = new BB();
        bb.cal();
        CC cc = new CC();
        cc.cal();
    }
}

abstract class AA {
    public abstract void job();//抽象方法
    public void cal() {
        long start = System.currentTimeMillis();
        job();
        long end = System.currentTimeMillis();
        System.out.println("执行任务需要" +(end - start) +"毫秒");
    }
}

class BB extends AA {
    public void job() {
        long num = 0;
        for (int i = 0; i < 800000; i++) {
            num +=i;
        }
    }
}
class CC extends AA {
    @Override
    public void job() {
        long num = 0;
        for (int i = 0; i < 417; i++) {
            for (int j = 0; j < 350; j++) {
                for (int k = 0; k < 257; k++) {
                    num ++;
                }
            }
        }
    }
}
```

用抽象类作为模板，其中抽象方法需要在子类重写
