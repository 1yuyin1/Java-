# 网络编程

## 网络基础

- 网络通信
  1. 概念：两台设备之间通过网络实现数据传输
  2. 网络通信：将数据通过网络从一台设备传输到另一台设备
  3. java.net包下提供了一系列的类或接口，供程序员使用，完成网络通信

- 网络
  1. 概念：两台或多台设备通过一定物理设备连接起来构成了网络
  2. 根据网络的覆盖范围不同，对网络进行分类：
     - 局域网：覆盖范围最小，仅仅覆盖一个教室或一个机房
     - 城域网：覆盖范围较大，可以覆盖一个城市
     - 广域网：覆盖范围最大，可以覆盖全国，甚至全球，万维网是广域网的代表

- ip地址
  1. 概念：用于唯一标识网络中的每台计算机/主机
  2. 查看ip地址：ipconfig
  3. ip地址的表示形式：点分十进制 xx.xx.xx.xx
  4. 每一个十进制数的范围：0~255
  5. ip地址的组成=网络地址+主机地址，比如：192.168.16.69
  6. IPv6是互联网工程任务组设计的用于替代IPv4的下一代IP协议，其地址数量号称可以为全世界的每一粒沙子编上一个地址
  7. 由于IPv4最大的问题在于网络地址资源优先，严重制约了互联网的应用和发展。IPv6的使用，不仅能解决网络地址资源数量的问题，而且也解决了多种接入设备连入互联网的障碍



- IPv4地址分类

  <img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230118172245568.png" alt="image-20230118172245568" style="zoom:80%;" />

![image-20230118172304069](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230118172304069.png)

特殊的：127.0.0.1 表示本机地址

- 域名
  1. www.baidu.com
  2. 好处：为了方便记忆，解决记ip的困难
  3. 概念：将ip地址映射为域名，这里怎么映射上：HTTP协议
- 端口号
  1. 用于标识计算机上某个特定的网络程序
  2. 表示形式：以整数形式，范围0~65535【2个字节】
  3. 0~1024已经被占用，比如ssh 22，ftp 21，smtp 25，http 80
  4. 常见的网络程序端口号
     - tomcat：8080
     - mysql：3306
     - oracle：1521
     - salserver：1433

## 网络协议

- 协议（tcp/ip)

  TCP/IP(Transmission Control Protocol/Internet Protocol)的简写，中文译名为传输控制协议/因特网互联协议，又叫网络通讯协议，这个协议是Internet最基本的协议，Internet国际互联网络的基础，简单地说，就是由网络层的IP协议和传输层的TCP协议组成的

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230118181417346.png" alt="image-20230118181417346" style="zoom:67%;" />

- 网络通信协议

  <img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230118200227660.png" alt="image-20230118200227660" style="zoom:80%;" />

## TCP和UDP

- TCP协议（传输控制协议）

  ① 使用TCP协议前，须先建立TCP连接，形成传输数据通道；

  ② 传输前，采用“三次握手”方式，确认是可靠的；

  ③ TCP协议进行通信的两个应用进程：客户端、服务端；

  ④ 在连接中可进行大数据量的传输；

  ⑤ 传输完毕，需释放已建立的连接，效率低。

- UDP协议（用户数据协议）

  ① 将数据、源、目的封装成数据包，不需要建立连接；

  ② 每个数据包的大小限制在64K内，不适合传输大量数据；

  ③ 因无需连接，故是不可靠的；

  ④ 发送数据结束时无需释放资源（因为不是面向连接的），速度快；

  ⑤ 举例：测试通知：发短信。

## InetAddress

- 相关方法：
  1. 获取本机InetAddress对象 getLocalHost
  2. 根据指定主机名/域名获取ip地址对象 getByName
  3. 获取InetAddress对象的主机名 getHostName
  4. 获取InetAddress对象的地址 getHostAddress

``` java
public class API_ {
    public static void main(String[] args) throws UnknownHostException {
 
        //1. 获取本机的InetAddress 对象
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);//DESKTOP-S4MP84S/192.168.12.1
 
        //2. 根据指定主机名 获取 InetAddress对象
        InetAddress host1 = InetAddress.getByName("DESKTOP-S4MP84S");
        System.out.println("host1=" + host1);//DESKTOP-S4MP84S/192.168.12.1
 
        //3. 根据域名返回 InetAddress对象, 比如 www.baidu.com 对应
        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        System.out.println("host2=" + host2);//www.baidu.com / 110.242.68.4
 
        //4. 通过 InetAddress 对象，获取对应的地址
        String hostAddress = host2.getHostAddress();//IP 110.242.68.4
        System.out.println("host2 对应的ip = " + hostAddress);//110.242.68.4
 
        //5. 通过 InetAddress 对象，获取对应的主机名/或者的域名
        String hostName = host2.getHostName();
        System.out.println("host2对应的主机名/域名=" + hostName); // www.baidu.com
 
    }
}
```

## Socket

- 基本介绍
  1. 套接字（Socket）开发网络应用程序被广泛采用，以至于成为事实上的标准；
  2. 通讯的两端都要有Socket，是两台机器之间的通讯端点；
  3. 网络通信其实就是Socket间的通信；
  4. Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输；
  5. 一般主动发起通信的应用程序属于客户端，等待通信请求的为服务端。



​																							TCP网络通信编程

- 基本介绍
  1. 基于客户端——服务端的网络通信
  2. 底层使用的是TCP/IP协议
  3. 应用场景举例：客户端发送数据，服务端接收并显示
  4. 基于Socket的TCP编程

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230118205431968.png" alt="image-20230118205431968" style="zoom:80%;" />

## TCP字节流编程

- 应用案例1（使用字节流）
  1. 编写一个服务器端和一个客户端
  2. 服务器端在9999端口监听
  3. 客户端连接到服务器端，发送”hello，server“然后退出
  4. 服务器端接收到客户端发送的信息，输出 并退出

``` java
//Server类
/**
 * 服务端
 */
public class SocketTCP01Server {
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
 
        System.out.println("服务端 socket =" + socket.getClass());
        //
        //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
        InputStream inputStream = socket.getInputStream();
        //4. IO读取
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, readLen));//根据读取到的实际长度，显示内容.
        }
        //5.关闭流和socket
        inputStream.close();
        socket.close();
        serverSocket.close();//关闭
 
    }
}
```

``` java
//Client类
 /**
   * 客户端，发送 "hello, server" 给服务端
 */
public class SocketTCP01Client {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 连接服务端 (ip , 端口）
        //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端 socket返回=" + socket.getClass());
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //   得到 和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
        //3. 通过输出流，写入数据到 数据通道
        outputStream.write("hello, server".getBytes());
        //4. 关闭流对象和socket, 必须关闭
        outputStream.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}
```



- 应用案例2（使用字节流）

  1. 编写一个服务端和一个客户端

  2. 服务器端在9999端口监听

  3. 客户端连接到服务端，发送”hello，server“ 并接收服务器端回发的"hello，client"，再退出

  4. 服务器端接收到 客户端发送的信息输出，并发送“hello，client”，再退出

     ``` JAVA
     //Client类
     /**
      * 客户端，发送 "hello, server" 给服务端
      */
     @SuppressWarnings({"all"})
     public class SocketTCP02Client {
         public static void main(String[] args) throws IOException {
             //思路
             //1. 连接服务端 (ip , 端口）
             //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
             Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
             System.out.println("客户端 socket返回=" + socket.getClass());
             //2. 连接上后，生成Socket, 通过socket.getOutputStream()
             //   得到 和 socket对象关联的输出流对象
             OutputStream outputStream = socket.getOutputStream();
             //3. 通过输出流，写入数据到 数据通道
             outputStream.write("hello, server".getBytes());
             //   设置结束标记
             socket.shutdownOutput();
      
             //4. 获取和socket关联的输入流. 读取数据(字节)，并显示
             InputStream inputStream = socket.getInputStream();
             byte[] buf = new byte[1024];
             int readLen = 0;
             while ((readLen = inputStream.read(buf)) != -1) {
                 System.out.println(new String(buf, 0, readLen));
             }
      
             //5. 关闭流对象和socket, 必须关闭
             inputStream.close();
             outputStream.close();
             socket.close();
             System.out.println("客户端退出.....");
         }
     }
     ```

     ``` java
     //Server类
     //** 
      * 服务端
      */
     @SuppressWarnings({"all"})
     public class SocketTCP02Server {
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
      
             System.out.println("服务端 socket =" + socket.getClass());
             //
             //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
             InputStream inputStream = socket.getInputStream();
             //4. IO读取
             byte[] buf = new byte[1024];
             int readLen = 0;
             while ((readLen = inputStream.read(buf)) != -1) {
                 System.out.println(new String(buf, 0, readLen));//根据读取到的实际长度，显示内容.
             }
             //5. 获取socket相关联的输出流
             OutputStream outputStream = socket.getOutputStream();
             outputStream.write("hello, client".getBytes());
             //   设置结束标记
             socket.shutdownOutput();
      
             //6.关闭流和socket
             outputStream.close();
             inputStream.close();
             socket.close();
             serverSocket.close();//关闭
      
         }
     }
     ```

> 如果不设置结束标记，就会出错，由于程序并不知道什么时候对方输出完毕。



- 应用案例3（使用字符流）

  1. 编写一个服务端和一个客户端
  2. 服务器端在9999端口监听
  3. 客户端连接到服务端，发送”hello，server“ 并接收服务器端回发的"hello，client"，再退出
  4. 服务器端接收到 客户端发送的信息输出，并发送“hello，client”，再退出

  ``` java
  //Client类
  /**
   * 客户端，发送 "hello, server" 给服务端， 使用字符流
   */
  @SuppressWarnings({"all"})
  public class SocketTCP03Client {
      public static void main(String[] args) throws IOException {
          //思路
          //1. 连接服务端 (ip , 端口）
          //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
          Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
          System.out.println("客户端 socket返回=" + socket.getClass());
          //2. 连接上后，生成Socket, 通过socket.getOutputStream()
          //   得到 和 socket对象关联的输出流对象
          OutputStream outputStream = socket.getOutputStream();
          //3. 通过输出流，写入数据到 数据通道, 使用字符流
          BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
          bufferedWriter.write("hello, server 字符流");
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
  public class SocketTCP03Server {
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
   
          System.out.println("服务端 socket =" + socket.getClass());
          //
          //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
          InputStream inputStream = socket.getInputStream();
          //4. IO读取, 使用字符流, 老师使用 InputStreamReader 将 inputStream 转成字符流
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
          String s = bufferedReader.readLine();
          System.out.println(s);//输出
   
          //5. 获取socket相关联的输出流
          OutputStream outputStream = socket.getOutputStream();
         //    使用字符输出流的方式回复信息
          BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
          bufferedWriter.write("hello client 字符流");
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

> 需要使用转换流将字节流转换成字符流。
> 并且可以将写入结束标记写为writer.newLine()//换行符，注意使用readLine()
> 以表示写入的结束。另外，还需要注意手动的flush否则，无法写入成功。（bufferedWriter的close包含了flush方法）