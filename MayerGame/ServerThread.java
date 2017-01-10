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
	boolean first = true;
	gameClass game = new gameClass();
	Server s;

	public ServerThread(Socket clientSocket, String serverText, Server p) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		s = p;
	}

	public void run() {
		try {
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			BufferedReader bir = new BufferedReader(new InputStreamReader(input));

			while (yourTurn) {
				try {
					if (first) {
						output.write("Start".getBytes());
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

				} catch (IOException e) {
					System.out.println("Some sort of error");
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



