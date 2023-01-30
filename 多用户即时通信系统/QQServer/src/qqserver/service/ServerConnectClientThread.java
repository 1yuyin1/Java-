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
