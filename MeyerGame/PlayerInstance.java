package MeyerGame;

import java.io.*;
import java.net.Socket;

public class PlayerInstance extends Thread {
	protected Socket clientSocket = null;
	protected String serverText = null;
	boolean yourTurn = false;
	int prevTotal;
	boolean turnDone = false;
	boolean first = false;
	GameClass game = new GameClass();
	Server s;
	PrintWriter pw;
	boolean started = false;

	public PlayerInstance(Socket clientSocket, String serverText, Server p) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		s = p;
	}

	public void run() {
		try {
			BufferedReader bir = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
			System.out.println("Init variables");

			while (1 == 1) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException cvd) {
				}

				if (!started) {
					if (s.gameFull) {
						pw.println("SGame");
						pw.flush();
						started = true;
					}
				}

				while (yourTurn) {
					System.out.println("It's your turn" + clientSocket.getPort());
					try {
						System.out.println(first);
						
						if (first) {
							System.out.println("output start");
							bos.write("Start \n".getBytes());
							bos.flush();
							
						} else {
							System.out.println("output wait");
							bos.write("norm \n".getBytes());
							bos.flush();
						}
						System.out.println("reading request");
						String clientRequest = bir.readLine();
						System.out.println(clientRequest);
						
						if (clientRequest.contains("RR")) {
							System.out.println("recieved dice request");
							rollDice(game, pw);
							updateTurn(false);
							s.turnDoneServer();
							
						} else if (clientRequest.contains("true")) {
							rollDice(game, pw);
							updateTurn(false);
							s.turnDoneServer();
							
						} else if (clientRequest.contains("false")) {
							s.prevRoll();
							updateTurn(false);
							s.roundDone();
							s.turnDoneServer();
						}
					} catch (IOException e) {
						System.out.println("Some sort of error");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("hello");
		}
	}

	public void rollDice(GameClass game, PrintWriter output) throws IOException {
		game.rollDice();
		output.println(game.getServerDice1());
		output.flush();
		output.println(game.getServerDice2());
		output.flush();
	}

	public void updateTurn(boolean currentTurn) {
		yourTurn = currentTurn;
	}

	public void updateFirst(boolean first) {
		this.first = first;
	}

	public boolean getTurnDone() {
		return turnDone;
	}
}