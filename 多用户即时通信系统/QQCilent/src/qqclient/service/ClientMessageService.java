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
