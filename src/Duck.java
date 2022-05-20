import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Duck extends JLabel implements MouseListener {
    boolean isAlive = true;
    long speed = 50;
    int hp = 5;

    public Duck(int speed) {
        super(new ImageIcon("duck.png"));
        this.speed = speed;

        switch ((int)(Math.random()*3)){
            case 0->{
                setIcon(new ImageIcon("duck3.png"));
                hp = 1;
            }
            case 1->{
                setIcon(new ImageIcon("duck1.png"));
                hp = 3;
            }
            case 2->{
                setIcon(new ImageIcon("duck2.png"));
                hp = 5;
            }
        }

        Thread moveDuck = new Thread(()->{
            int x = 0;
            int y = (int)(Math.random()*(500-69));//500 = Game.instance.getHeight()
            while (!Thread.interrupted() && isAlive){
                setBounds(x,y,32,32);
                x++;
                if(x>Game.getInstance().getWidth()) {
                    Player.getInstance().takeDamage();
                    Game.getInstance().remove(this);
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

    @Override
    public void mousePressed(MouseEvent e) {
        hp--;
        System.out.println(hp);
        if(hp<=0) {
            Game game = Game.getInstance();
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
