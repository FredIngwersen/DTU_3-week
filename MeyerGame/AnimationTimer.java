package MeyerGame;

public class AnimationTimer extends Thread {
	private DiceBoard db;
	private int delay;


	public  AnimationTimer(DiceBoard db, int delay) {
		this.db = db;
		this.delay = delay;
	}

	public void run() {
		int i = 0;
		// Dices is shuffled when a true boolean value is recieved for a roll. 
		while (true) { 
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.out.println("AnimationTimer Error");
			}
		}
	}
}
