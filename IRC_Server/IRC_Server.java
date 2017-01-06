package IRC_Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IRC_Server implements Runnable{

	protected int          serverPort   = 8080;
	private int maxPlayers = 4;
	private int x = 0;
	protected ServerSocket[] serverSockets = new ServerSocket[maxPlayers];
	protected Socket[] clientSockets = new Socket[maxPlayers];
	protected boolean      isStopped    = false;
	protected Thread       runningThread= null;
	boolean gameFull = false;
	gameClass[] gameClasses = new gameClass[maxPlayers];
	InputStream[] inputStreams;
	BufferedReader[] BIS;
	OutputStream[] outputStreams;


	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSockets();
		while(!gameFull){
			try {
				clientSockets[x] = serverSockets[x].accept();
				inputStreams[x] = clientSockets[x].getInputStream();
				BIS[x] = new BufferedReader(new InputStreamReader(clientSockets[x].getInputStream()));
				outputStreams[x] = clientSockets[x].getOutputStream();
			} catch (IOException e) {
				if(isStopped()) {
					System.out.println("Server Stopped.") ;
					return;
				}
				throw new RuntimeException(
						"Error accepting client connection", e);
			}
			x = x+1;
			if (x == maxPlayers)
			{
				gameFull=true;
			}
		}
		while (!isStopped)
		{
			x = 0;
			try {
				String clientRequest = BIS[x].readLine();
				if (clientRequest.equals("Request Roll"))
				{
					outputStreams[x].write(gameClasses[x].rollDice());
					outputStreams[x].flush();
				}
				for (int i = 0; i < maxPlayers; i ++)
				{

				}
			} catch (IOException e)
			{
				System.out.println("Some sort of error");
			}

			for (int i = 0; i < maxPlayers; i ++)
			{

			}
		}
		System.out.println("Server Stopped.") ;
	}


	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			for (int i = 0; i<maxPlayers; i++)
			{
				serverSockets[i].close();
			}

		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSockets() {
		try {
			for (int i = 0; i<maxPlayers; i++)
			{
				serverSockets[i] = new ServerSocket(this.serverPort);
				gameClasses[i] = new gameClass();
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}
}