# super

## super 基本语法

- 基本介绍 super代表父类的引用，用于访问父类的属性、方法、构造器

  1. 访问父类的属性，但不能访问父类的private属性  super.属性名

  2. 访问父类的方法，但不能访问父类的private方法  super.方法名

  3. 访问父类的构造器（这点前面用过）：

     ​									super（参数列表）；只能放在构造器的第一句

## super使用细节

1. 调用父类的构造器的好处（分工明确，父类属性由父类初始化，子类的属性由子类初始化）
2. 当子类中有和父类中的成员（属性和方法）重名时，为了访问父类的成员，必须通过super，如果没有重名，使用super、this、直接访问是一样的效果！
3. super的访问不限于直接父类，如果爷爷类和本类中有同名的成员，也可以使用super去访问爷爷类的成员；如果多个基类（上级类）中都有同名的成员，使用super访问遵循就近原则。A->B->C



​																					 **super和this的比较** 

| No.  | 区别点     | this                                                   | super                                    |
| ---- | :--------- | ------------------------------------------------------ | ---------------------------------------- |
| 1    | 访问属性   | 访问本类中的属性，如果本类没有此属性则从父类中继续查找 | 访问父类中的属性                         |
| 2    | 调用方法   | 访问本类中的方法，如果本类没有此方法则从父类继续查找   | 从父类查找方法                           |
| 3    | 调用构造器 | 调用本类构造器，必须放在构造器的首行                   | 调用父类构造器，必须放在子类构造器的首行 |
| 4    | 特殊       | 表示当前对象                                           | 子类中访问父类对象                       |

# 方法重写

## 基本介绍

方法覆盖就是子类有一个方法，和父类的某个方法的名称、返回类型、参数一样，那么我们就说子类的这个方法覆盖了父类的方法

## 注意事项

1. 子类的方法的形参列表，方法名称要和父类的参数，方法名称完全一样。
2. 子类方法的返回类型和父类的返回类型一样，或者是父类返回类型的子类。比如父类返回类型是Object，子类方法返回类型是String
3. 子类方法不能缩小父类方法的访问权限



​																							**重写和重载**

| 名称             | 发生范围 | 方法名   | 形参列表                         | 返回类型                                           | 修饰符                             |
| ---------------- | -------- | -------- | -------------------------------- | -------------------------------------------------- | ---------------------------------- |
| 重载（overload） | 本类     | 必须一样 | 类型，个数或者顺序至少有一个不同 | 无要求                                             | 无要求                             |
| 重写（override） | 父子类   | 必须一样 | 相同                             | 子类重写的方法，返回类型和父类的一样，或者是其子类 | 子类方法不能缩小父类方法的访问范围 |

# 多态

## 引出多态

![image-20220813190212358](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220813190212358.png)

传统方法代码的复用性不高，而且不利于代码维护

## 方法的多态

​		多态：方法或对象具有多种形态，是面向对象的第三大特征，多态是建立在封装和继承之基础上的。

## 对象的多态

1. 一个对象的编译类型和运行类型可以不一致
2. 编译类型在定义对象时就确定了，不能改变
3. 运行类型是可以变化的
4. 编译类型看定义时 = 号的左边，运行类型看 = 号的右边

Animal animal = new Dog();   【animal编译类型是Animal，运行类型是Dog】

animal = new Cat();  【animal的运行类型变成了Cat，编译类型仍然是Animal】



<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220813192057549.png" alt="image-20220813192057549" style="zoom:150%;" />

## 多态快速入门

将Master类中的feed的形参列表改为(Animal animal,Food food)

## 向上转型

- 多态的前提是：两个对象（类）存在继承关系
- 多态的向上转型

1. 本质：父类的引用指向了子类的对象
2. 语法：父类类型    引用名 = new 子类类型();
3. 特点：编译类型看左边，运行类型看右边。

​		可以调用父类中的所有成员（需遵守访问权限），

​		不能调用子类中特有成员；（因为在编译阶段，能调用哪些成员，是由编译类型决定的）

​		最终运行效果看子类（运行类型）的具体实现！即调用方法时，按照从子类（运行类型）开始查找方法

## 向下转型

1. 语法：子类类型 		引用名 = （子类类型）父类引用名;
2. 只能强转父类的引用，不能强转父类的对象
3. 要求父类的引用必须指向的是当前目标类型的对象l
4. 当向下转型后可以调用子类类型中所有成员

强转父类，其实是生成一个子类的引用名并指向之前生成的对象

## 属性重写问题

- 属性没有重写之说！属性的值看编译类型
- instanceOf  比较操作符，用于判断对象的运行类型是否为XX类型或XX类型的子类型 

## 多态课堂练习

![image-20220813221216072](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220813221216072.png)



![image-20220813221533759](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220813221533759.png)

20 20 true 10 20

## 动态绑定机制

1. 当调用对象方法的时候，该方法会和该对象的内存地址、运行类型绑定
2. 当调用对象属性时，没有动态绑定机制，哪里声明，哪里调用

## 多态数组

1. 数组的定义类型为父类类型，里面保存的实际元素类型为子类类型

​	应用实例：现有一个继承结构如下：要求创建1个Person对象、2个Student对象和2个Teacher对象，统一放在数组中，并调用每个对象say方法

![image-20220814184310501](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220814184310501.png)

``` java 
public class Demo {
    public static void main(String[] args) {
        Person[] persons = new Person[5];
        persons[0] = new Person("小白", 11);
        persons[1] = new Student("小明", 16, 78);
        persons[2] = new Student("小华", 17, 86);
        persons[3] = new Teacher("老李", 36, 6000);
        persons[4] = new Teacher("老王", 44, 7000);
        for (int i = 0; i < 5; i++) {
            System.out.println(persons[i].say());
        }
    }
}
```

``` java 
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String say() {
        return name + "\t" +"年龄 " +age;
    }
}
```

``` java 
public class Student  extends Person{
    private double score;

    public Student(String name, int age, double score) {
        super(name, age);
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String say() {
        return "学生" +super.say() +"\t成绩 " +getScore();
    }
}

```

``` java 
public class Teacher extends Person {
    private int salary;

    public Teacher(String name, int age, int salary) {
        super(name, age);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
       this.salary = salary;
    }

    @Override
    public String say() {
        return "老师" +super.say() +"\t薪水 " +getSalary();
    }
}

```



​		应用实例升级：如何调用子类特用的方法，比如Teacher有一个teach，Student有一个study，怎么调用？

将主函数改为：

``` JAVA 
public class Demo {
    public static void main(String[] args) {
        Person[] persons = new Person[5];
        persons[0] = new Person("小白", 11);
        persons[1] = new Student("小明", 16, 78);
        persons[2] = new Student("小华", 17, 86);
        persons[3] = new Teacher("老李", 36, 6000);
        persons[4] = new Teacher("老王", 44, 7000);
        for (int i = 0; i < 5; i++) {
            if(persons[i] instanceof Student) {
                ((Student)persons[i]).study();
/*                也可以使用
                        ((Student)persons[i]).study();
 */
            } else if(persons[i] instanceof Teacher) {
                Teacher teacher = (Teacher) persons[i];
                teacher.teach();
            } else {
                System.out.println(persons[i].say());
            }
        }
    }
}
```

将运行类型为student和teacher类的向下转型再调用其特有方法

## 多态参数

方法定义的形参类型为父类类型，实参类型允许为子类类型

![image-20220814212208625](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220814212208625.png)