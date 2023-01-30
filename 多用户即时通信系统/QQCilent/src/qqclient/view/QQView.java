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
