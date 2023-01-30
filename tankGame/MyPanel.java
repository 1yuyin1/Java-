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
