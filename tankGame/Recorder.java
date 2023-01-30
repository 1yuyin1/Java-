package tankGame;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allEnemyTankNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFilePath = "src/tankGame/myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static String getrecordFilePath() {
        return recordFilePath;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //增加一个方法，用于读取，恢复相关信息
    public static  Vector<Node> getNodes() {
        try {
            br = new BufferedReader(new FileReader(recordFilePath));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public static void storeRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFilePath));
            bw.write(allEnemyTankNum +"\r\n");
            //遍历敌人坦克，然后根据情况保存
            for(int i = 0; i < enemyTanks.size();i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + " " + enemyTank.getSpeed();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

}
