package MayerGame;

public class DiceThread extends Thread {
	public GameGui parent;

	public DiceThread(GameGui p) {
		parent = p;
	}

	public void run() {
		boolean thread_active = true;
		while(thread_active) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Error");
			}
			//System.out.println("Drawing dices...");
			parent.diceBoard.DrawDices();
		}
	}
}


