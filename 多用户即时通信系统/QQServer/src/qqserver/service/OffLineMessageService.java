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
