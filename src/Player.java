import javax.swing.*;

public class Player {
    private int hp;
    private int dmg = 1;
    private int score;
    private String nick = "unknown";
    private boolean isDead;
    private static Player instance;

    public Player(String nick) {
        hp = 10;
        score = 0;
        this.nick = nick;
        isDead = false;
        instance=this;
    }
    public Player() {
        this("unknown");
    }

    //region getters and setters
    public int getDmg() {
        return dmg;
    }

    public void upgradeDmg() {
        int cost = -dmg * 10;
        dmg++;
        addScore(cost);
    }

    public void addScore(int points) {
        score += points;
        Game.getInstance().getScoreLabel().setText("points: " + score);
        if(dmg < 5) Game.getInstance().getUpgradeButton().setEnabled(score >= dmg * 10);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    //endregion

    public static Player getInstance(){
        return instance;
    }
    public void takeDamage(){
        Game.getInstance().getHpBar().setValue(--hp);
        if(hp <= 0) {
            Game game = Game.getInstance();
            int totalScore = score;
            for (int i = 1; i < dmg; i++)totalScore+=10*i;
            totalScore *= ((game.getDifficulty()+1)/3 * game.getTime());
            Score s = new Score(totalScore, nick,game.getTime(),game.getDifficulty());
            isDead = true;
            JOptionPane.showMessageDialog(null, s, "YOU DIED", JOptionPane.PLAIN_MESSAGE);
            MyWindow.goToMenu();
        }
    }
}
