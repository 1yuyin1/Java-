# 多线程

## 线程相关概念

- 程序

  是为完成特定任务、用某种语言编写的一组指令的集合

  简单的来说：就是我们写的代码

- 进程

  1. 进程是指运行中的程序，比如我们使用QQ就启动了一个进程，操作系统就会为该进程分配内存空间。当我们使用迅雷，又启动了一个进程，操作系统就为迅雷分配新的内存空间。
  2. 进程是程序的一次执行过程，或是正在运行的一个程序。是动态过程： 它有自身的产生、存在和消亡的过程

- 什么是线程

  1. 线程由进程创建的，是进程的一个实体

  2. 一个进程可以有多个线程

- 其它相关概念

  单线程：同一个时刻，只允许执行一个线程

  多线程：同一个时刻，可以执行多个线程

  并发：同一个时刻，多个任务交替执行，造成一种“貌似同时”的错觉，简单的来说，单核cpu实现的多任务就是并发

  并行：同一个时刻，多个任务同时执行。多核cpu可以实现并行。

## 继承Thread创建线程

- 创建线程的两种方式
  1. 继承Thread类，重写run方法
  2. 实现Runnabel接口，重写run方法

案例1：

1. 请编写一个程序，开启一个线程，该线程每隔1秒。在控制台输出“喵喵，我是小猫咪”

2. 请对上题改进，当输出80次后，结束该线程

3. 使用JConsle监控线程执行情况，并画出程序示意图

   ``` java
   public class Demo {
       public static void main(String[] args) throws InterruptedException{
           //创建Cat对象，可以当作线程使用
           Cat cat = new Cat();
           cat.start();
           //cat.run();run方法就是一个普通的方法，没有真正的启动一个线程，就会把run方法执行完毕，才向下执行
           //说明：当main线程启动一个子线程后，主线程不会阻塞，会继续执行
           //这时，主线程和子线程交替执行
           System.out.println("程序继续执行" + Thread.currentThread().getName());
           for(int i = 0;i < 10;i++) {
               System.out.println("主线程" + i);
               Thread.sleep(1000);
           }
       }
   }
   
   //老韩说明：
   //1. 当一个类继承了 Thread类，该类就可以当作线程使用
   //2. 我们会重写run方法，写上自己的业务代码
   //run Thread 类  实现了Runnable 接口的run方法
   /*
       @Override
       public void run() {
           if (target != null) {
               target.run();
           }
       }
    */
   class Cat extends Thread {
       int times = 0;
   
       @Override
       public void run() {
           while (true) {
               System.out.println("喵喵，我是小猫咪" + Thread.currentThread().getName());
               times++;
               //让程序休眠1秒
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               if (times == 8) break;
           }
       }
   }
   ```

   ![image-20230107153351733](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230107153351733.png)

## 为很么调用的是start方法

1. 当我们调用start方法时，系统会进入

   ``` java
   public synchronized void start() {
           group.add(this);
           boolean started = false;
           try {
               start0();
               started = true;
           } finally {
               try {
                   if (!started) {
                       group.threadStartFailed(this);
                   }
               } catch (Throwable ignore) {
                   /* do nothing. If start0 threw a Throwable then
                     it will be passed up the call stack */
               }
           }
       }
   ```

2. 接着***\*public synchronized void start() {}\**** 这个方法会调用其中的核心方法**start0();**

   **start0()是一个本地方法，由JVM调用，底层由c/c++实现**

   **真正实现多线程效果的是start0()方法,而不是run()方法**

   调用start0()方法后，该线程并不一定会立马执行，只是将线程变成了可运行状态。具体什么时候执行，取决于CPU，由CPU统一调度

3. 最后再在start0()方法中调用run()方法

   ``` java
   private native void start0();
       @Override
       public void run() {
           if (target != null) {
               target.run();
           }
       }
   ```

## Runnable创建线程

- 线程应用案例二-实现Runnable接口

  - 说明：

    1. Java是单继承的，在某些情况下一个类可能已经继承了某个父类，这时再用继承Thread类方法来创建线程显然不可能了
    2. Java设计者们提供了另外一个方式创建线程，就是通过实现Runnable接口来创建线程

  - 应用案例

    1. 请编写程序，该程序可以每隔1秒。在控制台输出“Hi”，当输出10此后，自动退出。请使用实现Runnable接口的方法实现。这里是**静态代理**

    ``` java
    public class Thread02 {
        public static void main(String[] args) {
            Dog dog=new Dog();
            //dog.start();这里不能使用start
            //创建Thread对象，把dog对象（实现Runnable接口）,放入Thread
            Thread thread=new Thread(dog);
            thread.start();
        }
    }
    class Dog implements Runnable{//通过实现Runnable接口，开启线程
        int count=0;
     
        @Override
        public void run() {
            while (true){
                System.out.println("hi"+(++count)+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(count==10){
                    break;
                }
            }
        }
    }
    ```

## 多个子线程案例

请编写一个程序，创建两个线程，一个线程每隔1秒输出“helloworld”，输出10次，退出。一个线程每隔1秒输出”hi“输出5次退出。 

``` java
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new one());
        Thread thread1 = new Thread(new two());
        thread.start();
        thread1.start();
    }
}

class one implements Runnable {
    int count = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println("helloworld  " + Thread.currentThread().getName() + "    " + count);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 10)
                break;
        }

    }
}

class two implements Runnable {
    int count = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println("hi  " + Thread.currentThread().getName() + "    " + count);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5)
                break;
        }
    }
}
```

## 继承Thread和实现Runnable的区别

1. 从Java的设计来看，通过继承Thread或者实现Runnable接口来创建线程本质上没有区别，从jdk帮助文档我们可以看到Thread类本身就实现了Runable接口
2. 实现Runnable接口方式更加适合多个线程共享一个资源的情况，并且避免了单继承的限制

## 线程终止

- 基本说明

  1. 当线程完成任务后，会自动退出

  2. 还可以通过使用变量来控制run方法退出的方式停止线程，即通知方式

     ``` java
     public class Demo {
         public static void main(String[] args) throws InterruptedException {
             T t = new T();
             Thread thread = new Thread(t);
             thread.start();
             Thread.sleep(10 * 1000);
             t.setLoop(false);
         }
     }
     
     class T implements Runnable {
         int count = 0;
         private boolean loop = true;
     
         @Override
         public void run() {
             while (loop) {
                 System.out.println("helloworld  " + Thread.currentThread().getName() + "    " + count);
                 count++;
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }
     
         public void setLoop(boolean loop) {
             this.loop = loop;
         }
     }
     ```

## 线程中断

- 线程常用方法
  1. setName() //设置线程名称
  2. getNmae() //返回线程名称
  3. start() //使该线程开始执行，Java虚拟机底层调用该线程的start0方法
  4. run() //调用线程对象的run方法
  5. setPriority() //更改线程的优先级
  6. getPriority() //获取线程的优先级
  7. sleep() //在指定的毫秒数让当前正在执行的线程休眠
  8. interrupt() //中断线程，不是结束线程，一般用于中断正在休眠的线程，使其继续执行
- 注意事项和细节
  1. start底层会创建新的线程，调用run，run就是一个简单的方法调用，不会启动新线程
  2. 线程优先级的范围
  3. interrupt，中断线程，但并没有真正的结束线程。所以一般用于中断正在休眠的线程
  4. sleep：线程的静态方法，使当前线程休眠

``` java
public class ThreadMethod01 {
    public static void main(String[] args) throws InterruptedException {
        //测试相关的方法
        T t = new T();
        t.setName("老韩");
        t.setPriority(Thread.MIN_PRIORITY);//1
        t.start();//启动子线程
 
        //主线程打印5 hi ,然后我就中断 子线程的休眠
        for(int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println("hi " + i);
        }
 
        System.out.println(t.getName() + " 线程的优先级 =" + t.getPriority());//1
        t.interrupt();//当执行到这里，就会中断 t线程的休眠.
 
    }
}
 
class T extends Thread { //自定义的线程类
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 100; i++) {
                //Thread.currentThread().getName() 获取当前线程的名称
                System.out.println(Thread.currentThread().getName() + "  吃包子~~~~" + i);
            }
            try {
                System.out.println(Thread.currentThread().getName() + " 休眠中~~~");
                Thread.sleep(20000);//20秒
            } catch (InterruptedException e) {
                //当该线程执行到一个interrupt 方法时，就会catch 一个 异常, 可以加入自己的业务代码
                //InterruptedException 是捕获到一个中断异常.
                System.out.println(Thread.currentThread().getName() + "被 interrupt了");
            }
        }
    }
}
```

## 线程插队

- 常用方法第二组
  1. yield：线程的礼让。让出cpu，让其他线程执行，但礼让的时间不确定，所以也不一定礼让成功(静态方法)
  2. join：线程的插队，插队的线程一旦插队成功，则肯定先执行完插入的线程的所有任务

案例：创建一个子线程，每隔1秒输出hello，输出20次，主线程每隔1秒，输出hi，输出20次。要求：看i昂贵线程同时执行，当主线程输出5次后，就让子线程运行完毕，主线程再继续

``` java
public class ThreadMethod02 {
    public static void main(String[] args) throws InterruptedException {
 
        T2 t2 = new T2();
        t2.start();
        for(int i = 1; i <= 20; i++) {
            Thread.sleep(1000);
            System.out.println("主线程(小弟) 吃了 " + i  + " 包子");
            if(i == 5) {
                System.out.println("主线程(小弟) 让 子线程(老大) 先吃");
                //join, 线程插队
                //t2.join();// 这里相当于让t2 线程先执行完毕
                Thread.yield();//礼让，不一定成功..
                System.out.println("线程(老大) 吃完了 主线程(小弟) 接着吃..");
            }
        }
    }
}
 
class T2 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            try {
                Thread.sleep(1000);//休眠1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程(老大) 吃了 " + i +  " 包子");
        }
    }
}
```

## 守护线程

​																									线程常用方法

- 用户线程和守护线程
  1. 用户线程：也叫工作线程，当线程的任务执行完或通知方式结束
  2. 守护线程：一般是为工作线程服务的，当所有的用户线程结束，守护线程自动结束
  3. 常见的守护线程：垃圾回收机制

``` java
public class ThreadMethod03 {
    public static void main(String[] args) throws InterruptedException {
        MyDaemonThread myDaemonThread = new MyDaemonThread();
        //如果我们希望当main线程结束后，子线程自动结束
        //,只需将子线程设为守护线程即可
        myDaemonThread.setDaemon(true);
        myDaemonThread.start();

        for( int i = 1; i <= 10; i++) {//main线程
            System.out.println("宝强在辛苦的工作...");
            Thread.sleep(1000);
        }
    }
}
 
class MyDaemonThread extends Thread {
    public void run() {
        for (; ; ) {//无限循环
            try {
                Thread.sleep(1000);//休眠1000毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("马蓉和宋喆快乐聊天，哈哈哈~~~");
        }
    }
}
```

## 线程七大状态

- JDK中用Thread.State枚举表示了线程的几种状态

  ![image-20230107225504777](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230107225504777.png)

线程的生命周期

- 线程转换图

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230107230113505.png" alt="image-20230107230113505" style="zoom: 67%;" />

- 写程序查看线程状态

  ``` java
  public class ThreadState_ {
      public static void main(String[] args) throws InterruptedException {
          T3 t = new T3();
          System.out.println(t.getName() + " 状态 " + t.getState());
          t.start();
   
          while (Thread.State.TERMINATED != t.getState()) {
              System.out.println(t.getName() + " 状态 " + t.getState());
              Thread.sleep(500);
          }
          System.out.println(t.getName() + " 状态 " + t.getState());
      }
  }
  class T3 extends Thread {
      @Override
      public void run() {
          for (int i = 0; i < 10; i++) {
              System.out.println("hi " + i);
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }
  ```

  