# QQClient

##  service

### ClientConnectServer

``` java
package qqclient.service;

import qqclient.utils.Utility;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{

    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，因此我们while循环
        while (true) {
            System.out.println("客户端线程等待读取从服务器端发送的消息");

            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送对象，线程会阻塞在这里
                Message message = (Message) ois.readObject();

                //判断这个message类型，然后做相应的业务处理
                if(message.getMesType().equals(MessageType.MESSAGE_RETURN_ONLINE_FRIEND)) {
                    String[] split = message.getContent().split(" ");
                    System.out.println("\n===============当前在线用户列表===============");
                    for (int i = 0; i < split.length; i++) {
                        System.out.println("用户：" + split[i]);
                    }
                    
                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("\n" + message.getSendTime() + "\t" + message.getSender() + " 对" + message.getGetter() + "说：" + message.getContent());
                } else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println("\n" + message.getSendTime() + "\t" + message.getSender() + "对大家说：" + message.getContent());
                } else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    System.out.println("\n" + message.getSender() + " 给" + message.getGetter() +"发送文件：" + message.getSrc() + " 到我的电脑" + message.getDest());
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n保存文件成功");

                } else {
                    System.out.println("是其他类型的，暂不处理");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
```

###  ClientMessageService

```java
package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 余音
 * @Date: 2023/01/25/16:58
 * @Description:该类提供消息相关的服务方法
 */
public class ClientMessageService {
/**
 * @Description: 私聊功能
 * @Param: content
 * @Param: 发送用户senderId
 * @Param: 接收用户getterId
 * @return: void
 * @Author: 余音
 * @Date: 2023/1/25
 */
    public void sendMessage(String content,String senderId,String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(message.getSendTime() + "\t" + senderId + " 对" + getterId + "说：" + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  sendMessageToAll(String content,String senderId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);;
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(message.getSendTime() + "\t" + senderId + " 对大家说 " + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

###  FileClientService

```java
package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 余音
 * @Date: 2023/01/25/19:54
 * @Description: 该类完成对象传输服务
 */
public class FileClientService {
    public void sendFileToOne(String src,String dest,String senderId,String getterId) {
        //读取src文件并包装成message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSrc(src);
        message.setDest(dest);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSendTime(new Date().toString());

        FileInputStream fis = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];
        try {
            fis = new FileInputStream(src);
            fis.read(fileBytes);
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis !=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n" + senderId + "给" + getterId + "发送文件：" + src + " 到对方电脑");

        //发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### ManageClientConnectServerThread

```java
package qqclient.service;

import java.util.HashMap;

//该类管理客户端连接到服务端的线程的类
public class ManageClientConnectServerThread {
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }

}
```

### UserClientService

``` java
package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


//该类完成用户登录验证和用户注册等功能
public class UserClientService {
    boolean b = false;
    private User u = new User();
    private Socket socket;
    //根据userId 和 pwd到服务器验证该用户是否合法
    public boolean checkUser(String userId,String pwd)  {
        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            //将登录信息发送给服务器，以检验是否合法
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);
            //接收服务器返回的信息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                //这里为了方便后面客户端的扩展，我们将线程放入集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);
                b = true;
            } else {
                //如果登录失败，我们不能启动和服务器相连的线程
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    public void show_onlineFriendList() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        try {
            //得到userId 对应的线程对象
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + " 退出系统");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## Utils

### Utility

```java
package qqclient.utils;

/**
   工具类的作用:
   处理各种情况的用户输入，并且能够按照程序员的需求，得到用户的控制台输入。
*/

import java.util.*;
/**

   
*/
public class Utility {
   //静态属性。。。
    private static Scanner scanner = new Scanner(System.in);

    
    /**
     * 功能：读取键盘输入的一个菜单选项，值：1——5的范围
     * @return 1——5
     */
   public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);//包含一个字符的字符串
            c = str.charAt(0);//将字符串转换成字符char类型
            if (c != '1' && c != '2' && 
                c != '3' && c != '4' && c != '5') {
                System.out.print("选择错误，请重新输入：");
            } else break;
        }
        return c;
    }

   /**
    * 功能：读取键盘输入的一个字符
    * @return 一个字符
    */
    public static char readChar() {
        String str = readKeyBoard(1, false);//就是一个字符
        return str.charAt(0);
    }
    /**
     * 功能：读取键盘输入的一个字符，如果直接按回车，则返回指定的默认值；否则返回输入的那个字符
     * @param defaultValue 指定的默认值
     * @return 默认值或输入的字符
     */
    
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);//要么是空字符串，要么是一个字符
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }
   
    /**
     * 功能：读取键盘输入的整型，长度小于2位
     * @return 整数
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, false);//一个整数，长度<=10位
            try {
                n = Integer.parseInt(str);//将字符串转换成整数
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }
    /**
     * 功能：读取键盘输入的 整数或默认值，如果直接回车，则返回默认值，否则返回输入的整数
     * @param defaultValue 指定的默认值
     * @return 整数或默认值
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, true);
            if (str.equals("")) {
                return defaultValue;
            }
         
         //异常处理...
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串
     * @param limit 限制的长度
     * @return 指定长度的字符串
     */

    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串或默认值，如果直接回车，返回默认值，否则返回字符串
     * @param limit 限制的长度
     * @param defaultValue 指定的默认值
     * @return 指定长度的字符串
     */
   
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("")? defaultValue : str;
    }


   /**
    * 功能：读取键盘输入的确认选项，Y或N
    * 将小的功能，封装到一个方法中.
    * @return Y或N
    */
    public static char readConfirmSelection() {
        char c;
        for (; ; ) {//无限循环
           //在这里，将接受到字符，转成了大写字母
           //y => Y n=>N
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("选择错误，请重新输入：");
            }
        }
        return c;
    }

    /**
     * 功能： 读取一个字符串
     * @param limit 读取的长度
     * @param blankReturn 如果为true ,表示 可以读空字符串。 
     *                   如果为false表示 不能读空字符串。
     *           
    * 如果输入为空，或者输入大于limit的长度，就会提示重新输入。
     * @return
     */
    private static String readKeyBoard(int limit, boolean blankReturn) {
        
      //定义了字符串
      String line = "";

      //scanner.hasNextLine() 判断有没有下一行
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();//读取这一行
           
         //如果line.length=0, 即用户没有输入任何内容，直接回车
         if (line.length() == 0) {
                if (blankReturn) return line;//如果blankReturn=true,可以返回空串
                else continue; //如果blankReturn=false,不接受空串，必须输入内容
            }

         //如果用户输入的内容大于了 limit，就提示重写输入  
         //如果用户如的内容 >0 <= limit ,我就接受
            if (line.length() < 1 || line.length() > limit) {
                System.out.print("输入长度（不能大于" + limit + "）错误，请重新输入：");
                continue;
            }
            break;
        }

        return line;
    }
}
```

## view

### QQView

```java
package qqclient.view;

import qqclient.service.ClientMessageService;
import qqclient.service.FileClientService;
import qqclient.service.UserClientService;
import qqclient.utils.Utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class QQView {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();// 使用对象来使用登录服务/注册用户
    private ClientMessageService clientMessageService = new ClientMessageService();// 用户私聊/群聊
    private FileClientService fileClientService = new FileClientService();// 传输文件

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
        new QQView().mainMenu();
    }

    private void mainMenu() {
        while(loop) {
            System.out.println("===============欢迎登陆网络通信系统===============");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(20);
                    System.out.print("请输入密  码：");
                    String pwd = Utility.readString(20);
                    //下面需要到服务端验证用户是否合法

                    if(userClientService.checkUser(userId, pwd)) {
                        System.out.println("===============欢迎用户" + userId + "登录成功===============");
                        while(loop) {
                            System.out.println("\n===============网络通信系统二级菜单 用户" + userId + " ===============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
//                                    System.out.println("显示在线用户列表");
                                    userClientService.show_onlineFriendList();
                                    break;
                                case "2":
//                                    System.out.println("群发消息");
                                    System.out.println("请输入想对大家说的话：");
                                    String s = Utility.readString(100);
                                    clientMessageService.sendMessageToAll(s,userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想私聊的用户名（在线）：");
                                    String getterId = Utility.readString(20);
                                    System.out.print("请输入想说的话：");
                                    String content = Utility.readString(100);
                                    clientMessageService.sendMessage(content, userId,getterId);
//                                    System.out.println("私聊消息");
                                    break;
                                case "4":
//                                    System.out.println("发送文件");
                                    System.out.print("请输入你想发送文件到的用户（在线）：");
                                    getterId = Utility.readString(20);
                                    System.out.print("请输入文件的完整路径：");
                                    String src = Utility.readString(50);
                                    System.out.print("请输入发送到对方的路径：");
                                    String dest = Utility.readString(50);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    //调用方法，给服务器发送退出的信息
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
```

## qqcommon

### Message

```java
package qqcommon;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long serialVersionUID = 1;
    private String sender;
    private String getter;
    private String content;
    private String sendTime;
    private String mesType;

    //进行扩展和文件相关的成员
    private byte[] fileBytes;
    private int fileLen = 0;
    private String src;
    private String dest;

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Message() {
    }

    public Message(String sender, String getter, String content, String sendTime, String mesType) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendTime = sendTime;
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
```

###  MessageType

```java
package qqcommon;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";
    String MESSAGE_LOGIN_FAIL = "2";
    String MESSAGE_COMM_MES = "3";//普通信息包
    String MESSAGE_TO_ALL_MES = "3+";//群发消息包
    String MESSAGE_GET_ONLINE_FRIEND = "4";//要求返回在线用户列表
    String MESSAGE_RETURN_ONLINE_FRIEND = "5";//返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
    String MESSAGE_FILE_MES = "7";//文件消息发送
}
```

### User

```java
package qqcommon;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String passwd;

    public User() {
    }

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
```

# QQServer

## QQframe

### QQFrame

```java
package QQframe;

import qqserver.service.QQServer;

public class QQFrame {
    public static void main(String[] args) {
        new QQServer();
    }
}
```

## qqserver.service

### ManageClientThread

```java
package qqserver.service;


import java.util.HashMap;
import java.util.Iterator;


public class ManageClientThread {
    private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    public static void removeClientThread(String userId) {
        hm.remove(userId);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    public static String getOnlineUser() {
        Iterator<String> iterator = hm.keySet().iterator();
            String onlineUserList = "";
            while(iterator.hasNext()) {
                onlineUserList += iterator.next().toString() +" ";
            }
            return onlineUserList;
    }
}
```

### OffLineMessageService

```java
package qqserver.service;

import qqcommon.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 余音
 * @Date: 2023/01/25/22:40
 * @Description:
 */
@SuppressWarnings({"all"})
public class OffLineMessageService {
    private static ConcurrentHashMap<String, Vector<Message>> offLineDb = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Vector<Message>> getOffLineDb() {
        return offLineDb;
    }

    public static void setOffLineDb(ConcurrentHashMap<String, Vector<Message>> offLineDb) {
        OffLineMessageService.offLineDb = offLineDb;
    }

    public static void addOffMessage(Message message) {

        if (offLineDb.containsKey(message.getGetter())) {
            Vector<Message> messages = offLineDb.get(message.getGetter());
            messages.add(message);
        } else {
            Vector<Message> messages = new Vector<>();
            messages.add(message);
            offLineDb.put(message.getGetter(), messages);
        }
    }

    public static void sendOffMessage(String getterId, ConcurrentHashMap offlineMap) {
        //如果离线消息库中含有该接收者，就发送信息
        if (offlineMap.containsKey(getterId)) {
            try {
                //获取value
                Vector<Message> vector = (Vector<Message>) offlineMap.get(getterId);
                //服务器发送messa给geterrId
                ObjectOutputStream oos = new ObjectOutputStream
                        (ManageClientThread.getServerConnectClientThread(getterId).getSocket().getOutputStream());
                //遍历message集合发送到客户端
                for (Message message : vector) {
                    oos.writeObject(message);
                }

                System.out.println("发送成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isOnline(String getterId) {
        HashMap<String, ServerConnectClientThread> hm = ManageClientThread.getHm();
        return hm.containsKey(getterId);
    }

    public static void deleteOfflineMessage(String getterId) {
        if (offLineDb.containsKey(getterId)) {
            Vector<Message> remove = offLineDb.remove(getterId);
            System.out.println("删除消息成功");
        }
    }

}
```

### QQServer

```java
package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class QQServer {
    private ServerSocket ss = null;
    //ConcurrentHashMap 做了线程同步处理
    private static ConcurrentHashMap<String,User> validUsers = new ConcurrentHashMap<>();
    static {//在静态代码块，初始化validUsers
        validUsers.put("100", new User("100","123456"));
        validUsers.put("200", new User("200","123456"));
        validUsers.put("300", new User("300","123456"));
        validUsers.put("至尊宝", new User("至尊宝","123456"));
        validUsers.put("紫霞仙子", new User("紫霞仙子","123456"));
    }

    private boolean checkUser(String userId,String pwd) {
        User user = validUsers.get(userId);
        if(user == null) return false;
        if(! user.getPasswd().equals(pwd)) return false;
        return true;
    }

    public QQServer() {
        //注意：端口可以卸载配置文件
        try {
            System.out.println("服务端在9999端口监听");
            new SendNewsToAllService().start();
            ss = new ServerSocket(9999);
            while(true) {  //当和某个客户端连接后，会继续监听，因此while
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User u = (User) ois.readObject();

                //验证
                Message message = new Message();
                if(checkUser(u.getUserId(), u.getPasswd())) {
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //创建一个线程，和客户端保持通信
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThread.addClientThread(u.getUserId(), serverConnectClientThread);
                    //登录成功后，发送储存的离线信息
                    ConcurrentHashMap<String, Vector<Message>> offLineDb = OffLineMessageService.getOffLineDb();
                    OffLineMessageService.sendOffMessage(u.getUserId(), offLineDb);
                    OffLineMessageService.deleteOfflineMessage(u.getUserId());

                } else {
                    System.out.println("用户 id=" + u.getUserId() + "  pwd=" + u.getPasswd() + "\t登录失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### SendNewsToAllService

```java
package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.management.MemoryType;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 余音
 * @Date: 2023/01/25/21:13
 * @Description: 向所有在线用户推送新闻
 */
public class SendNewsToAllService extends Thread{
    @Override
    public void run() {
        while(true) {
            System.out.println("请输入服务器要推送的新闻/消息【输入exit】退出推送服务");
            String news = Utility.readString(100);

            if(news.equals("exit")) {
                break;
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人说：" + news);

            HashMap<String, ServerConnectClientThread> hm = ManageClientThread.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream
                            (ManageClientThread.getServerConnectClientThread(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

### ServerConnectClientThread

```java
package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;


public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public void run() {

        while(true) {
            System.out.println("服务端和客户端" + userId +"保持通信");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                //根据message类型做相应的业务处理
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println(message.getSender() + " 要在线用户列表");
                    String onlineUser = ManageClientThread.getOnlineUser();

                    //返回message
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_RETURN_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetter(message.getSender());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);

                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    if(!OffLineMessageService.isOnline(message.getGetter())) {
                        System.out.println("该用户不存在，将在登陆后接收到消息");
                        OffLineMessageService.addOffMessage(message);
                    } else {
                        ServerConnectClientThread serverConnectClientThread =
                                ManageClientThread.getServerConnectClientThread(message.getGetter());
                        ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);//转发，如果用户不在线，可以保存到数据库，以实现离线留言
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //遍历出所有在线用户，将信息发给非发送者的用户
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThread.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while(iterator.hasNext()) {
                        String onLineUserId = iterator.next().toString();

                        if(!onLineUserId.equals(message.getSender())) {
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);

                        }
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    //根据getterId获取对应线程，将message对象转发
                    if(!OffLineMessageService.isOnline(message.getGetter())) {
                        System.out.println("该用户不存在，将在登陆后接收到消息");
                        OffLineMessageService.addOffMessage(message);
                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream
                                (ManageClientThread.getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                        oos.writeObject(message);
                    }

                } else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println(message.getSender() + "退出");
                    ManageClientThread.removeClientThread(message.getSender());
                    socket.close();//关闭连接
                    break;//退出线程
                } else {
                    System.out.println("其他类型的，暂不处理");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

## qqcommon

## Utils

