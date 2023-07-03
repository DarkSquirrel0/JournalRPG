import java.util.ArrayList;

public class People {
    private int age = 0;
    private String name = " ";
    private char gender = ' ';
    private String role = " ";
    //could inherit the throne
    private boolean heir = false;
    private int health = 500;
    // For health, it only increases if you get the hunt event
    //It will decrease if you do not do your habits
    private boolean marriage = false;
    private ArrayList<People> lovers = new ArrayList<>();
    private ArrayList<People> children = new ArrayList<>();
    private ArrayList<People> relatives = new ArrayList<>();
    private boolean samePerson = false;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isHeir() {
        return heir;
    }

    public void setHeir(boolean heir) {
        this.heir = heir;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isMarriage() {
        return marriage;
    }

    public void setMarriage(boolean marriage) {
        this.marriage = marriage;
    }

    public ArrayList<People> getLovers() {
        return lovers;
    }

    public void setLovers(ArrayList<People> lovers) {
        this.lovers = lovers;
    }

    public ArrayList<People> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<People> children) {
        this.children = children;
    }

    public ArrayList<People> getRelatives() {
        return relatives;
    }

    public void setRelatives(ArrayList<People> relatives) {
        this.relatives = relatives;
    }

    public boolean isSamePerson() {
        return samePerson;
    }

    public void setSamePerson(boolean samePerson) {
        this.samePerson = samePerson;
    }
}
