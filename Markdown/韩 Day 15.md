# 异常

## 异常处理入门

``` java
public class Demo {
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 0;
//        int res = num1 / num2;
        try {
            int res = num1 / num2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("程序继续运行");
    }
}
```

## 异常基本介绍

- Java语言中，将程序执行中发生的不正常情况称为“异常”。（开发过程中的语法错误和逻辑错误不是异常）

- 执行过程中所发生的异常事件可分为两大类

  - Error：Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。比如：StackOverflowError【栈溢出】和OOM（out of memory），Error是严重错误，程序会崩溃。
  - Exception：其它因编程错误或偶然的外在因素导致的一般性问题，可以使用针对性的代码进行处理。如空指针访问，试图读取不存在的文件，网络连接中断等等，Exception分为两大类：运行时异常【程序运行时，发生的异常】和编译时异常【编译时，编译器检查出的异常】。

  ## 异常体系图

  **![image-20220828154136321](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220828154136321.png)**

- 异常体系图的小结
  - 异常分为两大类，运行时异常和编译时异常。
  - 运行时异常。编译器检查不出来。一般是指编程时的逻辑错误，是程序员应该避免其出现的异常。java.lang.RuntimeException类及它的子类都是运行时异常。
  - 对于运行时异常，可以不做处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响。
  - 编译时异常，是编译器要求必须处置的异常。

![image-20220828154519910](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220828154519910.png)

## 五大运行时异常

- 常见的运行时异常包括

  - NullPointerException 空指针异常

    当应用程序试图在需要对象的地方使用null时，抛出该异常。

    ```java
    public static void main(String[] args) {
        String name = null;
        System.out.println(name);
    }
    ```

  - ArithmeticException 数学运算异常

    当出现异常的运算条件时，抛出此异常。例如：一个整数除以零，抛出此类的一个实例

  - ArrayIndexOutOfBoundsException 数组下标越界异常

    用非法索引访问数组时抛出的异常。如果索引为负或大于等于数组大小，则该索引为非法索引

    ``` java
    public static void main(String[] args) {
        int[] nums = new int[3];
        for (int i = 0; i <= nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
    ```

  - ClassCastException 类型转换异常

    当试图将对象强制转换为不是实例的子类时，抛出该异常

    ``` java
    public class Demo {
        public static void main(String[] args) {
            A b = new B();
            B b1 = (B)b;
            C b2 = (C)b;
        }
    }
    class A {}
    class B extends A {}
    class C extends A {}
    ```

  - NumberFormatException 数字格式不正确异常

    当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。=>使用异常我们可以确保输入是满足条件数字。

    ```java
    public static void main(String[] args) {
        String a = "hhh";
        int num = Integer.parseInt(a);
        System.out.println(num);
    }
    ```

## 异常处理

- 基本介绍

  异常处理就是当异常发生时，对异常处理的方式

- 异常处理的方式

  - try-catch-finally

    程序员在代码中捕获发生的异常，自行处理

  - throws

    将发生的异常抛出，交给调用者（方法）来处理，最顶级的处理者就是JVM

## try-catch

1. Java提供try和catch块来处理异常，try块用于包含可能出错的代码。catch块用于处理try块中发生的异常。可以根据需要在程序中有多个try...catch块。

2. 基本语法

   ``` java
   try{
       //可疑代码
   }catch(异常){
       //对异常的处理
   }
   ```

3. try-catch方式处理异常-注意事项

- 如果异常发生了，则异常发生后面的代码不会执行，直接进入到catch块。
- 如果异常没有发生，则顺序执行try的代码块，不会进入到catch。
- 如果希望不管是否发生异常，都执行某段代码（比如关闭连接，释放资源等）则使用代码-try-catch-finally。

4. 可以有多个catch语句，捕获不同的异常（进行不同的业务处理），要求父类异常在后，子类异常在前，比如（Exception在后，NullPointerException在前），如果发生异常，只会匹配一个catch。
5. 可以进行try-finally配合使用，这种用法相当于没有捕获异常，因此程序会直接崩掉。应用场景，就是执行一段代码，不管是否发生异常，都必须执行某个业务逻辑

## try-catch练习

**题一：**

![image-20220828181812065](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220828181812065.png)

**题二：**

![image-20220828181923435](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220828181923435.png)

**题三：**

![image-20220828182007947](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220828182007947.png)

- 执行顺序小结

  - 如果没有出现异常，则执行try块中所有语句，不执行catch块中语句，如果有finally，最后还需要执行finally里面的语句

  - 如果出现异常，则try块中异常发生后，try块剩下的语句不再执行。将执行catch块中的语句，如果有finally，最后还需要执行finally里面的语句

    

- **练习题**

  如果用户输入的不是一个整数，就提醒他反复输入，知道输入一个整数为止

``` java
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = null;
        int num = 0;
        System.out.println("请输入一个整数");
        while(true) {
            str = scanner.next();
            try {
                num = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("请重新输入");
            }
        }
        System.out.println("输入的整数为" +num);
    }
}
```

## throws入门案例

- 基本介绍
  - 如果一个方法（中的语句执行时）可能生成某种异常，但是并不能确定如何处理这种异常，则此方法应显示地声明抛出异常，表明该方法将不对这些异常处理，而由该方法的调用者负责处理。
  - 在方法声明中用throws语句可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类

## throws使用细节

- 注意事项和使用细节
  - 对于编译异常，程序中必须处理，比如try-catch或者throws
  - 对于运行时异常，程序中如果没有处理，默认就是throws的方式处理
  - 子类重写父类的方法时，对抛出异常的规定：子类重写的方法，所抛出的异常要么和父类抛出的异常一致，要么为父类抛出异常的类型的子类型
  - 在throws过程中，如果有方法try-catch，就相当于异常处理，就可以不必throws

## 自定义异常

- 基本概念
  - 当程序中出现了某些"错误"，但该错误信息并没有在Throwable子类中描述处理，这个时候可以自己设计异常类，用于描述该错误信息

- 自定义异常的步骤
  - 定义类：自定义异常类名（程序员自己写）继承Exception或RuntimeException
  - 如果继承Exception，属于编译异常
  - 如果继承RuntimeException，属于运行异常（一般来说，继承RuntimeException）

- 实例

  当我们接收Person对象年龄时，要求范围在18-120之间，否则抛出一个自定义异常。

  ```java
  public class Demo {
      public static void main(String[] args) {
          int age = 200;
          if(!(age >= 20 && age <= 120)) {
              //这里我们可以通过构造器，设置信息
              throw new AgeException("年龄需要在18~20");
          }
          System.out.println("你的年龄范围正常");
      }
  }
      //一般情况下，我们自定义异常是继承RuntimeException
      //即把自定义异常当作运行时异常，好处是，可以使用默认的编译机制
      //即比较方便
  class AgeException extends RuntimeException{
      public AgeException(String message) {
          super(message);
      }
  }
  ```

## throw VS throws

|        | 意义                     | 位置       | 后面跟的东西 |
| ------ | ------------------------ | ---------- | ------------ |
| throws | 异常处理的一种方式       | 方法声明处 | 异常类型     |
| throw  | 手动生成异常对象的关键字 | 方法体中   | 异常对象     |



![image-20220830214944968](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220830214944968.png)

