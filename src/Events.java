import java.util.ArrayList;
import java.util.Scanner;

public class Events {

    // should I combine the events and probability class because it is easier to reference, and it would make more sense
    // or should I keep it as it is and just figure it out later how to combine the 2
    private int eventChange = 0;
    private String nameOfEvent = " ";
    //each point system starts are 15
    private int famine = 15;
    private int outbreak = 15;
    private int illness = 15;
    private int aAttempt = 15;
    private int fmUnalives = 15;
    private int war = 15;
    private int marriage = 15;
    private int consort = 15;
    private int child = 15;
    private int festival = 15;
    private int feast = 15;
    private int alliance = 15;
    private int riot = 15;
    //45 extra points for nothing happening that day


    /*
     famine(b): completely random
    outbreak(b): completely random
    illness(b): +older ruler
    assassination attempt(b): +/- age of ruler, + age of children, + more enemies
    family member unalives(b): + more enemies
    ************ War is random and the likelihood changes if you win or lose by the amount of enemies and alliances *****************
    war won(g): + alliances, - enemies
    war loss(b): - alliances, + enemies
    ruler unalives(b): + enemies (probably not needed)
    get married(g): + older ruler, + enemies, + alliance
    get consort(g): + younger ruler, + older ruler
    have child(g): + lovers, + young ruler, + spouse age, - older ruler, + enemies
    have festival(g): + alliances
    have feast(g): + alliances
    make alliances (g): + younger ruler, - older ruler
    make enemies(b): random
    riot starts(b): + new ruler, + younger ruler, - older ruler, + enemies
    nothing happens: completely random
     */

    public int getEventChange() {
        return eventChange;
    }

    public void setEventChange(int eventChange) {
        this.eventChange = eventChange;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public int getFamine() {
        return famine;
    }

    public void setFamine(int famine) {
        this.famine = famine;
    }

    public int getOutbreak() {
        return outbreak;
    }

    public void setOutbreak(int outbreak) {
        this.outbreak = outbreak;
    }

    public int getIllness() {
        return illness;
    }

    public void setIllness(int illness) {
        this.illness = illness;
    }

    public int getaAttempt() {
        return aAttempt;
    }

    public void setaAttempt(int aAttempt) {
        this.aAttempt = aAttempt;
    }

    public int getFmUnalives() {
        return fmUnalives;
    }

    public void setFmUnalives(int fmUnalives) {
        this.fmUnalives = fmUnalives;
    }

    public int getWar() {
        return war;
    }

    public void setWar(int war) {
        this.war = war;
    }

    public int getMarriage() {
        return marriage;
    }

    public void setMarriage(int marriage) {
        this.marriage = marriage;
    }

    public int getConsort() {
        return consort;
    }

    public void setConsort(int consort) {
        this.consort = consort;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getFestival() {
        return festival;
    }

    public void setFestival(int festival) {
        this.festival = festival;
    }

    public int getFeast() {
        return feast;
    }

    public void setFeast(int feast) {
        this.feast = feast;
    }

    public int getAlliance() {
        return alliance;
    }

    public void setAlliance(int alliance) {
        this.alliance = alliance;
    }

    public int getRiot() {
        return riot;
    }

    public void setRiot(int riot) {
        this.riot = riot;
    }

    private void probability(Kingdom kingdom) {
        People ruler = kingdom.getCurrentRuler();
        if (ruler.getAge() >= 50) {
            illness += 10;
        }
        if (ruler.getAge() > 49 || ruler.getAge() < 20 || kingdom.getEnemies() > 2) {
            if (ruler.getChildren().size() > 0) {
                if (ruler.getChildren().get(0).getAge() > 19) {
                    aAttempt += 7;
                }
            }
            aAttempt += 7;
        }
        if (ruler.getAge() > 25 || ruler.getChildren().size() < 1) {
            child += 10;
        }
        if (kingdom.getEnemies() > 3) {
            fmUnalives += 7;
        }
        if (ruler.getAge() <= 30 || kingdom.getEnemies() > 2 || kingdom.getAlliances() > 2) {
            int pointsAdded = 30 - ruler.getAge();
            marriage += 7 + pointsAdded;
        }
        if (ruler.getAge() < 19 || ruler.getAge() > 49) {
            consort += 5;
        }
        if (kingdom.getAlliances() > 2) {
            feast += 6;
            festival += 6;
        }
        if (ruler.getAge() < 23) {
            alliance += 10;
        }
        if (kingdom.getYearsInPower() < 3 || ruler.getAge() < 20 || ruler.getAge() > 53 || kingdom.getEnemies() > 3) {
            riot += 7;
        }
    }

    public void eventChooser(Kingdom kingdom) {
        if (kingdom.getCurrentRuler().getAge() > 29 && kingdom.getCurrentRuler().isMarriage() == false) {
            Scanner input = new Scanner(System.in);
            People lover = new People();
            nameOfEvent = "Congratulations on the Ruler's Marriage!";
            System.out.println("EVENT: " +nameOfEvent);
            kingdom.getCurrentRuler().setMarriage(true);
            int range = (kingdom.getCurrentRuler().getAge() + 3) - (kingdom.getCurrentRuler().getAge() - 5) + 1;
            int age = (int) ((Math.random() * range) + (kingdom.getCurrentRuler().getAge() - 5));
            lover.setAge(age);
            System.out.println("What is the persons name?");
            String name = input.nextLine();
            lover.setName(name);
            if (kingdom.getCurrentRuler().getGender() == 'M') {
                lover.setGender('F');
            } else {
                lover.setGender('M');
            }
            lover.setRole("spouse");
            lover.setMarriage(true);
            kingdom.getCurrentRuler().getLovers().add(lover);
        }
        probability(kingdom);
        int randomNumber = (int) (Math.random() * 240) + 1;
        Levels baseLevel = new Levels();
        if (randomNumber <= famine) {
            //take the level from the kingdom, and you compare it to the predetermined levels in the level class
            // then you take the amount over the base level (people [from kingdom] - level base) and make that the maximum number
            int currentLevel = kingdom.getLevel();
            int numberOfPeople = kingdom.getPeople();
            int compareLevel = baseLevel.getLevelBase(currentLevel);
            boolean bonusBuildingMatch = false;
            randomNumber = (int) (Math.random() * (numberOfPeople - compareLevel));
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("farm")) {
                        bonusBuildingMatch = true;
                    }
                }
            }
            if (bonusBuildingMatch) {
                eventChange = randomNumber / 2;
                nameOfEvent = "A Famine has Occurred in the Kingdom. Luckily we have built more farms recently. ";
                System.out.println("EVENT: " +nameOfEvent + eventChange + " people passed");
            } else {
                eventChange = randomNumber;
                nameOfEvent = "A Famine has Occurred in the Kingdom. ";
                System.out.println("EVENT: " +nameOfEvent + eventChange + " people passed");
            }
            kingdom.setPeople(kingdom.getPeople() - randomNumber);
        } else if ((randomNumber <= famine + outbreak) && (randomNumber > famine)) {
            int currentLevel = kingdom.getLevel();
            int numberOfPeople = kingdom.getPeople();
            int compareLevel = baseLevel.getLevelBase(currentLevel);
            boolean bonusBuildingMatch = false;
            randomNumber = (int) (Math.random() * (numberOfPeople - compareLevel));
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("hospital")) {
                        bonusBuildingMatch = true;
                    }
                }
            }
            if (bonusBuildingMatch) {
                eventChange = randomNumber / 2;
                nameOfEvent = "An Outbreak has Occurred in the Kingdom. Luckily we have built more hospitals recently. ";
                System.out.println("EVENT: " +nameOfEvent + eventChange + " people passed");
            } else {
                eventChange = randomNumber;
                nameOfEvent = "An Outbreak has Occurred in the Kingdom. ";
                System.out.println("EVENT: " +nameOfEvent + eventChange + " people passed");
            }
            kingdom.setPeople(kingdom.getPeople() - randomNumber);
        } else if ((randomNumber <= illness + famine + outbreak) && (randomNumber > famine + outbreak)) {
            char coin = flipCoin();
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("church")) {
                        eventChange = 0;
                        nameOfEvent = "Your Ruler has caught an illness, but will recover with the church's help";
                        System.out.println("EVENT: " +nameOfEvent);
                        break;
                    }
                }
            } else {
                if (coin == 'H') {
                    eventChange = kingdom.getCurrentRuler().getHealth() / 2;
                    nameOfEvent = "Your Ruler has caught an illness, but will recover";
                    System.out.println("EVENT: " + nameOfEvent);
                    kingdom.getCurrentRuler().setHealth(kingdom.getCurrentRuler().getHealth() - eventChange);
                } else {
                    if (kingdom.getCurrentRuler().getChildren().size() < 1) {
                        eventChange = kingdom.getCurrentRuler().getHealth() / 2;
                        nameOfEvent = "Your Ruler has caught an illness, but will recover";
                        System.out.println("EVENT: " + nameOfEvent);
                        kingdom.getCurrentRuler().setHealth(kingdom.getCurrentRuler().getHealth() - eventChange);
                    } else {
                        eventChange = -500;
                        boolean foundHeir = false;
                        int heirNumber = 100;
                        ArrayList<People> relatives = new ArrayList<>();
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
                            nameOfEvent = "Your Ruler has caught an illness and has passed. " + kingdom.getCurrentRuler().getChildren().get(heirNumber).getName()
                                    + " is the new ruler";
                            System.out.println("EVENT: " + nameOfEvent);
                            if (kingdom.getCurrentRuler().getChildren().size() > 0) {
                                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                    if (kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                                        kingdom.getCurrentRuler().getChildren().get(i).setRole("relative");
                                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                                    }

                                }
                                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                    if (!kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                                        kingdom.getCurrentRuler().getChildren().get(i).setRole("relative");
                                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                                    }
                                }
                                kingdom.setCurrentRuler(kingdom.getCurrentRuler().getChildren().get(heirNumber));
                                kingdom.getCurrentRuler().setRole("ruler");
                            } else if (kingdom.getCurrentRuler().getRelatives().size() > 0) {
                                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                    if (kingdom.getCurrentRuler().getRelatives().get(i).isHeir() && !foundHeir) {
                                        heirNumber = i;
                                        foundHeir = true;
                                        kingdom.getCurrentRuler().getChildren().get(i).setSamePerson(true);
                                    }
                                }
                                if (heirNumber == 100) {
                                    heirNumber = 0;
                                    kingdom.getCurrentRuler().getChildren().get(0).setSamePerson(true);
                                }
                                nameOfEvent = "Your Ruler has caught an illness and has passed. " + kingdom.getCurrentRuler().getChildren().get(heirNumber).getName()
                                        + " is the new ruler";
                                System.out.println("EVENT: " + nameOfEvent);
                                kingdom.setCurrentRuler(kingdom.getCurrentRuler().getChildren().get(heirNumber));
                                kingdom.getCurrentRuler().setRole("ruler");
                            } else {
                                nameOfEvent = "Your Ruler has caught an illness and has passed. ";
                                System.out.println("EVENT: " + nameOfEvent);
                                System.out.println("There is no heir to the throne");
                                System.out.println("To add a new ruler go to Settings");
                            }

                        }
                    }
                }
            }
        } else if (randomNumber <= aAttempt + illness + famine + outbreak) {
            char coin = flipCoin();
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("church")) {
                        eventChange = 0;
                        System.out.println("Hello 1");
                        nameOfEvent = "There has been an Assassination attempt against your ruler. Luckily your ruler survived due to the church's help.";
                        System.out.println("EVENT: " +nameOfEvent);
                    }
                }
            } else {
                if (coin == 'H') {
                    eventChange = 0;
                    nameOfEvent = "There has been an Assassination attempt against your ruler. Luckily your ruler survived";
                    System.out.println("EVENT: " +nameOfEvent);
                } else {
                    if (kingdom.getCurrentRuler().getChildren().size() < 1) {
                        eventChange = 0;
                        nameOfEvent = "There has been an Assassination attempt against your ruler. Luckily your ruler survived";
                        System.out.println("EVENT: " +nameOfEvent);
                    }
                    else {
                        eventChange = -500;
                        boolean foundHeir = false;
                        int heirNumber = 100;
                        ArrayList<People> relatives = new ArrayList<>();
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
                                kingdom.getCurrentRuler().getChildren().get(0).setSamePerson(true);
                            }
                            nameOfEvent = "There has been an Assassination attempt against your ruler. Unfortunately your ruler has passed away. " +
                                    kingdom.getCurrentRuler().getChildren().get(heirNumber).getName() + " is the new ruler";
                            System.out.println("EVENT: " + nameOfEvent);
                            if (kingdom.getCurrentRuler().getChildren().size() > 0) {
                                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                    if (kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                                        kingdom.getCurrentRuler().getChildren().get(i).setRole("relative");
                                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                                    }

                                }
                                for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                    if (!kingdom.getCurrentRuler().getChildren().get(i).isHeir() && !kingdom.getCurrentRuler().getChildren().get(i).isSamePerson()) {
                                        kingdom.getCurrentRuler().getChildren().get(i).setRole("relative");
                                        relatives.add(kingdom.getCurrentRuler().getChildren().get(i));
                                    }
                                }
                            }
                            kingdom.setCurrentRuler(kingdom.getCurrentRuler().getChildren().get(heirNumber));
                            kingdom.getCurrentRuler().setRole("ruler");
                        } else if (kingdom.getCurrentRuler().getRelatives().size() > 0) {
                            for (int i = 0; i < kingdom.getCurrentRuler().getChildren().size(); i++) {
                                if (kingdom.getCurrentRuler().getRelatives().get(i).isHeir() && !foundHeir) {
                                    heirNumber = i;
                                    foundHeir = true;
                                }
                            }
                            if (heirNumber == 100) {
                                heirNumber = 0;
                            }
                            nameOfEvent =  "There has been an Assassination attempt against your ruler. Unfortunately your ruler has passed away. " + kingdom.getCurrentRuler().getChildren().get(heirNumber).getName()
                                    + " is the new ruler";
                            System.out.println("EVENT: " +nameOfEvent);
                            kingdom.setCurrentRuler(kingdom.getCurrentRuler().getChildren().get(heirNumber));
                            kingdom.getCurrentRuler().setRole("ruler");
                        } else {
                            nameOfEvent =  "There has been an Assassination attempt against your ruler. Unfortunately your ruler has passed away. ";
                            System.out.println("EVENT: " +nameOfEvent);
                            System.out.println("There is no heir to the throne");
                            System.out.println("To add a new ruler go to Settings");
                        }

                    }
                }
            }
        } else if ((randomNumber <= fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > illness + famine + outbreak)) {
            // choose a random family member and then flip a coin to see if they pass
            ArrayList<People> familyMembers = new ArrayList<>();
            familyMembers.addAll(kingdom.getCurrentRuler().getLovers());
            familyMembers.addAll(kingdom.getCurrentRuler().getChildren());
            int randomMember = (int) (Math.random() * familyMembers.size());
            char coin = flipCoin();
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("church")) {
                        eventChange = 0;
                        System.out.println("hello 4");
                        nameOfEvent = "There has been an Assassination attempt against " + familyMembers.get(randomMember).getName()
                                + ". Luckily they survived with the church's help.";
                        System.out.println("EVENT: " +nameOfEvent);
                    }
                }
            } else {
                if (coin == 'H') {
                    eventChange = 0;
                    System.out.println("hello 5");
                    nameOfEvent = "There has been an Assassination attempt against " + familyMembers.get(randomMember).getName()
                            + ". Luckily they survived.";
                    System.out.println("EVENT: " +nameOfEvent);
                } else {
                    System.out.println("Hello 6");
                    eventChange = -500;
                    nameOfEvent = "There has been an Assassination attempt against " + familyMembers.get(randomMember).getName()
                            + " Unfortunately they did not survived";
                    System.out.println("EVENT: " +nameOfEvent);
                    if (familyMembers.get(randomMember) == kingdom.getCurrentRuler().getLovers().get(0)) {
                        kingdom.getCurrentRuler().getLovers().remove(0);
                        kingdom.getCurrentRuler().getLovers().add(new People());
                        kingdom.getCurrentRuler().setMarriage(false);
                    } else {
                        if (randomMember < familyMembers.size()) {
                            kingdom.getCurrentRuler().getLovers().remove(randomMember);
                        } else {
                            randomMember = randomMember - (familyMembers.size() - 1);
                            kingdom.getCurrentRuler().getChildren().remove(randomMember);
                        }
                    }
                }
            }
        } else if ((randomNumber <= war + fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > fmUnalives + aAttempt + illness + famine + outbreak)) {
            char coin = flipCoin();
            int points = baseLevel.getLevelBase(kingdom.getLevel() + 1) / 4;
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("barracks")) {
                        eventChange = points;
                        nameOfEvent = "Congratulation! You have won a war ";
                        if (kingdom.getEnemies() > 0) {
                            kingdom.setEnemies(kingdom.getEnemies() - 1);
                        }
                        System.out.println("EVENT: " +nameOfEvent + eventChange + " people came to the kingdom");
                        kingdom.setPeople(kingdom.getPeople() + points);
                    }
                }
            } else {
                if (coin == 'H') {
                    eventChange = points;
                    nameOfEvent = "Congratulation! You have won a war ";
                    if (kingdom.getEnemies() > 0) {
                        kingdom.setEnemies(kingdom.getEnemies() - 1);
                    }
                    System.out.println("EVENT: " +nameOfEvent + eventChange + " people came to the kingdom");
                    kingdom.setPeople(kingdom.getPeople() + points);
                } else {
                    //if the points make you go down a level then stop that from happening
                    // the points can only go up by a certain amount
                    // Level 1 = 0
                    // People: 15
                    // Points: 25
                    //
                    if (points > baseLevel.getLevelBase(kingdom.getLevel()) - kingdom.getPeople()) {
                        eventChange = (baseLevel.getLevelBase(kingdom.getLevel()) - kingdom.getPeople()) * -1;
                    } else {
                        eventChange = points * -1;
                    }
                    nameOfEvent = "Unfortunately You have lost a war ";
                    if (kingdom.getAlliances() > 0) {
                        kingdom.setAlliances(kingdom.getAlliances() - 1);
                    }
                    System.out.println("EVENT: " +nameOfEvent + eventChange + " people left the kingdom");
                    kingdom.setPeople(kingdom.getPeople() + points);
                }
            }
        } else if ((randomNumber <= consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > fmUnalives + aAttempt + illness + famine + outbreak)) {
            // not married
            // if married
            People lover = new People();
            Scanner input = new Scanner(System.in);

            if (!kingdom.getCurrentRuler().isMarriage()) {
                nameOfEvent = "Congratulations on the Ruler's Marriage!";
                System.out.println("EVENT: " +nameOfEvent);
                kingdom.getCurrentRuler().setMarriage(true);
                int range = (kingdom.getCurrentRuler().getAge() + 3) - (kingdom.getCurrentRuler().getAge() - 5) + 1;
                int age = (int) ((Math.random() * range) + (kingdom.getCurrentRuler().getAge() - 5));
                lover.setAge(age);
                System.out.println("What is the persons name?");
                String name = input.nextLine();
                lover.setName(name);
                if (kingdom.getCurrentRuler().getGender() == 'M') {
                    lover.setGender('F');
                } else {
                    lover.setGender('M');
                }
                lover.setRole("spouse");
            } else {
                nameOfEvent = "Someone else has caught the Ruler's eye";
                int range = (kingdom.getCurrentRuler().getAge() + 3) - (kingdom.getCurrentRuler().getAge() - 5) + 1;
                int age = (int) ((Math.random() * range) + (kingdom.getCurrentRuler().getAge() - 5));
                lover.setAge(age);
                System.out.println("EVENT: " +nameOfEvent);
                System.out.println("What is the persons name?");
                String name = input.nextLine();
                lover.setName(name);
                if (kingdom.getCurrentRuler().getGender() == 'M') {
                    lover.setGender('F');
                } else {
                    lover.setGender('M');
                }
                lover.setRole("consort");
            }
            lover.setHealth(500);
            lover.setMarriage(true);

            if (lover.getRole().equals("spouse")) {
                kingdom.getCurrentRuler().getLovers().add(0, lover);
            } else {
                kingdom.getCurrentRuler().getLovers().add(lover);
            }
        } else if ((randomNumber <= child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak)) {
            // find gender of child
            // name child
            // who is the other parent
            char coin = flipCoin();
            Scanner input = new Scanner(System.in);
            String name;
            People child = new People();
            int loverNumber = (int) (Math.random() * kingdom.getCurrentRuler().getLovers().size());
            if (coin == 'H') {
                nameOfEvent = "Congratulation! " + kingdom.getCurrentRuler().getLovers().get(loverNumber).getName() + " has had a baby boy.";
                System.out.println("EVENT: " +nameOfEvent);
                System.out.println("What will you name him?");
                name = input.nextLine();
                child.setGender('M');
            } else {
                nameOfEvent = "Congratulation! " + kingdom.getCurrentRuler().getLovers().get(loverNumber).getName() + " has had a baby girl.";
                System.out.println("EVENT: " +nameOfEvent);
                System.out.println("What will you name her?");
                name = input.nextLine();
                child.setGender('F');
            }

            if (loverNumber == 0) {
                child.setHeir(true);
            }

            child.setRole("child");
            child.setHealth(500);
            child.setName(name);

            kingdom.getCurrentRuler().getChildren().add(child);
        } else if (randomNumber <= festival + child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) {
            // 2 times the XP for that year
            eventChange = 2;
            nameOfEvent = "A Festival is being held at the Kingdom this year! ";
            System.out.println("EVENT: " +nameOfEvent + "There is double the amount of people usually");
        } else if (randomNumber <= feast + festival + child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) {
            // adds 100 back to health
            eventChange = 100;
            nameOfEvent = "Your ruler has held a massive hunt this year!";
            System.out.println("EVENT: " +nameOfEvent + " Your ruler's health has gone up by 100 points, if they are not at 500 already");
            if (kingdom.getCurrentRuler().getHealth() < 500) {
                if (kingdom.getCurrentRuler().getHealth() > 400) {
                    eventChange = 500 - kingdom.getCurrentRuler().getHealth();
                }
                kingdom.getCurrentRuler().setHealth(kingdom.getCurrentRuler().getHealth() + eventChange);
            }
        } else if ((randomNumber <= alliance + feast + festival + child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak)) {
            // 200 XP for that year
            int points = baseLevel.getLevelBase(kingdom.getLevel() + 1) / 4;
            if (kingdom.getBonusBuildings().size() > 0) {
                for (int i = 0; i < kingdom.getBonusBuildings().size(); i++) {
                    if (kingdom.getBonusBuildings().get(i).getName().equals("trade post")) {
                        eventChange = points * 2;
                        nameOfEvent = "Your kingdom has formed another alliance! And more people are coming in due to the trade post. ";
                        System.out.println("EVENT: " +nameOfEvent + eventChange + " people came to the kingdom");
                        kingdom.setPeople(kingdom.getPeople() + eventChange);
                        if (kingdom.getEnemies() > 0) {
                            kingdom.setEnemies(kingdom.getEnemies() - 1);
                        }
                    }
                }
            } else {
                eventChange = points;
                nameOfEvent = "Your kingdom has formed another alliance! ";
                System.out.println("EVENT: " +nameOfEvent + eventChange + " people came to the kingdom");
                kingdom.setPeople(kingdom.getPeople() + eventChange);
            }
            kingdom.setAlliances(kingdom.getAlliances() + 1);
        } else if ((randomNumber <= riot + alliance + feast + festival + child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak) && (randomNumber > alliance + feast + festival + child + consort + marriage + war + fmUnalives + aAttempt + illness + famine + outbreak)) {
            int points = baseLevel.getLevelBase(kingdom.getLevel() + 1) / 5;
            eventChange = points * -1;
            nameOfEvent = "A Riot has broke out in the kingdom. ";
            System.out.println("EVENT: " + nameOfEvent + (eventChange * -1) + " people have left the kingdom");
            kingdom.setPeople(kingdom.getPeople() + eventChange);
        } else {
            if (nameOfEvent.contains("Marriage")) {
                nameOfEvent = "Nothing else happened this year";
                System.out.println("EVENT: " + nameOfEvent);
            } else {
                nameOfEvent = "Nothing new has happened this year";
                System.out.println("EVENT: " + nameOfEvent);
            }
        }
    }

    private char flipCoin() {
        double random = Math.random();
        char outcome;
        if (random > .4) {
            outcome = 'H';
        } else {
            outcome = 'T';
        }
        return outcome;
    }
}
