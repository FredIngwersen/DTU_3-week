package IRC_Server;

public class runnit {

	public static void main(String[] args) {
		IRC_Server server = new IRC_Server(9000);
		new Thread(server).start();

		
	}

}
