# 反射

## 反射机制问题

- 请看下面的问题

  1. 根据配置文件 re.properties 指定信息，创建Cat对象并调用方法hi

     classfullpath = com.hspedu.Cat

     method=hi

     - 老韩思考：使用现有技术，你能做吗

       A:不能

  2. 这样的需求在学习框架时特别多，即通过外部文件配置，在不修改源码情况下，来控制程序，也符合设计模式的 ocp原则（开闭原则：不修改源码，还可以扩展功能）

  3. 快速入门

     ``` java
     public class temp {
         public static void main(String[] args) throws Exception {
             Properties properties = new Properties();
             properties.load(new FileInputStream("src:\\re.properties"));
             String classfullpath = properties.get("classfullpath").toString();
             String methodName = properties.get("method").toString();
             System.out.println("classfulpath=" + classfullpath);
             System.out.println("method=" + methodName);
     
             //创建对象，传统的方法行不通
     //        new classfullpath();
             //使用反射机制解决
             //（1）加载类，返回Class类型的对象cls
             Class cls = Class.forName(classfullpath);
             //（2）通过cls得到你加载的类的实例对象
             Object o = cls.newInstance();
             System.out.println("o的运行类型=" + o.getClass());
             //（3）通过cls得到你加载的类的 methodName的方法
             //     即：在反射中，可以把方法视为对象
             Method method = cls.getMethod(methodName);
             //（4）通过method调用方法：即通过方法对象来实现调用方法
             method.invoke(o);
     
     
         }
     }
     class Cat{
         public void hi(){
             System.out.println("hi");
         }
     }
     ```

## 反射原理图

- Java Reflection

  1. 反射机制允许程序在执行期间借助于Reflection API取得任何类的内部信息（比如：成员变量，构造器，成员方法等），并能操作对象的属性及方法。反射在设计模式和框架的底层都会用到

  2. 加载完类之后，在堆内存中就产生了一个Class类型的对象（一个类只有一个Class对象），这个对象包含了类的完整结构信息。通过这个对象得到类的结构。这个Class对象就像一面镜子，透过这个镜子看到类的结构，所以形象称之为：反射

     

![image-20230126184037779](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230126184037779.png)

## 反射相关类

- 反射机制可以完成
  1. 在运行时判断任意一个对象所属的类
  2. 在运行时构造任意一个类的对象
  3. 在运行时得到任意一个类所具有的成员变量和方法
  4. 在运行时调用任意一个对象的成员变量和方法
  5. 生成动态代理 

- 反射相关的主要类

  这些类在 java.lang.reflection包下：

  1. java.lang.Class：代表一个类，Class对象表示某个类加载后在堆内存中的对象

  2. java.lang.reflect.Method：代表类的方法，Method对象表示某个类的方法

  3. java.lang.reflect.Field：代表类的成员变量，Field对象表示某个类的成员变量

  4. java.lang.reflect.Constructor：代表类的构造方法，Constructor对象表示构造器

     ``` java
     public class Reflection01 {
         public static void main(String[] args) throws Exception {
             Properties properties = new Properties();
             properties.load(new FileInputStream("src\\re.properties"));
             String classfullpath = properties.get("classfullpath").toString(); //com.hspedu.Cat
             String methodName = properties.get("method").toString(); //hi
             Class cls = Class.forName(classfullpath);
             Object o = cls.newInstance();
     
             //java.lang.reflect.Field：代表类的成员变量，Field对象表示某个类的成员变量
             //getField()不能得到私有的属性
             Field nameField = cls.getField("age");
             System.out.println(nameField.get(o));
     
             //java.lang.reflect.Constructor：代表类的构造方法，Constructor对象表示构造器
             //()中可以指定构造器的参数类型，如果不传参就是无参构造器
             Constructor constructor = cls.getConstructor();
             System.out.println(constructor);
             //参数传递String.class就是String类的Class对象
             Constructor constructor1 = cls.getConstructor(String.class);
             System.out.println(constructor1);
         }
     }
     
     ```

## 反射调用优化

- 反射优点和缺点

  1. 优点：可以动态的创建和使用对象（也是框架底层核心），使用灵活，没有反射机制，框架技术就失去底层技术支撑

  2. 缺点：使用反射基本是解释执行，对执行速度有影响

  3. 应用实例：

     ``` java
     public class Reflection01 {
         public static void main(String[] args) throws Exception {
             m1(); //m1()耗时：3
             m2(); //m2()耗时：135
             m3(); //m3()耗时：94
         }
     
         //传统方式调用hi()，将hi()中的输出语句注释
         public static void m1() {
             Cat cat = new Cat();
             long start = System.currentTimeMillis();
             for (int i = 0; i < 90000000; i++) {
                 cat.hi();
             }
             long end = System.currentTimeMillis();
             System.out.println("m1()耗时：" + (end - start));
         }
     
         //反射机制调用hi()，将hi()中的输出语句注释
         public static void m2() throws Exception {
             Class cls = Class.forName("Cat");
             Object o = cls.newInstance();
             Method hi = cls.getMethod("hi");
             long start = System.currentTimeMillis();
             for (int i = 0; i < 90000000; i++) {
                 hi.invoke(o);
             }
             long end = System.currentTimeMillis();
             System.out.println("m2()耗时：" + (end - start));
         }
     
         //反射调用优化：关闭访问检测
         public static void m3() throws Exception {
             Class cls = Class.forName("Cat");
             Object o = cls.newInstance();
             Method hi = cls.getMethod("hi");
             //在反射调用方法时，取消访问检查
             hi.setAccessible(true);
             long start = System.currentTimeMillis();
             for (int i = 0; i < 90000000; i++) {
                 hi.invoke(o);
             }
             long end = System.currentTimeMillis();
             System.out.println("m3()耗时：" + (end - start));
         }
     }
     ```

- 反射调用优化

  1. Method、Field、Constructor对象都有setAccessible()方法
  2. setAccessible()作用是启动和禁用访问安全检查开关
  3. 参数为true表示：反射的对象在使用时取消访问检查，提高反射的效率。
     参数为false表示：反射的对象执行访问检查

## Class类分析

1. Class也是类，因此也继承Object类
2. Class类对象不是new出来的，而是系统创建的
3. 对于某个类的Class类对象，在内存中只有一份，因为类只加载一次
4. 每个类的实例都会记得自己是由哪个Class实例生成
5. 通过Class对象可以完整地得到一个类的完整结构，通过一系列API
6. Class对象存放在堆内存中
7. 类的字节码二进制数据会存放在方法区中，有的地方称为类的元数据（包括：方法，变量名，方法名，访问权限等）

## Class常用方法

![image-20230126214734204](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230126214734204.png)


``` java
public class Class02 {
    public static void main(String[] args) throws Exception {
        String classAllPath = "com.hspedu.Car";
        //1.获取Car类对应的Class对象
        //<?>表示不确定的Java类型
        Class<?> cls = Class.forName(classAllPath);
        //2.输出cls，显示cls对象是哪个类的Class对象
        System.out.println(cls); //com.hspedu.Car
        //输出cls运行类型 java.lang.Class
        System.out.println(cls.getClass());
        //3.获取包名，com.hspedu
        System.out.println(cls.getPackage().getName());
        //4.得到全类名，java.hspedu.Car
        System.out.println(cls.getName());
        //5.通过cls创建对象实例
        Car car = (Car) cls.newInstance();
        System.out.println(car);
        //6.通过反射获取属性 brand
        Field brand = cls.getField("brand");
        System.out.println(brand.get(car));
        //7.通过反射给属性赋值
        brand.set(car, "奔驰");
        System.out.println(brand.get(car));
        //8.获取所有的属性
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}
```

## 获取Class类对象六种方式

1. 前提：已知一个类的全类名，且该类在类路径下，可通过Class类的静态方法forName()获取，可能抛出ClassNotFoundException，实例：Class cls1 = Class.forName("java.lang.Cat")

   应用场景：多用于配置文件，读取类全路径，加载类

2. 前提：若已知具体的类，通过类的class获取，该方式最为安全可靠，程序性能最高

   实例：Class cls2 = Cat.class;

   应用场景：多用于参数传递，比如通过反射得到对应构造器对象

3. 前提：一致某个类的实例，调用该实例的getClass()方法获取Class对象，实例：Class clazz = 对象.getClass();

   应用场景：通过创建好的对象，获取Class对象

4. 其他方式

   ClassLoader cl = 对象.getClass().getClassLoader();

   Class clazz4 = cl.loadClass("类的全类名");

5. 基本数据类型(int,char ,boolean ,float ,double ,byte ,long ,short) 按如下方式得到Class类对象

   Class cls = 基本数据类型.class

6. 基本数据类型对应的包装类，可以通过 .type得到Class对象                     返回的是基本数据类型的class对象

   Class cls = 包装类.type



``` java
import com.hspedu.Car;

//得到Class对象的6种方式
public class GetClass_ {
    public static void main(String[] args) throws Exception {
        //1.Class.forName【编译阶段】，应用场景：通过读取配置文件获取
        String classAllPath = "com.hspedu.Car";
        Class cls1 = Class.forName(classAllPath);
        System.out.println(cls1);

        //2.类名.class【Class类阶段】，应用场景：用于参数传递
        Class cls2 = Car.class;
        System.out.println(cls2);

        //3.对象.getClass()【运行阶段】，应用场景：有对象实例
        Car car = new Car();
        Class cls3 = car.getClass();
        System.out.println(cls3);

        //4.通过类加载器（4种）【类加载阶段】来获取类的Class对象
        //4.1获取Car类的类加载器
        ClassLoader classLoader = car.getClass().getClassLoader();
        //4.2通过类加载器得到Class对象
        Class cls4 = classLoader.loadClass(classAllPath);
        System.out.println(cls4);

        //cls1,cls2,cls3,cls4都是同一个Class对象（一个类只有一个Class对象）
        System.out.println(cls1.hashCode());
        System.out.println(cls2.hashCode());
        System.out.println(cls3.hashCode());
        System.out.println(cls4.hashCode());

        //5.基本数据类型按照如下方式得到Class类对象
        Class<Integer> integerClass = int.class;
        Class<Character> characterClass = char.class;
        Class<Boolean> booleanClass = boolean.class;
        System.out.println(integerClass); //int

        //6.基本数据类型对应的包装类，可以通过.TYPE得到Class类对象  并且返回的是基本数据类型
        Class<Integer> type1 = Integer.TYPE;
        Class<Character> type2 = Character.TYPE;
        System.out.println(type1); //int
    }
}
```

## 哪些类型有Class对象

1. 外部类，成员内部类，静态内部类，局部内部类，匿名内部类
2. 接口
3. 数组
4. 枚举
5. 注解
6. 基本数据类型
7. void

## 动态类加载

反射机制是Java实现动态语言的关键，也就是通过反射实现类动态加载

1. 静态加载：编译时加载相关的，如果没有则报错，依赖性太强
2. 动态加载：运行时加载需要的类，如果运行时不用该类，即使不存在该类，则不报错，降低了依赖性
3. 举例说明



- 类加载时机
  1. 当创建对象时（new）//静态加载
  2. 当子类被加载，父类也加载//静态加载
  3. 调用类中的静态成员时 //静态加载
  4. 通过反射 //动态加载

## 类加载流程图

- 类加载过程图

  ![image-20230126235107418](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230126235107418.png)

- 类加载各阶段完成任务

  ![image-20230126235206410](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230126235206410.png)

加载和连接是由JVM机运行的，初始化是程序员可以去指定的。

## 类加载五个阶段

- 加载阶段

  JVM在该阶段的主要目的是将字节码从不同的数据源（可能是class文件、也可能是jar包，甚至网络）转化为二进制字节流加载到内存中，并生成一个代表该类的java.lang.Class 对象

- 连接阶段——验证
  1. 目的是为了确保Class文件的字节流中包含的信息符合当前要求，并且不会危害虚拟机自身的安全
  2. 包括：文件格式验证（是否以魔数 0xcafebabe开头）、元数据验证、字节码验证和符号引用验证
  3. 可以考虑使用 -Xverify:none 参数来关闭大部分的类验证措施，缩短虚拟机类加载的时间

- 连接阶段——准备

  1. JVM会在该阶段对静态变量，分配内存并默认初始化（对应数据类型的默认初始值，如0、0L、null、false 等）。这些变量所使用的内存都将在方法区中进行分配

  2. 案例：

     ``` java
     //类加载的连接阶段——准备
     public class ClassLoad02 {
         public static void main(String[] args) {
             //...
         }
     }
     
     class A {
         //属性（成员变量，字段）
         //分析类加载的连接阶段——准备，属性是如何处理：
         //1. n1是成员变量，不是静态变量，因此在准备阶段，不会分配内存
         //2. n2是静态变量，分配内存，n2是默认初始化0，而不是20
         //3. n3是static final常量，和静态变量不一样，因为一旦赋值就不变，n3 = 30
         public int n1 = 10;
         public static int n2 = 20;
         public static final int n3 = 30;
     }
     
     ```

- 连接阶段——解析

  1. 虚拟机将常量池内的符号引用替换为直接引用的过程

- 初始化

  1. 到初始化阶段，才真正开始执行类中定义的 Java程序代码，此阶段是执行<clinit>() 方法的过程

  2. <clinit>() 方法是由编译器按语句在源文件中出现的顺序，依次自动收集类中的所有静态变量的赋值动作和静态代码块中的语句，并进行合并。

     举例说明：

     ``` java
     //类加载初始化阶段
     public class ClassLoad03 {
         public static void main(String[] args) throws Exception {
             //1.加载B类，并生成对应的Class类对象
             //2.连接 num = 0;
             //3.初始化阶段：依次自动收集类中的所有静态变量的赋值动作和静态代码块中的语句，并合并
             /*
                 clinit(){
                     System.out.println("B 静态代码块被执行");
                     //num = 300;
                     num = 100;
                 }
                 合并：num = 100;
              */
     
             //new B(); //类加载
             //System.out.println(B.num); //100，如果直接使用类的静态属性，也会导致类的加载
     
             //加载类的时候，是有同步机制控制
             /*
                 protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                     //正因为有这个机制，才能保证某个类在内存中，只有一个 Class 对象
                     synchronized (getClassLoadingLock(name)) {
                         //...
                     }
                 }
              */
             B b = new B();
         }
     }
     class B {
         static {
             System.out.println("B 静态代码块被执行");
             num = 300;
         }
     
         static int num = 100;
         
         public B() {
             System.out.println("B 构造器被执行");
         }
     }
     ```

  3. 虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确地加锁、同步。如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的<clinit>()方法，其他线程都需要阻塞等待，直到活动线程执行完<clinit>() 方法完毕。
