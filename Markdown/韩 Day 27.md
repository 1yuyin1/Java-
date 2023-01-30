## 坦克大战2

## MyPanel


```java
//MyPanel
package tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;

    Vector<Bomb> bombs = new Vector<>();
    Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.gif"));
    Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.gif"));
    Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.gif"));

    public MyPanel() {
        myTank = new MyTank(500, 500, 0, 5);

        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0, 2, 1);
            new Thread(enemyTank).start();
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());

            enemyTank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//默认填充黑色
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

## Tank


``` java
//tank
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

## Bomb

``` java
//Bomb
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

## Mytank

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

    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    @Override
    public void run() {
        while (true) {
            //如果判断shots.size = 0，创建一颗子弹

            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < 50; i++) {
                        moveUp();
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
                        moveRight();
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
                        moveDown();
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
                        moveLeft();
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

## TankGame 02

``` java
package tankGame;

import javax.swing.*;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01(){
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
```

