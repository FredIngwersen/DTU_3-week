package IRC_Server;

public class runnit {

	public static void main(String[] args) {
		IRC_Server server = new IRC_Server(9000);
		new Thread(server).start();

		try {
		    Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("Stopping Server");
		server.stop();
	}

}
