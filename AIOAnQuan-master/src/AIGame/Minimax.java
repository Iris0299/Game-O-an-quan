package AIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Minimax {
    public static ArrayList<Integer> readFile() {
        ArrayList<Integer> result = new ArrayList<>();

        try (
                Scanner s = new Scanner(new FileReader("/home/quochuy/Downloads/AI6 (1)/src/AIGame/nhap.txt"))) {
            while (s.hasNext()) {
                result.add(s.nextInt());
            }
            return result;
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return  null;
    }

    public static void writeFile(int[] next) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/home/quochuy/Downloads/AI6 (1)/src/AIGame/xuat.txt"));
            writer.write(String.valueOf(next[0]));
            writer.write(String.valueOf(next[1]));
            writer.write(String.valueOf(next[2]));
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static int[] minimax(Board board, int depth, int player) {
        if (player == 1) {
            if (board.kiemTraHetQuan(player)) {
                board.raiQuan(player);
            }
        }
        Square[] squares = board.getSquares();
        int[] valueRoot = new int[14];
        for (int i = 0; i < 14; i++) {
            valueRoot[i] = squares[i].getValue();
        }
        ArrayList<Integer> moves = generateMoves(board, player);
        // nguoi choi 1 la max, 2 la min
        int bestScore = (player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestLocation = -1;
        int action = 0;
        // tra ve ham danh gia
        if (depth == 0 || board.finish()) {
            if (board.finish()) {
                board.addScore(1);
                board.addScore(2);
            }
            bestScore = squares[12].getValue() - squares[13].getValue();
        } else {
            for (int i = 0; i < moves.size(); i++) {
                // thuc hien ham left or right
                for (int k = 1; k < 3; k++) {
                    int score = 0;
                    int location = board.action(k, moves.get(i));
                    if (board.isEat(location)) {
                        score = board.eatting(k, location);
                    }

                    if (player == 1) { // max player
                        board.setScoreComputer(score);
                        currentScore = minimax(board, depth - 1, 2)[0];
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestLocation = moves.get(i);
                            action = k;
                        }
                    } else { // min player
                        board.setScorePlayer(score);
                        currentScore = minimax(board, depth - 1, 1)[0];
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestLocation = moves.get(i);
                            action = k;
                        }
                    }

                    // khoi phuc trang thai ban co cu
                    for (int j = 0; j < 14; j++) {
                        if (j == 0 || j == 6) {
                            squares[j] = new Square(j, valueRoot[j], true);
                        } else {
                            squares[j] = new Square(j, valueRoot[j], false);
                        }
                    }
                }
            }
        }
        return new int[] { bestScore, bestLocation, action };
    }

    // so buoc tiep theo co the co
    public static ArrayList<Integer> generateMoves(Board board, int player) {
        Square[] squares = board.getSquares();
        ArrayList<Integer> result = new ArrayList<>();
        if (board.finish()) {
            return result;
        }
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (squares[i].getValue() != 0) {
                    result.add(squares[i].getViTri());
                }
            }
        } else {
            for (int i = 7; i < 12; i++) {
                if (squares[i].getValue() != 0) {
                    result.add(squares[i].getViTri());
                }
            }
        }
        return result;
    }

}
