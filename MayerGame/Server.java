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
	boolean doneWaiting = false;
	private PlayerInstance[] threadArray = new PlayerInstance[maxPlayers];
	private BufferedReader[] inputStream = new BufferedReader[maxPlayers];

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
					threadArray[i].updateTurn(true);

					first = false;
					while (!doneWaiting){
						try{Thread.sleep(0);}
						catch (InterruptedException exc)
						{

						}
					}
					doneWaiting = false;
					System.out.println("turnDone");
					for (int c = 0; c < maxPlayers; c++) {
						if (i+1 == maxPlayers)
						{
							i = -1;
						}
						if (c == i+1) {
							threadArray[c].updateTurn(true);
							System.out.println("turn updateded");

						} else {
							threadArray[c].updateTurn(false);
							System.out.println("turn updated false");

						}
					}
					if (x == maxPlayers-1)
					{
						x = -1;
					}
					x = x + 1;
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
	public void turnDoneServer() {
		doneWaiting = true;
	}

}
