package gameLogic;

import java.util.List;

public class Cell {
    public int condition;
    public List<Cell> neighbours;


    public boolean OpenCell() {
        if (condition != 10) {
            return true;
        }
        if (condition == 19) {
            this.condition = 9;
            return false;
        } else {

            return true;
        }
    }
}
