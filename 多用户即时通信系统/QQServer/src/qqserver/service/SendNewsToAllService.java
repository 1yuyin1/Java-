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
