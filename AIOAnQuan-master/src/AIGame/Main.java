package AIGame;

import java.io.*;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		int turn = 0;
		while (true) {
			ArrayList<Integer> test = Minimax.readFile();
			if (test.get(0) != turn) {
				turn = test.get(0);
				Board board = new Board(test);
				int[] next = Minimax.minimax(board, 5, 1);
				System.out.println("next: " +  next[1] + next[2]);
				Minimax.writeFile(next);
			}
		}
	}
}
