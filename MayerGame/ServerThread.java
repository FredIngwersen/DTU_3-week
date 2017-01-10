package MayerGame;

/**
 * Created by William Ben Embarek on 10/01/2017.
 */

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

	protected Socket clientSocket = null;
	protected String serverText = null;
	boolean yourTurn = false;
	int prevTotal;
	boolean turnDone = false;
	boolean first = false;
	gameClass game = new gameClass();
	Server s;

	public ServerThread(Socket clientSocket, String serverText, Server p) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		s = p;
	}

	public void run() {
		System.out.println("We made it");
		try {
			System.out.println("Tried");
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			BufferedReader bir = new BufferedReader(new InputStreamReader(input));
			System.out.println("Init variables");
			while (1 == 1) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {}
				System.out.println(yourTurn);
				while (yourTurn) {
					System.out.println("Its our turn");
					try {
						if (first) {
							output.write("Start".getBytes());
							output.flush();
						} else {
							output.write("wait".getBytes());
							output.flush();
						}
						String clientRequest = bir.readLine();
						if (clientRequest.equals("Request Roll")) {
							rollDice(game, output);
						}
						String trustRoll = bir.readLine();
						if (trustRoll.equals("true")) {
							rollDice(game, output);
						} else if (trustRoll.equals("false")) {
							s.prevRoll();
							output.write(prevTotal);
							output.flush();
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
	public void rollDice(gameClass game, OutputStream output)
	{
		try {
			game.rollDice();
			output.write(game.getDice1());
			output.flush();
			output.write(game.getDice2());
			output.flush();
		}
		catch (IOException e)
		{
			System.out.println("error");
		}
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



