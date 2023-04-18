package player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerInfo {
    private final String PATH = "C:\\Users\\dzava\\IdeaProjects\\Landmine\\src\\resources\\records.txt";
    private int bestTimeEasy;
    private int bestTimeMedium;
    private int bestTimeHard;

    public int getBestTimeEasy() {
        return bestTimeEasy;
    }

    public int getBestTimeMedium() {
        return bestTimeMedium;
    }

    public int getBestTimeHard() {
        return bestTimeHard;
    }

    public PlayerInfo() throws IOException {
        FileReader reader = new FileReader(PATH);
        String content = new String();
        int c;
        while ((c=reader.read()) != -1) {
            content += (char)c;
        }
        bestTimeEasy = Integer.parseInt(content.split(" ")[0]);
        bestTimeMedium = Integer.parseInt(content.split(" ")[1]);
        bestTimeHard = Integer.parseInt(content.split(" ")[2]);
    }

    public void UpdateRecords (int difficulty, int newTime) throws IOException {
        FileWriter fw = new FileWriter(PATH);
        if (difficulty == 0 && newTime < bestTimeEasy) {
            bestTimeEasy = newTime;
        }
        if (difficulty == 1 && newTime < bestTimeMedium) {
            bestTimeMedium = newTime;
        }
        if (difficulty == 2 && newTime < bestTimeHard) {
            bestTimeHard = newTime;
        }
        fw.write(bestTimeEasy + "\n" + bestTimeMedium + "\n" + bestTimeHard);
    }
}
