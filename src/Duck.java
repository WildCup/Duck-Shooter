import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Duck extends JLabel implements MouseListener {
    private long speed = 50;
    private int hp = 5;
    private int points;

    private static Player player = Player.getInstance();
    private static Game game = Game.getInstance();

    public Duck(int speed) {
        super(new ImageIcon("duck0.png"));
        this.speed = speed;

        switch ((int)(Math.random()*3)){
            case 0->{
                setIcon(new ImageIcon("duck0.png"));
                hp = 1;
                points = 1;
            }
            case 1->{
                setIcon(new ImageIcon("duck1.png"));
                hp = 3;
                points = 2;
            }
            case 2->{
                setIcon(new ImageIcon("duck2.png"));
                hp = 5;
                points = 4;
            }
        }

        Thread moveDuck = new Thread(()->{
            int x = 0;
            int y = (int)(Math.random()*(500-69));//500 = Game.instance.getHeight()
            while (!Thread.interrupted() && !player.isDead()){
                setBounds(++x,y,32,32);
                if(x>game.getWidth()) {
                    player.takeDamage();
                    game.remove(this);
                    return;
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        moveDuck.start();

        addMouseListener(this);
    }

    //region getters setters
    public static void setPlayer(Player player) {
        Duck.player = player;
    }

    public static void setGame(Game game) {
        Duck.game = game;
    }

    //endregion
    @Override
    public void mousePressed(MouseEvent e) {
        hp--;
        if(hp<=0) {
            player.addScore(points);
            game.remove(this);
            game.revalidate();
            game.repaint();
        }
    }
    //region unneeded
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //endregion
}
