import java.util.ArrayList;

public class Kingdom {
    private int year = 0;
    private int yearsInPower = 0;

    private String name = " ";
    //amount of people in kingdom (XP)
    private int people = 0;
    private ArrayList<BonusBuildings> bonusBuildings = new ArrayList<>();
    // arrayList of Family members
    private People currentRuler = new People();
    private int level = 0;
    private int enemies = 0;
    private int alliances = 0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYearsInPower() {
        return yearsInPower;
    }

    public void setYearsInPower(int yearsInPower) {
        this.yearsInPower = yearsInPower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public People getCurrentRuler() {
        return currentRuler;
    }

    public void setCurrentRuler(People currentRuler) {
        this.currentRuler = currentRuler;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnemies() {
        return enemies;
    }

    public void setEnemies(int enemies) {
        this.enemies = enemies;
    }

    public int getAlliances() {
        return alliances;
    }

    public void setAlliances(int alliances) {
        this.alliances = alliances;
    }

    public ArrayList<BonusBuildings> getBonusBuildings() {
        return bonusBuildings;
    }

    public void setBonusBuildings(ArrayList<BonusBuildings> bonusBuildings) {
        this.bonusBuildings = bonusBuildings;
    }
}
