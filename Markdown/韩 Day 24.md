# 坦克大战1

## 坦克大战介绍

- 为什么写这个项目
  1. 好玩
  2. 涉及到Java各个方面的技术
     1.  Java面向对象编程
     2. 多线程
     3. 文件IO操作
     4. 数据库

## Java左边体系

左边原点位于左上角，以像素为单位。在Java坐标系中，第一个是x坐标，表示当前位置为水平方向，距离左边原点x个像素，第二个是y坐标，表示当前位置为垂直方向，距离坐标原点y个像素。

- 坐标体系——像素
  1. 绘图还必须要搞清一个非常重要的概念-像素 一个像素等于多少厘米？
  2. 计算机在屏幕上显示的内容是由屏幕上的每一个像素组成的。例如计算机显示器的分辨率是800×600，表示计算机屏幕上的每一行由800个点组成，共有600行，整个计算机屏幕由480000个像素。像素是一个**密度单位**，而厘米是**长度单位**，两者无法比较

## 绘图入门和机制

- 绘图原理
  1. pain(Graphics g) 绘制组建的外观
  2. repaint()刷新组件的外观

当组件第一次在屏幕显示的时候，程序会自动的调用paint()方法来绘制组件

在以下情况paint()将会被调用：

1. 窗口最小化，再最大化
2. 窗口的大小发生变化
3. repaint方法被调用

思考题：如何证明上面的三种情况，会调用 paint()方法

入门案例

``` java
public class DrawCircle extends JFrame{ //JFrame对应窗口,可以理解成是一个画框
    //定义一个面板
//    private MyPenal mp = null;
    public static void main(String[] args) {
        new DrawCircle();
    }
    
    public DrawCircle(){
        //初始化面板
        MyPenal mp = new MyPenal();
        //把面板放入到窗口(画框)
        this.add(mp);
        //设置窗口的大小
        this.setSize(400,300);
        //可视化窗口
        this.setVisible(true);
        //当点击窗口的小×，程序完全退出.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
//定义一个面板类
class MyPenal extends JPanel{//继承JPanel类， 画图形，就在面板上画
    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("haha");
        //画⚪ 距离x的距离，距离y的距离，宽，长
        g.drawOval(10,10,100,100);
    }
}
```

## 绘图方法

- Graphics类

  Graphics类你可以理解就是画笔，为我们提供了各种绘制图形的方法

1. 画直线 drawLine(int x1,int y1,int x2,int y2)；
2. 画矩形边框 drawRect(int x, int y, int width, int height)；
3. 画椭圆边框 drawOval(int x, int y, int width, int height)；
4. 填充矩形 fillRect(int x, int y, int width, int height)；
5. 填充椭圆 fillOval(int x, int y, int width, int height)；
6. 画图片 drawImage(Image img, int x, int y, ..)；
7. 画字符串 drawString(String str, int x, int y)//写字；
8. 设置画笔的字体 setFont(Font font)；
9. 设置画笔的颜色 setColor(Color c)。

``` java
@SuppressWarnings({"all"})
public class DrawCircle extends JFrame { //JFrame对应窗口,可以理解成是一个画框
    //定义一个面板
    private MyPanel mp = null;
    public static void main(String[] args) {
        new DrawCircle();
        System.out.println("退出程序~");
    }
 
    public DrawCircle() {//构造器
        //初始化面板
        mp = new MyPanel();
        //把面板放入到窗口(画框)
        this.add(mp);
        //设置窗口的大小
        this.setSize(400, 300);
        //当点击窗口的小×，程序完全退出.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//可以显示
    }
}
 
//1.先定义一个MyPanel, 继承JPanel类， 画图形，就在面板上画
class MyPanel extends JPanel {

    //说明:
    //1. MyPanel 对象就是一个画板
    //2. Graphics g 把 g 理解成一支画笔
    //3. Graphics 提供了很多绘图的方法
    //Graphics g
    @Override
    public void paint(Graphics g) {//绘图方法
        super.paint(g);//调用父类的方法完成初始化.
        System.out.println("paint 方法被调用了~");
        //画出一个圆形.
        //g.drawOval(10, 10, 100, 100);
 
        //演示绘制不同的图形..
        //画直线 drawLine(int x1,int y1,int x2,int y2)
        //g.drawLine(10, 10, 100, 100);
        //画矩形边框 drawRect(int x, int y, int width, int height)
        //g.drawRect(10, 10, 100, 100);
        //画椭圆边框 drawOval(int x, int y, int width, int height)
        //填充矩形 fillRect(int x, int y, int width, int height)
        //设置画笔的颜色
//        g.setColor(Color.blue);
//        g.fillRect(10, 10, 100, 100);
 
        //填充椭圆 fillOval(int x, int y, int width, int height)
//        g.setColor(Color.red);
//        g.fillOval(10, 10, 100, 100);
 
        //画图片 drawImage(Image img, int x, int y, ..)
        //1. 获取图片资源, /bg.png 表示在该项目的根目录去获取 bg.png 图片资源
//        Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bg.png"));
//        g.drawImage(image, 10, 10, 175, 221, this);
        //画字符串 drawString(String str, int x, int y)//写字
        //给画笔设置颜色和字体
        g.setColor(Color.red);
        g.setFont(new Font("隶书", Font.BOLD, 50));
        //这里设置的 100， 100， 是 "北京你好"左下角
        g.drawString("北京你好", 100, 100);
        //设置画笔的字体 setFont(Font font)
        //设置画笔的颜色 setColor(Color c)
    }
}
```

## 小球移动案例

``` java
public class temp extends JFrame{
    public static void main(String[] args) {
        new temp();
    }
    public temp(){
        MyPanel mp = new MyPanel();
        this.add(mp);
        this.setSize(400,300);
        //窗口JFrame，对象可以监听键盘事件
        this.addKeyListener(mp);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MyPanel extends JPanel implements KeyListener {
    int x = 20;
    int y = 20;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.cyan);
        g.fillOval(x, y, 10, 10);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println((char)e.getKeyChar() + "被按下");
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            y++;
        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
            y--;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
```

## Java事件处理机制

- 基本说明

  Java事件处理是“委派事件模型”，当事件发生时，产生事件的对象，会把此“信息”传递给“事件的监听者”处理，这里所说的“信息”实际上就是java.awt.event事件类库里某个类创建的对象，把它称为“事件的对象”。

- 示意图

  <img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230105171718142.png" alt="image-20230105171718142" style="zoom:80%;" />



- 事件处理机制深入理解
  1. 前面我们提到几个重要的概念 事件源，事件，事件监听器我们下面来全面的介绍他们
  2. 事件源：事件源是一个产生事件的对象，比如按钮、窗口等
  3. 事件：事件就是承载事件源状态改变时的对象，比如当键盘事件、鼠标事件、窗口事件等等，会生成一个事件对象，该对象保存着当前事件很多信息，比如KeyEvent对象有含义被按下键的Code值。java.awt.event包和javax.swing.event包中定义了各种事件类型

- 事件监听器接口：
  1. 当事件源产生一个事件，可以传送给事件监听者处理
  2. 事件监听者实际上就是一个类，该类实现了某个事件监听器接口，比如前面我们案例中的MyPanle就是一个类，它实现了KeyListener接口，它就可以作为一个事件监听者，对接收到的事件进行处理
  3. 事件监听器接口有多种，不同的事件监听器接口可以监听不同的事件，一个类可以实现多个监听接口
  4. 这些接口在java.awt.event包和javax.swing.event包中定义。。列出了常用的事件监听器接口，查看jdk文档

 ## 绘制敌人坦克

1. 因为敌人的坦克，是在MyPanel上，所以我们的代码在MyPanel
2. 因为敌人的坦克，后面有自己特殊的属性和方法，可以单开一个EnemyTank
3. 敌人坦克数量多，可以放到集合 Vector，因为考虑多线程问题

## 坦克大战1.0

``` java
//TankGame01类
package tankGame;

import javax.swing.*;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame01 tankGame01 = new TankGame01();

    }
    public TankGame01(){
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
```

``` java
//MyPanel类
package tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener {
    MyTank myTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;

    public MyPanel() {
        myTank = new MyTank(100, 100, 0, 1);
        for (int i = 0; i < enemyTankSize; i++) {
            enemyTanks.add(new EnemyTank(100 * (i + 1), 0, 2, 1));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//默认填充黑色
        //画出自己的坦克
        drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        //遍历Vector，画出敌人的坦克
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
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
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
```

``` java
//Tank类
package tankGame;

public class Tank {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct = 0;//坦克方向 0：上 1：右 2：下 3：左
    private int speed = 1;

    public Tank(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
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

``` java
//MyTank类
package tankGame;

public class MyTank extends Tank{
    public MyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }
}
//EnemyTank类
package tankGame;

public class EnemyTank extends Tank{
    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }
}
```

