import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel {
    private static Game instance;
    private int time;
    private int difficulty;
    private Player player;
    private JProgressBar hpBar;
    private JLabel scoreLabel;

    public Game(int difficulty, String nick) { //difficulty = number of trees and size of the game?
        instance = this;
        this.difficulty=difficulty;
        player = new Player(nick);
        Duck.setGame(this);
        Duck.setPlayer(player);

        setBackground(new Color(108, 241, 205));

        //region time
        JLabel timeLabel = new JLabel("time: 0s");
        timeLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        timeLabel.setBounds(5,5,100,100);
        timeLabel.setVerticalAlignment(SwingConstants.TOP);
        add(timeLabel);
        //endregion

        //region menuButton
        JButton button = new JButton("menu");
        button.setBounds(100,5,75,22);
        button.setFocusable(false);
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
        add(button);
        //endregion

        Thread spawnDuck = new Thread(()->{
            long sleep = 3000/(difficulty+1);
            while (!Thread.interrupted() && !player.isDead()){
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    return;
                }
                add(new Duck());
            }
        });
        spawnDuck.start();

        Thread countTime = new Thread(()->{
            int i = 5;
            Duck.setWaitTime(50);
            while (!Thread.interrupted() && !player.isDead()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                timeLabel.setText("time: " + ++time + "s");
                if(--i<=0) {
                    Duck.setWaitTime(Math.max(10, Duck.getWaitTime() -10));
                    i=5;
                }
            }
        });
        countTime.start();

        //region obstacles
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Tree.moveDown();
            }
        });
        add(new Tree());
        add(new Tree());
        add(new Tree());
        add(new Tree());

        Thread spawnCloud = new Thread(()->{
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                add(new Cloud());
                i++;
            }
        });
        spawnCloud.start();
        //endregion

        //region hpBar
        hpBar = new JProgressBar(0,10);
        hpBar.setValue(10);
        hpBar.setStringPainted(true);
        hpBar.setFont(new Font("MV Boli",Font.PLAIN,13));
        hpBar.setForeground(Color.red);
        hpBar.setBounds(220,5,140,22);
        add(hpBar);
        //endregion

        //region score
        scoreLabel = new JLabel("score: 0");
        scoreLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        scoreLabel.setVerticalAlignment(SwingConstants.TOP);
        scoreLabel.setBounds(400,5,100,100);
        add(scoreLabel);
        //endregion

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

    public JProgressBar getHpBar() {
        return hpBar;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
