package MayerGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDouble{
	public static void main(String[] args) throws IOException{
		int serverPort = 9000;
		int maxPlayers = 4;
		int x = 0;
		ServerSocket serverSockets = new ServerSocket(serverPort);
		Socket[] clientSockets = new Socket[maxPlayers];
		boolean isStopped = false;
		boolean gameFull = false;
		gameClass[] gameClasses = new gameClass[maxPlayers];
		InputStream[] inputStreams = new InputStream[maxPlayers];
		BufferedReader[] BIS = new BufferedReader[maxPlayers];
		OutputStream[] outputStreams = new OutputStream[maxPlayers];

		openServerSockets(maxPlayers, clientSockets, gameClasses, serverPort);
		while(!gameFull){
			try {
				clientSockets[x] = serverSockets.accept();
				inputStreams[x] = clientSockets[x].getInputStream();
				BIS[x] = new BufferedReader(new InputStreamReader(clientSockets[x].getInputStream()));
				outputStreams[x] = clientSockets[x].getOutputStream();
			} catch (IOException e) {
				if(isStopped) {
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
		stop(isStopped, maxPlayers, serverSockets);
		System.out.println("Server Stopped.") ;
	}

	public static void stop(Boolean isStopped, int maxPlayers, ServerSocket serverSockets){
		isStopped = true;
		try {
			for (int i = 0; i < maxPlayers; i++)
			{
				serverSockets.close();
			}

		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}
	public static void openServerSockets(int maxPlayers, Socket[] clientSockets, gameClass[] gameClasses, int serverPort) throws IOException {
		for (int i = 0; i < maxPlayers; i++)
		{
			clientSockets[i] = new Socket();
			gameClasses[i] = new gameClass();
		}
	}
}