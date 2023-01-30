package tankGame;

public class Tank {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct = 0;//坦克方向 0：上 1：右 2：下 3：左
    private int speed = 1;
    private boolean isLive = true;

    public Tank(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    public void moveUp() {
        if(y > 0) y -= speed;
    }

    public void moveRight() {
        if(x + 80 <1000) x += speed;

    }

    public void moveDown() {
        if(y + 100 <750) y += speed;
    }

    public void moveLeft() {
        if(x >0) x -= speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
