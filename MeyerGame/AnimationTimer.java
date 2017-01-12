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
		while (true) { //shuffle terningen når den modtager true boolean værdi for roll.
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}
	

}
