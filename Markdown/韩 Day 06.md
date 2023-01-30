# 访问修饰符

## 访问修饰符规则

Java提供四种访问控制修饰符号，用于控制方法和属性的访问权限

1. 公开级别：用public修饰，对外公开
2. 受保护级别：用protected修饰，对子类和同一个包中的类公开
3. 默认级别：没有修饰符号，向同一个包的类公开
4. 私有级别：用private修饰，只有类本身可以访问，不对外公开

![image-20220811165510070](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220811165510070.png)

# 封装

## 封装介绍

把抽象出的数据（属性）和对数据的操作（方法）封装在一起，数据被保护在内部，程序的其它部分只有通过被授权的操作（方法），才能对数据进行操作。

封装的好处：

1. 隐藏实现细节
2. 可以对细节进行验证，保证安全合理

## 封装步骤

1. 将属性私有化private
2. 提供一个公共的set方法，用于对属性判断并赋值
3. 提供一个公共的get方法，用于获取属性的值

## 封装快速入门

``` java
/*姓名可直接查看，年龄、薪水不可直接查看。姓名字符长度在2-6之间，年龄在1-120之间。
*/
public class Demo {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("小白");
        person.setAge(20);
        person.setSalary(10000);
        System.out.println(person.info());
    }
}

class Person {
    public String name;
    private int age;
    private int salary;

    private String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() >= 2 &&name.length() <=6) {
            this.name = name;
        } else System.out.println("名称长度不正确,恢复默认值");
    }

    private int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age >= 1&&age <= 120) {
            this.age = age;
        } else System.out.println("年龄违规，恢复默认值");
    }

    private int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    public String info () {
        return "姓名为 " +this.name +"\t年龄为 " +this.age +"\t薪水为 " +this.salary;
    }
}
```

## 封装与构造器

如果要在构造器中设置值，可以在构造器中调用setXxx方法

# 继承

## 为什么需要继承

当两个类代码大量相同，使用继承减少代码复用性

## 继承原理

 	继承可以解决代码复用，让我们的编程更加靠近人类思维。当多个类存在相同的属性和方法时，可以从这些类中抽象出父类，在父类中定义这些相同的属性和方法，所有的子类不需要重新定义这些属性和方法，只需要通过extends来声明继承父类即可。

![image-20220812165228882](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220812165228882.png)



继承的基本语法：

class 子类 extends 父类 {

}

1. 子类就会自动拥有父类定义的属性和方法
2. 父类又叫超类，基类
3. 子类又叫派生类  

## 继承使用细节

1. 子类继承了所有的属性和方法，但私有属性不能在子类直接访问，要通过公共的方法去访问

2. 子类必须调用父类的构造器，完成父类的初始化
3. 当创建子类对象时，不管使用子类的哪个构造器，默认情况下总会去调用父类的无参构造器，如果父类没有提供无参构造器，则必须在子类的构造器中用super去指定使用父类的哪个构造器完成对父类的初始化工作，否则，编译不会通过。
4. 如果希望指定去调用父类的某个构造器，则显式的调用一下 ：super（参数列表）
5. super在使用时，必须放在构造器的第一行（super只能在构造器中使用）
6. super（）和this（）都只能放在构造器的第一行，因此这两个方法不能共存在一个构造器
7. Java中Object是所有类的父类
8. 父类构造器的调用不限于直接父类，将一直往上追溯到Object类（顶级父类）
9. 子类最多继承一个父类（指直接继承），即Java中是单继承机制

​		思考：如何让A类继承B类和C类   （让A继承B，B继承C）

10. 不能滥用继承，子类和父类之间必须满足 is-a的逻辑关系

## 继承本质

![image-20220812190215209](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220812190215209.png)



![image-20220812190319257](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220812190319257.png)

当子类对象创建好之后，建立查找（从子类到父类）的关系

## 课堂练习



![image-20220812203248953](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220812203248953.png)