# 基本数据类型自动转化

1. 数据类型精度排序

![image-20220802142343478](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220802142343478.png)

2. 在Java中先转化为double，之后转换为float精度错误，所以写法错误。在c中无错误。并且在Java中float默认小数点后一位，c中默认六位。

``` java
int a=10;
float b=a+1.1;
```

​      解决方法

``` java
int a=10;
double b=a+1.1;
//或
int a=10;
float b=a+1.1F;
```

3. byte,short和char 之间不会转化

4. byte，short，char三者可以计算，在计算时首先转化为int
5. boolean 不参与转换
6. 自动提升原则：表达式结果的类型自动提升为操作数中最大的类型



# 强制类型转换

可能造成精度降低或溢出

1. 当数据的大小从大-->小就需要强制转换
2. 强制符号只针对于最近的操作有效
3. char类型可以保存int的常量值，但不能保存int的变量值，需要强转
4. byte和short类型在运算时，当作int类型处理

# 原码、反码、补码

对于有符号的而言：

1. 二进制的最高位是符号位:0表示正数，1表示负数
2. 正数的原码，反码，补码都一样
3. 负数的反码=它的符号位不变，其它位取反
4. 负数的补码=它的反码+1，负数的反码=它的补码-1
5. 0的反码，补码都是0
6. Java没有无符号数，换言之，Java中的数都是有符号的
7. 在计算机运行中，都是以补码的方式进行运算
8. 当我们看运算结果时，要看它的原码

# 数组扩容

![image-20220802185716867](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220802185716867.png)

``` java
 int[] arry = {1,2,3};
        int[] arryNew = new int[arry.length+1];
        for(int i = 0; i< arry.length; i++) {
            arryNew[i]=arry[i];
        }
        arryNew[arryNew.length-1]=4;
        arry = arryNew;
        for(int i = 0; i<arry.length; i++){
            System.out.print(arry[i]+" ");
        }
```

# 顺序查找练习

``` java
import java.util.Scanner;
public class Demo {
    public static void main(String[] args) {
        int flag = 0;
        String[] names={"白眉鹰王","金毛狮王","紫衫龙王","青蝠翼王"};
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入名称");
        String find=scanner.nextLine();
        for(int i = 0;i < names.length; i++) {
            if(find.equals(names[i])) {
                System.out.println("已找到大王  位置为: "+(i+1));
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
            System.out.println("没有找到大王 "+find);
        }
        scanner.close();
    }
}
```



# 数组使用注意事项

![image-20220802220727123](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220802220727123.png)



可以使用

``` java
int x[]= new int[10];
```

来创造含10个元素的数组，且元素默认为0

# 小试验

<img src="C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220802220243239.png" alt="image-20220802220243239"  />

用一个 Scanner scanner=new Scanner(System.in);      可以给多个变量输入
