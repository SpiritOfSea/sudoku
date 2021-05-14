package ru.spiritofsea.sudoku;

/*
 *            SUDOKU SOLVER
 *    Author: Dzmitry Padabed
 *    Git: https://github.com/spiritofsea
 *
 *    Feel free to use that code for non-commercial usage.
 *
 */


import java.io.File;
import java.util.Scanner;


public class Main {

    public static int[][] loadBoardFromFile(String path, int y, int x) {
        int[][] board = new int[y][x];

        try {
            String pathf = new File("").getAbsolutePath() + "\\" + path;
            File file = new File(pathf);
            Scanner sc = new Scanner(file);

            for (int i = 0; i < y; i++) {
                for (int a = 0; a < x; a++) {
                    board[i][a] = sc.nextInt();
                }
            }
        } catch (Exception e) {
            System.out.println("Error occured: ");
            e.printStackTrace();
        }

        return board;
    }

    public static void main(String[] args) {
        int[][] sample_board = {                                           
                {2, 0, 0, 3, 0, 0, 0, 0, 0},
                {8, 0, 4, 0, 6, 2, 0, 0, 3},
                {0, 1, 3, 8, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 2, 0, 3, 9, 0},
                {5, 0, 7, 0, 0, 0, 6, 2, 1},
                {0, 3, 2, 0, 0, 6, 0, 0, 0},
                {0, 2, 0, 0, 0, 9, 1, 4, 0},
                {6, 0, 1, 2, 5, 0, 8, 0, 9},
                {0, 0, 0, 0, 0, 1, 0, 0, 2}};

        Sudoku sampleGame = new Sudoku(loadBoardFromFile("board.txt", 9, 9));

        System.out.println(sampleGame);

        sampleGame.solveBoard();

        System.out.println(sampleGame);

    }
}
