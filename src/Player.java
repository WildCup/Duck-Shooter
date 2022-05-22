import javax.swing.*;

public class Player {
    private int hp;
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
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void addScore(int points) {
        score += points;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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
        if(--hp <= 0) {
            Game game = Game.getInstance();
            Score s = new Score(score, nick,game.getTime(),game.getDifficulty());
            isDead = true;
            JOptionPane.showMessageDialog(null, s, "YOU DIED", JOptionPane.PLAIN_MESSAGE);
            MyWindow.goToMenu();
        }
        System.out.println(hp);
    }
}
