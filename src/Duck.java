import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Duck extends JLabel implements MouseListener {
    private static long waitTime = 50;
    private int hp = 5;
    private int points;
    private boolean goRight = false;

    private static Player player = Player.getInstance();
    private static Game game = Game.getInstance();

    private Thread moveDuck;

    public Duck() {
        super(new ImageIcon("Images\\duck0.png"));
        if(Math.random()>0.5) goRight = true;

        switch ((int)(Math.random()*4)){
            case 0->{
                if(goRight) setIcon(new ImageIcon("Images\\duck0Right.png"));
                else setIcon(new ImageIcon("Images\\duck0.png"));
                hp = 1;
                points = 1;
            }
            case 1->{
                if(goRight) setIcon(new ImageIcon("Images\\duck1Right.png"));
                else setIcon(new ImageIcon("Images\\duck1.png"));
                hp = 3;
                points = 2;
            }
            case 2->{
                if(goRight) setIcon(new ImageIcon("Images\\duck2Right.png"));
                else setIcon(new ImageIcon("Images\\duck2.png"));
                hp = 5;
                points = 4;
            }
            case 3->{
                if(goRight) setIcon(new ImageIcon("Images\\duck3Right.png"));
                else setIcon(new ImageIcon("Images\\duck3.png"));
                hp = 10;
                points = 8;
            }
        }

        moveDuck = new Thread(()->{
            int x;
            if(goRight) x = -32;
            else x = game.getWidth();
            int y = (int)(Math.random()*(game.getHeight()-63)+30);// game.getHeight() = 500

            while (!Thread.interrupted() && !player.isDead()){
                if(goRight) x++;
                else x--;
                setBounds(x,y,32,32);
                if((goRight && x>game.getWidth())||(!goRight && x<-32)) {
                    player.takeDamage();
                    game.remove(this);
                    return;
                }
                try {
                    Thread.sleep(waitTime);
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

    public static long getWaitTime() {
        return waitTime;
    }

    public static void setWaitTime(long waitTime) {
        Duck.waitTime = waitTime;
    }

    //endregion
    @Override
    public void mousePressed(MouseEvent e) {
        hp -= Player.getInstance().getDmg();
        if(hp<=0) {
            player.addScore(points);
            game.remove(this);
            game.revalidate();
            game.repaint();
            moveDuck.interrupt();
        }
        System.out.println("duck hp: " + hp);
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
