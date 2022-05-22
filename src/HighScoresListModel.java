import javax.swing.*;

public class HighScoresListModel extends AbstractListModel<Score> {

    Score[] scores = Score.getScores().toArray(new Score[0]);
    @Override
    public int getSize() {
        return scores.length;
    }

    @Override
    public Score getElementAt(int index) {
        return scores[index];
    }
}
