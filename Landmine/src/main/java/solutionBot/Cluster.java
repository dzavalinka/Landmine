package solutionBot;

import gameLogic.Cell;

import java.util.ArrayList;

public class Cluster {
    public int mines;
    public ArrayList<Cell> cells;

    public Cluster(int mines) {
        this.mines = mines;
        this.cells = new ArrayList<>();
    }

    static void Substract(Cluster parent, Cluster child) {
        parent.mines -= child.mines;
        for (int i = 0; i <= child.cells.size(); i++) {
            parent.cells.remove(child.cells.get(i));
        }
    }

    static Cluster Intersect(Cluster c1, Cluster c2) {
        Cluster res = new Cluster(0);
        for (int i = 0; i <= c1.cells.size(); i++) {
            if (c2.cells.contains(c1.cells.get(i))) {
                res.cells.add(c1.cells.get(i));
            }
        }
        res.mines = c1.mines - (c2.cells.size() - res.cells.size());
        return res;
    }
}
