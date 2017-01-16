package MeyerGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread extends Thread {
	private Socket client;
	BufferedReader in = null;
	PrintWriter out = null;
	Server parent;

	//Constructor
	public ChatThread(Socket client, Server parent) {
		this.client = client;
		this.parent = parent;
	}

	public void run() {
		String text;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);			
		} catch (IOException e) {
			System.out.println("ERROR - Can't setup chat input and output stream");
		}
		try {
			text = in.readLine();
			while(text != null) {
				try {
					// Reads input and returns it to clients
					parent.sendToAll(text);
					text = in.readLine();
				} catch(IOException e) {
					System.out.println("ERROR: chatThread");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) throws IOException {
		out.println(message);
		out.flush();
	}
}