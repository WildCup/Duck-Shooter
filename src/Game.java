import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Game extends JPanel {
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

        setBackground(new Color(108, 241, 205));
        JLabel timeLabel = new JLabel("time: 0s");
        timeLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        timeLabel.setBounds(5,5,100,100);
        timeLabel.setVerticalAlignment(SwingConstants.TOP);
        add(timeLabel);

        JButton button = new JButton("Main menu");
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setDead(true);
                MyWindow.goToMenu();
            }
        };

        button.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK),"goBack");
        button.getActionMap().put("goBack",action);

        button.addActionListener(action);

        button.setBounds(100,5,70,20);
        add(button);

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
