# Object类详解

## == 和equals

- ​	== 和equals的对比

1.  == ：既可以判断基本类型，又可以判断引用类型
2.  == ：如果判断基本类型，判断的是值是否相等
3.  == ：如果判断引用类型，判断的是地址是否相等，即判定是不是同一个对象
4. equals：是Object类中的方法，只能判断引用类型
5. 默认（Object类）判断的是地址是否相等，子类(引用类型）中往往重写该方法，用于判断内容是否相等

## 重写equals方法

应用实例：判断两个Person对象的内容是否相等，如果两个Person对象的各个属性都相等，则返回true，反之false。

``` java 
public class Demo {
    public static void main(String[] args) {
        Person person1 = new Person("小白", 16, "male");
        Person person2 = new Person("小华", 17, "female");
        Person person3 = new Person("小白", 16, "male");
        System.out.println(person1.equals(person2));//false
        System.out.println(person1.equals(person3));//true
    }
}

class Person {
    private String name;
    private int age;
    private String gender;

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public boolean equals(Object obj) {
        //判断对象是否相等
        if (this == obj) {
            return true;
        } else if (obj instanceof Person) {  //向下转换，并判断各个属性是否相等
            Person p = (Person) obj;
            return this.name.equals(p.name) && this.age == p.age && this.gender.equals(p.gender);
        } return false;
    }
}
```

**练习2：**

![image-20220815112726639](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220815112726639.png)

**练习3：**

![image-20220815113129592](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220815113129592.png)

## hashCode

1. 提高具有哈希结构的容器的效率
2. 两个引用，如果指向的是同一个对象，则哈希值肯定是一样的
3. 两个引用，如果指向的是不同对象，则哈希值是不一样的
4. 哈希值主要根据地址号来的，不能完全将哈希值等价于地址

## toString

![image-20220815175142334](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220815175142334.png)

## finalize方法

1. 当对象被回收时，系统自动调用该对象的finalize方法，子类可以重写该方法，做一些释放资源的操作
2. 什么时候被回收：当某个对象没有任何引用时，则jvm就认为这个对象是一个垃圾对象，就会使用垃圾回收机制来销毁对象，在销毁该对象前，会先调用finalize方法
3. 垃圾回收机制的调用，是由系统来决定（即有自己的gc算法），也可以通过Syestem.gc()主动触发垃圾回收机制

提示：我们在实际开发中，几乎不会运用finalize方法，所以更多是为了应付面试

``` java 
public class Demo {
    public static void main(String[] args) {
        Person person1 = new Person("小白");
        person1 = null;
        System.gc();
        System.out.println("程序运行结束");
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("我们销毁了" +name);
        System.out.println("并且释放了某些资源....");
    }
}
```

![image-20220815192518337](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220815192518337.png)

# 断点调试

1. 断点调试是指在程序的某一行设置一个断点，调试时，程序运行到这一行就会停，然后可以一步一步往下调试，调试过程中可以看各个变量当前的值，出错的话，调试到出错的代码即显示错误，停下。进而分析从而找到这个Bug。
2. 断点调试是程序员必须掌握的技能
3. 断点调试也能帮助我们查看Java底层源代码的执行过程，提高程序员的水平
4. 在断点调试过程中，是运行状态，是以对象的运行类型来执行的。

- 断点调试的快捷键：

​		F7（跳入） F8（跳过） shift+F8（跳出） F9（resume，执行到下一个断点）

​		F7:跳入方法内

​		F8：逐行执行代码

​		shift+F8：跳出方法

![image-20220815220321681](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220815220321681.png)

**案例一：**

```java
public class Demo {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
            System.out.println(sum);
        }
        System.out.println("已退出for....");
    }
}
```

**案例二：**

```java
//数组越界
public class Demo {
    public static void main(String[] args) {
        int[] arry = {-1,3,6};
        for (int i = 0; i <= arry.length; i++) {
            System.out.println(arry[i]+" ");
        }
        System.out.println("退出for....");
    }
}
```

**案例三：**

```java
//查看源码如何实现
import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        int[] arry = {-10,2,1,30,21,19};
        Arrays.sort(arry);
        for (int i = 0; i < arry.length; i++) {
            System.out.println(arry[i]+" ");
        }
        System.out.println("退出for....");
    }
}
```

**案例四：**

```java
//使用F9resume
import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        int[] arry = {-10,2,1,30,21,19};
        Arrays.sort(arry);
        for (int i = 0; i < arry.length; i++) {
            System.out.println(arry[i]+" ");
        }
        System.out.println("退出for....");
        System.out.println("hello100");
        System.out.println("hello200");
        System.out.println("hello300");
        System.out.println("hello400");
        System.out.println("hello500");
    }
}
```

**案例五：**

```java
//使用断点调试，追踪下一个对象的创建过程
//1.加载类信息	2.1默认初始化 2.2显式初始化 2.3构造器初始化
public class Demo {
    public static void main(String[] args) {
        Person person = new Person("小白", 11);
        System.out.println(person);
    }
}
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

**案例六：**

​	使用断点调试，观察动态绑定机制如何工作