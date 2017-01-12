package MeyerGame;

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

	private ServerSocket chatServer;
	private ChatThread[] playerChat = new ChatThread[maxPlayers];
	private ServerSocket chatSocket;

	public Server(int port)
	{
		this.serverPort = port;
	}

	public void run() {
		try {
			chatSocket = new ServerSocket(8081);
			System.out.println("connected to chat");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Server is running");
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		try {
			while(!gameFull){
				System.out.println("Waiting for players to fill up a game table");
				Socket clientSocket = null;
				Socket chatSocket = null;
				try {
					clientSocket = this.serverSocket.accept();
					chatSocket = this.chatSocket.accept();
				} catch (IOException e) {
					if(isStopped()) {
						System.out.println("Server Stopped.") ;
						return;
					}
					throw new RuntimeException("Error accepting client connection", e);
				}
				playerChat[x] = new ChatThread(chatSocket); playerChat[x].start();
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
				for (int i = x; i < maxPlayers; i ++ ) {

					threadArray[i].updateFirst(first);
					threadArray[i].updateTurn(true);
					first = false;
					while (!doneWaiting){
						try{Thread.sleep(0);}
						catch (InterruptedException exc)
						{

						}
					}
					System.out.println("turnDone");

					x = x + 1;
					if (x == maxPlayers)
					{
						x = 0;
					}
					for (int c = 0; c < maxPlayers; c++ ) {
						if (c == x) {
							threadArray[c].updateTurn(true);
							System.out.println("started next turn");
						} else {
							threadArray[c].updateTurn(false);
							System.out.println("ended all other turns");
						}
					}

					threadArray[i].updateFirst(first);
					doneWaiting = false;

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
		int v = x;
		if (v == 0) {
			v = maxPlayers;
		}
		int prevRoll = threadArray[v-1].game.getTotalp1();
		for (int c = 0; c < maxPlayers; c++) {
			threadArray[c].prevTotal = prevRoll;
		}
		first = true;
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
