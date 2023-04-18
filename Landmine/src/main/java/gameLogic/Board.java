package gameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 15;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 11;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    public Cell[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    public int allCells;
    private final JLabel statusbar;

    private int size;
    private int columns;

    public Board(JLabel statusbar, int size) {

        this.statusbar = statusbar;
        this.size = size;
        this.columns = 10 + 8 * size;
        initBoard();
    }

    private void initBoard() {
        if (size == 0)
            setPreferredSize(new Dimension(10, 10));
        if (size == 1)
            setPreferredSize(new Dimension(18, 18));
        if (size == 2) {
            setPreferredSize(new Dimension(26, 26));
        }

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {

            var path = "C:\\Users\\dzava\\IdeaProjects\\Landmine\\src\\resources\\" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {

        int cell;

        var random = new Random();
        inGame = true;
        if (size == 0) {
            minesLeft = 10;
            allCells = 100;
        }
        if (size == 1) {
            minesLeft = 40;
            allCells = 324;
        }
        if (size == 2) {
            minesLeft = 99;
            allCells = 676;
        }

        int minesOverall = minesLeft;

        field = new Cell[allCells];

        for (int i = 0; i < allCells; i++) {

            field[i].condition = COVER_FOR_CELL;
        }

        statusbar.setText(Integer.toString(minesLeft));

        int i = 0;

        while (i < minesOverall) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position].condition != COVERED_MINE_CELL)) {

                int current_col = position % columns;
                field[position].condition = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - columns;
                    if (cell >= 0) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }

                    cell = position + columns - 1;
                    if (cell < allCells) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }
                }

                cell = position - columns;
                if (cell >= 0) {
                    if (field[cell].condition != COVERED_MINE_CELL) {
                        field[cell].condition += 1;
                    }
                }

                cell = position + columns;
                if (cell < allCells) {
                    if (field[cell].condition != COVERED_MINE_CELL) {
                        field[cell].condition += 1;
                    }
                }

                if (current_col < (columns - 1)) {
                    cell = position - columns + 1;
                    if (cell >= 0) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }
                    cell = position + columns + 1;
                    if (cell < allCells) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell].condition != COVERED_MINE_CELL) {
                            field[cell].condition += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {

        int current_col = j % columns;
        int cell;

        if (current_col > 0) {
            cell = j - columns - 1;
            if (cell >= 0) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + columns - 1;
            if (cell < allCells) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - columns;
        if (cell >= 0) {
            if (field[cell].condition > MINE_CELL) {
                field[cell].condition -= COVER_FOR_CELL;
                if (field[cell].condition == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + columns;
        if (cell < allCells) {
            if (field[cell].condition > MINE_CELL) {
                field[cell].condition -= COVER_FOR_CELL;
                if (field[cell].condition == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (columns - 1)) {
            cell = j - columns + 1;
            if (cell >= 0) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + columns + 1;
            if (cell < allCells) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell].condition > MINE_CELL) {
                    field[cell].condition -= COVER_FOR_CELL;
                    if (field[cell].condition == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < columns; i++) {

            for (int j = 0; j < columns; j++) {

                int cell = field[(i * columns) + j].condition;

                if (inGame && cell == MINE_CELL) {

                    inGame = false;
                }

                if (!inGame) {

                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }

                } else {

                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            statusbar.setText("Victory");

        } else if (!inGame) {
            statusbar.setText("Defeat");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {

                newGame();
                repaint();
            }

            if ((x < columns * CELL_SIZE) && (y < columns * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * columns) + cCol].condition > MINE_CELL) {

                        doRepaint = true;

                        if (field[(cRow * columns) + cCol].condition <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                field[(cRow * columns) + cCol].condition += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {

                            field[(cRow * columns) + cCol].condition -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                        }
                    }

                } else {

                    if (field[(cRow * columns) + cCol].condition > COVERED_MINE_CELL) {

                        return;
                    }

                    if ((field[(cRow * columns) + cCol].condition > MINE_CELL)
                            && (field[(cRow * columns) + cCol].condition < MARKED_MINE_CELL)) {

                        field[(cRow * columns) + cCol].condition -= COVER_FOR_CELL;
                        doRepaint = true;

                        if (field[(cRow * columns) + cCol].condition == MINE_CELL) {
                            inGame = false;
                        }

                        if (field[(cRow * columns) + cCol].condition == EMPTY_CELL) {
                            find_empty_cells((cRow * columns) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
