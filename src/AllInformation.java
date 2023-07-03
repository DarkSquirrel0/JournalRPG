import java.util.ArrayList;

public class AllInformation {
    private Kingdom kingdom = new Kingdom();
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<Item> todoList = new ArrayList<>();
    private Settings settings = new Settings();
    private boolean resetEverything = false;
    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    public ArrayList<Item> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<Item> todoList) {
        this.todoList = todoList;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public boolean isResetEverything() {
        return resetEverything;
    }

    public void setResetEverything(boolean resetEverything) {
        this.resetEverything = resetEverything;
    }
}
