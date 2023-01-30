package tankGame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    Shot s;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    //这里提供一个方法将MyPanle类的enemytanks成员  设置到EnemyTank
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isTouchEnemyTank() {
        if(enemyTanks.size() == 0) return true;
        switch (getDirect()) {
            case 0:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右上角坐标[this.getX() + 40,this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右上角坐标[this.getX() + 40,this.getY()]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 1:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克右上角坐标[this.getX() + 60,this.getY()]
                            if(this.getX() + 60>= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 60,this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克右上角坐标[this.getX() + 60,this.getY()]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 60,this.getY() + 40]
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 2:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左下角坐标[this.getX(),this.getY() + 60]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY()  + 60 >= enemyTank.getY()
                                    && this.getY()  + 60 <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 40,this.getY() + 60]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左下角坐标[this.getX(),this.getY() + 60]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克右下角坐标[this.getX() + 40,this.getY() + 60]
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 3:
                //让当前的敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if(enemyTank != this) {
                        //如果敌人坦克是上/下  x的范围[enemyTank.getX(),enemyTank.getX() + 40]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 60]
                        if(enemyTank.getDirect() == 0|| enemyTank.getDirect() == 2) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;

                            //当前坦克左下角坐标[this.getX(),this.getY() + 40]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60)
                                return true;
                        }

                        //如果敌人坦克是左/右  x的范围[enemyTank.getX(),enemyTank.getX() + 60]
                        //                  y的范围[enemyTank.getY(),enemyTank.getY() + 40]
                        if(enemyTank.getDirect() == 1|| enemyTank.getDirect() == 3) {
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;

                            //当前坦克左下角坐标[this.getX(),this.getY() + 40]
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
        }
        return false;
    }
    @Override
    public void run() {
        while (true) {
            //如果判断shots.size = 0，创建一颗子弹

            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveUp();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveRight();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveDown();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 50; i++) {
                        if(!isTouchEnemyTank()) moveLeft();
                        shuttle();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            setDirect((int) (Math.random() * 4));
            if (!isLive()) break;
        }
    }
    public void shuttle(){
        int temp = (int) (Math.random() *100);
        if ( temp == 31 && isLive() && shots.size() < 3) {
            switch (getDirect()) {
                case 0:
                    s = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1:
                    s = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2:
                    s = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3:
                    s = new Shot(getX(), getY() + 20, 3);
                    break;
            }
            shots.add(s);
            //启动
            new Thread(s).start();
        }
    }
}

