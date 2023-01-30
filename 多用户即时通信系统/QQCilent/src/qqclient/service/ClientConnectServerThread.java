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
