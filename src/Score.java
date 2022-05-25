import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Score implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int score;
    private final String nick;
    private final LocalDate date;
    private final int time;
    private final int difficulty;

    private static List<Score> scores = new LinkedList<>();

    public Score(int score, String nick, int time, int difficulty) {
        this.score = score;
        if (nick.equals("") || nick.equals(" ")) this.nick = "unknown";
        else this.nick = nick;
        date = LocalDate.now();
        this.time = time;
        this.difficulty = difficulty;

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).score < score) {
                scores.add(i, this);
                save();
                return;
            }
        }
        scores.add(this);
        save();
    }

    static void printScores() {
        for (Score s : scores) {
            System.out.println(s.score + " " + s.nick);
        }
        System.out.println("................");
    }

    //region getters setters
    public static List<Score> getScores() {
        return scores;
    }

    public static void setScores(List<Score> scores) {
        Score.scores = scores;
    }
    //endregion

    @Override
    public String toString() {
        return "Score:" + score + " \n" +
                nick + " " + date + " \n" +
                "time: " + time + "s " +
                "lvl:" + difficulty;
    }
    public String toStringNice() {
        String returnMe = "Score:" + score;
        while (returnMe.length()<13) returnMe += " ";
        returnMe += nick;
        while (returnMe.length()<27) returnMe += " ";
        returnMe += "time: " + time + "s";
        while (returnMe.length()<40) returnMe += " ";
        returnMe += "lvl:" + difficulty;
        while (returnMe.length()<48) returnMe += " ";
        returnMe += date;

        return returnMe;
    }

    public static void save() {
        try {
            FileOutputStream stream = new FileOutputStream("save.txt");
            ObjectOutputStream stream2 = new ObjectOutputStream(stream);
            stream2.writeObject(new SaveData());
            stream2.flush();
            stream2.close();
        } catch (IOException e) {
            System.out.println("saving didn't work ?");
        }
    }

    public static void load() {
        try {
            FileInputStream stream = new FileInputStream("save.txt");
            ObjectInputStream stream2 = new ObjectInputStream(stream);
            SaveData saveData = (SaveData) stream2.readObject();
            Score.scores.clear();
            Score.scores = saveData.getScores();
            stream2.close();
            Score.setScores(saveData.getScores());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File not found ?");
        }
    }
}

class SaveData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Score> scores;

    public SaveData() {
        scores = Score.getScores();
    }

    public List<Score> getScores() {
        return scores;
    }
}
