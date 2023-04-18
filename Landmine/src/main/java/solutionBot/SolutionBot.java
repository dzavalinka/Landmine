package solutionBot;

import gameLogic.Board;

import java.util.ArrayList;

public class SolutionBot {
    public Board board;

    public SolutionBot(Board board) {
        this.board = board;
    }

    private void SolveBoard() {
        ArrayList<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i <= board.allCells; i++) {
            if (board.field[i].condition < 10 && board.field[i].condition > 1) {
                Cluster newClust = new Cluster(board.field[i].condition);
                for (int j = 0; j < board.field[i].neighbours.size(); j++) {
                    if (board.field[i].neighbours.get((j)).condition == 10) {
                        newClust.cells.add(board.field[i].neighbours.get((j)));
                    }
                    if (board.field[i].neighbours.get((j)).condition == 9) {
                        newClust.mines--;
                    }
                }
            }
        }
        for (int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).cells.size() == 0) {
                clusters.remove(i);
                i--;
            }
        }

        boolean repeat = true;
        do {

            for (int i = 0; i < clusters.size() - 1; i++) {
                Cluster clusterI = clusters.get(i);
                for (int j = i + 1; j < clusters.size(); j++) {
                    Cluster clusterJ = clusters.get(j);
                    if (clusterI.equals(clusterJ)) {
                        clusters.remove(j--);
                        break;
                    }
                    Cluster parent;
                    Cluster child;
                    if (clusterI.cells.size() > clusterJ.cells.size()) {
                        parent = clusterI;
                        child = clusterJ;
                    } else {
                        parent = clusterJ;
                        child = clusterI;
                    }
                    if (parent.cells.contains(child.cells)) {
                        Cluster.Substract(parent, child);
                        repeat = true;
                    } else {
                        Cluster tmp;
                        if (clusterI.mines > clusterJ.mines) {
                            tmp = Cluster.Intersect(clusterI, clusterJ);
                        } else {
                            tmp = Cluster.Intersect(clusterJ, clusterI);
                        }
                        if (tmp.cells.size() > 0) {
                            clusters.add(tmp);
                            repeat = true;
                        }
                    }
                }

            }
        } while (repeat);

    }
}
