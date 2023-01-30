## IO流

## 文件基础知识

- 什么是文件

  文件，对我们并不陌生，文件是保存数据的地方，比如说大家经常使用的word文档，txt文件...它即可以保存一张图片，也可以保存视频，声音...

- 文件流

  文件在程序中是以流的形式来操作的

  

  流：数据在数据源（文件）和程序（内存）之间经历的路径

  输入流：数据从数据源（文件）到程序（内存）的路径

  输出流：数据从程序（内存）到数据源（文件）的路径 

## 创建文件

- 创建文件对象相关构造器和方法

  - 相关方法

    new File(String pathname)//根据路径创建一个File对象

    new File(File parent,String child)//根据父目录文件+子路径构建

    new File(String parent,String child)//根据父目录+子路径构建



​				creatNewFile 构建新文件

- 案例演示：请在e盘下，创建文件 news1.txt、news2.txt、news3.txt，用不同方式创建

``` java
public class FileCreate {
    public static void main(String[] args) {
 
    }
 
    //方式1 new File(String pathname)
    @Test
    public void create01() {
        String filePath = "e:\\news1.txt";
        File file = new File(filePath);
 
        try {
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    //方式2 new File(File parent,String child) //根据父目录文件+子路径构建
    //e:\\news2.txt
    @Test
    public  void create02() {
        File parentFile = new File("e:\\");
        String fileName = "news2.txt";
        //这里的file对象，在java程序中，只是一个对象
        //只有执行了createNewFile 方法，才会真正的，在磁盘创建该文件
        File file = new File(parentFile, fileName);
 
        try {
            file.createNewFile();
            System.out.println("创建成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    //方式3 new File(String parent,String child) //根据父目录+子路径构建
    @Test
    public void create03() {
        //String parentPath = "e:\\";
        String parentPath = "e:\\";
        String fileName = "news4.txt";
        File file = new File(parentPath, fileName);
 
        try {
            file.createNewFile();
            System.out.println("创建成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    //下面四个都是抽象类
    //
    //InputStream
    //OutputStream
    //Reader //字符输入流
    //Writer  //字符输出流
}
```

## 获取文件信息

getName、getAbsolutePath、getParent、length、exists、isFile、isDirectory

案例演示：

``` java
public class FileInformation {
    public static void main(String[] args) {
 
    }
    //获取文件的信息
    @Test
    public void info() {
        //先创建文件对象
        File file = new File("e:\\news1.txt");
 
        //调用相应的方法，得到对应信息
        System.out.println("文件名字=" + file.getName());
        //getName、getAbsolutePath、getParent、length、exists、isFile、isDirectory
        System.out.println("文件绝对路径=" + file.getAbsolutePath());
        System.out.println("文件父级目录=" + file.getParent());
        System.out.println("文件大小(字节)=" + file.length());
        //按字节计算，utf-8编码下，一个英文字母一个字节，一个汉字三个字节
        System.out.println("文件是否存在=" + file.exists());//T
        System.out.println("是不是一个文件=" + file.isFile());//T
        System.out.println("是不是一个目录=" + file.isDirectory());//F
    }
}
```

## 目录的操作和文件删除

mkdir创建一级目录，mkdirs创建多级目录，delete删除空目录或文件

``` java
public class Directory_ {
    public static void main(String[] args) {
        
    }
 
    //判断 d:\\news1.txt 是否存在，如果存在就删除
    @Test
    public void m1() {
        String filePath = "e:\\news1.txt";
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(filePath + "删除成功");
            } else {
                System.out.println(filePath + "删除失败");
            }
        } else {
            System.out.println("该文件不存在...");
        }
    }
 
    //判断 D:\\demo02 是否存在，存在就删除，否则提示不存在
    //这里我们需要体会到，在java编程中，目录也被当做文件
    @Test
    public void m2() {
        String filePath = "D:\\demo02";
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(filePath + "删除成功");
            } else {
                System.out.println(filePath + "删除失败");
            }
        } else {
            System.out.println("该目录不存在...");
        }
    }
 
    //判断 D:\\demo\\a\\b\\c 目录是否存在，如果存在就提示已经存在，否则就创建
    @Test
    public void m3() {
        String directoryPath = "D:\\demo\\a\\b\\c";
        File file = new File(directoryPath);
        if (file.exists()) {
            System.out.println(directoryPath + "存在..");
        } else {
            if (file.mkdirs()) { //创建一级目录使用mkdir() ，创建多级目录使用mkdirs()
                System.out.println(directoryPath + "创建成功..");
            } else {
                System.out.println(directoryPath + "创建失败...");
            }
        }
    }
    //判断 D:\\demo 目录是否存在，如果存在就提示已经存在，否则就创建
    @Test
    public void m4() {
        String directoryPath = "D:\\demo";
        File file = new File(directoryPath);
        if (file.exists()) {
            System.out.println(directoryPath + "存在..");
        } else {
            if (file.mkdir()) { //创建一级目录使用mkdir() ，创建多级目录使用mkdirs()
                System.out.println(directoryPath + "创建成功..");
            } else {
                System.out.println(directoryPath + "创建失败...");
            }
        }
    }
}
```

## IO流原理和分类

- Java IO流原理
  1. I/O是Input/Output的缩写，I/O技术是非常实用的技术，用于处理数据传输，如读/写文件，网络通讯等
  2. Java程序中，对于数据的输入/输出操作以流（stream）的方式进行
  3. java.io包下提供了各种流类和接口，用以获取不同种类的数据，并通过方法输入或输出数据
  4. 输入input：读取外部数据（磁盘、光盘等存储设备的数据）到程序（内存）中
  5. 输出output：将程序（内存）数据输出到磁盘、光盘的存储设备中

- 流的分类

  - 按照操作数据单位不同分为：字节流（8bit）【便于处理二进制文件】，字符流（按字符）【便于处理文本文件】；

  - 按数据流的流入不同分为：输入流，输出流；
  - 按流的角色的不同分为：节点流，处理流/包装流。

- <img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230114155947976.png" alt="image-20230114155947976" style="zoom:150%;" />

1. Java的IO流共涉及40多个类，实际上非常规则，都是从如上4个抽象基类派生的
2. 由这四个类派生出来的子类名称都是以其父类名作为子类名后缀

## FileInputStream

- InputStream：字节输入流

  InputStream抽象类是所有类字节输入流的超类

  - InputStream 常用的子类
    1. FileInputStream：文件输入流
    2. BufferedInputStream：缓冲字节输入流
    3. ObjectInputStream：对象字节输入流



要求：请使用FileInputStream读取hello.txt文件，并将文件显示到控制台

``` java
public class FileInputStream_ {
    public static void main(String[] args) {
    }
    /**
     * 演示读取文件...
     * 单个字节的读取，效率比较低
     * -> 使用 read(byte[] b)
     */
    @Test
    public void readFile01() {
        String filePath = "e:\\hello.txt";
        int readData = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取一个字节的数据。 如果没有输入可用，此方法将阻止。
            //如果返回-1 , 表示读取完毕
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char)readData);//转成char显示
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
    }
 
    /**
     * 使用 read(byte[] b) 读取文件，提高效率
     */
    @Test
    public void readFile02() {
        String filePath = "e:\\hello.txt";
        //字节数组
        byte[] buf = new byte[8]; //一次读取8个字节.
        int readLen = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取最多b.length字节的数据到字节数组。 此方法将阻塞，直到某些输入可用。
            //如果返回-1 , 表示读取完毕
            //如果读取正常, 返回实际读取的字节数
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));//显示
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
    }
}
```

## FileOutputStream

要求：请使用FileOutputStream在a.txt文件中写入”hello，world“如果文件不存在，会创建文件。（前提是目录已经存在）

``` java
public class FileOutputStream01 {
    public static void main(String[] args) {
    }
    
    /**
     * 演示使用FileOutputStream 将数据写到文件中,
     * 如果该文件不存在，则创建该文件
     */
    @Test
    public void writeFile() {
        //创建 FileOutputStream对象
        String filePath = "e:\\a.txt";
        FileOutputStream fileOutputStream = null;
        try {
            //得到 FileOutputStream对象 对象
            //老师说明
            //1. new FileOutputStream(filePath) 创建方式，当写入内容是，会覆盖原来的内容
            //2. new FileOutputStream(filePath, true) 创建方式，当写入内容是，是追加到文件后面
            fileOutputStream = new FileOutputStream(filePath, true);
            //写入一个字节
            //fileOutputStream.write('H');//
            //写入字符串
            String str = "hsp,world!";
            //str.getBytes() 可以把 字符串-> 字节数组
            //fileOutputStream.write(str.getBytes());
            /*
            write(byte[] b, int off, int len) 将 len字节从位于偏移量 off的指定字节数组写入此文件输出流
             */
            fileOutputStream.write(str.getBytes(), 0, 3);
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 文件拷贝

``` java
public class FileCopy {
    public static void main(String[] args) {
        //完成 文件拷贝，将 e:\\Koala.jpg 拷贝 c:\\
        //思路分析
        //1. 创建文件的输入流 , 将文件读入到程序
        //2. 创建文件的输出流， 将读取到的文件数据，写入到指定的文件.
        String srcFilePath = "e:\\Koala.jpg";
        String destFilePath = "e:\\Koala3.jpg";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
 
        try {
 
            fileInputStream = new FileInputStream(srcFilePath);
            fileOutputStream = new FileOutputStream(destFilePath);
            //定义一个字节数组,提高读取效果
            byte[] buf = new byte[1024];
            int readLen = 0;
            while ((readLen = fileInputStream.read(buf)) != -1) {
                //读取到后，就写入到文件 通过 fileOutputStream
                //即，是一边读，一边写
                fileOutputStream.write(buf, 0, readLen);//一定要使用这个方法，
                //fileOutputStream.write(buf);如果有的不满一个数组的话，
                //就会出现将空的null也传过去，可能会出现打不开图片的情况。
 
            }
            System.out.println("拷贝ok~");
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入流和输出流，释放资源
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 文件字符流说明

- FileReader和FileWritier介绍

  FileReader和FileWritier 是字符流，即按照字符来操作IO

- FileReade相关方法

  1. new FileReader(File/String)
  2. read：每次读取单个字符，返回该字符，如果到文件末尾返回-1
  3. read(char[])：批量读取多个字符到数组，返回读取到的字符数，如果到文件末尾返回-1

  相关API：

  1. new String(char[])：将char[]转换成String
  2. new String(char[],off,len)：将char[]的指定部分转换成String

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230114210950666.png" alt="image-20230114210950666" style="zoom:80%;" />

- FileWriter常用方法

  1. new FileWriter(File/String)：覆盖模式，相当于流的指针在首端
  2. new FileWriter(File/String,true)：追加模式，相当于流的指针在尾端
  3. write(int)：写入单个字符
  4. write(char[])：写入指定数组
  5. write(char[],off,len)：写入指定数组的指定部分
  6. write(String)：写入整个字符串
  7. write(String,off,len)：写入字符串的指定部分

  相关API：String类：toCharArray：将String转换成char[]

- 注意：

  FileWriter使用后，必须要关闭或刷新，否则写入不到指定的文件

## FileReader

使用FileReader读取story.txt文件的内容，并显示

```java
    public class FileReader_ {
    public static void main(String[] args) {
 
    }
    /**
     * 单个字符读取文件
     */
    @Test
    public void readFile01() {
        String filePath = "e:\\story.txt";
        FileReader fileReader = null;
        int data = 0;
        //1. 创建FileReader对象
        try {
            fileReader = new FileReader(filePath);
            //循环读取 使用read, 单个字符读取
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 字符数组读取文件
     */
    @Test
    public void readFile02() {
        System.out.println("~~~readFile02 ~~~");
        String filePath = "e:\\story.txt";
        FileReader fileReader = null;
 
        int readLen = 0;
        char[] buf = new char[8];
        //1. 创建FileReader对象
        try {
            fileReader = new FileReader(filePath);
            //循环读取 使用read(buf), 返回的是实际读取到的字符数
            //如果返回-1, 说明到文件结束
            while ((readLen = fileReader.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## FileWriter

使用FileWriter将”风雨之后，定见彩虹“写入到note.txt文件中，注意细节

``` java
public class FileWriter_ {
    public static void main(String[] args) {
        String filePath = "e:\\note.txt";
        //创建FileWriter对象
        FileWriter fileWriter = null;
        char[] chars = {'a', 'b', 'c'};
        try {
            fileWriter = new FileWriter(filePath);//默认是覆盖写入
//            3) write(int):写入单个字符
            fileWriter.write('H');
//            4) write(char[]):写入指定数组
            fileWriter.write(chars);
//            5) write(char[],off,len):写入指定数组的指定部分
            fileWriter.write("韩顺平教育".toCharArray(), 0, 3);
//            6) write（string）：写入整个字符串
            fileWriter.write(" 你好北京~");
            fileWriter.write("风雨之后，定见彩虹");
//            7) write(string,off,len):写入字符串的指定部分
            fileWriter.write("上海天津", 0, 2);
            //在数据量大的情况下，可以使用循环操作.
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //对应FileWriter , 一定要关闭流，或者flush才能真正的把数据写入到文件
            //老韩看源码就知道原因.
            /*
                看看代码
    private void writeBytes() throws IOException {
        this.bb.flip();
        int var1 = this.bb.limit();
        int var2 = this.bb.position();
        assert var2 <= var1;
        int var3 = var2 <= var1 ? var1 - var2 : 0;
        if (var3 > 0) {
            if (this.ch != null) {
                assert this.ch.write(this.bb) == var3 : var3;
            } else {
                this.out.write(this.bb.array(), this.bb.arrayOffset() + var2, var3);
            }
        }
        this.bb.clear();
    }
             */
            try {
                //fileWriter.flush();
                //关闭文件流，等价 flush() + 关闭
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("程序结束...");
    }
}
```

## 节点流处理流

- 基本介绍

  1. 节点流可以从一个特定的数据源读写数据，如FileReader、FileWriter

  2. 处理流（也叫包装流）是”连接“在已存在的流（节点流或处理流）之上，为程序提供更为强大的读写功能，如BufferReader、BufferWriter

     处理流例如BufferedReader类中，有属性Reader，既可以封装一个节点流，该节点流可以是任意的，只要是Reader子类。

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230114222555117.png" alt="image-20230114222555117" style="zoom:80%;" />

- 节点流和处理流一览图

  <img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230114222542050.png" alt="image-20230114222542050" style="zoom:150%;" />