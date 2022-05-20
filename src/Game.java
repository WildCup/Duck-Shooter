import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private static Game instance;
    private int time;
    private int difficulty;
    private Player player;

    public Game(int difficulty, String nick) { //difficulty = number of trees and size of the game?
        instance = this;
        player = new Player(nick);
        this.difficulty = difficulty;

        getContentPane().setBackground(new Color(108, 241, 205));
        JLabel timeLabel = new JLabel("time: 0s");
        timeLabel.setFont(new Font("MV Boli",Font.PLAIN,10));
        timeLabel.setBounds(5,5,20,20);
        add(timeLabel);

        Thread spawnDuck = new Thread(()->{
            while (!Thread.interrupted()){
                add(new Duck(10));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        spawnDuck.start();

        Thread countTime = new Thread(()->{
            while (!Thread.interrupted()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                timeLabel.setText("time: " + ++time + "s");
            }
        });
        countTime.start();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static Game getInstance() {
        return instance;
    }

    public int getTime() {
        return time;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
