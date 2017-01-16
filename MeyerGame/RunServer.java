package MeyerGame;

public class RunServer {

	public static void main(String[] args) {
		Server server = new Server(8080);
		new Thread(server).start();
	}
}