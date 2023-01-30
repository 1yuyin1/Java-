## 线程同步机制

- 先看一个问题

  以前面的窗口卖票为例看问题

- 线程同步机制
  1. 在多线程编程，一些敏感数据不允许被多个线程同时访问，此时就使用同步访问技术，保证数据在任何同一时刻，最多有一个线程访问，以保证数据的完整性
  2. 也可以理解为：线程同步，即当有一个线程在对内存进行操作时，其他线程都不可以对这个内存地址进行操作，直到该线程完成操作，其他线程才能对该内存地址进行操作。

- 同步具体方法-Synchronized

  1. 同步代码块

     synchronized（对象） { //得到对象的锁，才能操作同步代码

     ​		//需要被同步代码;

     }

  2. synchronized还可以放在方法声明中，表示整个方法为同步方法

     public synchronized void m (String name) {

     ​						//需要被同步的代码

     }

  3. 如何理解：

     就好像某小伙伴上厕所前先把们关上（上锁），完事后再出来（解锁），那么其它小伙伴就可在使用厕所了

  4. 使用synchronized解决售票问题

``` java
public class Practice02{
    public static void main(String[] args) {
 
//        System.out.println("===使用继承类方式来售票=====");
//        SellTicket03 sellTicket1 = new SellTicket03();
//        SellTicket03 sellTicket2 = new SellTicket03();
//        SellTicket03 sellTicket3 = new SellTicket03();
//
//        sellTicket1.start();//启动售票线程
//        sellTicket2.start();//启动售票线程
//        sellTicket3.start();//启动售票线程
 
        System.out.println("===使用实现接口方式来售票=====");
        SellTicket04 sellTicket02 = new SellTicket04();
 
        new Thread(sellTicket02).start();//第1个线程-窗口
        new Thread(sellTicket02).start();//第2个线程-窗口
        new Thread(sellTicket02).start();//第3个线程-窗口
 
    }
}
 
//使用Thread方式
class SellTicket03 extends Thread {
    private static int ticketNum = 100;//让多个线程共享 ticketNum
    private static boolean loop = true;//控制run方法的变量
    public static synchronized void sell(){//同步方法，同一时刻，只能有一个线程执行该方法
        if (ticketNum <= 0) {
            System.out.println("售票结束...");
            loop = false;
            return;
        }
 
        //休眠50毫秒, 模拟
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        System.out.println("窗口 " + Thread.currentThread().getName() + " 售出一张票"
                + " 剩余票数=" + (--ticketNum));
    }
    @Override
    public void  run() {
        while (loop) {
 
            sell();
 
        }
    }
}
//实现接口方式
class SellTicket04 implements Runnable {
    private int ticketNum = 100;//让多个线程共享 ticketNum
    private boolean loop = true;
    public synchronized void sell(){
        if (ticketNum <= 0) {
            System.out.println("售票结束...");
            loop = false;
            return;
        }
 
        //休眠50毫秒, 模拟
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        System.out.println("窗口 " + Thread.currentThread().getName() + " 售出一张票"
                + " 剩余票数=" + (--ticketNum));//1 - 0 - -1
    }
    @Override
    public void run() {
        while (loop) {
            sell();
        }
    }
}
```

## 互斥锁

- 基本介绍
  1. Java语言中，引入了对象互斥锁的概念，来保证共享数据操作的完整性
  2. 每个对象都对应于一个可称为”互斥锁“的标记，这个标记用来保证在任一时刻，只能有一个线程访问该对象
  3. 关键字synchronized来与对象的互斥锁联系。当某个对象用synchronized修饰时，表示该对象在任一时刻只能由一个线程访问
  4. 同步的局限性：导致程序的执行效率要降低
  5. 同步（非静态的）锁可以是this，也可以是其他对象（要求是同一个对象）
  6. 同步方法（静态）的锁为当前类本身

- 注意事项和细节
  1. 同步方法如果没有使用static修饰：默认锁对象为this
  2. 如果方法使用static修饰，默认锁对象：当前类.class
  3. 实现的落地步骤：
     - 需要先分析上锁的代码
     - 选择同步代码块或同步方法
     - 要求多个线程的锁对象为同一个即可

## 线程死锁

- 基本介绍

  多个线程都占用了对方的锁资源，但不肯相让，导致了死锁，在编程是一定要避免死锁的发生

- 应用案例

  妈妈： 你先完成作业，才让你玩手机

  小明：你先让我玩手机，我才完成作业

案例说明

``` java
public class DeadLock_ {
    public static void main(String[] args) {
        //模拟死锁现象
        DeadLockDemo A = new DeadLockDemo(true);
        A.setName("A线程");
        DeadLockDemo B = new DeadLockDemo(false);
        B.setName("B线程");
        A.start();
        B.start();
    }
}
 
//线程
class DeadLockDemo extends Thread {
    static Object o1 = new Object();// 保证多线程，共享一个对象,这里使用static
    static Object o2 = new Object();
    boolean flag;
 
    public DeadLockDemo(boolean flag) {//构造器
        this.flag = flag;
    }
 
    @Override
    public void run() {
        //下面业务逻辑的分析
        //1. 如果flag 为 T, 线程A 就会先得到/持有 o1 对象锁, 然后尝试去获取 o2 对象锁
        //2. 如果线程A 得不到 o2 对象锁，就会Blocked
        //3. 如果flag 为 F, 线程B 就会先得到/持有 o2 对象锁, 然后尝试去获取 o1 对象锁
        //4. 如果线程B 得不到 o1 对象锁，就会Blocked
        if (flag) {
            synchronized (o1) {//对象互斥锁, 下面就是同步代码
                System.out.println(Thread.currentThread().getName() + " 进入1");
                synchronized (o2) { // 这里获得li对象的监视权
                    System.out.println(Thread.currentThread().getName() + " 进入2");
                }
                
            }
        } else {
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + " 进入3");
                synchronized (o1) { // 这里获得li对象的监视权
                    System.out.println(Thread.currentThread().getName() + " 进入4");
                }
            }
        }
    }
}
```

## 释放锁

- 下面操作会释放锁

  1. 当前线程的同步方法、同步代码块执行结束

     案例：上厕所，完事出来

  2. 当前线程在同步代码块、同步方法中遇到break、return

     案例：当前没有正常的完事，经理叫他修改bug，不得已出来

  3. 当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导致异常结束

     案例：没有正常的完事，发现忘带纸，不得已出来

  4. 当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线程暂停，并释放锁

     案例：没有正常完事，觉得需要酝酿下，所以出来等会再进去

- 下面操作不会释放锁

  1. 线程执行同步代码块或同步方法时，线程调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行，不会释放锁

     案例：上厕所，太困了，在坑位上眯了一会

  2. 线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起，该线程不会释放锁

     提示：尽量避免使用suspend()和resume()来控制线程，方法不再推荐使用

## 作业

- 第一题
  1. 在main方法中启动两个线程
  2. 第一个线程循环随机打印100以内的整数
  3. 直到第2个线程从键盘读取了“Q”命令
- 第二题
  1. 有2个用户分别从同一个卡上取钱（总额：10000）
  2. 每次都取1000，当余额不足时，就不能取款了
  3. 不能出现超取现象

# 坦克大战2

分析如何实现当用户按下J键，我们的坦克就发射一颗子弹

思路：

1. 当发射一颗子弹后，就相当于启动一个线程
2. Hero有子弹的对象，当按下J时，我们就启动一个发射行为（线程），让子弹不停的移动，形成一个射击的效果
3. 我们MyPanel需要不停的重绘子弹，才能出现该效果
4. 当子弹移动到面板的边界时，就应该销毁（把启动的子弹的线程销毁）