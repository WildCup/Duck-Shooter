import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class Tree extends JLabel implements MouseListener {
    private static final int a = 88;
    private static final int b = 112;
    private static final Game game = Game.getInstance();

    private static List<Tree> trees = new LinkedList<>();

    public Tree() {
        super(new ImageIcon("Images\\tree.png"));
        setBounds(trees.size() *150 +(int)(Math.random()*40)-20,500- b -35, a, b);
        trees.add(this);
        addMouseListener(this);
    }
    public static void moveDown(){
        for (Tree tree : trees) {
            tree.setBounds(tree.getX(),game.getHeight()+37- b -30, a, b);
        }
       if (game.getWidth() > trees.size() *150) game.add(new Tree());
    }

    public static void clear(){
        trees.clear();
    }
    //region unused
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("tree");
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

class Cloud extends JLabel implements MouseListener {
    private static final int a = 56;
    private static final int b = 24;
    private static final Game game = Game.getInstance();

    public Cloud() {
        super(new ImageIcon("Images\\cloud.png"));
        addMouseListener(this);

        Thread moveCloud = new Thread(()->{
            int x = -50;
            int y = (int)(Math.random()*280) + 25;

            while (!Thread.interrupted() && !Player.getInstance().isDead()){
                x++;
                setBounds(x,y,a,b);
                if(x>game.getWidth())
                    x = -50;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        moveCloud.start();
    }

    //region unused
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("cloud");
    }

    @Override
    public void mousePressed(MouseEvent e) {

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