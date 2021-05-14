package ru.spiritofsea.sudoku;

import java.util.Arrays;

public class Sudoku {
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
        return "\n  " + Arrays.deepToString(this.board)
                .replace(" ", "  ")
                .replace("],", "\n")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "") + "\n";
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

    public void solveBoard() {
        try {
            this.solve(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean solve(int y, int x) throws InterruptedException {        // solving method
        int val = this.get(y, x);                                           // get current value

        if (val == 0) {                                                     // if cell is empty by default.
            val++;
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
