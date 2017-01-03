package obligatory_2;

public class PrimeThread extends Thread {
	public SimpleGui parent;
	static int prime = 3;

	public PrimeThread(SimpleGui p) {
		parent = p;
	}
	public static void restart() {
		prime = 3;
	}

	public void run() {
		boolean runThread = true;
		boolean isPrime = false;
		parent.textArea.append("2 ");
		while (runThread) {
			for (prime = 3; prime > 0; prime++) {
				try {
					sleep(100);
				}catch (InterruptedException e) {
					System.out.println("Error");
				}

				for (int j = 2; j < prime; j++) {
					if (!(prime%j == 0)) {
						isPrime = true;
					} else {
						isPrime = false;
						break;
					}
				}

				if (isPrime) {
					parent.textArea.append(Integer.toString(prime) + " ");
				}
			}
		}
	}
}