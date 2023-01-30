# 枚举类

## 引出

```java
public class Demo {
    public static void main(String[] args) {
        Season spring = new Season("春天", "温暖");
        Season summer = new Season("夏天", "炎热");
        Season autumn = new Season("秋天", "凉爽");
        Season winter = new Season("冬天", "寒冷");
        System.out.println("===============================");
        //对于季节，它的对象有固定的四个，对于这个设计思路，不能体现季节是固定的四个
        //因此，这种设计不好
        Season season = new Season("红天", "大大大");
    }
}

class Season {
    private String name;
    private String desc;

    public Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setSec(String desc) {
        this.desc = desc;
    }
}
```

- 分析问题
  - 创建Season对象有如下特点
  - 季节的值是有限的几个值
  - 只读，不需要修改

## 自定义枚举类

- 枚举的两种实现方式
  - 自定义类实现枚举
  - 使用enum关键字实现枚举

- 应用案例
  - 不需要提供setXxx方法，因为枚举对象值通常为只读
  - 对枚举对象/属性使用final+static 共同修饰，实现底层优化
  - 枚举对象名通常使用全部大写，常量的命名规范
  - 枚举对象根据需要，也可以有多个属性、

``` java 
public class Demo {
    public static void main(String[] args) {
        System.out.println(Season.SPRING.toString());
        System.out.println(Season.AUTUMN.toString());
    }
}

class Season {
    private String name;
    private String desc;
    public final static Season SPRING = new Season("春天", "温暖");
    public final static Season SUMMER = new Season("夏天", "炎热");
    public final static Season AUTUMN = new Season("秋天", "凉爽");
    public final static Season WINTER = new Season("冬天", "寒冷");
    //1. 将构造器私有化，目的是防止直接new
    //2. 去掉setXxx方法，防止属性被修改
    //3. 直接在Season内部，创建对象
    private Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
```

- 小结：进行自定义类实现枚举，有如下特点：
  - 构造器私有化
  - 本类内部创建一组对象
  - 对外暴露对象（通过为对象添加public final static修饰符）
  - 可以提供 get方法，但是不要提供set

## enum枚举类

使用enum来实现前面的枚举案例



```java
public class Demo {
    public static void main(String[] args) {
        System.out.println(Season.SPRING.toString());
        System.out.println(Season.AUTUMN.toString());
    }
}

enum Season {
    SPRING("春天", "温暖"),SUMMER("夏天", "炎热"),AUTUMN("秋天", "凉爽"),WINTER("冬天", "寒冷");
    private String name;
    private String desc;
    //1. 使用enum 代替 class
    //2. 使用SPRING("春天","温暖");   来进行创建对象
    //3. 如果有多个常量（对象），使用逗号间隔即可
    //4. 如果使用enum 来实现枚举，要求定义常量对象，写在前面
    private Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
```

- 注意事项
  - 当我们使用enum 关键字开发一个枚举类时，默认会继承Enum类，而且是一个 final类，通过Javap工具可看出
  - 传统的 public final static Season AUTUMN = new Season("秋天", "凉爽");简化成 AUTUMN("秋天","凉爽"); 
  - 如果使用无参构造器创建枚举对象，则实参列表和小括号都可以省略
  - 当有多个枚举对象时，使用,间隔，最后有一个分号结尾
  - 枚举对象必须放在枚举类的行首



- 课堂练习

```java
public class Demo {
    public static void main(String[] args) {
        Gender boy = Gender.BOY;
        Gender boy2 = Gender.BOY;
        System.out.println(boy);
//        Enum 中的 toString方法
//        public String toString() {
//            return name;
//        }
        System.out.println(boy == boy2);
    }
}

enum Gender {
    BOY,GIRL;
}
```

## Enum成员方法

- toString：Enum类已经重写过了，返回的是当前对象名，子类可以重写该方法，用于返回对象的属性信息
- name： 返回当前的对象名（常量名），子类中不能重写
- ordinal： 返回当前对象的位置号，默认从0开始

====================================================================================================

- compareTo： 比较两个枚举常量，比较的是编号
- valueOf： 将字符串转换成枚举对象，要求字符串必须为已有的常量名，否则报异常！
- values： 返回当前枚举类中所有的常量

``` java
public class Demo {
    public static void main(String[] args) {
        System.out.println(Season.AUTUMN.name());
        System.out.println("===========================");
        System.out.println(Season.AUTUMN.ordinal());
        System.out.println("===========================");
        System.out.println(Season.AUTUMN.compareTo(Season.SPRING));
        System.out.println("===========================");
        Season value = Season.valueOf("SPRING");
        System.out.println(value);
        System.out.println("===========================");
        Season[] values = Season.values();
        for (Season season:values) {
            System.out.println(season);
        }
    }
}

enum Season {
    SPRING("春天", "温暖"),SUMMER("夏天", "炎热"),AUTUMN("秋天", "凉爽"),WINTER("冬天", "寒冷");
    private String name;
    private String desc;

    private Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
```

## 课堂练习

```java
public class Demo {
    public static void main(String[] args) {
        Week[] weeks = Week.values();
        System.out.println("以下是Week的信息");
        for (Week week:weeks) {
            System.out.println(week);
        }
    }
}

enum Week {
    Monday("星期一"),Tuesday("星期二"),Wendesday("星期三"),Thursday("星期四"),
    Friday("星期五"),Saturday("星期六"),Sunday("星期日");
    private String name;

    private Week(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
}
```

## 注意事项

```java
public class Demo {
    public static void main(String[] args) {
        Music.CLASSICMUSIC.working();
    }
}
class A {

}
interface B {
     void working();
}
enum Music implements B{
//    ;
    CLASSICMUSIC;
    @Override
    public void working() {
        System.out.println("唱歌歌歌儿");
    }
}
```

- 使用enum关键字后，就不能再继承其它类了，因为enum会隐式继承Enum，而Java是单继承机制
- 枚举类和普通类一样，可以实现接口
- 枚举类如果不创建对象，必须在首行有一个分号

# 注解

## Override注解

- 注解（Annotation）也称为元数据（Metadata），用于修饰解释 包、类、方法、属性、构造器、局部变量等数据信息。
- 和注释一样，注解不影响程序逻辑，但注解可以被编译或运行，相当于嵌入在代码中的补充信息。
- 在JavaSE中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在JavaEE中注解占据了更重要的角色，例如用来配置应用程序的任何切面，代替JavaEE旧版中所遗留的繁冗代码和XML配置等。



- Override 注解的案例

  - Override：限定某个方法，是重写父类方法，该注释只能用于方法

  - 

  - ```java
    class Father {
        public void say() {
            System.out.println("hello");
        }
    }
    
    class Son extends Father {
        //Override注解在say方法，表示子类的say方法重写了父类的
        //这里如果没有写Override 还是重写了父类方法
        //如果写了Override，编译器就会去检查该方法是否重写了父类的方法，
        // 如果重写了，则编译通过，否则，编译错误
        //看看Override的代码  @interface表示以一个注解类
        /*
            @Target(ElementType.METHOD)
            @Retention(RetentionPolicy.SOURCE)
            public @interface Override {
            }
         */
        @Override
        public void say() {
            System.out.println("hi");
        }
    }
    ```

- Override使用说明
  - @Override表示指定重写父类的方法（从编译层面验证），如果父类没有该方法，则会报错
  - 如果不写@Override注释，而父类仍有该方法，仍然构成重写
  - @Override只能修饰方法
  - 查看@Override注解源码为@Target(ElementType.METHOD)，说明只能修是方法
  - @Target是修饰注释的注释，称为元注释

## Deprecated注解

- 用于表示某个程序元素（类、方法等）已经过时
- 可以修饰方法，类，字段，包，参数 等等
- @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
- @Deprecated的作用可以做到新旧版本的兼容和过渡

```java
public class Demo {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.n);
        a.say();
    }
}
/*
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Deprecated {
}
*/

@Deprecated
class A {
    public int n = 1;
    @Deprecated
    public void say() {
        System.out.println("hello");
    }
}
```

## SuppressWarnings注解

- 注解案例
  - unchecked是忽略没有检查的警告
  - rawtypes是忽略没有指定泛型的警告
  - unused是忽略没有使用某个变量的警告错误
  - @SuppressWarnings可以修饰的程序元素为，查看@Target
  - 生成@SuppressWarnings时，不用背，直接点击左侧黄色提示，就可以选择

## 元注解

- 元注解的基本介绍
  - JDK的元Annotation 用于修饰其他 Annotation
  - 元注解：本身作用不大，将这个是希望看源码时，可以知道他是干什么的
- 元注解的种类（使用不多，了解即可，不用深入研究）
  - Retention	指定注解的作用范围，三种SOURCE,CLASS,RUNTIME
  - Target     指定注解可以在哪些地方使用
  - Documented    指定该注解是否会在Javadoc中体现
  - Inherited    子类会继承父类注解



- @Retention注解
  - 只能用于修饰一个Annotation定义，用于指定该Annotation可以保留多长时间，@Retention包含一个RetentionPolicy类型的成员变量，使用@Retention时必须为该value成员变量指定值
  - @Retention的三种值
    - RetentionPolicy.SOURCE:编译器使用后，直接丢弃这种策略的注解
    - RetentionPolicy.CLASS：编译器将把注解记录在class文件中，当运行Java程序时，JVM不会保留注解。
    - RetentionPolicy.RUNTIME：编译器将把注解记录在class文件中，当运行Java程序时，JVM会保留注解，程序可以通过反射获取该注解
- @Target
  - 用于修饰Annotation定义，用于指定被修饰的Annotation能用于修饰哪些程序元素。@Target也包含一个名为value的成员变量
- @Documented
  - @Documented：用于指定该元Annotation修饰的Annotation 类将被javadoc 工具提取成文档，即在生成文档时，可以看到该注解
  - 定义为Documented的注解必须设置Rentention值为RUNTIME
- @Inherited注解
  - 被它修饰的Annotation将具有继承性。如果某个类使用了被@Inherited修饰的Annotation，则其子类将自动具有该注解

