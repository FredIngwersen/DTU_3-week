package IRC_Server;

<<<<<<< HEAD
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
=======
import java.io.*;
>>>>>>> ac0feac25ecfd3a9261da4a1b9312de58e4d6ba3
import java.net.Socket;


public class WorkerRunnable implements Runnable{

<<<<<<< HEAD
	protected Socket clientSocket  = null;
	protected String serverText    = null;
	protected boolean clientActive = true;

=======
	protected Socket clientSocket = null;
	protected String serverText   = null;
>>>>>>> ac0feac25ecfd3a9261da4a1b9312de58e4d6ba3
	public WorkerRunnable(Socket clientSocket, String serverText) {
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
<<<<<<< HEAD
			System.out.println("Request processed: " + time);
			while(clientActive){
				
				BufferedReader bir = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());

				String request = bir.readLine();
			}
			
=======

>>>>>>> ac0feac25ecfd3a9261da4a1b9312de58e4d6ba3
			output.close();
			input.close();
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
	}

}