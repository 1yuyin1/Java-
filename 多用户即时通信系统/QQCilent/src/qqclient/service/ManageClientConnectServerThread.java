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
