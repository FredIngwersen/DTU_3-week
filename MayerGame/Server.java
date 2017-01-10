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
	private ServerThread[] threadArray = new ServerThread[maxPlayers];
	boolean turnDone = true;

	public Server(int port)
	{
		this.serverPort = port;
	}

	public void run(){
		System.out.println("its running server");
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while(!gameFull){
			System.out.println("gamenotfullloop");
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
			threadArray[x] = new ServerThread(clientSocket, "Multithreaded Server", this);
			threadArray[x].start();
			System.out.println("we started ThreadArray" + x);
			x = x+1;
			System.out.println(x);
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

				while (turnDone)
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
