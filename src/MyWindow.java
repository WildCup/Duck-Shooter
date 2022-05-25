import javax.swing.*;
import javax.swing.plaf.multi.MultiButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    private Game game;
    private Color color = new Color(110, 234, 234);
    private static MyWindow instance;
    private JPanel mainPanel;

    public MyWindow() {
        instance = this;
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(3, 3, 3, 3);
        mainPanel.setBackground(color);

        JButton playButton = new MenuButton("New Game");
        JButton scoreButton = new MenuButton("High Score");
        JButton exitButton = new MenuButton("Exit");

        //region type your nick
        JPanel nickPanel = new JPanel(new FlowLayout());
        nickPanel.setPreferredSize(new Dimension(200, 60));
        nickPanel.setBackground(color);

        JLabel label = new JLabel("Nick:");
        label.setFont(new Font("MV Boli", Font.BOLD, 20));

        JTextField textField = new JTextField("unknown");
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setFont(new Font("MV Boli", Font.PLAIN, 16));
        textField.setCaretColor(new Color(1, 1, 1, 25));
        textField.setBackground(color);
        textField.setBorder(BorderFactory.createEtchedBorder(new Color(255, 0, 0, 100), new Color(0, 60, 255, 100)));

        nickPanel.add(label);
        nickPanel.add(textField);
        mainPanel.add(nickPanel, gbc);
        //endregion

        playButton.addActionListener((e) -> {
            String[] ss = {"easy", "normal", "hard"};
            int o = JOptionPane.showOptionDialog(this, "Choose difficulty level", "Difficulty",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, ss, ss[0]);
            if (o != -1) {
                game = new Game(o, textField.getText());
                remove(mainPanel);
                add(game);
                revalidate();
                repaint();
            }
        });

        scoreButton.addActionListener((e) -> {
            JPanel scoresPanel = new JPanel(new BorderLayout());
            JLabel label1 = new JLabel("<html><div style='text-align: center;'><h1>HIGH SCORES <br>CURRENTLY DO NOT WORK :C</h1>stay tuned</div></html>");

            label1.setHorizontalAlignment(SwingConstants.CENTER);
            scoresPanel.add(label1);

            //Jlist
            JList list = new JList(new HighScoresListModel());
            list.setCellRenderer(new ListCellRenderer<Score>() {
                @Override
                public Component getListCellRendererComponent(JList list, Score value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = new JLabel((index + 1) + ".  " + value.toStringNice());
                    String text = (index + 1) + ".";
                    while (text.length()<4) text += " ";
                    text += value.toStringNice();
                    label.setText(text);
                    label.setFont(new Font("Courier New",Font.PLAIN,13));
                    return label;
                }
            });
            scoresPanel.add(new JScrollPane(list));

            JButton button = new JButton("GO BACK");
            button.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
            button.addActionListener((ev) -> {
                remove(scoresPanel);
                add(mainPanel);
                revalidate();
                repaint();
            });
            scoresPanel.add(button, BorderLayout.PAGE_END);

            remove(mainPanel);
            add(scoresPanel);
            revalidate();
            repaint();
        });

        exitButton.addActionListener((e) -> System.exit(0));

        mainPanel.add(playButton, gbc);
        mainPanel.add(scoreButton, gbc);
        mainPanel.add(exitButton, gbc);

        add(mainPanel);

        setSize(500, 500);
        setTitle("Duck Shooter");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void goToMenu() {
        instance.remove(instance.game);
        instance.add(instance.mainPanel);
        instance.revalidate();
        instance.repaint();
    }
}
