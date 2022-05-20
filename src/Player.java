import javax.swing.*;

public class Player {
    private int hp = 5;
    private int score = 0;
    private String nick = "unknown";
    private static Player instance;

    public Player(String nick) {
        hp = 10;
        score = 0;
        this.nick = nick;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    //endregion

    public static Player getInstance(){
        return instance;
    }
    public void takeDamage(){
        if(--hp <= 0) {
            JOptionPane.showMessageDialog(null, "Score: ", "YOU DIED", JOptionPane.PLAIN_MESSAGE);
            Game game = Game.getInstance();
            Score s = new Score(score, nick,game.getTime(),game.getDifficulty());
        }
    }
}
