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
        Duck.setGame(this);
        Duck.setPlayer(player);
        long sleep = 1000/(difficulty+1);

        getContentPane().setBackground(new Color(108, 241, 205));
        JLabel timeLabel = new JLabel("time: 0s");
        timeLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        timeLabel.setBounds(5,5,100,100);
        timeLabel.setVerticalAlignment(SwingConstants.TOP);
        add(timeLabel);

        Thread spawnDuck = new Thread(()->{
            while (!Thread.interrupted() && !player.isDead()){
                add(new Duck(10));
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        spawnDuck.start();

        Thread countTime = new Thread(()->{
            while (!Thread.interrupted() && !player.isDead()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                timeLabel.setText("time: " + ++time + "s");
            }
        });
        countTime.start();

        setLayout(null);
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
