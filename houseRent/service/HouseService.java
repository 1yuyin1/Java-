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
