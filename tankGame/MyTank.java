package tankGame;

import java.util.Vector;

public class MyTank extends Tank {
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    public void Myshot() {
        if(shots.size() == 5) return;
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY() - 10, 0);
                break;
            case 1:
                shot = new Shot(getX() + 70, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 70, 2);
                break;
            case 3:
                shot = new Shot(getX() - 10, getY() + 20, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
