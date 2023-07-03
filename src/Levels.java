public class Levels {
    private final int level1 = 0;
    private final int level2 = 100;
    private final int level3 = 500;
    private final int level4 = 1000;
    private final int level5 = 2500;
    private final int level6 = 5000;
    private final int level7 = 7500;
    private final int level8 = 10000;
    private final int level9 = 15000;
    private final int level10 = 20000;
    private final int level11 = 35000;
    private final int level12 = 50000;
    private final int level13 = 75000;
    private final int level14 = 100000;
    private final int level15 = 200000;
    private final int level16 = 500000;
    private final int level17 = 700000;
    private final int level18 = 1000000;
    private final int level19 = 1150000;
    private final int level20 = 1250000;
    private final int level21 = 1350000;

    public int getLevelBase(int level) {
        int levelBase = 0;
        switch (level) {
            case 1:
                levelBase = level1;
                break;
            case 2:
                levelBase = level2;
                break;
            case 3:
                levelBase = level3;
                break;
            case 4:
                levelBase = level4;
                break;
            case 5:
                levelBase = level5;
                break;
            case 6:
                levelBase = level6;
                break;
            case 7:
                levelBase = level7;
                break;
            case 8:
                levelBase = level8;
                break;
            case 9:
                levelBase = level9;
                break;
            case 10:
                levelBase = level10;
                break;
            case 11:
                levelBase = level11;
                break;
            case 12:
                levelBase = level12;
                break;
            case 13:
                levelBase = level13;
                break;
            case 14:
                levelBase = level14;
                break;
            case 15:
                levelBase = level15;
                break;
            case 16:
                levelBase = level16;
                break;
            case 17:
                levelBase = level17;
                break;
            case 18:
                levelBase = level18;
                break;
            case 19:
                levelBase = level19;
                break;
            case 20:
                levelBase = level20;
                break;
            case 21:
                levelBase = level21;
                break;
        }
        return levelBase;
    }
}
