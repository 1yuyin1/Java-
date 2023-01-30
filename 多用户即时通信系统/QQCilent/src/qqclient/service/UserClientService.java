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
