import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Tree extends JLabel {
    private static final int x = 50;
    private static final int y = 200;
    private static final Game game = Game.getInstance();

    private static List<Tree> trees = new LinkedList<>();

    public Tree() {
        super(new ImageIcon("Images\\tree.jpg"));
        setBounds(trees.size() *150 +(int)(Math.random()*40)-20,500-y-35,x,y);
        trees.add(this);
    }
    public static void moveDown(){
        for (Tree tree : trees) {
            tree.setBounds(tree.getX(),game.getHeight()+37-y-30,x,y);
        }
       if (game.getWidth() > trees.size() *150) game.add(new Tree());
    }
}

class Cloud extends JLabel {
    private static final int a = 85;
    private static final int b = 50;
    private static final Game game = Game.getInstance();

    public Cloud() {
        super(new ImageIcon("Images\\cloud.jpg"));

        Thread moveCloud = new Thread(()->{
            int x = -50;
            int y = (int)(Math.random()*60) + 25;

            while (!Thread.interrupted()){
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
}