## 成员方法传参机制

基本数据类型，传递的是值，形参的任何改变不影响实参

引用类型传递的是地址，可以通过形参影响实参

## 克隆对象

编写一个方法，可以复制一个Person对象，返回复制的对象。克隆对象，主义要求得到新对象和原来的对象是两个独立的对象，只是它们的属性相同

``` java
public class Demo {
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "小白";
        p1.age=10;
        System.out.println("调用前的信息" +"\t" +p1.name +"\t" + p1.age +"\t" +"地址" +"\t" +p1);
        p1.kelong(p1);
        Person p2 = p1.kelong(p1);
        System.out.println("调用前的信息" +"\t" + p2.name +"\t" + p2.age +"\t" +"地址" +"\t" +p2);
    }
}

class  Person {
    String name;
    int age;
    public Person kelong(Person p) {
        String name = p.name;
        int age = p.age;
        p = new Person();
        p.name = name;
        p.age = age;
        return p;
    }
}
```

# 算法练习
## 递归斐波那契数列

``` java
import java.util.Scanner;
public class Demo {
    public static void main(String[] args) {
        int n;
        int sum;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        caul caul = new caul();
        sum = caul.sl(n);
        System.out.println(sum);
        scanner.close();
        }
}

class  caul {
    public int sl(int n) {
        int sum = 0;
        if(n < 3) {
            sum = 1;
        }
        else if(n >= 3) {
            sum = sl(n-1) + sl(n-2);
        }
        return sum;
    }
}

```

## 猴子吃桃

```java
import java.util.Scanner;
public class Demo {
    public static void main(String[] args) {
        int n;
        int sum;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
         T T = new T();
        sum = T.caul(n);
        System.out.print("第"+ n+"天有");
        System.out.println("\t" +sum+"个");
    }
}
class T {
    public int caul(int n) {
        if (n == 10) {
            return 1;
        } else if ( n >= 1 && n <= 9) {
            return (caul(n+1) + 1) * 2;
        } else {
            System.out.println("错误");
            return -1;
        }
    }
}
```

  通过逆推，第10天有1个，第n天则通过下一天来计算，如第八天要将第九天的先加一再乘二（即数量关系为第八天是第九天加一的二倍)，一直到第十天结束。

## 老鼠迷宫

```java
public class Demo {
    public static void main(String[] args) {
        // 先创建迷宫,元素是0代表可以走，为1是障碍物
        int[][] map = new int[8][8];
        for (int i = 0; i < 8; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][7] = 1;
        }
        map[3][1] = map [2][6] = 1;
        // 输出当前迷宫
        System.out.println("===当前地图情况===");
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        T t1 = new T();
        t1.findWay(map, 1, 1); // 引用传递，会影响main方法中的数组
        System.out.println("\n===找路后===");
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0 ; j < map[i].length; ++j) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class T {
    public boolean findWay(int[][] map, int i, int j) {
        // 0 可以走     // 1 障碍物    // 2 是通路   // 3 是死路
        if (map[6][6] == 2) { // 终点通了，成功
            return true;
        } else { // 继续找
            if (map[i][j] == 0) { // 0是可以走，但还没走过
                // 假定可以走通
                map[i][j] = 2;
                // 下，右，上，左
                if (findWay(map, i + 1, j)) { // 下
                    return true;
                } else if (findWay(map, i, j + 1)) { // 右
                    return true;
                } else if (findWay(map, i - 1, j)) { // 上
                    return true;
                } else if (findWay(map, i, j - 1)) { // 左
                    return true;
                } else { // 假定失败，是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { // 1是障碍物走不了，2是通路已经测试过了，3是死路
                return false;
            }
        }
    }
}
```

## 汉诺塔

```java
public class Demo {
    public static void main(String[] args) {
        T T = new T();
        T.move(3,'A', 'B','C');
    }
}

class T {
    public void move(int num,char a,char b,char c) {
        if(num == 1) {//如果只有一个盘子，直接从a塔移到c塔
            System.out.println(a+ "->"+ c);
        }
        else {
            move(num-1, a, c, b);//先将a塔中除了最底下的盘子，借助c塔，移到b塔
            System.out.println(a+ "->"+ c);//将a塔最下面的盘子，移到c塔    将一个盘子移动，直接打印即可，
            //上一句也可以表示为            move(1, a, b, c);
            move(num-1,b,a,c);//最后将b塔中盘子，借助a塔，移到c塔
        }
    }
}
```

将问题简化，这道题首先将所有盘子看作两部分，先将上面的移到b，再将最下面一个移到c，最后将上面的从b移到c即可。

并且加上条件判断

## 八皇后问题

```java
public class Demo {
    int [] map = new int[8];
    static int count = 0;
    public static void main(String[] args) {
        Demo Demo = new Demo();
        Demo.run(0);
        System.out.println("==============================================");
        System.out.println("共有\t" + count +"\t种解法");
    }
    public void run( int x) {
        if(x == 8){
            count ++;
            print();
            return;
        }
        for (int i = 0; i < 8; i++) {
            map[x] = i;
            if(check(x)) {
                run(x+1);
            }
        }
    }
    public boolean check(int n){
        for (int i = 0; i < n; i++) {
            if(map[n] == map [i] || Math.abs(n-i) == Math.abs(map[n]-map[i])) {
                return false;
            }
        }
        return true;
    }
    public void print() {
        for (int i = 0; i < map.length; i++) {
            System.out.print(map[i] + " ");
        }
        System.out.println();
    }
}
```

调用的方法总共分为三部分。map数组的下标表示行，值表示列，以替代二维数组。

1. 作为主体的run，参数为行数，通过一个循环赋值，然后如果检查通过，则进行下一行，如果不通过返回false，则需要继续赋值。

如果到了最后一行，则计数加一，并回溯。

2. 作为检查的check，首先判断该列（即所赋给的值）是否等于（通过循环，与小于该行数的行判断）之前的列数（即值），再判断对角线，首先计算出行数的绝对差值再与列数的绝对差值比较是否相等。 两项都通过则直接返回false。方法的最后再返回true。
3. 第三个方法为打印数组。





