
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand;
        if (args.length > 1) {
			Integer seed = Integer.valueOf(args[1]);
			rand = new Random(seed);
		} else {
			rand = new Random();
		}

		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		do {
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) >= 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}
}
