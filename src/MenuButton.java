import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
    Color backgroundColor = new Color(245, 245, 245);
    Color hoverBackgroundColor = new Color(218, 218, 218);
    Color pressedBackgroundColor = new Color(189, 189, 189);
    public MenuButton(String text) {
        super(text);
        super.setContentAreaFilled(false);

        setPreferredSize(new Dimension(200,60));
        setFont(new Font("MV Boli",Font.BOLD,20));
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(backgroundColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
