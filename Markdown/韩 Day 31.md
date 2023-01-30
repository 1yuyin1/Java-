# 坦克大战3

## TankGame01

``` java
package tankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import static java.lang.System.*;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01(){
        System.out.println("请输入选择 1：新游戏 2：继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp);
        this.setSize(1300,750);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.storeRecord();
                exit(0);
            }
        });
    }
}
```

## MyPanel

``` java
package tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 4;
    //定义一个存放Node对象的Vector
    Vector<Node> nodes = null;

    Vector<Bomb> bombs = new Vector<>();
    Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.gif"));
    Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.gif"));
    Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.gif"));

    public MyPanel(String key) {

        File file = new File(Recorder.getrecordFilePath());
        if(file.exists()) {
            nodes = Recorder.getNodes();
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = "1";
        }
        //将MyPanle对象的 enemyTanks地址 设置给 Recorder，为了关闭的时候存储数据
        Recorder.setEnemyTanks(enemyTanks);
        myTank = new MyTank(500, 500, 0, 5);
        switch (key) {
            case "1" :
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0, 2, 1);
                    enemyTank.setEnemyTanks(enemyTanks);
                    new Thread(enemyTank).start();

                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            case "2" :
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY(), node.getDirect(), node.getSpeed());
                    //将enemyTanks设置给enemyTank 以检测是否碰撞
                    enemyTank.setEnemyTanks(enemyTanks);
                    new Thread(enemyTank).start();

                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }
        new AePlayWave("src/tankGame/China-X.wav").start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//默认填充黑色
        showInfo(g);//显示记录的信息
        //画出自己的坦克
        if(myTank != null && myTank.isLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }
        //遍历Vector，画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                //画出所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.setColor(Color.YELLOW);
                        g.fillOval(shot.x, shot.y, 3, 3);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

//        //我的坦克的子弹
//        if (myTank.shot != null && myTank.shot.isLive) {
//            g.fillOval(myTank.shot.x, myTank.shot.y, 3, 3);
//        }
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive) {
                g.setColor(Color.cyan);
                g.fillOval(shot.x, shot.y, 3, 3);
            } else {
                myTank.shots.remove(shot);
            }
        }

        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            bomb.lifeDown();

            if (!bomb.isLive) {
                bombs.remove(bomb);
            }
        }

    }
    public void showInfo(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 25));

        g.drawString("您累计击毁敌方坦克",1020,30);
        drawTank(1020, 60, g, 0, 1);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum() +"", 1080, 100);

    }
    //绘制坦克的方法
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0://我的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向，来绘制对应的坦克
        //direct 表示方向(0:向上 1：向右 2：向下 3：向左）
        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克舱
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y - 10);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克舱
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 70, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克舱
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 70);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克舱
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x - 10, y + 20);
                break;
            default:
                System.out.println("暂时不处理");
        }
    }
    public void hitMe() {
        EnemyTank enemyTank;
        Shot shot;
        for (int i = 0; i < enemyTanks.size(); i++) {
                 enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                 shot = enemyTank.shots.get(j);
                if(myTank.isLive() && shot.isLive) {
                    hitTank(shot,myTank);
                }
            }
        }
    }
    public void hitEnemy() {
        Shot shot = null;
        EnemyTank enemyTank = null;
        for (int i = 0; i < myTank.shots.size(); i++) {
            shot = myTank.shots.get(i);
            if (shot != null && shot.isLive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.setLive(false);

                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象加到集合中
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
            case 1:
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.setLive(false);
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.setDirect(0);
            myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(1);
            myTank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(2);
            myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(3);
            myTank.moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
//            //发射一颗子弹
//            if (myTank.shot == null || !myTank.shot.isLive) {
//                myTank.Myshot();
//            }
            myTank.Myshot();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            hitEnemy();
            hitMe();
            this.repaint();
        }
    }
}
```

## Recoder

``` java
package tankGame;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allEnemyTankNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFilePath = "src/tankGame/myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static String getrecordFilePath() {
        return recordFilePath;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //增加一个方法，用于读取，恢复相关信息
    public static  Vector<Node> getNodes() {
        try {
            br = new BufferedReader(new FileReader(recordFilePath));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public static void storeRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFilePath));
            bw.write(allEnemyTankNum +"\r\n");
            //遍历敌人坦克，然后根据情况保存
            for(int i = 0; i < enemyTanks.size();i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + " " + enemyTank.getSpeed();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

}
```

## Node

``` java
package tankGame;

public class Node {
    private int x;
    private int y;
    private int direct;
    private  int speed;

    public Node(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
```

## AePlayWave

``` java
package tankGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AePlayWave extends Thread {
    private String filename;

    public AePlayWave(String wavfile) { //构造器 , 指定文件
        filename = wavfile;

    }

    public void run() {

        File soundFile = new File(filename);

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        //缓冲
        byte[] abData = new byte[512];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}
```

## Tank

``` java
package tankGame;

public class Tank {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct = 0;//坦克方向 0：上 1：右 2：下 3：左
    private int speed = 1;
    private boolean isLive = true;

    public Tank(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    public void moveUp() {
        if(y > 0) y -= speed;
    }

    public void moveRight() {
        if(x + 80 <1000) x += speed;

    }

    public void moveDown() {
        if(y + 100 <750) y += speed;
    }

    public void moveLeft() {
        if(x >0) x -= speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
```

## MyTank

``` java
package tankGame;

import java.util.Vector;

public class MyTank extends Tank {
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    public void Myshot() {
        if(shots.size() == 5) return;
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY() - 10, 0);
                break;
            case 1:
                shot = new Shot(getX() + 70, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 70, 2);
                break;
            case 3:
                shot = new Shot(getX() - 10, getY() + 20, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
```

## EnemyTank

``` java
package tankGame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    Shot s;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    //这里提供一个方法将MyPanle类的enemytanks成员  设置到EnemyTank
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isTouchEnemyTank() {
        if(enemyTanks.size() == 0) return true;
        switch (getDirect()) {
            case 0:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右上角坐标[this.getX() + 40,this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右上角坐标[this.getX() + 40,this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 1:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克右上角坐标[this.getX() + 60,this.getY()]
                            if(this.getX() + 60>= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 60,this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克右上角坐标[this.getX() + 60,this.getY()]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 60,this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 2:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左下角坐标[this.getX(),this.getY() + 60]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY()  + 60 >= enemyTank.getY()
                                    && this.getY()  + 60 <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 40,this.getY() + 60]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左下角坐标[this.getX(),this.getY() + 60]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 40,this.getY() + 60]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 3:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克左下角坐标[this.getX(),this.getY() + 40]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克左下角坐标[this.getX(),this.getY() + 40]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
        }
        return false;
    }
    @Override
    public void run() {
        while (true) {
            //如果判断shots.size = 0，创建一颗子弹

            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveUp();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveRight();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveDown();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveLeft();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            setDirect((int) (Math.random() * 4));
            if (!isLive()) break;
        }
    }
    public void shuttle(){
        int temp = (int) (Math.random() *100);
        if ( temp == 31 && isLive() && shots.size() < 3) {
            switch (getDirect()) {
                case 0:
                    s = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1:
                    s = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2:
                    s = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3:
                    s = new Shot(getX(), getY() + 20, 3);
                    break;
            }
            shots.add(s);
            //启动
            new Thread(s).start();
        }
    }
}
```

## Shot

``` java
package tankGame;

public class Shot implements Runnable {
    int x;
    int y;
    int direct = 0;
    int speed = 2;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750) && isLive) {
                isLive = false;
                break;
            }
        }
    }
}
```

## Boomb

``` java
package tankGame;

public class Bomb {
    int x,y;
    int life = 9;
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if(life > 0) {
            life --;
        } else {
            isLive = false;
        }
    }
}
```

