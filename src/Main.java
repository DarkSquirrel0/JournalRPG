import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        File settings = new File("settings.txt");

        try {
            Scanner info = new Scanner(settings);
             if (!info.hasNext()) {
                 firstTime();
             }
             else {
                 returnProgram();
             }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }

    }

    private static void firstTime() {
        //todo add more info for each thing the person is adding
        System.out.println("Welcome to the Bullet Journal Program.");
        System.out.println("This is a place to track habits and your to-do list in a fun way.");
        System.out.println("First what would you like to name your kingdom?");

        AllInformation allInformation = new AllInformation();
        Kingdom kingdom = new Kingdom();
        Settings settings = allInformation.getSettings();

        Scanner input = new Scanner(System.in);
        String kingdomName = input.nextLine();

        System.out.println("\n");
        System.out.println("Next what is the name of your ruler?");
        String rulerName = input.nextLine();


        System.out.println("What is the gender of your ruler?");
        System.out.println("F for Female and M for Male");
        String rulerGender = input.nextLine();
        rulerGender = rulerGender.toUpperCase();
        char rulerGenderChar = rulerGender.charAt(0);

        System.out.println("Now we move on to habits\n\n");

        System.out.println("Habits are used to keep your ruler alive");
        System.out.println("If you miss any habits one day you will lose 100 HP");
        System.out.println("If your rulers passes you will have an heir take the throne\n\n\n");

        System.out.println("How many habits do you want to add? I recommend 3.");
        System.out.println("You can change the habits later in Settings");


        ArrayList<Habit> habits = new ArrayList<>();
        int count = 0;
        String habitName;
       do {
            System.out.print("Enter in a habit: ");
            habitName = input.nextLine();
            Habit habit = new Habit();
            habit.setName(habitName);
            habits.add(habit);
            count++;
        } while (count < 3);

        People ruler = new People();
        ruler.setName(rulerName);
        ruler.setAge(20);
        ruler.setGender(rulerGenderChar);
        ruler.setHealth(500);
        ruler.setRole("ruler");

        kingdom.setName(kingdomName);
        kingdom.setLevel(1);
        kingdom.setCurrentRuler(ruler);

        allInformation.setKingdom(kingdom);
        allInformation.setHabits(habits);

        System.out.println("You can also add task to the tracker");
        System.out.println("The more you complete task, the closer you get to leveling up\n");

        System.out.println("But be careful, leveling up leads to more enemies\n\n\n");

        System.out.println("There are different events that can happen in the year (normal day)");
        System.out.println("So enjoy the program\n\n\n");

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(date);
        allInformation.getSettings().setDate(currentDate);

        Events newEvent = new Events();
        newEvent.eventChooser(kingdom);
        settings.setEvent(newEvent);

        int choice;
        do {
            menu();
            System.out.println("Choose a number from above");
            choice = input.nextInt();
            if (choice == 1) {
                kingdomOverview(allInformation);
            } else if (choice == 2) {
                habits(allInformation.getHabits());
            } else if (choice == 3) {
                todoList(allInformation.getTodoList(), allInformation);
            } else if (choice == 4) {
                settings(allInformation);
            } else if (choice == 5) {
                System.out.println("Thank you for using the program");
            } else {
                System.out.println("That was not a choice. Please enter a choice from the ones above");
            }
        } while (choice != 5);

        writeToKingdom(allInformation.getKingdom());
        writeToPeople(allInformation.getKingdom());
        writeToHabits(allInformation.getHabits());
        writeToTodoList(allInformation.getTodoList());
        writeToSettings(allInformation);
    }
    private static void returnProgram() {
        Scanner input = new Scanner(System.in);
        Kingdom kingdom = new Kingdom();
        ArrayList<Habit> habits = readHabits();
        ArrayList<Item> todoList = readItems();
        AllInformation allInformation = new AllInformation();
        readInformation(kingdom);
        allInformation.setKingdom(kingdom);
        allInformation.setHabits(habits);
        allInformation.setTodoList(todoList);
        readSettings(allInformation);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(date);
        String currentDay = currentDate.substring(0,2);
        String currentMonth = currentDate.substring(3,5);
        String currentYear = currentDate.substring(6,10);

        String lastDate = allInformation.getSettings().getDate();
        String lastDay = lastDate.substring(0,2);
        String lastMonth = lastDate.substring(3,5);
        String lastYear = lastDate.substring(6,10);

        int currentDayNum = Integer.parseInt(currentDay);
        int currentMonthNum = Integer.parseInt(currentMonth);
        int currentYearNum = Integer.parseInt(currentYear);

        int lastDayNum = Integer.parseInt(lastDay);
        int lastMonthNum = Integer.parseInt(lastMonth);
        int lastYearNum = Integer.parseInt(lastYear);

        //If a day changed by +1 and the month did not and the year did not change
        //If a day changed by -29, -28, -30, and the month went up year did not change
        //If a day went down by -30 and month went -11 and year went +1
        if ((currentDayNum == (lastDayNum + 1) && (currentMonthNum == lastMonthNum) && lastYearNum == currentYearNum)
                || (((currentDayNum == (lastDayNum - 28)) || (currentDayNum == (lastDayNum - 27))
                || (currentDayNum == (lastDayNum - 29)) || (currentDayNum == (lastDayNum - 30)))
                && (currentMonthNum == (lastMonthNum + 1 )) && (currentYearNum == lastYearNum) )
                || ((currentDayNum == (lastDayNum - 30)) && (currentMonthNum == (lastMonthNum - 11))
                && (currentYearNum == (lastYearNum + 1)))) {
            allInformation.getSettings().setStreak(allInformation.getSettings().getStreak() + 1);
        } else {
            allInformation.getSettings().setStreak(0);
        }

        if (!currentDay.equals(lastDay) || !currentMonth.equals(lastMonth) || !currentYear.equals(lastYear)) {
            allInformation.getSettings().setDate(currentDate);
            System.out.println("New Day, New You");
            newYear(allInformation);
        } else {
            System.out.println("Welcome Back");
        }

        int choice;
        do {
            menu();
            System.out.println("Choose a number from above");
            choice = input.nextInt();
            if (choice == 1) {
                kingdomOverview(allInformation);
            } else if (choice == 2) {
                habits(allInformation.getHabits());
            } else if (choice == 3) {
                todoList(allInformation.getTodoList(), allInformation);
            } else if (choice == 4) {
                settings(allInformation);
            } else if (choice == 5) {
                System.out.println("Thank you for using the program");
            } else {
                System.out.println("That was not a choice. Please enter a choice from the ones above");
            }
        } while (choice != 5);

        if (!allInformation.isResetEverything()) {
            writeToKingdom(allInformation.getKingdom());
            writeToPeople(allInformation.getKingdom());
            writeToHabits(allInformation.getHabits());
            writeToTodoList(allInformation.getTodoList());
            writeToSettings(allInformation);
        }
    }

    private static void readInformation(Kingdom kingdom) {
        //kingdom file
        File file = new File("info.txt");
        try {
            Scanner info = new Scanner(file);
            //Age of Kingdom
            String kingdomAgeString = info.nextLine();
            kingdomAgeString = removeThings(kingdomAgeString);
            int kingdomAge = Integer.parseInt(kingdomAgeString);
            kingdom.setYear(kingdomAge);

            //Years in Power
            String kingdomPowerString = info.nextLine();
            kingdomPowerString = removeThings(kingdomPowerString);
            int kingdomPower = Integer.parseInt(kingdomPowerString);
            kingdom.setYearsInPower(kingdomPower);

            //Name
            String kingdomName = info.nextLine();
            kingdomName = removeThings(kingdomName);
            kingdom.setName(kingdomName);

            //People
            String kingdomPeopleString = info.nextLine();
            kingdomPeopleString = removeThings(kingdomPeopleString);
            int kingdomPeople = Integer.parseInt(kingdomPeopleString);
            kingdom.setPeople(kingdomPeople);

            //Level
            String kingdomLevelString = info.nextLine();
            kingdomLevelString = removeThings(kingdomLevelString);
            int kingdomLevel = Integer.parseInt(kingdomLevelString);
            kingdom.setLevel(kingdomLevel);

            //Enemies
            String enemiesString = info.nextLine();
            enemiesString = removeThings(enemiesString);
            int enemies = Integer.parseInt(enemiesString);
            kingdom.setEnemies(enemies);

            //Alliances
            String alliancesString = info.nextLine();
            alliancesString = removeThings(alliancesString);
            int alliances = Integer.parseInt(alliancesString);
            kingdom.setAlliances(alliances);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }

        ArrayList<People> lovers = new ArrayList<>();
        ArrayList<People> children = new ArrayList<>();
        ArrayList<People> relatives = new ArrayList<>();
        //people file
        File people = new File("people.txt");
        try {
            Scanner info = new Scanner(people);
            for (int i = 0; info.hasNext(); i++) {
                People person = new People();

                //Age
                String ageString = info.nextLine();
                ageString = removeThings(ageString);
                int age = Integer.parseInt(ageString);
                person.setAge(age);

                //Name
                String name = info.nextLine();
                name = removeThings(name);
                person.setName(name);

                //Gender
                String genderString = info.nextLine();
                genderString = removeThings(genderString);
                char gender = genderString.charAt(0);
                person.setGender(gender);

                //Role
                String role = info.nextLine();
                role = removeThings(role);
                person.setRole(role);

                //Heir
                String heirString = info.nextLine();
                heirString = removeThings(heirString);
                boolean heir = Boolean.parseBoolean(heirString);
                person.setHeir(heir);

                //Health
                String healthString = info.nextLine();
                healthString = removeThings(healthString);
                int health = Integer.parseInt(healthString);
                person.setHealth(health);

                //Marriage
                String marriageString = info.nextLine();
                marriageString = removeThings(marriageString);
                boolean marriage = Boolean.parseBoolean(marriageString);
                person.setMarriage(marriage);

                if (person.getRole().equals("ruler")) {
                    kingdom.setCurrentRuler(person);
                } else if (person.getRole().equals("child")) {
                    children.add(person);
                } else if (person.getRole().equals("relative")) {
                    relatives.add(person);
                } else {
                    lovers.add(person);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }
        kingdom.getCurrentRuler().setChildren(children);
        kingdom.getCurrentRuler().setLovers(lovers);

    }

    private static void newYear(AllInformation allInformation) {
        Scanner input = new Scanner(System.in);
        Settings settings = allInformation.getSettings();
        Kingdom kingdom = allInformation.getKingdom();
        ArrayList<Habit> habits = allInformation.getHabits();
        ArrayList<Item> todoList = allInformation.getTodoList();
        File todoListFile = new File("todoList.txt");


        kingdom.setYear(kingdom.getYear() + 1);
        kingdom.setYearsInPower(kingdom.getYearsInPower() + 1);
        kingdom.getCurrentRuler().setAge(kingdom.getCurrentRuler().getAge() + 1);
        for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
            kingdom.getCurrentRuler().getChildren().get(i).setAge(kingdom.getCurrentRuler().getChildren().get(i).getAge() + 1);
        }

        for (int i = 0; i < kingdom.getCurrentRuler().getLovers().size(); i++) {
            kingdom.getCurrentRuler().getLovers().get(i).setAge(kingdom.getCurrentRuler().getLovers().get(i).getAge() + 1);
        }

        Events newEvent = new Events();
        newEvent.eventChooser(kingdom);
        settings.setEvent(newEvent);

        Levels level = new Levels();

        if (level.getLevelBase(kingdom.getLevel() + 1) <= kingdom.getPeople()) {
            System.out.println("Congratulations your Kingdom went up a level\n");
            kingdom.setLevel(kingdom.getLevel() + 1);
            kingdom.setEnemies(kingdom.getEnemies() + 1);
        }
        for (int i = 0; i < habits.size(); i++) {
            if (!habits.get(i).isCompleted()) {
                kingdom.getCurrentRuler().setHealth(kingdom.getCurrentRuler().getHealth() - 100);
            }
        }

        for (int i = 0; i < habits.size(); i++) {
            habits.get(i).setCompleted(false);
        }

        ArrayList<People> relatives = new ArrayList<>();
        if (kingdom.getCurrentRuler().getHealth() <= 0 || kingdom.getCurrentRuler().getAge() == 60) {
            System.out.println("Your ruler has passed away");
            boolean foundHeir = false;
            int heirNumber = 100;
            if (kingdom.getCurrentRuler().getChildren().size() > 0) {
                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                    if (kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !foundHeir) {
                        heirNumber = i;
                        foundHeir = true;
                        kingdom.getCurrentRuler().getChildren().get(i).setSamePerson(true);
                    }
                }
                if (heirNumber == 100) {
                    heirNumber = 0;
                    kingdom.getCurrentRuler().getChildren().get(heirNumber).setSamePerson(true);
                }
                System.out.println(kingdom.getCurrentRuler().getChildren().get(heirNumber).getName() + " is now the new ruler\n\n");
            } else if (kingdom.getCurrentRuler().getRelatives().size() > 0){
                for (int i = 0; i < kingdom.getCurrentRuler().getRelatives().size(); i++) {
                    if (kingdom.getCurrentRuler().getRelatives().get(i).isHeir() && !foundHeir) {
                        heirNumber = i;
                        foundHeir = true;
                        kingdom.getCurrentRuler().getChildren().get(i).setSamePerson(true);
                    }
                    System.out.println(kingdom.getCurrentRuler().getChildren().get(heirNumber).getName() + " is now the new ruler\n\n");

                }
            } else {
                System.out.println("There is no heir to the throne");
                System.out.println("To add a new ruler go to Settings");
            }

            if (kingdom.getCurrentRuler().getChildren().size() > 0) {
                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                    if (kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                    }

                }
                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                    if (!kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                    }
                }
                kingdom.setCurrentRuler(kingdom.getCurrentRuler().getChildren().get(heirNumber));
                kingdom.getCurrentRuler().setRole("ruler");
            }
        }

        int size = todoList.size();
        for (int i = 0; i < size; i++) {
            todoList.remove(0);
        }

        int buildingSize = kingdom.getBonusBuildings().size();
        for (int i = 0; i < buildingSize; i++) {
            kingdom.getBonusBuildings().get(i).setYearsLeft(kingdom.getBonusBuildings().get(i).getYearsLeft() - 1);
        }
        ArrayList<BonusBuildings> goodBonusBuildings = new ArrayList<>();
        for (int i = 0; i < buildingSize; i++) {
            if (kingdom.getBonusBuildings().get(i).getYearsLeft() > 0) {
                goodBonusBuildings.add(kingdom.getBonusBuildings().get(i));
            }
        }
        kingdom.setBonusBuildings(goodBonusBuildings);

        if (settings.getStreak() >= 3) {
            System.out.println("Congrats on keeping a " + settings.getStreak() + " day streak. You get to chose a bonus building");
            System.out.println("A bonus building last for 3 years (in program)");
            System.out.println("Choose a building from the ones down below\n");
            bonusBuildingMenu();

            int choice = input.nextInt();
            BonusBuildings bonusBuilding = new BonusBuildings();
            if (choice == 1) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("hospital");
            } else if (choice == 2) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("church");
            } else if (choice == 3) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("farm");
            } else if (choice == 4) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("tavern");
            } else if (choice == 5) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("barracks");
            } else if (choice == 6) {
                bonusBuilding.setYearsLeft(3);
                bonusBuilding.setName("trade post");
            }

            kingdom.getBonusBuildings().add(bonusBuilding);
        }
        clearFile(todoListFile);
    }
    private static void bonusBuildingMenu() {
        System.out.println("_____________________BONUS BUILDING MENU_________________________");
        System.out.println("1. Hospital");
        System.out.println("2. Church");
        System.out.println("3. Farm");
        System.out.println("4. Tavern");
        System.out.println("5. Barracks");
        System.out.println("6. Trade Post");
        System.out.println("_________________________________________________________________");
    }
    private static ArrayList<Habit> readHabits() {
        //habits file
        File habits = new File("habits.txt");
        ArrayList<Habit> habit = new ArrayList<>();
        try {
            Scanner info = new Scanner(habits);
            for (int i = 0; info.hasNext(); i++) {
                Habit currentHabit = new Habit();
                String name = info.nextLine();

                String completedString = info.nextLine();
                boolean completed = Boolean.parseBoolean(completedString);

                currentHabit.setName(name);
                currentHabit.setCompleted(completed);
                habit.add(i, currentHabit);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }
        return habit;
    }

    private static ArrayList<Item> readItems() {
        //Item list file
        File todoList = new File("todoList.txt");
        ArrayList<Item> items = new ArrayList<>();
        try {
            Scanner info = new Scanner(todoList);
            for (int i = 0; info.hasNextLine(); i++) {
                Item item = new Item();
                String name = info.nextLine();

                String finishedString = info.nextLine();
                boolean finished = Boolean.parseBoolean(finishedString);

                item.setName(name);
                item.setCompleted(finished);
                items.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }
        return items;
    }

    private static void readSettings(AllInformation allInformation) {
        File setting = new File("settings.txt");
        Kingdom kingdom = allInformation.getKingdom();
        Settings settings = new Settings();
        ArrayList<BonusBuildings> bonusBuildings = new ArrayList<>();
        Events events = new Events();
        try {
            Scanner info = new Scanner(setting);
            String dateString = info.nextLine();

            String event = info.nextLine();

            String eventChangeString = info.nextLine();
            int eventChange = Integer.parseInt(eventChangeString);

            String streakString = info.nextLine();
            int streak = Integer.parseInt(streakString);

            for (int i = 0; info.hasNextLine(); i++) {
                BonusBuildings bonus = new BonusBuildings();
                String name = info.nextLine();

                String yearsLeftString = info.nextLine();
                int yearsLeft = Integer.parseInt(yearsLeftString);

                bonus.setName(name);
                bonus.setYearsLeft(yearsLeft);
                bonusBuildings.add(bonus);

            }
            events.setEventChange(eventChange);
            events.setNameOfEvent(event);
            settings.setDate(dateString);
            settings.setStreak(streak);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }
        settings.setEvent(events);
        kingdom.setBonusBuildings(bonusBuildings);
       allInformation.setKingdom(kingdom);
        allInformation.setSettings(settings);

    }
    private static String removeThings(String header) {
        boolean reachedColon = false;
        String value = "";
        for (int i = 0; i < header.length(); i++) {
            if (header.charAt(i) == ':') {
                reachedColon = true;
            }
            if (reachedColon == true) {
                value += header.charAt(i);
            }
        }
        value = value.substring(1, value.length());
        return value;
    }

    private static void menu() {
        System.out.println("__________________________MAIN MENU______________________________");
        System.out.println("1. Kingdom Overview");
        System.out.println("2. Habits");
        System.out.println("3. TO-DO List");
        System.out.println("4. Settings");
        System.out.println("5. Quit");
        System.out.println("_________________________________________________________________");
    }

    private static void kingdomOverview(AllInformation allInformation) {
        Kingdom kingdom = allInformation.getKingdom();
        Settings settings = allInformation.getSettings();
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an item from down below");
        int choice;
        do {
            kingdomOverviewMenu();
            choice = input.nextInt();
            if (choice == 1) {
                kingdomOverviewThings(kingdom);
            } else if (choice == 2) {
                rulerOverview(kingdom);
            } else if (choice == 3) {
                childrenList(kingdom);
            } else if (choice == 4) {
                loversList(kingdom);
            } else if (choice == 5) {
                eventOfTheDay(settings);
            } else if (choice == 6) {
                bonusBuildings(kingdom);
            } else if (choice == 7) {
                System.out.println("Going back to Main Menu");
            } else {
                System.out.println("Not a choice on the Kingdom Menu");
            }
        } while (choice != 7);
    }
    private static void kingdomOverviewMenu(){
        System.out.println("_________________________KINGDOM MENU______________________________");
        System.out.println("1. Kingdom Overview");
        System.out.println("2. Ruler Overview");
        System.out.println("3. Ruler's Children");
        System.out.println("4. Ruler's Lovers");
        System.out.println("5. Event of The Day");
        System.out.println("6. Bonus Buildings");
        System.out.println("7. Quit");
        System.out.println("___________________________________________________________________");
    }

    private static void kingdomOverviewThings(Kingdom kingdom) {
        System.out.println("______________________________KINGDOM______________________________");
        System.out.println("Name: " + kingdom.getName());
        System.out.println("Age: " + kingdom.getYear());
        System.out.println("People: " + kingdom.getPeople());
        System.out.println("Level: " + kingdom.getLevel());
        System.out.println("Enemies: " + kingdom.getEnemies() + " Alliances: " + kingdom.getAlliances());
        System.out.println();
    }

    private static void rulerOverview(Kingdom kingdom) {
        System.out.println("______________________________RULER______________________________");
        System.out.println("Name: " + kingdom.getCurrentRuler().getName());
        System.out.println("Age: " + kingdom.getCurrentRuler().getAge());
        System.out.println("Marriage Status: " + kingdom.getCurrentRuler().isMarriage());
        System.out.println("Health: " + kingdom.getCurrentRuler().getHealth());
        System.out.println("Role: " + kingdom.getCurrentRuler().getRole());
        System.out.println("Years in Power: " + kingdom.getYearsInPower());
        System.out.println("Amount of Children: " + kingdom.getCurrentRuler().getChildren().size());
        System.out.println("Amount of Lovers: " + kingdom.getCurrentRuler().getLovers().size());
        boolean foundHeir = false;
        int heirNumber = 100;
        if (kingdom.getCurrentRuler().getChildren().size() > 0) {
            for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                if (kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !foundHeir) {
                    heirNumber = i;
                    foundHeir = true;
                }
            }
            if (heirNumber == 100) {
                heirNumber = 0;
            }
            System.out.println("Heir To Throne: " + kingdom.getCurrentRuler().getChildren().get(heirNumber).getName());
        } else if (kingdom.getCurrentRuler().getRelatives().size() > 0){
            for (int i = 0; i < kingdom.getCurrentRuler().getRelatives().size(); i++) {
                if (kingdom.getCurrentRuler().getRelatives().get(i).isHeir() && !foundHeir) {
                    heirNumber = i;
                    foundHeir = true;
                }
                System.out.println("Heir To Throne: " + kingdom.getCurrentRuler().getChildren().get(heirNumber).getName());
            }
        } else {
            System.out.println("Heir To Throne: None");
        }
        System.out.println();
    }

    private static void childrenList(Kingdom kingdom) {
        ArrayList<People> children = kingdom.getCurrentRuler().getChildren();
        System.out.println("______________________________CHILDREN_____________________________");
        if (children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                System.out.println("Name: " + children.get(i).getName());
                System.out.println("Age: " + children.get(i).getAge());
                System.out.println("Heir: " + children.get(i).isHeir());
                System.out.println("Health: " + children.get(i).getHealth());
                System.out.println("Role: " + children.get(i).getRole());
            }
        } else {
            System.out.println("None");
        }
        System.out.println();
    }
    private static void loversList(Kingdom kingdom) {
        ArrayList<People> lovers = kingdom.getCurrentRuler().getLovers();
        System.out.println("_______________________________LOVERS______________________________");
        if (lovers.size() > 0) {
            for (int i = 0; i < lovers.size(); i++) {
                System.out.println("Name: " + lovers.get(i).getName());
                System.out.println("Age: " + lovers.get(i).getAge());
                System.out.println("Health: " + lovers.get(i).getHealth());
                System.out.println("Role: " + lovers.get(i).getRole());
            }
        } else {
            System.out.println("None");
        }
        System.out.println();
    }
    private static void eventOfTheDay(Settings settings) {
        System.out.println("______________________________EVENT OF THE DAY______________________________");
        System.out.println(settings.getEvent().getNameOfEvent());
        System.out.println();
    }

    private static void bonusBuildings(Kingdom kingdom) {
        System.out.println("______________________________BONUS BUILDINGS______________________________");
        for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
            System.out.println("Building Name: "+ kingdom.getBonusBuildings().get(i).getName() +" Years Left: "
                    + kingdom.getBonusBuildings().get(i).getYearsLeft());
        }
        System.out.println();
    }
    private static void habits(ArrayList<Habit> habits) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            habitMenu();
            System.out.println("Choose from the number above.");
            choice = input.nextInt();
            if (choice == 1) {
                System.out.println("_________________________HABITS__________________________________");
                for (int i = 0; i < habits.size(); i++) {
                    if (habits.get(i).isCompleted())
                        System.out.println("[✓] " + habits.get(i).getName());
                    else
                        System.out.println("[ ] " + habits.get(i).getName());
                }
            } else if (choice == 2) {
                checkOffHabits(habits);
            } else if (choice == 3) {
                System.out.println("Leaving the habit menu");
            } else {
                System.out.println("That is not an option above.");
            }
        } while (choice != 3);
    }

    private static void habitMenu() {
        System.out.println("_________________________HABITS MENU_____________________________");
        System.out.println("1. Habits List");
        System.out.println("2. Check off habit");
        System.out.println("3. Quit");
        System.out.println("_________________________________________________________________");
    }

    private static void habitMenu2() {
        System.out.println("_________________________HABITS MENU_____________________________");
        System.out.println("1. Add a habit");
        System.out.println("2. Remove a habit");
        System.out.println("3. Quit");
        System.out.println("_________________________________________________________________");
    }
    private static void addHabit(ArrayList<Habit> habitArrayList) {
        Scanner input = new Scanner(System.in);
        System.out.println("What habit do you want to add?");
        String name = input.nextLine();
        System.out.println("Have you completed the habit for the day?");
        System.out.println("Type Y for Yes and N for No");
        String completed = input.nextLine();
        completed = completed.toUpperCase();
        char completedChar = completed.charAt(0);
        boolean completedBoolean = false;
        if (completedChar == 'Y') {
            completedBoolean = true;
        }
        Habit newHabit = new Habit();
        newHabit.setName(name);
        newHabit.setCompleted(completedBoolean);
        habitArrayList.add(newHabit);
    }

    private static void removeHabit(ArrayList<Habit> habitArrayList) {
        System.out.println("Which habit do you want to remove?");
        System.out.println("_________________________HABITS__________________________________");
        for (int i = 0; i < habitArrayList.size(); i++) {
            if (habitArrayList.get(i).isCompleted())
                System.out.println((i + 1) + ". [✓] " + habitArrayList.get(i).getName());
            else
                System.out.println((i + 1) + ". [ ] " + habitArrayList.get(i).getName());
        }
        System.out.println((habitArrayList.size() + 1) + ". Quit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        String yesOrNo;
        if (choice >= 1 || choice < habitArrayList.size()) {
            System.out.println("Are you sure you want to remove a habit?");
            System.out.println("Y for Yes and N for No");
            yesOrNo = input.next();
            yesOrNo = yesOrNo.toUpperCase();
            char yesOrNoChar = yesOrNo.charAt(0);
            if (yesOrNoChar == 'Y') {
                habitArrayList.remove(choice - 1);
            }
        } else {
            System.out.println("Going back to Habit Menu");
        }
    }

    private static void checkOffHabits(ArrayList<Habit> habitArrayList) {
        System.out.println("Which habit do you want to check off today?");
        System.out.println("_________________________HABITS__________________________________");
        for (int i = 0; i < habitArrayList.size(); i++) {
            if (habitArrayList.get(i).isCompleted())
                System.out.println((i + 1) + ". [✓] " + habitArrayList.get(i).getName());
            else
                System.out.println((i + 1) + ". [ ] " + habitArrayList.get(i).getName());
        }
        System.out.println((habitArrayList.size() + 1) + ". Quit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        if (habitArrayList.size() > 0) {
            if (choice < 0 || choice > habitArrayList.size()) {
                System.out.println("Going back to Habit Menu");
            } else {
                habitArrayList.get(choice - 1).setCompleted(true);
            }
        } else {
            System.out.println("There is nothing on the Habits List");
            System.out.println("going back to Habit Menu");
        }
    }

    private static void todoList(ArrayList<Item> todoList, AllInformation allInformation) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            todoMenu();
            System.out.println("Choose from the numbers above.");
            choice = input.nextInt();
            if (choice == 1) {
                System.out.println("______________________________TO-DO LIST_________________________");
                for (int i = 0; i < todoList.size(); i++) {
                    if (todoList.get(i).isCompleted())
                        System.out.println("[✓] " + todoList.get(i).getName());
                    else
                        System.out.println("[ ] " + todoList.get(i).getName());
                }
            } else if (choice == 2) {
                addItem(todoList, allInformation);
            } else if (choice == 3) {
                removeItem(todoList);
            } else if (choice == 4) {
                checkOffItem(todoList, allInformation);
            } else if (choice == 5) {
                System.out.println("Leaving the to-do menu");
            } else {
                System.out.println("That is not an option above.");
            }
        } while (choice != 5);
    }

    private static void addItem(ArrayList<Item> items, AllInformation allInformation) {
        Scanner input = new Scanner(System.in);
        System.out.println("What item do you want to add?");
        String name = input.nextLine();
        System.out.println("Have you completed the habit for the day?");
        System.out.println("Type Y for Yes and N for No");
        String completed = input.nextLine();
        completed = completed.toUpperCase();
        char completedChar = completed.charAt(0);
        boolean completedBoolean = false;
        int points = 0;
        Kingdom kingdom = allInformation.getKingdom();
        Levels level = new Levels();
        if (completedChar == 'Y') {
            completedBoolean = true;
            points = (level.getLevelBase(kingdom.getLevel() + 1) / 10) * checkTavernAndFestival(allInformation);
            kingdom.setPeople(kingdom.getPeople() + points);
            System.out.println("Congrats " + points + " more people came to the kingdom");
            if (level.getLevelBase(kingdom.getLevel() + 1) < kingdom.getPeople()) {
                System.out.println("Congratulations your Kingdom went up a level");
                kingdom.setLevel(kingdom.getLevel() + 1);
                kingdom.setEnemies(kingdom.getEnemies() + 1);
            }
        }
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setCompleted(completedBoolean);
        items.add(newItem);
    }

    private static void removeItem(ArrayList<Item> items) {
        System.out.println("Which habit do you want to remove?");
        System.out.println("_________________________HABITS__________________________________");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isCompleted())
                System.out.println((i + 1) + ". [✓] " + items.get(i).getName());
            else
                System.out.println((i + 1) + ". [ ] " + items.get(i).getName());
        }
        System.out.println((items.size() + 1) + ". Quit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        String yesOrNo;
        if (choice >= 1 || choice < items.size()) {
            System.out.println("Are you sure you want to remove an item?");
            System.out.println("Y for Yes and N for No");
            yesOrNo = input.next();
            yesOrNo = yesOrNo.toUpperCase();
            char yesOrNoChar = yesOrNo.charAt(0);
            if (yesOrNoChar == 'Y') {
                items.remove(choice - 1);
            }
        } else {
            System.out.println("Going back to To-Do Menu");
        }
    }

    private static void checkOffItem(ArrayList<Item> itemArrayList, AllInformation allInformation) {
        Kingdom kingdom = allInformation.getKingdom();
        System.out.println("Which item do you want to check off today?");
        System.out.println("_______________________TO-DO LIST________________________________");
        for (int i = 0; i < itemArrayList.size(); i++) {
            if (itemArrayList.get(i).isCompleted())
                System.out.println((i + 1) + ". [✓] " + itemArrayList.get(i).getName());
            else
                System.out.println((i + 1) + ". [ ] " + itemArrayList.get(i).getName());
        }
        System.out.println((itemArrayList.size() + 1) + ". Quit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        Levels level = new Levels();
        int points = 0;
        if (itemArrayList.size() > 0) {
            if (choice >= 1 || choice < itemArrayList.size()) {
                itemArrayList.get(choice - 1).setCompleted(true);
                points = (level.getLevelBase(kingdom.getLevel() + 1) / 10) * checkTavernAndFestival(allInformation);
                kingdom.setPeople(kingdom.getPeople() + points);
                System.out.println("Congrats " + points + " more people came to the kingdom");
                if (level.getLevelBase(kingdom.getLevel() + 1) < kingdom.getPeople()) {
                    System.out.println("Congratulations your Kingdom went up a level");
                    kingdom.setLevel(kingdom.getLevel() + 1);
                    kingdom.setEnemies(kingdom.getEnemies() + 1);
                }
            } else {
                System.out.println("Going back to To-Do Menu");
            }
        } else {
            System.out.println("There is nothing on the list");
            System.out.println("Going back to To-Do Menu");
        }
    }

    private static int checkTavernAndFestival(AllInformation allInformation) {
        int pointsMultiplier = 1;
        Kingdom kingdom = allInformation.getKingdom();
        Settings settings = allInformation.getSettings();
        if (settings.getEvent().getNameOfEvent().contains("Festival")) {
            pointsMultiplier *= 2;
        }
        for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
            if (kingdom.getBonusBuildings().get(i).getName().equals("tavern")) {
                pointsMultiplier *= 2;
            }
        }
        return pointsMultiplier;
    }
    private static void todoMenu() {
        System.out.println("__________________________TO-DO MENU_____________________________");
        System.out.println("1. To-do List");
        System.out.println("2. Add an item");
        System.out.println("3. Remove a item");
        System.out.println("4. Check off item");
        System.out.println("5. Quit");
        System.out.println("_________________________________________________________________");
    }

    private static void settings(AllInformation allInformation) {
        /*
        reset the year
        change the kingdom name
        change the year (keep everything the same, but change the year you are starting on)
        reset the entire program
         */
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            settingsMenu();
            System.out.println("Choose from the numbers above");
            choice = input.nextInt();
            if (choice == 1) {
                resetYear(allInformation.getKingdom());
            } else if (choice == 2) {
                changeKingdomName(allInformation.getKingdom());
            } else if (choice == 3) {
                changeTheYear(allInformation.getKingdom());
            } else if (choice == 4) {
                changeHabits(allInformation.getHabits());
            } else if (choice == 5) {
                resetEntireProgram(allInformation);
            } else if (choice == 6) {
                addNewRuler(allInformation);
            } else {
                System.out.println("Going back to main menu");
            }
        } while (choice != 7);

    }

    private static void settingsMenu() {
        System.out.println("__________________________SETTINGS_____________________________");
        System.out.println("1. Reset The Year");
        System.out.println("2. Change the Kingdom Name");
        System.out.println("3. Change the Year");
        System.out.println("4. Change Habits");
        System.out.println("5. Reset Entire Program");
        System.out.println("6. Add New Ruler");
        System.out.println("7. Quit");
        System.out.println("_________________________________________________________________");
    }

    private static void resetYear(Kingdom kingdom) {
        Scanner input = new Scanner(System.in);

        System.out.println("Are you sure you want to reset the year back to 0?");
        System.out.println("Y for Yes and N for No");
        String yesOrNo = input.next();
        yesOrNo = yesOrNo.toUpperCase();
        char yesOrNoChar = yesOrNo.charAt(0);
        if (yesOrNoChar == 'Y') {
            System.out.println("The old kingdom year was: "+ kingdom.getYear());
            kingdom.setYear(0);
            System.out.println("The new kingdom year is: " + kingdom.getYear());
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }

    private static void changeKingdomName(Kingdom kingdom) {
        Scanner input = new Scanner(System.in);

        System.out.println("Are you sure you want to change the name?");
        System.out.println("Y for Yes and N for No");
        String yesOrNo = input.nextLine();
        yesOrNo = yesOrNo.toUpperCase();
        char yesOrNoChar = yesOrNo.charAt(0);
        if (yesOrNoChar == 'Y') {
            System.out.println("What would you like to name the kingdom?");
            //todo fix nextLine for input
            String newKingdomName = input.nextLine();
            String oldKingdomName = kingdom.getName();
            kingdom.setName(newKingdomName);
            System.out.println("The old kingdom name was: "+ oldKingdomName);
            System.out.println("The new kingdom is: " + kingdom.getName());
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }

    private static void changeTheYear(Kingdom kingdom) {
        Scanner input = new Scanner(System.in);

        System.out.println("Are you sure you want to change the year?");
        System.out.println("Y for Yes and N for No");
        String yesOrNo = input.next();
        yesOrNo = yesOrNo.toUpperCase();
        char yesOrNoChar = yesOrNo.charAt(0);
        int newKingdomYear;
        if (yesOrNoChar == 'Y') {
            System.out.println("What would you like the year to be?");
            System.out.println("PLEASE DO NOT PUT A DECIMAL YEAR");
            newKingdomYear = input.nextInt();
            System.out.println("The old kingdom year was: "+ kingdom.getYear());
            kingdom.setYear(newKingdomYear);
            System.out.println("The new kingdom year is: " + kingdom.getYear());
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }

    private static void changeHabits(ArrayList<Habit> habits) {
        habitMenu2();

        Scanner input = new Scanner(System.in);
        System.out.println("Choose a number from above");
        int choice = input.nextInt();
        if (choice == 1) {
            addHabit(habits);
        } else if (choice == 2) {
            removeHabit(habits);
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }

    private static void resetEntireProgram(AllInformation allInformation) {
        Scanner input = new Scanner(System.in);

        File info = new File("info.txt");
        File habits = new File("habits.txt");
        File people = new File("people.txt");
        File settings = new File("settings.txt");
        File todoList = new File("todoList.txt");

        System.out.println("Are you sure you want to reset the entire program?");
        System.out.println("Y for Yes and N for No");
        String yesOrNo = input.nextLine();
        yesOrNo = yesOrNo.toUpperCase();
        char yesOrNoChar = yesOrNo.charAt(0);
        if (yesOrNoChar == 'Y') {
            clearFile(info);
            clearFile(habits);
            clearFile(people);
            clearFile(settings);
            clearFile(todoList);
            allInformation.setResetEverything(true);
            System.out.println("When you run the program again it will be completely restarted");
            System.out.println("Until then the program will have the same info as before");
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }

    private static void addNewRuler(AllInformation allInformation) {
        Scanner input = new Scanner(System.in);
        System.out.println("Are you sure you want to add a new ruler?");
        System.out.println("Y for Yes and N for No");
        String yesOrNo = input.nextLine();
        yesOrNo = yesOrNo.toUpperCase();
        char yesOrNoChar = yesOrNo.charAt(0);
        People newRuler = new People();
        if (yesOrNoChar == 'Y') {

            System.out.println("what would you like to name the ruler?");
            String name = input.nextLine();

            System.out.println("What is the ruler's gender?");
            System.out.println("Put F for Female and M for Male");
            String genderString = input.nextLine();
            genderString = genderString.toUpperCase();
            char gender = genderString.charAt(0);

            newRuler.setName(name);
            newRuler.setAge(20);
            newRuler.setGender(gender);
            newRuler.setRole("ruler");
            allInformation.getKingdom().setCurrentRuler(newRuler);
        } else {
            System.out.println("Going back to Settings Menu");
        }
    }
    private static void clearFile(File file) {
        try {
            Scanner input = new Scanner(file);
            FileWriter writer = new FileWriter(file);
            for (int i = 0; input.hasNextLine(); i++) {
                writer.write("");
            }
        } catch (IOException e) {
            System.out.println("This file does not exist");
            e.printStackTrace();
        }
    }

    private static void writeToKingdom(Kingdom kingdom) {
        File info = new File("info.txt");
        clearFile(info);
        try {
            FileWriter writer = new FileWriter(info);
            writer.flush();
            writer.write("Year:" + kingdom.getYear() + "\n");
            writer.write("Years In Power:" + kingdom.getYearsInPower() + "\n");
            writer.append("Name:" + kingdom.getName() + "\n");
            writer.append("People:" + kingdom.getPeople() + "\n");
            writer.append("Level:" + kingdom.getLevel() + "\n");
            writer.append("Enemies:" + kingdom.getEnemies() + "\n");
            writer.append("Alliances:" + kingdom.getAlliances());
            writer.close();
        } catch (IOException e) {
            System.out.println("This file does not exist");
            e.printStackTrace();
        }
    }

    private static void writeToPeople(Kingdom kingdom) {
        File people = new File("people.txt");
        clearFile(people);
        People ruler = kingdom.getCurrentRuler();
        ArrayList<People> importantPeople = ruler.getLovers();
        importantPeople.addAll(ruler.getChildren());
        importantPeople.addAll(ruler.getRelatives());
        try {
            FileWriter writer = new FileWriter(people);
            writer.flush();

            writer.write("Age:" + ruler.getAge() +"\n");
            writer.write("Name:" + ruler.getName() +"\n");
            writer.write("Gender:" + ruler.getGender() +"\n");
            writer.write("Role:" + ruler.getRole() +"\n");
            writer.write("Heir:" + ruler.isHeir() +"\n");
            writer.write("Health:" + ruler.getHealth() +"\n");
            writer.write("Marriage:" + ruler.isMarriage() +"\n");

            for (int i = 0; i < importantPeople.size(); i++) {
                writer.write("Age:" + importantPeople.get(i).getAge() +"\n");
                writer.write("Name:" + importantPeople.get(i).getName() +"\n");
                writer.write("Gender:" + importantPeople.get(i).getGender() +"\n");
                writer.write("Role:" + importantPeople.get(i).getRole() +"\n");
                writer.write("Heir:" + importantPeople.get(i).isHeir() +"\n");
                writer.write("Health:" +importantPeople.get(i).getHealth() +"\n");
                writer.write("Marriage:" + importantPeople.get(i).isMarriage() +"\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("This file does not exist");
            e.printStackTrace();
        }
    }

    private static void writeToHabits(ArrayList<Habit> habits) {
        File habit = new File("habits.txt");
        clearFile(habit);
        try {
            FileWriter writer = new FileWriter(habit);
            writer.flush();
            for (int i = 0; i < habits.size(); i++) {
                writer.append(habits.get(i).getName() + "\n");
                String complete = String.valueOf(habits.get(i).isCompleted());
                writer.append(complete + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("This file does not exist");
            e.printStackTrace();
        }
    }

    private static void writeToTodoList(ArrayList<Item> todoList) {
        File todoL = new File("todoList.txt");
        clearFile(todoL);
        try {
            FileWriter writer = new FileWriter(todoL);
            writer.flush();
            for (int i = 0; i < todoList.size(); i++) {
                writer.append(todoList.get(i).getName() + "\n");
                String complete = String.valueOf(todoList.get(i).isCompleted());
                writer.append(complete + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("This file does not exist");
            e.printStackTrace();
        }
    }

    private static void writeToSettings(AllInformation allInformation) {
        Kingdom kingdom = allInformation.getKingdom();
        Settings settings = allInformation.getSettings();

        File setting = new File("settings.txt");
        clearFile(setting);
        try {
            FileWriter writer = new FileWriter(setting);
            writer.flush();

            writer.append(settings.getDate() + "\n");
            writer.append(settings.getEvent().getNameOfEvent() + "\n");
            String eventChange = String.valueOf(settings.getEvent().getEventChange());
            writer.append(eventChange + "\n");
            String streak = String.valueOf(settings.getStreak());
            writer.append(streak + "\n");
            for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                writer.flush();
                writer.append(kingdom.getBonusBuildings().get(i).getName() + "\n");
                String yearsLeft = String.valueOf(kingdom.getBonusBuildings().get(i).getYearsLeft());
                writer.append(yearsLeft + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("This file does not exist");
        }
    }
}
