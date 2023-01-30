# IDEA快捷使用

## IDEA快捷键

1. 删除当前行  默认是 ctrl + Y 自己配置 ctrl + d
2. 复制当前行 自己配置 ctrl + alt + 向下光标
3. 补全代码 alt + /
4. 添加或取消注释 ctrl + /  另外一种 ctrl + shift + /
5. 导入该行需要的类 alt + enter
6. 快速格式化代码 ctrl + alt + l
7. 快速运行 ctrl + shift +f10
8. 生成构造器等 alt + insert （101键需要 alt + shift + 0)
9. 查看一个类的继承关系 ctrl + h
10. 将光标放在一个方法上，输入 ctrl + B（或者 ctrl + 鼠标左键），可以选择定位到哪个类的方法
11. 自动给变量分配变量名 在后面加.var（或者alt + enter)

## IDEA模板

在设置里的编辑器 ->实时模板可以查看和设置模板

# 包

## 包基本介绍

- 三大作用
  - 区分相同名字的类
  - 当类很多时可以更好地管理类
  - 控制访问范围
- 包基本语法

​		package com.hspedu；

​		说明：

​			1.package 关键字，表示打包

​			2.com.hspedu：表示包名

## 包的原理

- 包的本质是创建不同的文件夹来保存类文件

![image-20220809185057155](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220809185057155.png)

## 快速入门

![image-20220809191136303](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220809191136303.png)

可以使用import 来引入使用包，但是只能一个类名，不能引入多个。也可以在类名前加 com.包名.类名 来使用

## 包命名

![image-20220809232450246](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220809232450246.png)

## 常用的包

![image-20220809233021638](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220809233021638.png)

## 包的使用细节

![image-20220809233903399](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20220809233903399.png)

不建议使用*

案例：使用系统提供的Arrays 完成数组排序

``` java
import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        int[] a = {-1,10,3,-4,46,22};
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] +"\t");
        }
    }
}
```

注意事项和细节

- packa的作用是声明当前类所在的包，需要放在class的最上面，一个类中最多只有一句package
- import指令 放置在package的下面，在类定义前面，可以有多句且没有顺序的要求