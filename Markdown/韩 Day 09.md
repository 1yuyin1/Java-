# 零钱通

## 面向过程

//零钱明细

//收益入账

//		消费

//确认退出

//金额校验



``` java 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        boolean loop = true;
        int key;
        Scanner scanner = new Scanner(System.in);
        //完成零钱通明细  1.可以把收益入账和消费保存到数组 2.可以使用对象 3.使用String拼接
        String details = "-----------零钱通明细----------";
        //完成收益入账
        double balance = 0;
        double money = 0;
        Date date = null;//获取时间的对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用来格式化日期
        //完成消费
        String note;
        //确认退出功能
        String choice;
        //金额校验
        do {
            System.out.println("===========零钱通菜单==========");
            System.out.println("\t\t1 零钱通明细");
            System.out.println("\t\t2 收益入账");
            System.out.println("\t\t3 消费");
            System.out.println("\t\t4 退出");
            System.out.println("请选择(1-4)");
            System.out.println("=============================");
            key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.println(details);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("输入入账金额");
                    money = scanner.nextDouble();
                    if(money <0 ){
                        System.out.println("金额不正确");
                        break;
                    }
                    balance += money;
                    date = new Date();//获取时间
                    details +="\n收益入账\t+"+money +"\t" +sdf.format(date)+"\t" +balance;
                    break;
                case 3:
                    System.out.println("消费说明:");
                    note = scanner.next();
                    System.out.println("消费金额:");
                    money = scanner.nextDouble();
                    if(money < 0) {
                        System.out.println("金额不正确");
                        break;
                    }
                    if(money > balance) {
                        System.out.println("超出限额，无法支付");
                        break;
                    }
                    balance -= money;
                    date = new Date();//获取时间
                    details +="\n"+note +"\t-" +money +"\t" +sdf.format(date)+"\t" +balance;
                    break;
                case 4:
                    System.out.println("确认是否退出");
                    while(true) {
                        choice = scanner.next();
                        if( choice.equals("y") ||choice.equals("n")) {
                            break;
                        }
                    }
                    if(choice.equals("y")) {
                        loop = false;
                    }
                    break;
                default:
                    System.out.println("请输入正确的数字");
                    break;
            }
        }while(loop);

        System.out.println("\n已退出零钱通");
    }
}

```

## OOP

``` java 
public class app {
    public static void main(String[] args) {
        new oop().mainmenu();
    }
}

```



``` java 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class oop {
    boolean loop = true;
    int key;
    Scanner scanner = new Scanner(System.in);
    //完成零钱通明细  1.可以把收益入账和消费保存到数组 2.可以使用对象 3.使用String拼接
    String details = "-----------零钱通明细----------";
    //完成收益入账
    double balance = 0;
    double money = 0;
    Date date = null;//获取时间的对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用来格式化日期
    //完成消费
    String note;
    //确认退出功能
    String choice;

    public void mainmenu() {
        do {
            System.out.println("===========零钱通菜单==========");
            System.out.println("\t\t1 零钱通明细");
            System.out.println("\t\t2 收益入账");
            System.out.println("\t\t3 消费");
            System.out.println("\t\t4 退出");
            System.out.println("请选择(1-4)");
            System.out.println("=============================");
            key = scanner.nextInt();
            switch (key) {
                case 1:
                    this.details();
                    break;
                case 2:
                    this.income();
                    break;
                case 3:
                    this.pay();
                    break;
                case 4:
                    this.exit();
                    break;
                default:
                    System.out.println("请输入正确的数字");
                    break;
            }
        }while(loop);

        System.out.println("\n已退出零钱通");
    }
    public void details() {
        System.out.println(details);
        System.out.println();
    }
    public void income() {
        System.out.println("输入入账金额");
        money = scanner.nextDouble();
        if(money <0 ){
            System.out.println("金额不正确");
            return;
        }
        balance += money;
        date = new Date();//获取时间
        details +="\n收益入账\t+"+money +"\t" +sdf.format(date)+"\t" +balance;
    }
    public void pay() {
        System.out.println("消费说明:");
        note = scanner.next();
        System.out.println("消费金额:");
        money = scanner.nextDouble();
        if(money < 0) {
            System.out.println("金额不正确");
            return;
        }
        if(money > balance) {
            System.out.println("超出限额，无法支付");
            return;
        }
        balance -= money;
        date = new Date();//获取时间
        details +="\n"+note +"\t-" +money +"\t" +sdf.format(date)+"\t" +balance;
    }
    public void exit() {
        System.out.println("确认是否退出");
        while(true) {
            choice = scanner.next();
            if( choice.equals("y") ||choice.equals("n")) {
                break;
            }
        }
        if(choice.equals("y")) {
            loop = false;
        }
        }
    }
```

# 章节作业

## 作业一

![image-20220817151956317](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817151956317.png)



``` java 
public class Demo {
    public static void main(String[] args) {
        Person person = new Person("小明", 16, "学生");
        Person person1 = new Person("小黑", 46, "矿工");
        Person person2 = new Person("渣辉", 41, "演员");
        person.sort(person,person1,person2);
    }
}

class Person {
    private String name;
    private int age;
    private String job;

    public Person(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public void sort(Person... p) {
        Person temp = null;
        for (int i = 0; i < p.length - 1; i++) {
            for (int j = 0; j < p.length - 1 - i; j++) {
                if( p[j].getAge() > p[j+1].getAge() ) {
                    temp = p[j];
                    p[j] = p[j+1];
                    p[j+1] = temp;
                }
            }
        }
        for (int i = 0; i < p.length; i++) {
            System.out.println(p[i].getName() +"\t" + p[i].getAge() +"\t" +p[i].getJob());
        }
    }
}
```

## 作业二

![image-20220817152041888](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817152041888.png)



|          | 本类 | 同包 | 子类 | 不同包 |
| :------- | ---- | ---- | ---- | ------ |
| public   | √    | √    | √    | √      |
| proteced | √    | √    | √    | ×      |
| 默认     | √    | √    | ×    | ×      |
| private  | √    | ×    | ×    | ×      |

## 作业三

![image-20220817154122485](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817154122485.png)



``` java
public class Demo {
    public static void main(String[] args) {
        Prosfessor prosfessor = new Prosfessor("大华", 41, "教授", 1.3);
        prosfessor.introduce();
    }
}
class Teacher {
    private String name;
    private int age;
    private String post;
    private double salary;

    public Teacher(String name, int age, String post, double salary) {
        this.name = name;
        this.age = age;
        this.post = post;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void introduce() {
        System.out.println("姓名 " +this.getName() +"\t年龄 " +this.getAge() +"\t职称 " +this.post +"\t薪水 " +this.getSalary());
    }
}

class Prosfessor extends Teacher {
    public Prosfessor(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }

    @Override
    public void introduce() {
        System.out.println("这是教授的信息");
        super.introduce();
    }
}
class fuProsfessor extends Teacher {
    public fuProsfessor(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }

    @Override
    public void introduce() {
        System.out.println("这是副教授的信息");
        super.introduce();
    }
}
class Speescher extends Teacher {
    public Speescher(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }

    @Override
    public void introduce() {
        System.out.println("这是讲师的信息");
        super.introduce();
    }
}
```

## 作业七

![image-20220817163128928](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817163128928.png)



![image-20220817163108648](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817163108648.png)

# 房屋出租系统

## 设计

项目设计-程序框架图（分层模式）

![image-20220817175333833](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220817175333833.png)

## 房屋出租House类

- 完成House类

​		编号 房主  电话  地址  月租  状态


## 房屋出租菜单

化繁为简（一个一个功能逐步实现）

老师说明：实现功能的三部曲【明确完成功能->思路分析->代码实现】

功能说明：用户打开软件，可以看到主菜单，可以退出软件

静态方法不需要创建对象即可调用

## 已完成的系统

```java
package houseRent.domian;

public class House {
    //编号 房主  电话  地址  月租  状态
    private int id;
    private String name;
    private String phone;
    private String address;
    private int rent;
    private String state;

    public House(int id, String name, String phone, String address, int rent, String state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.rent = rent;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return  id +
                "\t" + name +
                "\t" + phone +
                "\t" + address +
                "\t" + rent +
                "\t" + state ;
    }
}
```



```java
package houseRent.service;

import houseRent.domian.House;

public class HouseService {
    private House[] houses;
    private int housenums = 1;
    private int idCounter = 1;
    public HouseService(int size) {
        houses = new House[size];
        //创建一个对象以做测试
        houses[0] = new House(1, "Jack", "13785485996", "海淀区", 4000, "未出租");
    }

    public House[] list() {
        return houses;
    }

    public boolean add (House newHouse) {
        if(housenums == houses.length) {
            System.out.println("房屋已满，不能再添加了");
            return false;
        }
        houses[housenums++] = newHouse;//将队尾的房屋信息更新，并增加房屋数量
        newHouse.setId(++idCounter);//更新id  因为House是引用类型，所以上一行传递的是地址，这里只需要更新newHouse的id即可
        return true;
    }
    public boolean del(int delId) {
        int index = -1;
        for (int i = 0; i < housenums; i++) {
            if(delId == houses[i].getId()) {
                index = i;//如果找到，用index记录编号
            }
        }
        if(index == -1) { //没有找到该房屋
            return false;
        }
        //开始删除房屋信息，通过向前移位，并把最后移位置空
        for (int i = index; i < housenums - 1; i++) {
            houses[i] = houses[i+1];
        }
        houses[--housenums] = null; //将最后一位置空，并将房屋数量减一
        return true;
    }
    public House find(int findId) {
        for (int i = 0; i < housenums; i++) {
            if(findId == houses[i].getId()){
                return houses[i];
            }
        }
        return null;
    }
}
```



```java
package houseRent.utils;

/**
   工具类的作用:
   处理各种情况的用户输入，并且能够按照程序员的需求，得到用户的控制台输入。
*/

import java.util.*;
/**

   
*/
public class Utility {
   //静态属性。。。
    private static Scanner scanner = new Scanner(System.in);

    
    /**
     * 功能：读取键盘输入的一个菜单选项，值：1——5的范围
     * @return 1——5
     */
   public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);//包含一个字符的字符串
            c = str.charAt(0);//将字符串转换成字符char类型
            if (c != '1' && c != '2' && 
                c != '3' && c != '4' && c != '5') {
                System.out.print("选择错误，请重新输入：");
            } else break;
        }
        return c;
    }

   /**
    * 功能：读取键盘输入的一个字符
    * @return 一个字符
    */
    public static char readChar() {
        String str = readKeyBoard(1, false);//就是一个字符
        return str.charAt(0);
    }
    /**
     * 功能：读取键盘输入的一个字符，如果直接按回车，则返回指定的默认值；否则返回输入的那个字符
     * @param defaultValue 指定的默认值
     * @return 默认值或输入的字符
     */
    
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);//要么是空字符串，要么是一个字符
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }
   
    /**
     * 功能：读取键盘输入的整型，长度小于2位
     * @return 整数
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, false);//一个整数，长度<=10位
            try {
                n = Integer.parseInt(str);//将字符串转换成整数
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }
    /**
     * 功能：读取键盘输入的 整数或默认值，如果直接回车，则返回默认值，否则返回输入的整数
     * @param defaultValue 指定的默认值
     * @return 整数或默认值
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, true);
            if (str.equals("")) {
                return defaultValue;
            }
         
         //异常处理...
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("数字输入错误，请重新输入：");
            }
        }
        return n;
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串
     * @param limit 限制的长度
     * @return 指定长度的字符串
     */

    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串或默认值，如果直接回车，返回默认值，否则返回字符串
     * @param limit 限制的长度
     * @param defaultValue 指定的默认值
     * @return 指定长度的字符串
     */
   
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("")? defaultValue : str;
    }


   /**
    * 功能：读取键盘输入的确认选项，Y或N
    * 将小的功能，封装到一个方法中.
    * @return Y或N
    */
    public static char readConfirmSelection() {
        char c;
        for (; ; ) {//无限循环
           //在这里，将接受到字符，转成了大写字母
           //y => Y n=>N
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("选择错误，请重新输入：");
            }
        }
        return c;
    }

    /**
     * 功能： 读取一个字符串
     * @param limit 读取的长度
     * @param blankReturn 如果为true ,表示 可以读空字符串。 
     *                   如果为false表示 不能读空字符串。
     *           
    * 如果输入为空，或者输入大于limit的长度，就会提示重新输入。
     * @return
     */
    private static String readKeyBoard(int limit, boolean blankReturn) {
        
      //定义了字符串
      String line = "";

      //scanner.hasNextLine() 判断有没有下一行
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();//读取这一行
           
         //如果line.length=0, 即用户没有输入任何内容，直接回车
         if (line.length() == 0) {
                if (blankReturn) return line;//如果blankReturn=true,可以返回空串
                else continue; //如果blankReturn=false,不接受空串，必须输入内容
            }

         //如果用户输入的内容大于了 limit，就提示重写输入  
         //如果用户如的内容 >0 <= limit ,我就接受
            if (line.length() < 1 || line.length() > limit) {
                System.out.print("输入长度（不能大于" + limit + "）错误，请重新输入：");
                continue;
            }
            break;
        }

        return line;
    }
}
```



```java
package houseRent.view;

import houseRent.domian.House;
import houseRent.service.HouseService;
import houseRent.utils.Utility;

public class houseMenu {
    private boolean loop = true;
    private char key;
    private HouseService houseservice  = new HouseService(10);

    //编写addHouse    接受输入的信息，创建新对象，调用service的add方法
    public void addHouse() {
        System.out.println("--------------------添加房屋--------------------");
        System.out.print("输入名字：");
        String name = Utility.readString(6);
        System.out.print("输入电话：");
        String phone = Utility.readString(12);
        System.out.print("输入地址：");
        String address = Utility.readString(10);
        System.out.print("输入月租：");
        int rent = Utility.readInt();
        System.out.print("输入状态：");
        String state = Utility.readString(3);
        House newhouse = new House(0, name, phone, address, rent, state);
        if(houseservice.add(newhouse)) {
            System.out.println("--------------------添加成功--------------------");
            System.out.println();
        }else {
            System.out.println("==================添加失败==================");
            System.out.println();
        }
    }

    //编写findHouse   接受输入的id，调用service的find方法
    public void findHouse() {
        System.out.println("==================查找房屋信息==================");
        System.out.println("请输入查找的房屋编号(-1退出)");
        int findId = Utility.readInt();
        if(findId == -1){
            System.out.println("=================放弃查找房屋信息=================");
            return;
        }
        House findHouse = houseservice.find(findId);
        if(findHouse != null) {
            System.out.println("编号\t房主\t\t电话\t\t\t地址\t\t月租\t\t状态");
            System.out.println(findHouse +"\n");
        }else {
            System.out.println("=================查找的房屋不存在=================\n");
        }
    }

    //编写delHouse    接受输入的id，调用service的del方法
    public void delHouse() {
        System.out.println("==================删除房屋信息==================");
        System.out.println("请输入待删除的房屋编号(-1退出)");
        int delId = Utility.readInt();
        if (delId == -1) {
            System.out.println("=================放弃删除房屋信息=================");
            return;
        }
        System.out.println("请确认是否删除(Y/N)");
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            if (houseservice.del(delId)) {
                System.out.println("=================删除房屋信息成功=================");
            } else System.out.println("===================编号错误====================\n");
        } else {
            System.out.println("=================放弃删除房屋信息=================");
        }
    }

    //编写更新房屋列表  接受输入的id，判断id的正确性，开始修改信息
    public void update() {
        System.out.println("==================修改房屋信息==================");
        System.out.println("请输入待修改的房屋编号(-1退出)");
        int updateId = Utility.readInt();
        if(updateId == -1) {
            System.out.println("=================放弃修改房屋信息=================");
            return;
        }
        //开始查找对象，以判断id是否正确
        House house = houseservice.find(updateId);
        if(house == null) {
            System.out.println("=================修改的房屋不存在=================\n");
            return;
        }
        //开始修改
        System.out.print("姓名(" + house.getName() +"):");
        String name = Utility.readString(6,"");
        if(!name.equals("")) {
            house.setName(name);
        }
        System.out.print("电话(" + house.getPhone() +"):");
        String phone = Utility.readString(12,"");
        if(!phone.equals("")) {
            house.setPhone(phone);
        }
        System.out.print("地址(" + house.getAddress() +"):");
        String address = Utility.readString(10,"");
        if(!address.equals("")) {
            house.setPhone(address);
        }
        System.out.print("租金(" + house.getRent() + "):");
        int rent = Utility.readInt(-1);
        if (rent != -1) {
            house.setRent(rent);
        }
        System.out.print("状态(" + house.getState() + "):");
        String state = Utility.readString(3, "");
        if (!"".equals(state)) {
            house.setState(state);
        }
        System.out.println("=============修改房屋信息成功============\n");
    }

    //编写查看房屋列表  打印表头，调用service的list方法，并接受list、循环打印
    public void housesList() {
        System.out.println("--------------------房屋列表--------------------");
        System.out.println("编号\t房主\t\t电话\t\t\t地址\t\t月租\t\t状态");
        House[] houses = houseservice.list();
        for (int i = 0; i < houses.length ; i++) {
            if (houses[i] == null) {//如果空（没信息），不进行输出
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println();
    }

    //编写退出  接受输入的确认符号，判断是否退出
    public void exit() {
        System.out.println("请确认是否退出(Y/N)");
        char choice = Utility.readConfirmSelection();
        if(choice == 'Y') {
            loop = false;
        }
    }

    public void mainMenu() {
        do {
            System.out.println("==================房屋出租系统==================");
            System.out.println("\t\t\t1 新 增 房 源");
            System.out.println("\t\t\t2 查 找 房 源");
            System.out.println("\t\t\t3 删 除 房 源");
            System.out.println("\t\t\t4 修 改 房 屋 信 息");
            System.out.println("\t\t\t5 房 源 列 表");
            System.out.println("\t\t\t6 退      出");
            System.out.println("请输入选择(1-6)");
            System.out.println("=============================================");
            key = Utility.readChar();
            switch (key) {
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    update();
                    break;
                case '5':
                    housesList();
                    break;
                case '6':
                    exit();
                    break;
                default:
                    System.out.println("输入错误");
                    break;
            }
        }while(loop);
    }
}
```



```java
package houseRent.view;

public class houseRentApp {
    public static void main(String[] args) {
        new houseMenu().mainMenu();
        System.out.println("\n房屋出租系统已退出");
    }
}
```
