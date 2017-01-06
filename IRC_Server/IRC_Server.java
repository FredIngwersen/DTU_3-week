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

	public IRC_Server(int port){
		this.serverPort = port;
	}

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
		x = 0;
		while (!isStopped)
		{

			try {
				outputStreams[x].write("Start".getBytes());
				outputStreams[x].flush();
				String clientRequest = BIS[x].readLine();
				if (clientRequest.equals("Request Roll"))
				{
					outputStreams[x].write(gameClasses[x].rollDice());
					outputStreams[x].flush();
				}
				x = x +1;
				for (int i = x; i < maxPlayers; i ++)
				{
					for (int c = 0; c < maxPlayers; c++)
					{
						if (c == i)
						{
							outputStreams[c].write("yourTurn".getBytes());
						}
						else{
							outputStreams[c].write("wait".getBytes());
						}
					}

					String trustRoll = BIS[i].readLine();
					if (trustRoll.equals("true"))
					{
						gameClasses[i].rollDice();
						outputStreams[i].write(gameClasses[x].getDice1());
						outputStreams[i].flush();
						outputStreams[i].write(gameClasses[x].getDice2());
						outputStreams[i].flush();
						x = x+1;
					}
					else if (trustRoll.equals("false")) {
						if (i == 0) {
							i = maxPlayers;
						}
						for (int y = 0; y < maxPlayers; y++){
							outputStreams[y].write(gameClasses[i-1].getTotalp1());
						}
						x = x+1;
						break;
					}
					if (i == maxPlayers-1){
						i = 0;
					}
				}
			} catch (IOException e)
			{
				System.out.println("Some sort of error");
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