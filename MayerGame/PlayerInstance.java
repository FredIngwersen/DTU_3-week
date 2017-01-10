package MayerGame;

/**
 * Created by William Ben Embarek on 10/01/2017.
 */

import java.io.*;
import java.net.Socket;

public class PlayerInstance extends Thread {

	protected Socket clientSocket = null;
	protected String serverText = null;
	boolean yourTurn = false;
	int prevTotal;
	boolean turnDone = false;
	boolean first = false;
	gameClass game = new gameClass();
	Server s;

	public PlayerInstance(Socket clientSocket, String serverText, Server p) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		s = p;
	}

	public void run() {
		try {
			BufferedReader bir = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
			System.out.println("Init variables");
			while (1 == 1) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {}
				//System.out.println(clientSocket.toString() + " " + yourTurn);
				while (yourTurn) {
					System.out.println("It's your turn");
					try {
						if (first) {
							pw.println("Start");
							pw.flush();
						} else {
							pw.println("wait");
							pw.flush();
						}
						String clientRequest = bir.readLine();
						if (clientRequest.equals("Request Roll")) {
							rollDice(game, pw);
						}
						String trustRoll = bir.readLine();
						if (trustRoll.equals("true")) {
							rollDice(game, pw);
						} else if (trustRoll.equals("false")) {
							s.prevRoll();
							pw.println(prevTotal);
							pw.flush();
						}
						s.updateTurn();
					} catch (IOException e) {
						System.out.println("Some sort of error");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("hello");
		}
	}
	public void rollDice(gameClass game, PrintWriter output) throws IOException
	{
		game.rollDice();
		output.println(game.getDice1());
		output.flush();
		output.println(game.getDice2());
		output.flush();
	}
	public void updateTurn(boolean currentTurn)
	{
		yourTurn = currentTurn;
	}
	public void updateFirst(boolean first)
	{
		this.first = first;
	}

}



