import javax.swing.*;
import java.awt.*;

public class HighScores extends JPanel {
    public HighScores(JPanel mainPanel) {
        JLabel label = new JLabel("<html><div style='text-align: center;'><h1>HIGH SCORES CURRENTLY DO NOT WORK :C</h1>stay tuned</div></html>");

        add(label);

        JButton button = new JButton("GO BACK");
        button.setFont(new Font(Font.SERIF,Font.PLAIN,20));
        button.addActionListener((ev)->{
            SwingUtilities.invokeLater(MyWindow::new);
            //dispose(frame);
        });
        add(button,BorderLayout.PAGE_END);
    }
}
