## 网络上传文件

- 应用案例4
  1. 编写一个服务端和一个客户端
  2. 服务端在8888端口监听
  3. 客户端连接到服务端发送一张图片
  4. 服务器端接收到  客户端发送的图片，保存到src下，发送“收到图片”再退出
  5. 客户端接收到服务端发送的“收到图片”再退出
  6. 该程序要求使用 StreamUtils

``` java
//Client类
/**
 * 文件上传的客户端
 */
public class TCPFileUploadClient {
    public static void main(String[] args) throws Exception {
 
        //客户端连接服务端 8888，得到Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        //创建读取磁盘文件的输入流
        //String filePath = "e:\\qie.png";
        String filePath = "e:\\abc.mp4";
        BufferedInputStream bis  = new BufferedInputStream(new FileInputStream(filePath));
 
        //bytes 就是filePath对应的字节数组
        byte[] bytes = StreamUtils.streamToByteArray(bis);
 
        //通过socket获取到输出流, 将bytes数据发送给服务端
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);//将文件对应的字节数组的内容，写入到数据通道
        bis.close();
        socket.shutdownOutput();//设置写入数据的结束标记
 
        //=====接收从服务端回复的消息=====
 
        InputStream inputStream = socket.getInputStream();
        //使用StreamUtils 的方法，直接将 inputStream 读取到的内容 转成字符串
        String s = StreamUtils.streamToString(inputStream);
        System.out.println(s);
 
 
        //关闭相关的流
        inputStream.close();
        bos.close();
        socket.close();
 
    }
}
```

```java
//Server类
/**
 * 文件上传的服务端
 */
public class TCPFileUploadServer {
    public static void main(String[] args) throws Exception {
 
        //1. 服务端在本机监听8888端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端在8888端口监听....");
        //2. 等待连接
        Socket socket = serverSocket.accept();
 
 
        //3. 读取客户端发送的数据
        //   通过Socket得到输入流
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //4. 将得到 bytes 数组，写入到指定的路径，就得到一个文件了
        String destFilePath = "src\\abc.mp4";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
        bos.write(bytes);
        bos.close();
 
        // 向客户端回复 "收到图片"
        // 通过socket 获取到输出流(字符)
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("收到图片");
        writer.flush();//把内容刷新到数据通道
        socket.shutdownOutput();//设置写入结束标记
 
        //关闭其他资源
        writer.close();
        bis.close();
        socket.close();
        serverSocket.close();
    }
}
```

``` java
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
 
public class StreamUtils {
    public static byte[] streamToByteArray(InputStream is) throws Exception {
        // 创建输出流对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 字节数组
        byte[] b = new byte[1024];
        int len;
        while ((len = is.read(b)) != -1) {
            // 循环读取
            // 把读取到的数据，写入 bos
            bos.write(b, 0, len);
        }
        byte[] array = bos.toByteArray();
        bos.close();
        return array;
    }
 
    public static String streamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\r\n");
        }
        return builder.toString();
    }
}
```


## netstat

1. netstat-an	可以查看当前主机网络情况，包括端口监听情况和网络连接情况
2. netstat-an|more 可以分页显示
3. 要求在dos控制台下执行

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230119154638817.png" alt="image-20230119154638817" style="zoom:80%;" />

说明：

1. Listening 表示某个端口在监听
2. 如果有一个外部程序（客户端）连接到该端口，就会显示一条连接信息
3. 可以输入ctrl + c 退出指令

## TCP连接的秘密

当客户端连接到服务端后，实际上客户端也是通过一个端口和服务端进行通讯的，这个端口是TCP/IP来分配的

程序验证 + netstat

## UDP原理

- 基本介绍
  1. 类DatagramSocket和DatagramPacket 【数据包/数据报】 实现了基于UDP协议网络程序
  2. UDP数据包通过数据包套接字DatagramSocket 发送和接收，系统不保证UDP数据包一定能够安全送到目的地，也不确定什么时候可以抵达
  3. DatagramPacket 对象封装了UDP数据包，在数据包中包含了发送端的IP地址和端口号以及接收端的IP地址和端口号
  4. UDP协议中每隔数据包都给出了完整的地址信息，因此无需建立发送方和接收方的连接

- UDP说明
  1. 没有明确的服务端和客户端，演变成数据的发送端和接收端
  2. 接收数据和发送数据是通过DatagramSocket对象完成
  3. 将数据封装到DatagramPacket对象，发送
  4. 当接收到DatagramPacket 对象，需要进行拆包，取出数据
  5. DatagramSocket 可以指定在哪个端口接收数据 

- 基本流程
  1. 核心的两个类/对象，DatagramSocket 和DatagramPacket
  2. 建立发送端和接收端【没有服务端和客户端概念】
  3. 发送数据前，建立数据包
  4. 调用DatagramSocket 的发送、接收方法
  5. 关闭DatagramSocket 

## UDP网络编程

- 应用案例

  1. 编写一个接收端A和一个发送端B

  2. 接收端A在9999端口等待接收数据

  3. 发送端B向接收端B发送数据“hello，明天吃火锅~”

  4. 接收端A接收到发送端B发送的数据，回复“好的，明天见”，再退出

  5. 发送端接收回复的数据，再退出

     ``` java
     //ReceiverA类
     /**
      * UDP接收端
      */
     public class UDPReceiverA {
         public static void main(String[] args) throws IOException {
             //1. 创建一个 DatagramSocket 对象，准备在9999接收数据
             DatagramSocket socket = new DatagramSocket(9999);
             //2. 构建一个 DatagramPacket 对象，准备接收数据
             //   在前面讲解UDP 协议时，老师说过一个数据包最大 64k
             byte[] buf = new byte[1024];
             DatagramPacket packet = new DatagramPacket(buf, buf.length);
             //3. 调用 接收方法, 将通过网络传输的 DatagramPacket 对象
             //   填充到 packet对象
             //老师提示: 当有数据包发送到 本机的9999端口时，就会接收到数据
             //   如果没有数据包发送到 本机的9999端口, 就会阻塞等待.
             System.out.println("接收端A 等待接收数据..");
             socket.receive(packet);
      
             //4. 可以把packet 进行拆包，取出数据，并显示.
             int length = packet.getLength();//实际接收到的数据字节长度
             byte[] data = packet.getData();//接收到数据
             String s = new String(data, 0, length);
             System.out.println(s);
      
      
             //===回复信息给B端
             //将需要发送的数据，封装到 DatagramPacket对象
             data = "好的, 明天见".getBytes();
             //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
             packet =
                     new DatagramPacket(data, data.length, InetAddress.getByName("192.168.12.1"), 9998);
      
             socket.send(packet);//发送
      
             //5. 关闭资源
             socket.close();
             System.out.println("A端退出...");
         }
     }
     ```

     ``` java
     //UDPSenderB类
     /**
      * 发送端B ====> 也可以接收数据
      */
     @SuppressWarnings({"all"})
     public class UDPSenderB {
         public static void main(String[] args) throws IOException {
      
             //1.创建 DatagramSocket 对象，准备在9998端口 接收数据
             DatagramSocket socket = new DatagramSocket(9998);
      
             //2. 将需要发送的数据，封装到 DatagramPacket对象
             byte[] data = "hello 明天吃火锅~".getBytes(); //
      
             //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
             DatagramPacket packet =
                     new DatagramPacket(data, data.length, InetAddress.getByName("192.168.12.1"), 9999);
      
             socket.send(packet);
      
             //3.=== 接收从A端回复的信息
             //(1)   构建一个 DatagramPacket 对象，准备接收数据
             //   在前面讲解UDP 协议时，老师说过一个数据包最大 64k
             byte[] buf = new byte[1024];
             packet = new DatagramPacket(buf, buf.length);
             //(2)    调用 接收方法, 将通过网络传输的 DatagramPacket 对象
             //   填充到 packet对象
             //老师提示: 当有数据包发送到 本机的9998端口时，就会接收到数据
             //   如果没有数据包发送到 本机的9998端口, 就会阻塞等待.
             socket.receive(packet);
      
             //(3)  可以把packet 进行拆包，取出数据，并显示.
             int length = packet.getLength();//实际接收到的数据字节长度
             data = packet.getData();//接收到数据
             String s = new String(data, 0, length);
             System.out.println(s);
      
             //关闭资源
             socket.close();
             System.out.println("B端退出");
         }
     }
     ```

## 课后作业

- 编程题1
  1. 使用字符流的方式，编写一个客户端程序和服务器端程序
  2. 客户端发送“name”，服务器端接收到后，返回“我是nova”
  3. 客户端发送“hobby”，服务器端接收到后，返回“编写Java程序”
  4. 不是这两个问题，回复“你说啥呢”

``` java
//Client类
/**
 * 客户端，发送 "hello, server" 给服务端， 使用字符流
 */
@SuppressWarnings({"all"})
public class Homework01Client {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 连接服务端 (ip , 端口）
        //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
 
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //   得到 和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
        //3. 通过输出流，写入数据到 数据通道, 使用字符流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
 
        //从键盘读取用户的问题
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的问题");
        String question = scanner.next();
 
        bufferedWriter.write(question);
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束, 注意，要求对方使用readLine()!!!!
        bufferedWriter.flush();// 如果使用的字符流，需要手动刷新，否则数据不会写入数据通道
 
 
        //4. 获取和socket关联的输入流. 读取数据(字符)，并显示
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);
 
        //5. 关闭流对象和socket, 必须关闭
        bufferedReader.close();//关闭外层流
        bufferedWriter.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}
```

``` java
//Server类
/**
 * 服务端, 使用字符流方式读写
 */
@SuppressWarnings({"all"})
public class Homework01Server {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 在本机 的9999端口监听, 等待连接
        //   细节: 要求在本机没有其它服务在监听9999
        //   细节：这个 ServerSocket 可以通过 accept() 返回多个Socket[多个客户端连接服务器的并发]
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端，在9999端口监听，等待连接..");
        //2. 当没有客户端连接9999端口时，程序会 阻塞, 等待连接
        //   如果有客户端连接，则会返回Socket对象，程序继续
 
        Socket socket = serverSocket.accept();
 
        //
        //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
        InputStream inputStream = socket.getInputStream();
        //4. IO读取, 使用字符流, 老师使用 InputStreamReader 将 inputStream 转成字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        String answer = "";
        if ("name".equals(s)) {
            answer = "我是韩顺平";
        } else if("hobby".equals(s)) {
            answer = "编写java程序";
        } else {
            answer = "你说的啥子";
        }
 
 
        //5. 获取socket相关联的输出流
        OutputStream outputStream = socket.getOutputStream();
        //    使用字符输出流的方式回复信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(answer);
        bufferedWriter.newLine();// 插入一个换行符，表示回复内容的结束
        bufferedWriter.flush();//注意需要手动的flush
 
 
        //6.关闭流和socket
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();//关闭
 
    }
}
```

**调用socket.shutdownOutput();之后，就无法再用这个输出流了**



- 编程题2

  1. 编写一个接收端A和一个发送端B，使用UDP协议完成
  2. 接收端在8888端口等待接收数据
  3. 发送端向     接收端发送数据“四大名著是哪些”
  4. 接收端接收到    发送端发送的数据后，返回“四大名著是《红楼梦》....”，否则返回what？
  5. 接收端和发送端程序退出

  ``` java
  //Homework02ReceiverA类
  /**
   * UDP接收端
   */
  @SuppressWarnings({"all"})
  public class Homework02ReceiverA {
      public static void main(String[] args) throws IOException {
          //1. 创建一个 DatagramSocket 对象，准备在8888接收数据
          DatagramSocket socket = new DatagramSocket(8888);
          //2. 构建一个 DatagramPacket 对象，准备接收数据
          //   在前面讲解UDP 协议时，老师说过一个数据包最大 64k
          byte[] buf = new byte[1024];
          DatagramPacket packet = new DatagramPacket(buf, buf.length);
          //3. 调用 接收方法, 将通过网络传输的 DatagramPacket 对象
          //   填充到 packet对象
          System.out.println("接收端 等待接收问题 ");
          socket.receive(packet);
   
          //4. 可以把packet 进行拆包，取出数据，并显示.
          int length = packet.getLength();//实际接收到的数据字节长度
          byte[] data = packet.getData();//接收到数据
          String s = new String(data, 0, length);
          //判断接收到的信息是什么
          String answer = "";
          if("四大名著是哪些".equals(s)) {
              answer = "四大名著 <<红楼梦>> <<三国演示>> <<西游记>> <<水浒传>>";
          } else {
              answer = "what?";
          }
   
   
          //===回复信息给B端
          //将需要发送的数据，封装到 DatagramPacket对象
          data = answer.getBytes();
          //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
          packet =
                  new DatagramPacket(data, data.length, InetAddress.getByName("192.168.12.1"), 9998);
   
          socket.send(packet);//发送
   
          //5. 关闭资源
          socket.close();
          System.out.println("A端退出...");
   
      }
  }
  ```

  ``` java
  //Homework02SenderB类
  /**
   * 发送端B ====> 也可以接收数据
   */
  @SuppressWarnings({"all"})
  public class Homework02SenderB {
      public static void main(String[] args) throws IOException {
   
          //1.创建 DatagramSocket 对象，准备在9998端口 接收数据
          DatagramSocket socket = new DatagramSocket(9998);
   
          //2. 将需要发送的数据，封装到 DatagramPacket对象
          Scanner scanner = new Scanner(System.in);
          System.out.println("请输入你的问题: ");
          String question = scanner.next();
          byte[] data = question.getBytes(); //
   
          //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
          DatagramPacket packet =
                  new DatagramPacket(data, data.length, InetAddress.getByName("192.168.12.1"), 8888);
   
          socket.send(packet);
   
          //3.=== 接收从A端回复的信息
          //(1)   构建一个 DatagramPacket 对象，准备接收数据
          //   在前面讲解UDP 协议时，老师说过一个数据包最大 64k
          byte[] buf = new byte[1024];
          packet = new DatagramPacket(buf, buf.length);
          //(2)    调用 接收方法, 将通过网络传输的 DatagramPacket 对象
          //   填充到 packet对象
          //老师提示: 当有数据包发送到 本机的9998端口时，就会接收到数据
          //   如果没有数据包发送到 本机的9998端口, 就会阻塞等待.
          socket.receive(packet);
   
          //(3)  可以把packet 进行拆包，取出数据，并显示.
          int length = packet.getLength();//实际接收到的数据字节长度
          data = packet.getData();//接收到数据
          String s = new String(data, 0, length);
          System.out.println(s);
   
          //关闭资源
          socket.close();
          System.out.println("B端退出");
      }
  }
  ```



- 编程题3

  1. 编写客户端程序和服务端程序
  2. 客户端可以输入一个音乐文件名，比如  高山流水，服务端收到音乐名后，可以给客户端返回这个音乐文件，如果服务器没有这个文件，返回一个默认的音乐即可
  3. 客户端收到文件后，保存到本地e:\\
  4. 提示：该程序可以使用StreamUtils.java

  本质：其实就是指定下载文件的应用

```java
//Homework03Server类
/**
 * 先写文件下载的服务端
 */
public class Homework03Server {
    public static void main(String[] args) throws Exception {
        //1 监听 9999端口
        ServerSocket serverSocket = new ServerSocket(9999);
        //2.等待客户端连接
        System.out.println("服务端，在9999端口监听，等待下载文件");
        Socket socket = serverSocket.accept();
        //3.读取 客户端发送要下载的文件名
        //  这里老师使用了while读取文件名，时考虑将来客户端发送的数据较大的情况
        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        int len = 0;
        String downLoadFileName = "";
        while ((len = inputStream.read(b)) != -1) {
            downLoadFileName += new String(b, 0 , len);
        }
        System.out.println("客户端希望下载文件名=" + downLoadFileName);
 
        //老师在服务器上有两个文件, 无名.mp3 高山流水.mp3
        //如果客户下载的是 高山流水 我们就返回该文件，否则一律返回 无名.mp3
 
        String resFileName = "";
        if("高山流水".equals(downLoadFileName)) {
            resFileName = "src\\高山流水.mp3";
        } else {
            resFileName = "src\\无名.mp3";
        }
 
        //4. 创建一个输入流，读取文件
        BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(resFileName));
 
        //5. 使用工具类StreamUtils ，读取文件到一个字节数组
 
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //6. 得到Socket关联的输出流
        BufferedOutputStream bos =
                new BufferedOutputStream(socket.getOutputStream());
        //7. 写入到数据通道，返回给客户端
        bos.write(bytes);
        socket.shutdownOutput();//很关键.
 
        //8 关闭相关的资源
        bis.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出...");
 
    }
}
```

```java
//Homework03Client类
/**
 * 文件下载的客户端
 */
public class Homework03Client {
    public static void main(String[] args) throws Exception {
 
        //1. 接收用户输入，指定下载文件名
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入下载文件名");
        String downloadFileName = scanner.next();
 
        //2. 客户端连接服务端，准备发送
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        //3. 获取和Socket关联的输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(downloadFileName.getBytes());
        //设置写入结束的标志
        socket.shutdownOutput();
 
        //4. 读取服务端返回的文件(字节数据)
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //5. 得到一个输出流，准备将 bytes 写入到磁盘文件
        //比如你下载的是 高山流水 => 下载的就是 高山流水.mp3
        //    你下载的是 韩顺平 => 下载的就是 无名.mp3  文件名 韩顺平.mp3
        String filePath = "e:\\" + downloadFileName + ".mp3";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        bos.write(bytes);
 
        //6. 关闭相关的资源
        bos.close();
        bis.close();
        outputStream.close();
        socket.close();
 
        System.out.println("客户端下载完毕，正确退出..");
    }
}
```

