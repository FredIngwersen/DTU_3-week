package obligatory_2;

public class CustomThread extends Thread {
	public SimpleGui parent;

	public CustomThread(SimpleGui p) {
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
			parent.customComponent.DrawSomething();
		}
	}
}