package ru.spiritofsea.sudoku;

/*
 *            SUDOKU SOLVER
 *    Author: Dzmitry Padabed
 *    Git: https://github.com/spiritofsea
 *
 *    Feel free to use that code for non-commercial usage.
 *
 */



import java.util.Arrays;

class Sudoku {
    public final int[][] board;
    public final int x_size, y_size, square_y, square_x, num_limit;

    public Sudoku(int[][] board) {
        this.board = board;
        this.x_size = 9;
        this.y_size = 9;
        this.square_x = 3;
        this.square_y = 3;
        this.num_limit = 9;
    }

    public Sudoku(int[][] board, int y, int x, int square_x, int square_y, int lim) {
        this.board = board;
        this.y_size = y;
        this.x_size = x;
        this.square_y = square_y;
        this.square_x = square_x;
        this.num_limit = lim;
    }

    public String toString() {                                              // just pretty string representation of board
        return "  " + Arrays.deepToString(this.board)
                .replace(" ", "  ")
                .replace("],", "\n")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
    }

    public int get(int y, int x) {
        return this.board[y][x];
    }               // simple getter method

    public int[] get_row(int y) {
        return this.board[y];
    }                   // getting board row as array

    public int[] get_column(int x) {                                        // getting board column as array
        int[] column = new int[this.y_size];

        for (int i = 0; i < this.y_size; i++) {
            column[i] = this.board[i][x];
        }

        return column;
    }

    public int[] get_square(int y, int x) {                                 // getting current number "square" of nums
        int shift_x;
        int shift_y;
        int[] square = new int[this.square_x * this.square_y];
        int counter = 0;
        shift_x = (int) Math.ceil(((double) x + 1) / this.square_x) - 1;
        shift_y = (int) Math.ceil(((double) y + 1) / this.square_y) - 1;

        for (int i = 0; i < this.square_y; i++) {
            for (int a = 0; a < this.square_x; a++) {
                square[counter] = this.get(i + this.square_y * shift_y, a + this.square_x * shift_x);
                counter++;
            }
        }
        return square;

    }

    public void set(int y, int x, int digit) {                              // default setter
        this.board[y][x] = digit;
    }

    public boolean solve(int y, int x) throws InterruptedException {        // solving method
        int val = this.get(y, x);                                           // get current value
        boolean flag = false;
        if (val == 0) {                                                     // mark is current digit is part of puzzle
            val = 1;
        } else {
            val++;
            flag = true;
        }
        if (!flag) {                                                        // if cell is empty by default.
            Thread.sleep(0);
            for (int i = val; i <= this.num_limit; i++) {
                if (check_condition(y, x, i)) {                             // if number meets sudoku conditions,
                    if (x < this.x_size - 1) {                              // move to next cell.
                        set(y, x, i);
                        if (!solve(y, x + 1)) {                          // if next cell returned false, we continue
                            set(y, x, 0);                             // answer search.
                        } else {
                            return true;                                    // "true" reachable only if last cell reported "true"
                        }
                    } else if (x == this.x_size - 1 && y < this.y_size - 1) { // ..if EOL, set cursor to next line
                        set(y, x, i);
                        if (!solve(y + 1, 0)) {
                            set(y, x, 0);
                        } else {
                            return true;
                        }
                    } else if (x == this.x_size - 1 && y == this.y_size - 1) { // if last cell is correct, we assume that
                        set(y, x, i);                                       // everything before was correct and start
                        return true;                                        // recursion exit chain.
                    }
                } else if (i == this.num_limit) {                           // if reached 9, return "false" to previous
                    return false;                                           // iteration, marking that previous cell was
                } else {                                                    // wrong.
                    ;
                }
            }
        } else {                                                            // if number was set by puzzle, we don't need
            if (x < this.x_size - 1) {                                      // to change it.
                return solve(y, x + 1);
            } else if (x == this.x_size - 1 && y < this.y_size - 1) {
                return solve(y + 1, 0);
            } else {
                return true;
            }
        }
        return false;

    }

    public boolean check_condition(int y, int x, int digit) {               // check does digit fits sudoku rules
        return Arrays.stream(get_column(x)).noneMatch(n -> n == digit) &&
                Arrays.stream(get_row(y)).noneMatch(n -> n == digit) &&
                Arrays.stream(get_square(y, x)).noneMatch(n -> n == digit);
    }
}

public class Main {

    public static void main(String[] args) {
        int[][] sample_board = {                                            // TODO: add file/graphical input
                {2, 0, 0, 3, 0, 0, 0, 0, 0},
                {8, 0, 4, 0, 6, 2, 0, 0, 3},
                {0, 1, 3, 8, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 2, 0, 3, 9, 0},
                {5, 0, 7, 0, 0, 0, 6, 2, 1},
                {0, 3, 2, 0, 0, 6, 0, 0, 0},
                {0, 2, 0, 0, 0, 9, 1, 4, 0},
                {6, 0, 1, 2, 5, 0, 8, 0, 9},
                {0, 0, 0, 0, 0, 1, 0, 0, 2}};
        Sudoku sampleGame = new Sudoku(sample_board);                       // TODO: GUI
        System.out.println(sampleGame);
        try {                                                               // we could delete try/catch block if we
            sampleGame.solve(0, 0);                                   // don't need thread.sleep() functionality
        } catch (InterruptedException e) {
            System.out.println("Exception!");
        }
        System.out.println("\n\n" + sampleGame);
    }
}
