# 常用类

## System类

1. exit 退出当前程序
2. arrycopy 复制数组元素，比较适合底层调用，一般使用Arrays.copyOf完成复制数组
3. currentTimeMillens：返回当前时间距离1900-1-1的毫秒数
4. gc：运行垃圾回收机制 System.gc();

## 大数处理方案

- BigInteger和BigDecimal常见方法

  1. add

  2. substract

  3. multiply

  4. divide

     ``` java
             BigDecimal bigDecimal = new BigDecimal("33.2222");
             BigDecimal bigDecimal1 = new BigDecimal("2.3");
             System.out.println(bigDecimal.divide(bigDecimal1,BigDecimal.ROUND_CEILING));//由于结果为无限不循环小数，需要加BigDecimal.ROUND_CEILING以保留为分子的位数
     ```

     

BigInteger适合保存比较大的整形，BigDecimal适合保存精度更高的浮点型

## 日期类介绍

- Date 类

``` java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class temp {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();//获取当前系统时间
        Date date2 = new Date(9852644);//通过指定毫秒得到时间
        System.out.println(date2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日 hh:mm:ss E");
        System.out.println(sdf.format(date));

        String s = "1996年01月01日 10:20:30 星期一";
        Date parse = sdf.parse(s);
        System.out.println(parse);
    }
}
```




- Calendar类

  Calendar是一个抽象类，并且构造器是private

  可以通过 getInstance() 来获取实例

``` java
        Calendar c = Calendar.getInstance();
        System.out.println(c);

        System.out.println("年   "+c.get(Calendar.YEAR));
        System.out.println("月   "+(c.get(Calendar.MONTH)+1));//月需要手动加1
        System.out.println("日   "+c.get(Calendar.DAY_OF_MONTH));
```



- 第三代日期类

  JDK 1.0中包含了一个java.util.Date类，但是它的大多数方法已经在JDK 1.1引入Calendar后被启用了，而Calendar也存在问题

  1. 可变性：像日期和时间这样的类应该是不可变的
  2. 偏移性：Date中的年份是从1900年开始的，而月份都从0开始
  3. 格式化：格式化支队Date有用，Calendar则不行
  4. 此外，它们也不是线程安全的，不能处理闰秒等（每隔两天，多出1s）

- 第三代日期类常见方法

  1. LocalDate（日期/年月日）、LocalTime（时间/时分秒）、LocalDateTime（日期时间/年月日时分秒）JDK8加入

     LocalDate 只包含日期，可以获取日期字段

     LocalTime 只包含时间，可以获取时间字段

     LocalDateTime包含日期+时间，可以获取日期和时间字段

      ``` java
           LocalDateTime ldt = LocalDateTime.now();
           System.out.println(ldt);
           System.out.println("年   "+ldt.getYear());
           System.out.println("月   "+ldt.getMonthValue());
           System.out.println("日   "+ldt.getDayOfMonth());
           System.out.println("星期  "+ldt.getDayOfWeek());
      ```

  2. DateTimeFormatter格式日期类
  
     类似于SimpleDateFormat
  
     ``` java
             LocalDateTime ldt = LocalDateTime.now();
             System.out.println(ldt);
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
             System.out.println(dtf.format(ldt));
     ```
  
  3. Instant 时间戳
  
     类似于Date
  
     提供了一系列和Date类转换的方式
  
     Instant——>Date
  
     Date date = Date.from(Instant);
  
     Date——>Instant
  
     Instant instant = date.toInstant();
  
  4. 第三代日期类更多方法
  
     - LocalDateTime类
     - MonthDay类：检查重复事件
     - 是否是闰年
     - 增加日期的某个部分
     - 使用plus方法测试增加时间的某个部分
     - 使用minus方法测试查看一年前和一年后的日期 



## String翻转

``` java
public class temp {
    public static void main(String[] args) {
        String str = "abcdef";
        System.out.println("====交换前===");
        System.out.println(str);
        System.out.println("===交换后===");
        try {
            System.out.println(reverse(str, 1, 14));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    static String reverse(String str, int start, int end) {
        if (!(str != null && start >= 0 && end < str.length() && start < end)) {
            throw new RuntimeException("参数错误");
        }
        char[] chars = str.toCharArray();
        char temp;
        for (int i = start, j = end; i < j; i++, j--) {
            temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}
```

## String 内存测试

``` java
        class Animal {
            String name;

            public Animal(String name) {
                this.name = name;
            }
        }
        String s1 = "hspedu";
        Animal a = new Animal(s1);
        Animal b = new Animal(s1);
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a.name == b.name);

        String s4 = new String("hspedu");
        String s5 = "hspedu";
        System.out.println(s1 == s4);
        System.out.println(s4 == s5);

        String t1 = "hello" + s1;
        String t2 = "hellohspedu";
        System.out.println(t1.intern() == t2);
```

