## Mysql三层结构

1. 所谓安装Mysql数据库，就是在主机安装一个数据库管理系统（DBMS），这个管理程序可以管理多个数据库

2. 一个数据库可以创建多个表，以保存数据

3. 数据库管理系统、数据库和表的关系如图所示：

   ![image-20230128174652210](C:\Users\yu'yin\AppData\Roaming\Typora\typora-user-images\image-20230128174652210.png)

 数据在数据库中以行（row）和列（column）存储。表的一行称之为一条记录 -> 在Java程序中，一行记录往往使用对象



- SQL语句分类
  - DDLL:数据定义语句 【create 表，库...】
  - DML:数据操作语句 【增加 insert，修改 update， 删除 delete】
  - DQL: 数据查询语句 【select】
  - DCL:数据控制语句 【管理数据库：比如用户权限 grant revoke】

## Java 操作Mysql

体会通过Java 创建 Mysql

1. 创建一个商品表，选用适当的数据类型

2. 添加两条数据

3. 删除表

   ``` java
   import org.junit.Test;
   
   import java.sql.*;
   
   public class JavaMysql {
       @Test
       public void JavaMysqlTest() throws ClassNotFoundException, SQLException {
           Class.forName("com.mysql.jdbc.Driver");
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db02", "root", "wty");
           //String sql = "create table goods ( id int, name varchar(32), price double, introduce text)" ;
           //String sql = "insert into goods values(1, '华为手机', 2000, '这是不错的一款手机')" ;
           String sql = "drop table goods" ;
   
           //得到statement对象，把sql 语法发送给mysql执行
           Statement statement = connection.createStatement();
           statement.executeUpdate(sql);
   
           //关闭连接
           statement.close();
           connection.close();
           System.out.println("成功~");
   
       }
   }
   ```

   

## 创建数据库

1. CHARACTER SET: 指定数据库采用的字符集，如果不指定字符集，默认utf8
2. COLLATE：指定数据库字符集的校对规则（常用的utf8_bin【区分大小写】、utf8_general_ci 注意默认是utf8_general_ci ）

```sql
#创建数据库
# 1.创建一个名称为db_01的数据库(图形化+指令)
#使用指令创建和删除数据库
DROP DATABASE db_01

# 这里什么也不写，字符集默认是utf-8 数据库排序规则是utf-8 bin 区分大小写
CREATE DATABASE db_01

# 2.创建一个utf-8字符集的db_02数据库
CREATE DATABASE db_02 CHARACTER SET utf8;
# 3.创建一个使用utf-8字符集，并带校对规则的db_03数据库
CREATE DATABASE db_03 CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE db_04 CHARACTER SET utf8 COLLATE utf8_bin;
#utf8_general_ci 不区分大小写，可以查询出来2个
SELECT * FROM db_03.t2 WHERE NAME = 'tom'
#utf8_bin 区分大小写只能查询出来1个
SELECT * FROM db_04.t1 WHERE NAME = 'tom'
```

## 查询数据库

显示数据库语句： SHOW DATABASES

显示数据库创建语句： SHOW CREATE DATABASE 名字

数据库删除语句【慎用】：DROP DATABASE [IF EXISTS] 名字

``` sql
#演示删除和查询数据库
#查看当前数据库服务器中的所有数据库
SHOW DATABASES;

#查看前面创建的数据库的定义信息
# 在创建数据库、表的时候，为了规避关键字可以用``解决
SHOW CREATE DATABASE db_01
# 反引号的引用
CREATE DATABASE `CREATE`

#删除前面创建的数据库(慎用)
DROP DATABASE `CREATE`
```

## 备份恢复数据库

- 备份数据库：（注意：在dos执行）

  mysqldump -u 用户名 -p -B 数据库1 数据库2 数据库n > 文件名.sql

- 恢复数据库（注意：进入Mysql命令行再执行）

  Source 文件名.sql

- 进入Mysql

  mysql -u 用户名 -p

## 创建表

CREATE TABLE table_name

(

​			field1 datatype

​			field2 datatype

)character set 字符集 collate校对规则 engine 存储引擎

field：指定列名 datatype：指定列类型（字段类型）

character set：如不指定则为所在数据库字符集

collate：如不指定则为所在数据库校对规则

engine：引擎，这个涉及较多，后面单独讲解

``` sql
CREATE TABLE ti (
	id INT,
	‘name‘ VARCHAR(255),
	‘password‘ VARCHAR(255),
	‘birthday‘ DATE
)
CHARACTER SET utf8 COLLATE utf8_bin ENGINE INNODB
```

