import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyWindow::new);
        Score.load();

        System.out.println("lena");
        System.out.println("hubert");
        System.out.println("aaaa");
    }
}
