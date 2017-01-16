package MeyerGame;

public class DiceThread extends Thread {
	public GameGUI parent;

	public DiceThread(GameGUI p) {
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
			parent.diceBoard.DrawDices();
		}
	}
}


