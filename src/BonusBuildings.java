public class BonusBuildings {
    // hospital (1/2 outbreak damage)
    // church (stops damage to rulers health and family members health)
    // farm (1/2 famine damage)
    // tavern (2x people coming in)
        // change when in main class so that when points are positive and not a heath event double points
    // barracks (win wars)
    //trade post (Doubles alliance XP and removes enemies)

    private String name  = "";
    private int yearsLeft = 0;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsLeft() {
        return yearsLeft;
    }

    public void setYearsLeft(int yearsLeft) {
        this.yearsLeft = yearsLeft;
    }
}
