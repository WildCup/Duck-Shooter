import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

//        JMenuItem menuItem = new JMenuItem("Say Hello");
//        KeyStroke ctrlH = KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
//        menuItem.setAccelerator(ctrlH);
//        menuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("Hello, World");
//            }
//        });
//        menuItem.setBounds(100,5,40,20);
//        add(menuItem);

        JButton button = new JButton("hello");
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(MyWindow::new);
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
