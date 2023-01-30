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
