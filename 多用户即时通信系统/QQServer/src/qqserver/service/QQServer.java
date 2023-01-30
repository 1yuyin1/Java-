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
