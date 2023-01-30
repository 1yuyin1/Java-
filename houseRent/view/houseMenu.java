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
