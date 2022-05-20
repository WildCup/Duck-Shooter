import java.time.LocalDate;

public class Score {
    private int score;
    private String nick;
    private LocalDate date;
    private int time;
    private int difficulty;

    public Score(int score, String nick, int time, int difficulty) {
        this.score = score;
        this.nick = nick;
        date = LocalDate.now();
        this.time = time;
        this.difficulty = difficulty;
    }
}
