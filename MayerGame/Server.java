package MayerGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	int maxPlayers = 2;
	int x = 0;
	private int          serverPort;
	private ServerSocket serverSocket = null;
	private Thread       runningThread= null;
	private boolean isStopped = false;
	boolean gameFull = false;
	boolean first;
	private PlayerInstance[] threadArray = new PlayerInstance[maxPlayers];
	private BufferedReader[] inputStream = new BufferedReader[maxPlayers];
	boolean turnDone = true;

	public Server(int port)
	{
		this.serverPort = port;
	}

	public void run() {
		System.out.println("Server is running");
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		try {
			while(!gameFull){
				System.out.println("Waiting for players to fill up a game table");
				Socket clientSocket = null;
				try {
					clientSocket = this.serverSocket.accept();
				} catch (IOException e) {
					if(isStopped()) {
						System.out.println("Server Stopped.") ;
						return;
					}
					throw new RuntimeException("Error accepting client connection", e);
				}

				threadArray[x] = new PlayerInstance(clientSocket, "Multithreaded Server", this);
				threadArray[x].start();
				inputStream[x] = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				System.out.println("Thread [" + x + "] started");
				x = x+1;
				if (x == maxPlayers){
					gameFull = true;
				}
			}
			x = 0;
			while(isStopped() == false)
			{

				first = true;
				for (int i = x; i < maxPlayers; i ++) {

					threadArray[i].updateFirst(first);		

					first = false;

					while (turnDone){
						String clientString = inputStream[i].readLine();     // <--- Her er linjerne der fucker alt op.
						System.out.println(clientString);
					}
						for (int c = 0; c < maxPlayers; c++) {
							if (c == i) {

								threadArray[c].updateTurn(true);

							} else {

								threadArray[c].updateTurn(false);

							}
						}
					if (i == maxPlayers-1)
					{
						i = 0;
					}
				}
			}
			stop();
			System.out.println("Server Stopped.") ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void prevRoll() {
		int prevRoll = threadArray[x].game.getTotalp1();
		for (int c = 0; c < maxPlayers; c++) {
			threadArray[c].prevTotal = prevRoll;
		}
	}
	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port" + serverPort, e);
		}
	}
	public void updateTurn() {
		turnDone = false;
	}
}
