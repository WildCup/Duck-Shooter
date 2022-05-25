import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JLayeredPane  {
    private static Game instance;
    private int time;
    private int difficulty;
    private Player player;
    private JProgressBar hpBar;
    private JLabel scoreLabel;
    private JButton upgradeButton;

    public Game(int difficulty, String nick) { //difficulty = number of trees and size of the game?
        instance = this;
        this.difficulty=difficulty;
        player = new Player(nick);
        Duck.setGame(this);
        Duck.setPlayer(player);

        setBackground(new Color(108, 241, 205));
        setOpaque(true);

        //region time
        JLabel timeLabel = new JLabel("time: 0s");
        timeLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        timeLabel.setBounds(5,5,100,100);
        timeLabel.setVerticalAlignment(SwingConstants.TOP);
        add(timeLabel,3);
        //endregion

        //region menuButton
        JButton button = new JButton("MENU");
        button.setFont(new Font("MV Boli",Font.PLAIN,13));
        button.setBackground(new Color(161, 161, 161, 255));
        button.setToolTipText("Go to the main menu");
        button.setBounds(75,5,73,22);
        button.setFocusable(false);
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setDead(true);
                MyWindow.goToMenu();
                System.out.println("go to menu");
            }
        };

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK),"goBack");
        getActionMap().put("goBack",action);

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
        Tree.clear();
        add(new Tree(),2);
        add(new Tree(),2);
        add(new Tree(),2);
        add(new Tree(),2);

        Thread spawnCloud = new Thread(()->{
            for (int i = 0; i < 10 + difficulty*2; i++) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                add(new Cloud(),2);
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
        hpBar.setForeground(new Color(222, 54, 54));
        hpBar.setBounds(160,5,130,22);
        add(hpBar,3);
        //endregion

        //region upgrade
        JLabel gunLabel = new JLabel(new ImageIcon("Images\\gun.png"));
        gunLabel.setText("dmg: " + player.getDmg());
        gunLabel.setFont(new Font("MV Boli",Font.PLAIN,13));

        gunLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        gunLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        gunLabel.setBounds(310,5,45,60);
        add(gunLabel,3);

        upgradeButton = new JButton();
        upgradeButton.setIcon(new ImageIcon("Images\\up.png"));
        upgradeButton.setFont(new Font(Font.SERIF,Font.BOLD,10));
        upgradeButton.setBackground(new Color(161, 61, 161, 255));
        upgradeButton.setForeground(Color.black);
        upgradeButton.setToolTipText("Upgrade weapon. Cost: " + player.getDmg() * 10);
        upgradeButton.setBounds(360,5,30,30);
        upgradeButton.setFocusable(false);
        upgradeButton.setEnabled(false);
        upgradeButton.addActionListener((e -> {
            player.upgradeDmg();
            if(player.getDmg() >= 5){
                upgradeButton.setToolTipText("level MAX");
                gunLabel.setText("dmg: " + player.getDmg()); //+ " (MAX)"
                upgradeButton.setEnabled(false);
            } else {
                upgradeButton.setToolTipText("Upgrade weapon. Cost: " + player.getDmg() * 10);
                gunLabel.setText("dmg: " + player.getDmg());
            }
        }));
        add(upgradeButton,3);
        //endregion

        //region score
        scoreLabel = new JLabel("points: 0");
        scoreLabel.setFont(new Font("MV Boli",Font.PLAIN,13));
        scoreLabel.setVerticalAlignment(SwingConstants.TOP);
        scoreLabel.setBounds(400,5,100,100);
        add(scoreLabel,3);
        //endregion

        setLayout(null);
    }

    //region getter and setter
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

    public JButton getUpgradeButton() {
        return upgradeButton;
    }
    //endregion
}
