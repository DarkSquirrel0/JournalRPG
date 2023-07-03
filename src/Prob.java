public class Prob {
    /*
    25 and younger is a young ruler
    50 and older is an old ruler
    At 60 your ruler dies
    ruler is married at 30 no matter what
    no matter what if the XP goes down you still stay at the base level

    famine(b): completely random
    outbreak(b): completely random
    illness(b): +older ruler
    assassination attempt(b): +/- age of ruler, + age of children, + more enemies
    family member unalives(b): + more enemies
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
    //here to show how many extra points there are
    private int nothing = 45;

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
    private void probability(Kingdom kingdom) {
        People ruler = kingdom.getCurrentRuler();
        if (ruler.getAge() >= 50) {
            illness += 10;
        }
        if (ruler.getAge() > 49 || ruler.getAge() < 20 || ruler.getChildren().get(0).getAge() > 19 || kingdom.getEnemies() > 2) {
            aAttempt += 5;
        }
        if (kingdom.getEnemies() > 3) {
            fmUnalives += 5;
        }
        if (ruler.getAge() <= 30 || kingdom.getEnemies() > 2 || kingdom.getAlliances() > 2) {
            int pointsAdded = 30 - ruler.getAge();
            marriage += 5 + pointsAdded;
        }
        if (ruler.getAge() < 19 || ruler.getAge() > 49) {
            consort += 3;
        }
        if (kingdom.getAlliances() > 2) {
            feast += 4;
            festival += 4;
        }
        if (ruler.getAge() < 23) {
            alliance += 7;
        }
        if (kingdom.getYearsInPower() < 3 || ruler.getAge() < 20 || ruler.getAge() > 53 || kingdom.getEnemies() > 3) {
            riot += 5;
        }
    }
}
