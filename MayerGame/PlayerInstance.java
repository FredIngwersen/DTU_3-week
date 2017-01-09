package MayerGame;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.net.Socket;


public class PlayerInstance implements Runnable{

	protected Socket clientSocket  = null;
	protected String serverText    = null;
	protected boolean clientActive = true;
	
	public PlayerInstance(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText   = serverText;
	}

	public void run() {
		try {
			InputStream input  = clientSocket.getInputStream();
			BufferedReader BIS = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			OutputStream output = clientSocket.getOutputStream();
			long time = System.currentTimeMillis();
			output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
					this.serverText + " - " +
					time +
					"").getBytes());

			System.out.println("Request processed: " + time);
			while(clientActive){
				
				BufferedReader bir = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());

				String request = bir.readLine();
			}
			

			output.close();
			input.close();
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
	}

}