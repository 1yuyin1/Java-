## 处理流设计模式

- 节点流和处理流的区别和联系

  1. 节点流是底层流/低级流，直接跟数据源相接；

  2. 处理流包装节点流，既可以消除不同节点流的实现差异，也可以提供更方便的方法来实现输入输出；

  3. 处理流（包装流）对节点流进行包装，使用了 修饰器设计模式，不会直接于数据源相连。

- 处理流的功能主要体现在以下两个方面

  1. 性能的提高：主要以增加缓冲的方式来提高输入输出的效率
  2. 操作的便捷：处理流可能提供了一系列便捷的方法来一次输入输出大批量的数据，使用更加灵活方便



模拟修饰器设计模式

``` java
//Reader_类
public abstract class Reader_ { //抽象类
    public void readFile() {
    }
    public void readString() {
    }
 
    //在Reader_ 抽象类，使用read方法统一管理.
    //后面在调用时，利于对象动态绑定机制， 绑定到对应的实现子类即可.
    //public abstract void read();
}
```

``` java
//FileReader_类
public class FileReader_ extends Reader_ {
 
        public void readFile() {
        System.out.println("对文件进行读取...");
    }
}
```

``` java
// StringReader_类
public class StringReader_ extends Reader_ {
    public void readString() {
        System.out.println("读取字符串..");
    }
 
}
```

``` java
// BufferedReader_类
public class BufferedReader_ extends Reader_{
 
    private Reader_ reader_; //属性是 Reader_类型
 
    //接收Reader_ 子类对象
    public BufferedReader_(Reader_ reader_) {
        this.reader_ = reader_;
    }
 
    public void readFile() { //封装一层
        reader_.readFile();
    }
 
    //让方法更加灵活， 多次读取文件, 或者加缓冲byte[] ....
    public void readFiles(int num) {
        for(int i = 0; i < num; i++) {
            reader_.readFile();
        }
    }
 
    //扩展 readString, 批量处理字符串数据
    public void readStrings(int num) {
        for(int i = 0; i <num; i++) {
            reader_.readString();
        }
    }
}
```

``` java
//Test_类
public class Test_ {
    public static void main(String[] args) {
 
 
        BufferedReader_ bufferedReader_ = new BufferedReader_(new FileReader_());
        bufferedReader_.readFiles(10);
        //bufferedReader_.readFile();
        //Serializable
        //Externalizable
        //ObjectInputStream
        //ObjectOutputStream
        //这次希望通过 BufferedReader_ 多次读取字符串
        BufferedReader_ bufferedReader_2 = new BufferedReader_(new StringReader_());
        bufferedReader_2.readStrings(5);
    }
}
```

## BufferedReader

- BufferedReader和BufferedWriter属于字符流，是按照字符来读取数据的
- 关闭时，只需要关闭外层流即可 



案例：使用BufferedReader读取文本文件，并显示在控制台

``` java
public class BufferedReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "e:\\a.java";
        //创建bufferedReader
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        //读取
        String line; //按行读取, 效率高
        //说明
        //1. bufferedReader.readLine() 是按行读取文件
        //2. 当返回null 时，表示文件读取完毕
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
 
        //关闭流, 这里注意，只需要关闭 BufferedReader ，因为底层会自动的去关闭 节点流
        //FileReader。
        /*
            public void close() throws IOException {
                synchronized (lock) {
                    if (in == null)
                        return;
                    try {
                        in.close();//in 就是我们传入的 new FileReader(filePath), 关闭了.
                    } finally {
                        in = null;
                        cb = null;
                    }
                }
            }
         */
        bufferedReader.close();
    }
}
```

## BufferedWriter

使用BufferedWriter将“hello，world”写入文件

``` java
public class BufferedWriter_ {
    public static void main(String[] args) throws IOException {
        String filePath = "e:\\ok.txt";
        //创建BufferedWriter
        //说明:
        //1. new FileWriter(filePath, true) 表示以追加的方式写入
        //2. new FileWriter(filePath) , 表示以覆盖的方式写入
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("hello, world!");
        bufferedWriter.newLine();//插入一个和系统相关的换行
        bufferedWriter.write("hello2, world!");
        bufferedWriter.newLine();
        bufferedWriter.write("hello3, world!");
        bufferedWriter.newLine();
 
        //说明：关闭外层流即可 ， 传入的 new FileWriter(filePath) ,会在底层关闭
        bufferedWriter.close();
 
    }
}
```

 ## Buffered拷贝

``` java
public class BufferedCopy_ {
    public static void main(String[] args) {
 
        //说明
        //1. BufferedReader 和 BufferedWriter 是安装字符操作
        //2. 不要去操作 二进制文件[声音，视频，doc, pdf ], 可能造成文件损坏
        //BufferedInputStream
        //BufferedOutputStream
        String srcFilePath = "e:\\a.java";
        String destFilePath = "e:\\a2.java";
//        String srcFilePath = "e:\\0245_韩顺平零基础学Java_引出this.avi";
//        String destFilePath = "e:\\a2韩顺平.avi";
        BufferedReader br = null;
        BufferedWriter bw = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(srcFilePath));
            bw = new BufferedWriter(new FileWriter(destFilePath));
 
            //说明: readLine 读取一行内容，但是没有换行
            while ((line = br.readLine()) != null) {
                //每读取一行，就写入
                bw.write(line);
                //插入一个换行
                bw.newLine();
            }
            System.out.println("拷贝完毕...");
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(br != null) {
                    br.close();
                }
                if(bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Buffered字节处理流

- 介绍BufferedInputStream

![image-20230115183100142](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230115183100142.png)

BufferedInputStream是字节流，在创建BufferedInputStream时，会创建内部缓冲区数组。

- 介绍 BufferedOutputStream

![image-20230115183127289](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230115183127289.png)

 BufferedOutputStream是字节流，实现缓冲的输出流，可以将多个字节写入底层输出流中，而不必对每次字节写入调用底层系统。

## 字节处理流拷贝文件

``` java
/ * 思考：字节流可以操作二进制文件，可以操作文本文件吗？当然可以
 */
public class BufferedCopy02 {
    public static void main(String[] args) {
//        String srcFilePath = "e:\\Koala.jpg";
//        String destFilePath = "e:\\hsp.jpg";
//        String srcFilePath = "e:\\0245_韩顺平零基础学Java_引出this.avi";
//        String destFilePath = "e:\\hsp.avi";
        String srcFilePath = "e:\\a.java";
        String destFilePath = "e:\\a3.java";
 
        //创建BufferedOutputStream对象BufferedInputStream对象
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
 
        try {
            //因为 FileInputStream  是 InputStream 子类
            bis = new BufferedInputStream(new FileInputStream(srcFilePath));
            bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
 
            //循环的读取文件，并写入到 destFilePath
            byte[] buff = new byte[1024];
            int readLen = 0;
            //当返回 -1 时，就表示文件读取完毕
            while ((readLen = bis.read(buff)) != -1) {
                bos.write(buff, 0, readLen);
            }
            System.out.println("文件拷贝完毕~~~");
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流 , 关闭外层的处理流即可，底层会去关闭节点流
            try {
                if(bis != null) {
                    bis.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 对象处理流

- 看一个需求
  1. 将 int num = 100 这个int数据保存到文件中，注意不是100数字而是int 100，并且，能够从文件中直接恢复 int 100
  2. 将Dog dog = new Dog("小黄",3) 这个dog对象保存到文件中，并且能够从文件恢复
  3. 上面的要求，就是能够将基本数据类型或者对象进行 序列化和 反序列化操作

- 序列化和反序列化
  1. 序列化就是在保存数据时，保存数据的值和数据类型
  
  2. 反序列化就是在恢复数据时，恢复数据的值和数据类型
  
  3. 需要让某个对象支持序列化机制，则必须让其类是可序列化的，为了让某个类是可序列化的，
  
     该类必须实现如下两个接口之一：
  
     1. Serializable  //这是一个标记接口，没有方法
     2. Externalizable  //该接口有方法需要实现，因此我们一般实现上面的接口



- 基本介绍
  1. 功能：提供了对基本类型或对象类型的序列化和反序列化的方法
  2. ObjectOutputStream提供 序列化功能
  3. ObjectInputStream提供  反序列化功能

## ObjectOutputStream

使用ObjectOutputStream	序列化 基本数据类型和一个 Dog对象(name,age)，并保存到data.txt中

``` java
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Demo {
    public static void main(String[] args) throws Exception{
        //序列化后，保存的文件格式，不是存文本，而是按照它的格式来保存
        String filepath = "e://hello.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));

        //将序列化数据保存到 e://hello.dat
        oos.writeInt(100);//int->Integer（实现了 Serializable）
        oos.writeBoolean(true);
        oos.writeChar('a');
        oos.writeDouble(2.1);
        oos.writeUTF("韩顺平教育");
        oos.writeObject(new Dog("旺财", 3));
        
        oos.close();
        System.out.println("数据保存完毕（序列化形式）");
    }
}
class Dog implements Serializable {
    String name;
    int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

## ObjectInputStream 

使用ObjectInputStream 读取hello.dat 并反序列化数据

``` java
import java.io.*;

public class Demo {
    public static void main(String[] args) throws Exception{
        //指定反序列化的文件
        String filepath = "e://hello.dat";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
        //读取
        //老师解读
        //1. 读取（反序列化）的顺序需要和你保存数据（序列化）的顺序一致
        //2. 否则会出现异常

        System.out.println(ois.readInt());
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        System.out.println(ois.readUTF());
        Object dog = ois.readObject();
        System.out.println("运行类型"  + dog.getClass());
        System.out.println("dog信息=" + dog);//底层 Object-> dog

        //这里是特别重要的细节：
        //1. 如果我们希望调用Dog的方法，需要向下转型
        //2. 并且需要将Dog类的定义，放在可以引用的位置
        Dog dog2 = (Dog)dog;
        
        ois.close();
    }
}
class Dog implements Serializable {
    String name;
    int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
        @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

## 对象处理流使用细节

- 注意事项和细节说明

  1. 读写顺序要一致

  2. 要求序列化或反序列化类，实现Serializable接口

  3.  序列化的类中建议添加SerialVersionUID，为了提高版本的兼容性

     > private static final long SerialVersionUID = 1L;	这样如果添加一个新的属性，程序并不会认为是一个新的类

  4. 序列化对象时，默认将里面所有属性都序列化，但除了static或transient修饰的成员

  5. 序列化对象时，要求里面属性的类型也需要实现序列化接口

  6. 序列化具备可继承性，也就是如果某类已经实现了序列化，则它的所有子类也已经默认实现了序列化

     

