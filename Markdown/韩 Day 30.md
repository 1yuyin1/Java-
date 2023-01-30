## 标准输入输出流

- 介绍

|                      | 类型        | 默认设备 |
| :------------------- | ----------- | -------- |
| System.in  标准输入  | InputStream | 键盘     |
| System.out  标准输出 | PrintStream | 显示器   |

``` java
public class InputAndOutput {
    public static void main(String[] args) {
        //System 类 的 public final static InputStream in = null;
        // System.in 编译类型   InputStream
        // System.in 运行类型   BufferedInputStream
        // 表示的是标准输入 键盘
        System.out.println(System.in.getClass());

        //老韩解读
        //1. System.out public final static PrintStream out = null;
        //2. 编译类型 PrintStream
        //3. 运行类型 PrintStream
        //4. 表示标准输出 显示器
        System.out.println(System.out.getClass());
 
        System.out.println("hello, world~");
 
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入内容");
        String next = scanner.next();
        System.out.println("next=" + next);
    }
}
```

## 转换流

- 转换流-InputStreamReader和OutputStreamWriter
- 先看一个文件乱码问题，引出学习转换流必要性 

``` java
public class CodeQuestion {
    public static void main(String[] args) throws IOException {
        //读取e:\\a.txt 文件到程序
        //思路
        //1.  创建字符输入流 BufferedReader [处理流]
        //2. 使用 BufferedReader 对象读取a.txt
        //3. 默认情况下，读取文件是按照 utf-8 编码，若不是，可能出现乱码情况。
        String filePath = "e:\\a.txt";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
 
        String s = br.readLine();
        System.out.println("读取到的内容: " + s);
        br.close();
 
        //InputStreamReader
        //OutputStreamWriter
    }
}
```

- 介绍

  1. InputStreamReader：Reader的子类，可以将InputStream（字节流）包装成（转换）Reader（字符流）；

  2. OutputStreamWriter：Writer的子类，实现将OutputStream（字节流）包装成Writer（字符流）；

  3. 当处理纯文本数据时，如果使用字符流效率更高，并且可以有效解决中文问题，所以建议将字节流转换为字符流；

  4. 可以在使用时指定编码格式（比如utf-8、gbk、gb2312、ISO8859-1等）。

- InputStreamReader应用

  编程将	字节流FileInputStream包转成（转换成）字符流InputStreamReader，对文件进行读取（按照 utf-8/gbk 格式），进而再包装成BufferedReader

``` java
/**
 * 演示使用 InputStreamReader 转换流解决中文乱码问题
 * 将字节流 FileInputStream 转成字符流  InputStreamReader, 指定编码 gbk/utf-8
 */
public class InputStreamReader_ {
    public static void main(String[] args) throws IOException {
 
        String filePath = "e:\\a.txt";
        //解读
        //1. 把 FileInputStream 转成 InputStreamReader
        //2. 指定编码 gbk
        //InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gbk");
        //3. 把 InputStreamReader 传入 BufferedReader
        //BufferedReader br = new BufferedReader(isr);
 
        //将2 和 3 合在一起
        BufferedReader br = new BufferedReader(new InputStreamReader(
                                                    new FileInputStream(filePath), "gbk"));
 
        //4. 读取
        String s = br.readLine();
        System.out.println("读取内容=" + s);
        //5. 关闭外层流
        br.close();
    }
}
```

- OutputStreamWriter应用

  编程将字节流 Fi了OutputStream包装成（转换成）字符流OutputStreamWriter，对文件进行写入（按照gbk格式，可以指定其他，比如utf-8）

  ``` java
  /**
   * 演示 OutputStreamWriter 使用
   * 把FileOutputStream 字节流，转成字符流 OutputStreamWriter
   * 指定处理的编码 gbk/utf-8/utf8
   */
  public class OutputStreamWriter_ {
      public static void main(String[] args) throws IOException {
          String filePath = "e:\\hsp.txt";
          String charSet = "utf-8";
          OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), charSet);
          osw.write("hi, 哈哈哈");
          osw.close();
          System.out.println("按照 " + charSet + " 保存文件成功~");
      }
  }
  ```

## PrintStream	字节打印流

打印流只有输出流，没有输入流 

``` java
/**
 * 演示PrintStream （字节打印流/输出流）
 */
public class PrintStream_ {
    public static void main(String[] args) throws IOException {
 
        PrintStream out = System.out;
        //在默认情况下，PrintStream 输出数据的位置是 标准输出，即显示器
        /*
             public void print(String s) {
                if (s == null) {
                    s = "null";
                }
                write(s);
            }
         */
        out.print("john, hello");
        //因为print底层使用的是write , 所以我们可以直接调用write进行打印/输出
        out.write("韩顺平,你好".getBytes());
        out.close();
 
        //我们可以去修改打印流输出的位置/设备
        //1. 输出修改成到 "e:\\f1.txt"
        //2. "hello, 韩顺平教育~" 就会输出到 e:\f1.txt
        //3. public static void setOut(PrintStream out) {
        //        checkIO();
        //        setOut0(out); // native 方法，修改了out
        //   }
        System.setOut(new PrintStream("e:\\f1.txt"));
        System.out.println("hello, 韩顺平教育~");
    }
}
```

## PrintWriter	字符打印流

```java
public class PrintWriter_ {
    public static void main(String[] args) throws IOException {
 
        //PrintWriter printWriter = new PrintWriter(System.out);
        PrintWriter printWriter = new PrintWriter(new FileWriter("e:\\f2.txt"));
        printWriter.print("hi, 北京你好~~~~");
        printWriter.close();//flush + 关闭流, 才会将数据写入到文件..
 
    }
}
```

## 配置文件引出Properties

- 看一个需求

  如下一个配置文件mysql.properties

  ip=192.168.0.13

  user=root

  pwd=12345

请问编程读取 ip、user和pwd的值是多少

- 分析
  1. 传统的方法
  2. 使用Properties类可以方便实现

## Properties读文件

- 基本介绍

  1. 专门用于读写配置文件的集合类

     配置文件的格式：

     键=值

     键=值

  2. 注意：键值对不需要用空格，值不需要用引号括起来。默认类型是String

  3. Properties的常见方法

     - load：加载配置文件的键值对到Properties对象；
     - list：将数据显示到指定设备；
     -  getProperty(key)：根据键获取值；
     - setProperty(key,value)：设置键值对到Properties对象【没有就是新建，有就是修改】；
     - store：将Properties中的键值对存储到配置文件中，在IDEA中，保存信息到配置文件，如果含有中文，会存储为unicode码。

``` java
public class Properties02 {
    public static void main(String[] args) throws IOException {
        //使用Properties 类来读取mysql.properties 文件
 
        //1. 创建Properties 对象
        Properties properties = new Properties();
        //2. 加载指定配置文件
        properties.load(new FileReader("src\\mysql.properties"));
        //3. 把k-v显示控制台
        properties.list(System.out);
        //4. 根据key 获取对应的值
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        System.out.println("用户名=" + user);
        System.out.println("密码是=" + pwd);
    }
}
```

## Properties修改文件

``` java
public class Properties03 {
    public static void main(String[] args) throws IOException {
        //使用Properties 类来创建 配置文件, 修改配置文件内容
 
        Properties properties = new Properties();
        //创建
        //1.如果该文件没有key 就是创建
        //2.如果该文件有key ,就是修改
        /*
            Properties 父类是 Hashtable ， 底层就是Hashtable 核心方法
            public synchronized V put(K key, V value) {
                // Make sure the value is not null
                if (value == null) {
                    throw new NullPointerException();
                }
                // Makes sure the key is not already in the hashtable.
                Entry<?,?> tab[] = table;
                int hash = key.hashCode();
                int index = (hash & 0x7FFFFFFF) % tab.length;
                @SuppressWarnings("unchecked")
                Entry<K,V> entry = (Entry<K,V>)tab[index];
                for(; entry != null ; entry = entry.next) {
                    if ((entry.hash == hash) && entry.key.equals(key)) {
                        V old = entry.value;
                        entry.value = value;//如果key 存在，就替换
                        return old;
                    }
                }
                addEntry(hash, key, value, index);//如果是新k, 就addEntry
                return null;
            }
         */
        properties.setProperty("charset", "utf8");
        properties.setProperty("user", "汤姆");//注意保存时，是中文的 unicode码值
        properties.setProperty("pwd", "888888");
 
        //将k-v 存储文件中即可
        //注意null是指properties配置文件没有要说明的注释，如果有的话会在配置文件的
        //最上方显示。
        properties.store(new FileOutputStream("src\\mysql2.properties"), null);
        System.out.println("保存配置文件成功~");
    }
}
```

## 作业

- 1. 判读e盘下是否有文件夹mytemp，如果没有就创建mytemp
  2. 在e:\\mytemp目录下创建文件hello.txt
  3. 如果hello.txt  已经存在，提示该文件已经存在,就不要再重复创建了
- 使用BufferedReader读取一个文本文件，为每行加上行号，再连同内容一并输出到屏幕上

- 1. 编写一个 dog.properties

     name=tom

     age=5

     color=red

  2. 编写Dog类，创建一个dog对象，读取dog.properties 用相应的内容完成属性初始化，并输出。

  3. 将dog对象序列化和反序列化