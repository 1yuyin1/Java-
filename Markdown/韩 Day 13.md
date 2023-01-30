# 内部类

## 四种内部类

- 基本介绍

  - 一个类的内部又完整地嵌套了另一个类结构，被嵌套的类称为内部类，嵌套其它类的类称为外部类。是我们类的第五大成员，内部类最大的特点是可以直接访问私有属性，并且可以体现类与类之间的包含关系

- 基本语法

  class Outer{ //外部类

  ​	class Inner{ //内部类

  ​	}

  }

  class Other { //外部其他类

  }

- 内部类的分类

  - 定义在外部类局部位置上（比如方法内）
    - 局部内部类（有类名）
    - 匿名内部类（没有类名）
  - 定义在外部类的成员位置上
    - 成员内部类（没用static修饰）
    - 静态内部类（使用static修饰）

# 局部内部类

- 局部内部类的使用
  - 说明：局部内部类是定义在外部类的局部位置，比如方法中，且有类名。
  - 可以直接访问外部类的所有成员，包括私有的
  - 不能添加访问修饰符，因为它的地位就是一个局部变量，局部变量是不能使用修饰符的。但是可以使用final修饰，因为局部变量也可以使用final
  - 作用域：仅仅在定义它的方法或代码块中
  - 局部内部类--访问---->外部类的成员 【访问方式：直接访问】
  - 外部类--访问---->局部内部类的成员
  - 访问方式：创建对象，再访问（注意：必须在作用域内）
  - 外部其它类--不能访问---->局部内部类（因为局部内部类地位是一个局部变量）
  - 如果外部类和局部内部类的成员重名时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问

# 匿名内部类 *

## 匿名内部类本质

1.本质是类 2.内部类 3. 该类没有名字 4.同时还是一个对象

说明：匿名内部类是定义在外部类的局部位置，比如方法中，并且没有类名

匿名内部类的基本语法

new 类或接口(参数列表) {

​		类体

};

``` java
public class Demo {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.method();
    }
}

class Outer {
    private int num = 1;
    public  void method() {
        IA tiger = new IA() {
            @Override
            public void say() {
                System.out.println("老虎叫了一声");
            }
        };
        System.out.println(tiger.getClass());
        tiger.say();
    }
}
interface IA{
    void say();
}
```

## 匿名内部类使用

``` java
public class Demo {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.method();
    }
}

class Outer {
    private int num = 1;
    public  void method() {
        // class Outer$1 extends Father {
        //  }
        Father father = new Father("大华") {
            public void say(){
//                super.say();
                System.out.println("匿名内部类重写了方法");
            }
        };
        System.out.println(father.getClass());
        father.say();
        nn nn = new nn() {
            @Override
            public void cry() {
                super.cry();
            }
        };
        nn.cry();
    }
}
class Father {
    private String name;

    public Father(String name) {
        this.name = name;
    }
    public void say() {
        System.out.println(name);
    }
}
abstract class nn {
    public void cry() {
        System.out.println("ccry");
    }
}
```

## 匿名内部类细节

1. 匿名内部类的语法比较奇特，因为匿名内部类既是一个类的定义，同时它本身也是一个对象，因此从语法上看，它既有定义类的特征，也有创建对象的特征，对前面代码分析可以看出，因此可以直接调用匿名内部类方法。
2. 可以直接访问外部类的所有成员，包括私有的
3. 不能添加访问修饰符，因为它的地位就是一个局部变量
4. 作用域：仅仅在定义它的方法或代码块中。
5. 匿名内部类--访问-->外部类成员 【访问方式：直接访问】

## 匿名内部类实践

- 当作实参直接传递，简介高效

  

  - ```java
    public class Demo {
        public static void main(String[] args) {
            show(new Usb() {
                @Override
                public void say() {
                    System.out.println("hello");
                }
            });
        }
        public static void show(Usb usb) {
            usb.say();
        }
    }
    
    interface Usb {
        void say ();
    }
    ```

- 有一个铃声接口Bell，里面有个ring方法

  有一个手机类CellPhone，具有闹钟功能alarmclock，参数是Bell类型

  测试手机类的闹钟功能，通过匿名内部类（对象）作为参数，打印懒猪起床了

  再传入另一个匿名内部类（对象），打印：小伙伴上课了
  
  ```java
  public class Demo {
      public static void main(String[] args) {
          CellPhone cellPhone = new CellPhone();
          cellPhone.alarmclock(new Bell() {
              @Override
              public void ring() {
                  System.out.println("懒猪起床了");
              }
          });
          System.out.println("=================");
          cellPhone.alarmclock(new Bell() {
              @Override
              public void ring() {
                  System.out.println("小伙伴上课了");
              }
          });
      }
  }
  
  interface Bell {
      void ring();
  }
  class CellPhone {
      public void alarmclock(Bell bell) {
          bell.ring();
      }
  }
  ```

# 成员内部类

说明：成员内部类是定义在外部类的成员位置，并且没有static修饰。

1. 可以直接访问外部类的所有成员，包括私有的

2. 可以添加任意访问修饰符（public、protected、默认、private），因为它的地位就是一个成员

3. 作用域    和外部类的其他成员一样，为整个类体

4. 成员内部类--访问---->外部类（比如：属性）【访问方式：直接访问】

5. 外部类---访问---->内部类（说明）【访问方式：先创建对象，再访问】

6. 外部其它类---访问---->成员内部类

   三种方法

   ``` java
   public class Demo {
       public static void main(String[] args) {
           Outer text = new Outer();
           Outer.Inner get = text.new Inner();
           get.say();
           System.out.println(get.hashCode());
           System.out.println("========================");
           Outer.Inner get1 = text.gettext02();
           get.say();
           System.out.println(get1.hashCode());
           System.out.println("========================");
           Outer.Inner get2 = new Outer().new Inner();
           get2.say();
           System.out.println(get2.hashCode());
       }
   }
   
   class  Outer{
       private int num = 10;
       public class Inner {
           public void say() {
               System.out.println("hello");
           }
       }
       public Inner gettext02() {//通过调用方法，从外部其他类调用成员内部类
           return new Inner();
       }
   }
   ```

   

7. 如果外部类和内部类的成员重名时，内部类访问的话，默认遵循就近原则，如果想访问外部类的成员，则可以使用(外部类名.this.成员) 去访问

# 静态内部类

说明：静态内部类是定义在外部类的成员位置，并且有static修饰。

1. 可以直接访问外部类的所有静态成语，包括私有的，但不能直接访问非静态成员

2. 可以添加任意访问修饰符（public、protected、默认、private），因为它的地位就是一个成员

3. 作用域：同其他的成员，为整个类体

4. 静态内部类---访问---->外部类（比如：静态属性）【访问方式：直接访问所有静态成语】

5. 外部类---访问---->静态内部类 访问方式：创建对象再访问

6. 外部其它类---访问---->静态内部类

   ```java
   public class Demo {
       public static void main(String[] args) {
           Outer.Inner get = new Outer.Inner();
           get.say();
           System.out.println("============");
           Outer outer = new Outer();
           Outer.Inner get1 = outer.getInner();
           get1.say();
           System.out.println("============");
           Outer.Inner get2 = Outer.getInner();
           get2.say();
       }
   }
   
   class  Outer{
       private int num = 10;
       static public class Inner {
           public void say() {
               System.out.println("hello");
           }
       }
       static public Inner getInner() {
           return new Inner();
       }
   }
   ```




7. 如果外部类和静态内部类的成员重名时，静态内部类访问时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.成员）去访问