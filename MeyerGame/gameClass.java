package MeyerGame;

import java.util.concurrent.ThreadLocalRandom;

public class GameClass {
	int totalp1;
	private int dice1;
	private int dice2;

	// Rolls the two dices and returns the sum of the two dices as an integer. Roll 3 and 6 evaluates as 36
	public int rollDice() {
		String total;
		dice1 = ThreadLocalRandom.current().nextInt(1, 7);
		dice2 = ThreadLocalRandom.current().nextInt(1, 7);

		if (dice1 > dice2) {
			total = Integer.toString(dice1) + Integer.toString(dice2);

		} else if (dice2 > dice1) {
			total = Integer.toString(dice2) + Integer.toString(dice1);

		} else {
			total = Integer.toString(dice1) + Integer.toString(dice2);
		}
		totalp1 = Integer.parseInt(total);
		return totalp1;
	}

	// Returns true if player 1 beats player 2's roll, otherwise it returns false 
	public boolean beatRoll(int totalp1, int totalp2) {
		boolean p1Doubles = false;
		boolean p2Doubles = false;

		if (Math.floor(totalp1 / 10) == totalp1 % Math.floor(totalp1 / 10) * 10) {
			p1Doubles = true;
		}

		if (Math.floor(totalp2 / 10) == totalp2 % Math.floor(totalp2 / 10) * 10) {
			p2Doubles = true;
		}

		if (totalp1 == 21 && totalp2 != 21) {
			return true;
		}

		else if (totalp1 == 31 && (totalp2 != 21 && totalp2 != 31)) {
			return true;
		}

		else if (p1Doubles && p2Doubles) {
			if (Math.floor(totalp1 / 10) > Math.floor(totalp2 / 10)) {
				return true;
			} else {
				return false;
			}
		}

		else if (p1Doubles && !(p2Doubles || totalp2 == 21 || totalp2 == 31)) {
			return true;
		}

		else if (totalp1 > totalp2 && !((p2Doubles || totalp2 == 21 || totalp2 == 31))) {
			return true;
		}

		else {
			return false;
		}
	}

	public void notTrust(int totalp2) {
		totalp1 = rollDice();
		if(beatRoll(totalp1, totalp2)) {
			//TODO CODE THAT HANDLES PLAYER WINNING.
		} else {
			//TODO CODE THAT HANDLES PLAYER LOSING.
		}
	}

	public int getServerDice1() {
		return dice1-1;
	}

	public int getServerDice2() {
		return dice2-1;
	}

	public int getTotalp1() {
		return totalp1;
	}
}
